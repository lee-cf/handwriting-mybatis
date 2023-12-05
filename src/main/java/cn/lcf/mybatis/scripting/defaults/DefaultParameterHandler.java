package cn.lcf.mybatis.scripting.defaults;

import cn.lcf.mybatis.executor.parameter.ParameterHandler;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.ParameterMapping;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.type.JdbcType;
import cn.lcf.mybatis.type.TypeHandler;
import cn.lcf.mybatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/12/4 17:25
 * @description :
 * @modyified By:
 */
public class DefaultParameterHandler implements ParameterHandler {
    private final TypeHandlerRegistry typeHandlerRegistry;

    private final MappedStatement mappedStatement;
    private final Object parameterObject;
    private final BoundSql boundSql;
    private final Configuration configuration;

    public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                Object value = parameterObject;
                TypeHandler typeHandler = parameterMapping.getTypeHandler();
                JdbcType jdbcType = parameterMapping.getJdbcType();
                typeHandler.setParameter(ps, i + 1, value, jdbcType);
            }
        }
    }
}