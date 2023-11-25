package cn.lcf.mybatis.mapping;

import cn.lcf.mybatis.session.Configuration;
import lombok.Data;

import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
@Data
public class MappedStatement {
    private Configuration configuration;
    private String id;
    private SqlSource sqlSource;

    public MappedStatement(Configuration configuration, String id, SqlSource sqlSource) {
        this.configuration = configuration;
        this.id = id;
        this.sqlSource = sqlSource;
    }

    public BoundSql getBoundSql(Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            boundSql = new BoundSql(configuration, boundSql.getSql(), parameterMap.getParameterMappings(), parameterObject);
        }

        // check for nested result maps in parameter mappings (issue #30)
        for (ParameterMapping pm : boundSql.getParameterMappings()) {
            String rmId = pm.getResultMapId();
            if (rmId != null) {
                ResultMap rm = configuration.getResultMap(rmId);
                if (rm != null) {
                    hasNestedResultMaps |= rm.hasNestedResultMaps();
                }
            }
        }

        return boundSql;
    }
}