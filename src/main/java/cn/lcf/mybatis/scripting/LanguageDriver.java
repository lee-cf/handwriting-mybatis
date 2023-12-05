package cn.lcf.mybatis.scripting;

import cn.lcf.mybatis.executor.parameter.ParameterHandler;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.session.Configuration;

public interface LanguageDriver {
    SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);
}
