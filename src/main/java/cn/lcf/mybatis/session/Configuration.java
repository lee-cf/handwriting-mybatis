package cn.lcf.mybatis.session;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.datasource.druid.DruidDataSourceFactory;
import cn.lcf.mybatis.executor.Executor;
import cn.lcf.mybatis.executor.SimpleExecutor;
import cn.lcf.mybatis.executor.parameter.ParameterHandler;
import cn.lcf.mybatis.executor.resultset.DefaultResultSetHandler;
import cn.lcf.mybatis.executor.resultset.ResultSetHandler;
import cn.lcf.mybatis.executor.statement.PreparedStatementHandler;
import cn.lcf.mybatis.executor.statement.SimpleStatementHandler;
import cn.lcf.mybatis.executor.statement.StatementHandler;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.Environment;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.ResultMap;
import cn.lcf.mybatis.plugin.InterceptorChain;
import cn.lcf.mybatis.reflection.DefaultReflectorFactory;
import cn.lcf.mybatis.reflection.MetaObject;
import cn.lcf.mybatis.reflection.ReflectorFactory;
import cn.lcf.mybatis.reflection.factory.DefaultObjectFactory;
import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import cn.lcf.mybatis.reflection.wrapper.ObjectWrapperFactory;
import cn.lcf.mybatis.scripting.LanguageDriver;
import cn.lcf.mybatis.scripting.LanguageDriverRegistry;
import cn.lcf.mybatis.scripting.xmltags.XMLLanguageDriver;
import cn.lcf.mybatis.session.defaults.DefaultSqlSession;
import cn.lcf.mybatis.transaction.Transaction;
import cn.lcf.mybatis.transaction.jdbc.JdbcTransactionFactory;
import cn.lcf.mybatis.type.TypeAliasRegistry;
import cn.lcf.mybatis.type.TypeHandlerRegistry;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lichaofeng
 * @date :2023/11/17 17:25
 * @description :
 * @modyified By:
 */
@Data
public class Configuration {
    //环境
    protected Environment environment;
    protected boolean useColumnLabel = true;

    protected boolean useActualParamName = true;

    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);
    protected final InterceptorChain interceptorChain = new InterceptorChain();

    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

    protected final Map<String, ResultMap> resultMaps = new HashMap<>();


    protected boolean lazyLoadingEnabled = false;

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String statement) {
        return mappedStatements.get(statement);
    }

    public <T> T getMapper(Class<T> type, DefaultSqlSession defaultSqlSession) {
        return (T) mapperRegistry.getMapper(type, defaultSqlSession);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }




    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
        return parameterHandler;
    }

    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler,
                                                ResultHandler resultHandler, BoundSql boundSql) {
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
        return resultSetHandler;
    }

    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        StatementHandler statementHandler = new PreparedStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
        statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }

    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this,transaction);
    }

    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return languageRegistry.getDefaultDriver();
    }

    public boolean hasStatement(String statementName) {
        return hasStatement(statementName, true);
    }
    public boolean hasStatement(String statementName, boolean validateIncompleteStatements) {
//        if (validateIncompleteStatements) {
//            buildAllStatements();
//        }
        return mappedStatements.containsKey(statementName);
    }

    public ResultMap getResultMap(String id) {
        return resultMaps.get(id);
    }
}