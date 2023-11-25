package cn.lcf.mybatis.scripting.xmltags;

import cn.lcf.mybatis.builder.BaseBuilder;
import cn.lcf.mybatis.mapping.SqlSource;
import cn.lcf.mybatis.parsing.XNode;
import cn.lcf.mybatis.scripting.defaults.RawSqlSource;
import cn.lcf.mybatis.session.Configuration;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/25 15:54
 * @description :
 * @modyified By:
 */
public class XMLScriptBuilder extends BaseBuilder {
    private XNode context;
    private boolean isDynamic;

    private Class<?> parameterType;

    public XMLScriptBuilder(Configuration configuration, XNode context) {
        this(configuration, context, null);
    }

    public XMLScriptBuilder(Configuration configuration, XNode context, Class<?> parameterType) {
        super(configuration);
        this.context = context;
        this.parameterType = parameterType;
    }

    public SqlSource parseScriptNode() {
        MixedSqlNode rootSqlNode = parseDynamicTags(context);
        return new RawSqlSource(configuration, rootSqlNode, parameterType);
    }

    private MixedSqlNode parseDynamicTags(XNode node) {
        isDynamic = false;
        List<SqlNode> contents = new ArrayList<>();
        NodeList children = node.getNode().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            XNode child = node.newXNode(children.item(i));
            String data = child.getStringBody("");
            contents.add(new StaticTextSqlNode(data));
        }
        return new MixedSqlNode(contents);
    }
}