package cn.lcf.mybatis.session;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.session.defaults.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lichaofeng
 * @date :2023/11/17 17:25
 * @description :
 * @modyified By:
 */
public class Configuration {

    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

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
        return (T) mapperRegistry.getMapper(type,defaultSqlSession);
    }
}