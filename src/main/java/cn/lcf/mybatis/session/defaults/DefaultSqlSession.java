package cn.lcf.mybatis.session.defaults;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.SqlSession;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:36
 * @description :
 * @modyified By:
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }


    @Override
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("你被代理了！" + "\n方法：" + statement + "\n入参：" + parameter + "\n待执行SQL：" + mappedStatement.getSql());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }
}