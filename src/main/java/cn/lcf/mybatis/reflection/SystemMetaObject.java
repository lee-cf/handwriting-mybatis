package cn.lcf.mybatis.reflection;

import cn.lcf.mybatis.reflection.factory.DefaultObjectFactory;
import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import cn.lcf.mybatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:49
 * @description :
 * @modyified By:
 */
public class SystemMetaObject {
    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(new NullObject(), DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

    private SystemMetaObject() {
        // Prevent Instantiation of Static Class
    }

    private static class NullObject {
    }

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
    }
}