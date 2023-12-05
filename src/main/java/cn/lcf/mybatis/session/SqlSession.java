package cn.lcf.mybatis.session;

import java.util.List;

public interface SqlSession {

    <T> T selectOne(String statement, Object parameter);
    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);
    <E> List<E> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> type);

    Configuration getConfiguration();
}
