package cn.lcf.mybatis.session;

import cn.lcf.mybatis.builder.xml.XMLConfigBuilder;
import cn.lcf.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author : lichaofeng
 * @date :2023/11/17 17:24
 * @description :
 * @modyified By:
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}