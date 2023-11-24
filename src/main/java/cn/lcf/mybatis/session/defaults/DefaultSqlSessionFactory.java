package cn.lcf.mybatis.session.defaults;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.SqlSession;
import cn.lcf.mybatis.session.SqlSessionFactory;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:37
 * @description :
 * @modyified By:
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    private MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}