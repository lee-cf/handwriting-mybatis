package cn.lcf.mybatis.builder;

import cn.lcf.mybatis.session.Configuration;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
public class BaseBuilder {

    protected Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
}