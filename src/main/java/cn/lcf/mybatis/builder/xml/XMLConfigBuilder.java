package cn.lcf.mybatis.builder.xml;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.datasource.DataSourceFactory;
import cn.lcf.mybatis.io.Resources;
import cn.lcf.mybatis.mapping.Environment;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.parsing.XPathParser;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
public class XMLConfigBuilder extends BaseBuilder {

    private XPathParser parser;

    private String environment;

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
            environmentsElement(root.evalNode("environments"));
            mapperElement(root.evalNode("mappers"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void environmentsElement(XNode context) throws Exception {
        if (environment == null) {
            environment = context.getStringAttribute("default");
        }
        if(context != null){
            for (XNode child : context.getChildren()) {
                String id = child.getStringAttribute("id");
                if (isSpecifiedEnvironment(id)) {
                    TransactionFactory txFactory = transactionManagerElement(child.evalNode("transactionManager"));
                    DataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
                    DataSource dataSource = dsFactory.getDataSource();
                    Environment.Builder environmentBuilder = new Environment.Builder(id)
                            .transactionFactory(txFactory)
                            .dataSource(dataSource);
                    configuration.setEnvironment(environmentBuilder.build());
                    break;
                }
            }
        }
    }

    private TransactionFactory transactionManagerElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            Properties props = context.getChildrenAsProperties();
            TransactionFactory factory = (TransactionFactory) resolveClass(type).getDeclaredConstructor().newInstance();
            factory.setProperties(props);
            return factory;
        }
        throw new RuntimeException("Environment declaration requires a TransactionFactory.");
    }

    private DataSourceFactory dataSourceElement(XNode context) throws Exception {
        if (context != null) {
            String type = context.getStringAttribute("type");
            Properties props = context.getChildrenAsProperties();
            DataSourceFactory factory = (DataSourceFactory) resolveClass(type).getDeclaredConstructor().newInstance();
            factory.setProperties(props);
            return factory;
        }
        throw new RuntimeException("Environment declaration requires a DataSourceFactory.");
    }

    private boolean isSpecifiedEnvironment(String id) {
        if (environment == null) {
            throw new RuntimeException("No environment specified.");
        }
        if (id == null) {
            throw new RuntimeException("Environment requires an id attribute.");
        }
        return environment.equals(id);
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