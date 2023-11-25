package cn.lcf.mybatis.mapping;

import cn.lcf.mybatis.reflection.MetaObject;
import cn.lcf.mybatis.reflection.property.PropertyTokenizer;
import cn.lcf.mybatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lichaofeng
 * @date :2023/11/24 17:09
 * @description :
 * @modyified By:
 */
public class BoundSql {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
    private MetaObject metaParameters;

    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public boolean hasAdditionalParameter(String name) {
        String paramName = new PropertyTokenizer(name).getName();
        return additionalParameters.containsKey(paramName);
    }
    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }
}