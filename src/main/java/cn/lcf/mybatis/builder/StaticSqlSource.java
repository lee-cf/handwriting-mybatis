package cn.lcf.mybatis.builder;

import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.ParameterMapping;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.session.Configuration;

import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:38
 * @description :
 * @modyified By:
 */
public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}