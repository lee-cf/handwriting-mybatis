package cn.lcf.mybatis.scripting.xmltags;

public interface SqlNode {
    boolean apply(DynamicContext context);
}
