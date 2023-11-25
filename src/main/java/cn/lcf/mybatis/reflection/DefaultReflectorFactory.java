package cn.lcf.mybatis.reflection;

import cn.lcf.mybatis.util.MapUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : lichaofeng
 * @date :2023/11/25 10:53
 * @description :
 * @modyified By:
 */
public class DefaultReflectorFactory implements ReflectorFactory{
    private boolean classCacheEnabled = true;
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

    public DefaultReflectorFactory() {
    }

    @Override
    public boolean isClassCacheEnabled() {
        return classCacheEnabled;
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    @Override
    public Reflector findForClass(Class<?> type) {
        if (classCacheEnabled) {
            return MapUtil.computeIfAbsent(reflectorMap, type, Reflector::new);
        } else {
            return new Reflector(type);
        }
    }

}