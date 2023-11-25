package cn.lcf.mybatis.scripting.xmltags;

import cn.lcf.mybatis.session.Configuration;

import java.util.StringJoiner;

/**
 * @author : lichaofeng
 * @date :2023/11/25 16:08
 * @description :
 * @modyified By:
 */
public class DynamicContext {
    private final StringJoiner sqlBuilder = new StringJoiner(" ");

    private Configuration configuration;

    public DynamicContext(Configuration configuration) {
        this.configuration = configuration;
    }

    public void appendSql(String sql) {
        sqlBuilder.add(sql);
    }

    public String getSql() {
        return sqlBuilder.toString().trim();
    }
}