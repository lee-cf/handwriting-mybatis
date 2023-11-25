package cn.lcf.mybatis.reflection.wrapper;

import cn.lcf.mybatis.reflection.MetaObject;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:48
 * @description :
 * @modyified By:
 */
public interface ObjectWrapperFactory {
    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}