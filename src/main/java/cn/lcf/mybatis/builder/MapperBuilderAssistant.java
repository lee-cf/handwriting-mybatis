package cn.lcf.mybatis.builder;

import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.session.Configuration;

/**
 * @author : lichaofeng
 * @date :2023/11/24 15:32
 * @description :
 * @modyified By:
 */

public class MapperBuilderAssistant extends BaseBuilder {
    private final String resource;

    private String currentNamespace;

    public MapperBuilderAssistant(Configuration configuration, String resource) {
        super(configuration);
        this.resource = resource;
    }

    public String getCurrentNamespace() {
        return currentNamespace;
    }

    public void setCurrentNamespace(String currentNamespace) {
        this.currentNamespace = currentNamespace;
    }

    public MappedStatement addMappedStatement(String id, SqlSource sqlSource) {
        MappedStatement statement = new MappedStatement(configuration, id, sqlSource);
        configuration.addMappedStatement(statement);
        return statement;
    }



}