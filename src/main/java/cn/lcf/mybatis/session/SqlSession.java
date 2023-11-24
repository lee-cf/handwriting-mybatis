package cn.lcf.mybatis.session;

public interface SqlSession {

    <T> T selectOne(String statement, Object parameter);

    <T> T getMapper(Class<T> type);

}
