package cn.lcf.mybatis.scripting.defaults;

import cn.lcf.mybatis.builder.SqlSourceBuilder;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.scripting.xmltags.DynamicContext;
import cn.lcf.mybatis.scripting.xmltags.SqlNode;
import cn.lcf.mybatis.session.Configuration;

import java.util.HashMap;

/**
 * @author : lichaofeng
 * @date :2023/11/25 16:11
 * @description :
 * @modyified By:
 */
public class RawSqlSource implements SqlSource {
    private SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }
    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }
    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration);
        rootSqlNode.apply(context);
        return context.getSql();
    }
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }
}