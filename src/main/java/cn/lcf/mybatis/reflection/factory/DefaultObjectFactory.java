package cn.lcf.mybatis.reflection.factory;

import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:47
 * @description :
 * @modyified By:
 */
public class DefaultObjectFactory implements ObjectFactory {
    @Override
    public <T> T create(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}