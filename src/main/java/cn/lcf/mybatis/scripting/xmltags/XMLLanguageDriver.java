package cn.lcf.mybatis.scripting.xmltags;

import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.scripting.LanguageDriver;
import cn.lcf.mybatis.session.Configuration;

/**
 * @author : lichaofeng
 * @date :2023/11/25 15:51
 * @description :
 * @modyified By:
 */
public class XMLLanguageDriver implements LanguageDriver {
    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }
}