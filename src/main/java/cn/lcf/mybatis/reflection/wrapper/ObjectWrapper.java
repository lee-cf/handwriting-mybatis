package cn.lcf.mybatis.reflection.wrapper;

import cn.lcf.mybatis.reflection.MetaObject;
import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/25 9:48
 * @description :
 * @modyified By:
 */
public interface ObjectWrapper {
    Object get(PropertyTokenizer prop);

    void set(PropertyTokenizer prop, Object value);

    String findProperty(String name, boolean useCamelCaseMapping);

    String[] getGetterNames();

    String[] getSetterNames();

    Class<?> getSetterType(String name);

    Class<?> getGetterType(String name);

    boolean hasSetter(String name);

    boolean hasGetter(String name);

    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    boolean isCollection();

    void add(Object element);

    <E> void addAll(List<E> element);

}