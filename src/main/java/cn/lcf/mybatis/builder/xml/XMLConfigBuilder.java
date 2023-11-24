package cn.lcf.mybatis.builder.xml;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.io.Resources;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.parsing.XPathParser;
import cn.lcf.mybatis.session.Configuration;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
public class XMLConfigBuilder extends BaseBuilder {

    private XPathParser parser;

    public XMLConfigBuilder(Reader reader) {
        // 1. 调用父类初始化Configuration
        super(new Configuration());
        parser = new XPathParser(reader);

    }

    public Configuration parse() {
        parseConfiguration(parser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode root) {
        try {
            mapperElement(root.evalNode("mappers"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapperElement(XNode parent) throws Exception {
        if (parent != null) {
            for (XNode child : parent.getChildren()) {
                String resource = child.getStringAttribute("resource");
                InputStream inputStream = Resources.getResourceAsStream(resource);
                XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource);
                mapperParser.parse();
            }
        }
    }
}