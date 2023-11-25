package cn.lcf.mybatis.session;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.datasource.druid.DruidDataSourceFactory;
import cn.lcf.mybatis.mapping.Environment;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.reflection.DefaultReflectorFactory;
import cn.lcf.mybatis.reflection.MetaObject;
import cn.lcf.mybatis.reflection.ReflectorFactory;
import cn.lcf.mybatis.reflection.factory.DefaultObjectFactory;
import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import cn.lcf.mybatis.reflection.wrapper.ObjectWrapperFactory;
import cn.lcf.mybatis.session.defaults.DefaultSqlSession;
import cn.lcf.mybatis.transaction.jdbc.JdbcTransactionFactory;
import cn.lcf.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lichaofeng
 * @date :2023/11/17 17:25
 * @description :
 * @modyified By:
 */
public class Configuration {
    //环境
    protected Environment environment;

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

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
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
}