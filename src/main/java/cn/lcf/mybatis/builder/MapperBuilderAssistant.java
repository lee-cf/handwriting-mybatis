package cn.lcf.mybatis.builder;

import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.mapping.ResultMap;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;

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

    public MappedStatement addMappedStatement(String id,
                                              String resultMap,
                                              Class<?> resultType,
                                              SqlSource sqlSource) {
        id = applyCurrentNamespace(id, false);
        MappedStatement statement = new MappedStatement(configuration, id, sqlSource);
        List<ResultMap> resultMaps = getStatementResultMaps(resultMap,resultType,id);
        statement.setResultMaps(resultMaps);
        configuration.addMappedStatement(statement);
        return statement;
    }

    public String applyCurrentNamespace(String base, boolean isReference) {
        if (base == null) {
            return null;
        }
        if (isReference) {
            // is it qualified with any namespace yet?
            if (base.contains(".")) {
                return base;
            }
        } else {
            // is it qualified with this namespace yet?
            if (base.startsWith(currentNamespace + ".")) {
                return base;
            }
            if (base.contains(".")) {
                throw new RuntimeException("Dots are not allowed in element names, please remove it from " + base);
            }
        }
        return currentNamespace + "." + base;
    }

    private List<ResultMap> getStatementResultMaps(
            String resultMap,
            Class<?> resultType,
            String statementId) {
        resultMap = applyCurrentNamespace(resultMap, true);

        List<ResultMap> resultMaps = new ArrayList<>();
        if (resultMap != null) {
            String[] resultMapNames = resultMap.split(",");
            for (String resultMapName : resultMapNames) {
                try {
                    resultMaps.add(configuration.getResultMap(resultMapName.trim()));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Could not find result map '" + resultMapName + "' referenced from '" + statementId + "'", e);
                }
            }
        } else if (resultType != null) {
            ResultMap inlineResultMap = new ResultMap.Builder(
                    configuration,
                    statementId + "-Inline",
                    resultType,
                    new ArrayList<>(),
                    null).build();
            resultMaps.add(inlineResultMap);
        }
        return resultMaps;
    }

}