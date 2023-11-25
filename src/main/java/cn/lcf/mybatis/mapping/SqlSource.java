package cn.lcf.mybatis.mapping;

public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
