package cn.lcf.mybatis.session;

import cn.lcf.mybatis.binging.MapperRegistry;

import java.util.HashMap;

/**
 * @author : lichaofeng
 * @date :2023/11/17 17:25
 * @description :
 * @modyified By:
 */
public class Configuration {

    protected MapperRegistry mapperRegistry = new MapperRegistry();
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }
}