package cn.lcf.mybatis.builder;

import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.type.TypeAliasRegistry;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
public class BaseBuilder {

    protected Configuration configuration;

    protected TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    protected <T> Class<? extends T> resolveClass(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return resolveAlias(alias);
        } catch (Exception e) {
            throw new RuntimeException("Error resolving class. Cause: " + e, e);
        }
    }

    protected <T> Class<? extends T> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}