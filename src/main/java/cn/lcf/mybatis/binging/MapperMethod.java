package cn.lcf.mybatis.binging;

import cn.lcf.mybatis.cursor.Cursor;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.SqlCommandType;
import cn.lcf.mybatis.reflection.ParamNameResolver;
import cn.lcf.mybatis.reflection.TypeParameterResolver;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.ResultHandler;
import cn.lcf.mybatis.session.RowBounds;
import cn.lcf.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : lichaofeng
 * @date :2023/12/1 10:55
 * @description :
 * @modyified By:
 */
public class MapperMethod {
    private final SqlCommand command;
    private final MethodSignature method;


    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        switch (command.getType()) {
//            case INSERT: {
//                Object param = method.convertArgsToSqlCommandParam(args);
//                result = rowCountResult(sqlSession.insert(command.getName(), param));
//                break;
//            }
//            case UPDATE: {
//                Object param = method.convertArgsToSqlCommandParam(args);
//                result = rowCountResult(sqlSession.update(command.getName(), param));
//                break;
//            }
//            case DELETE: {
//                Object param = method.convertArgsToSqlCommandParam(args);
//                result = rowCountResult(sqlSession.delete(command.getName(), param));
//                break;
//            }
            case SELECT:
//                if (method.returnsVoid() && method.hasResultHandler()) {
//                    executeWithResultHandler(sqlSession, args);
//                    result = null;
//                } else if (method.returnsMany()) {
                    result = executeForMany(sqlSession, args);
//                } else if (method.returnsMap()) {
//                    result = executeForMap(sqlSession, args);
//                } else if (method.returnsCursor()) {
//                    result = executeForCursor(sqlSession, args);
//                } else {
//                    Object param = method.convertArgsToSqlCommandParam(args);
//                    result = sqlSession.selectOne(command.getName(), param);
//                    if (method.returnsOptional()
//                            && (result == null || !method.getReturnType().equals(result.getClass()))) {
//                        result = Optional.ofNullable(result);
//                    }
//                }
                break;
//            case FLUSH:
//                result = sqlSession.flushStatements();
//                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
            throw new RuntimeException("Mapper method '" + command.getName()
                    + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
        }
        return result;
    }


    private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
        List<E> result;
        Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.selectList(command.getName(), param, rowBounds);
        } else {
            result = sqlSession.selectList(command.getName(), param);
        }
        return result;
    }

    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new RuntimeException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }



    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass,
                    configuration);
//            if (ms == null) {
//                if (method.getAnnotation(Flush.class) != null) {
//                    name = null;
//                    type = SqlCommandType.FLUSH;
//                } else {
//                    throw new RuntimeException("Invalid bound statement (not found): "
//                            + mapperInterface.getName() + "." + methodName);
//                }
//            } else {
                name = ms.getId();
//                type = ms.getSqlCommandType();
                type = SqlCommandType.SELECT;
                if (type == SqlCommandType.UNKNOWN) {
                    throw new RuntimeException("Unknown execution method for: " + name);
                }
//            }
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }

        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {
            String statementId = mapperInterface.getName() + "." + methodName;
            if (configuration.hasStatement(statementId)) {
                return configuration.getMappedStatement(statementId);
            } else if (mapperInterface.equals(declaringClass)) {
                return null;
            }
            for (Class<?> superInterface : mapperInterface.getInterfaces()) {
                if (declaringClass.isAssignableFrom(superInterface)) {
                    MappedStatement ms = resolveMappedStatement(superInterface, methodName,
                            declaringClass, configuration);
                    if (ms != null) {
                        return ms;
                    }
                }
            }
            return null;
        }
    }

    public static class MethodSignature {

        private final boolean returnsMany;
        private final boolean returnsMap;
        private final boolean returnsVoid;
        private final boolean returnsCursor;
        private final boolean returnsOptional;
        private final Class<?> returnType;
        private final String mapKey;
        private final Integer resultHandlerIndex;
        private final Integer rowBoundsIndex;
        private final ParamNameResolver paramNameResolver;

        public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }
            this.returnsVoid = void.class.equals(this.returnType);
            this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
            this.returnsCursor = Cursor.class.equals(this.returnType);
            this.returnsOptional = Optional.class.equals(this.returnType);
            this.mapKey = getMapKey(method);
            this.returnsMap = this.mapKey != null;
            this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
            this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
            this.paramNameResolver = new ParamNameResolver(configuration, method);
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.getNamedParams(args);
        }

        public boolean hasRowBounds() {
            return rowBoundsIndex != null;
        }

        public RowBounds extractRowBounds(Object[] args) {
            return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
        }

        public boolean hasResultHandler() {
            return resultHandlerIndex != null;
        }

        public ResultHandler extractResultHandler(Object[] args) {
            return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public boolean returnsMany() {
            return returnsMany;
        }

        public boolean returnsMap() {
            return returnsMap;
        }

        public boolean returnsVoid() {
            return returnsVoid;
        }

        public boolean returnsCursor() {
            return returnsCursor;
        }

        /**
         * return whether return type is {@code java.util.Optional}.
         *
         * @return return {@code true}, if return type is {@code java.util.Optional}
         * @since 3.5.0
         */
        public boolean returnsOptional() {
            return returnsOptional;
        }

        private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
            Integer index = null;
            final Class<?>[] argTypes = method.getParameterTypes();
            for (int i = 0; i < argTypes.length; i++) {
                if (paramType.isAssignableFrom(argTypes[i])) {
                    if (index == null) {
                        index = i;
                    } else {
                        throw new RuntimeException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
                    }
                }
            }
            return index;
        }

        public String getMapKey() {
            return mapKey;
        }

        private String getMapKey(Method method) {
//            String mapKey = null;
//            if (Map.class.isAssignableFrom(method.getReturnType())) {
//                final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
//                if (mapKeyAnnotation != null) {
//                    mapKey = mapKeyAnnotation.value();
//                }
//            }
//            return mapKey;
            return null;
        }
    }

}