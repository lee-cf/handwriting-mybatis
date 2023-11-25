package cn.lcf.mybatis.scripting.xmltags;

import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/25 16:09
 * @description :
 * @modyified By:
 */
public class MixedSqlNode implements SqlNode{
    private final List<SqlNode> contents;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public boolean apply(DynamicContext context) {
        contents.forEach(node -> node.apply(context));
        return true;
    }
}