package cn.lcf.mybatis.mapping;

import cn.lcf.mybatis.executor.keygen.KeyGenerator;
import cn.lcf.mybatis.logging.Log;
import cn.lcf.mybatis.scripting.LanguageDriver;
import cn.lcf.mybatis.session.Configuration;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
public class MappedStatement {
    private Configuration configuration;
    private String id;
    private SqlSource sqlSource;
    private LanguageDriver lang;

    private String[] keyProperties;
    private String[] keyColumns;

    private KeyGenerator keyGenerator;

    private List<ResultMap> resultMaps;

    private Log statementLog;

    private boolean flushCacheRequired;

    public MappedStatement(Configuration configuration, String id, SqlSource sqlSource) {
        this.configuration = configuration;
        this.id = id;
        this.sqlSource = sqlSource;
        this.lang = configuration.getDefaultScriptingLanguageInstance();
        this.resultMaps = new ArrayList();
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public LanguageDriver getLang() {
        return lang;
    }

    public void setLang(LanguageDriver lang) {
        this.lang = lang;
    }

    public String[] getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String[] keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String[] getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(String[] keyColumns) {
        this.keyColumns = keyColumns;
    }

    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public List<ResultMap> getResultMaps() {
        return resultMaps;
    }

    public void setResultMaps(List<ResultMap> resultMaps) {
        this.resultMaps = resultMaps;
    }

    public Log getStatementLog() {
        return statementLog;
    }

    public void setStatementLog(Log statementLog) {
        this.statementLog = statementLog;
    }

    public boolean isFlushCacheRequired() {
        return flushCacheRequired;
    }

    public void setFlushCacheRequired(boolean flushCacheRequired) {
        this.flushCacheRequired = flushCacheRequired;
    }

    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }
}