package cn.lcf.mybatis.reflection.wrapper;

import cn.lcf.mybatis.reflection.MetaObject;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:48
 * @description :
 * @modyified By:
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory{
    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}