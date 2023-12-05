package cn.lcf.mybatis.builder.xml;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.builder.MapperBuilderAssistant;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.scripting.LanguageDriver;
import cn.lcf.mybatis.scripting.xmltags.XMLLanguageDriver;
import cn.lcf.mybatis.session.Configuration;

/**
 * @author : lichaofeng
 * @date :2023/11/24 15:38
 * @description :
 * @modyified By:
 */
public class XMLStatementBuilder extends BaseBuilder {

    private final MapperBuilderAssistant builderAssistant;
    private final XNode context;
    private final String requiredDatabaseId;

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context, String databaseId) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.context = context;
        this.requiredDatabaseId = databaseId;
    }

    public void parseStatementNode() {
        String id = context.getStringAttribute("id");
        String resultMap = context.getStringAttribute("resultMap");
        String resultType = context.getStringAttribute("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);
        LanguageDriver langDriver = new XMLLanguageDriver();
        SqlSource sqlSource = langDriver.createSqlSource(configuration, context, null);
        builderAssistant.addMappedStatement(id, resultMap,resultTypeClass, sqlSource);
    }
}