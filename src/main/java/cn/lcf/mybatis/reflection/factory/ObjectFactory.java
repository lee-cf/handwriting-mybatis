package cn.lcf.mybatis.reflection.factory;

public interface ObjectFactory {
    <T> T create(Class<T> type);

    <T> boolean isCollection(Class<T> type);
}
