package cn.lcf.mybatis.binging;

import cn.lcf.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:04
 * @description :
 * @modyified By:
 */
public class MapperProxyFactory<T> {
    private Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        MapperProxy mapperProxy = new MapperProxy(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}