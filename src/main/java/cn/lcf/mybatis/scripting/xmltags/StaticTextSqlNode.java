package cn.lcf.mybatis.scripting.xmltags;

/**
 * @author : lichaofeng
 * @date :2023/11/25 16:09
 * @description :
 * @modyified By:
 */
public class StaticTextSqlNode implements SqlNode{
    private final String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean apply(DynamicContext context) {
        context.appendSql(text);
        return true;
    }
}