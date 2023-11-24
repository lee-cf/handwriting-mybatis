package cn.lcf.mybatis.builder.xml;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.builder.MapperBuilderAssistant;
import cn.lcf.mybatis.parsing.XNode;
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
        String sql = context.getStringBody();
        builderAssistant.addMappedStatement(id, sql);
    }
}