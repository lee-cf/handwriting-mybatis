package cn.lcf.mybatis.type;

import cn.lcf.mybatis.datasource.unpooled.UnpooledDataSource;
import cn.lcf.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import cn.lcf.mybatis.io.Resources;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author : lichaofeng
 * @date :2023/11/24 17:10
 * @description :
 * @modyified By:
 */
public class TypeAliasRegistry {
    private final Map<String, Class<?>> typeAliases = new HashMap<>();

    public TypeAliasRegistry() {
        // 构造函数里注册系统内置的类型别名
        registerAlias("string", String.class);

        // 基本包装类型
        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);
        registerAlias("unpooled", UnpooledDataSourceFactory.class);
    }

    public void registerAlias(String alias, Class<?> value) {
        String key = alias.toLowerCase(Locale.ENGLISH);
        typeAliases.put(key, value);
    }

    public <T> Class<T> resolveAlias(String string) {
        try {
            String key = string.toLowerCase(Locale.ENGLISH);
            Class<T> value;
            if (typeAliases.containsKey(key)) {
                value = (Class<T>) typeAliases.get(key);
            } else {
                value = (Class<T>) Resources.classForName(string);
            }
            return value;
        }catch (Exception e){
            throw new RuntimeException("Could not resolve type alias '" + string + "'.  Cause: " + e, e);
        }
    }
}