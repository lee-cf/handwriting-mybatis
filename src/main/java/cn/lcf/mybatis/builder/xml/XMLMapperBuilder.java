package cn.lcf.mybatis.builder.xml;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.builder.MapperBuilderAssistant;
import cn.lcf.mybatis.io.Resources;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.parsing.XPathParser;
import cn.lcf.mybatis.session.Configuration;

import java.io.InputStream;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/24 14:28
 * @description :
 * @modyified By:
 */
public class XMLMapperBuilder extends BaseBuilder {

    private final XPathParser parser;
    private final MapperBuilderAssistant builderAssistant;
    private final String resource;


    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) {
        this(new XPathParser(inputStream), configuration, resource);
    }

    private XMLMapperBuilder(XPathParser parser, Configuration configuration, String resource) {
        super(configuration);
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.parser = parser;
        this.resource = resource;
    }

    public void parse() {
        configurationElement(parser.evalNode("/mapper"));
    }

    private void configurationElement(XNode context) {
        try {
            String namespace = context.getStringAttribute("namespace");
            if (namespace == null || namespace.isEmpty()) {
                throw new RuntimeException("Mapper's namespace cannot be empty");
            }
            builderAssistant.setCurrentNamespace(namespace);
            buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
        }
    }

    private void buildStatementFromContext(List<XNode> list) {
        buildStatementFromContext(list, null);
        bindMapperForNamespace();
    }

    private void bindMapperForNamespace() {
        String namespace = builderAssistant.getCurrentNamespace();
        if (namespace != null) {
            try {
                Class<?> boundType = Resources.classForName(namespace);
                configuration.addMapper(boundType);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
        for (XNode context : list) {
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, context, requiredDatabaseId);
            try {
                statementParser.parseStatementNode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}