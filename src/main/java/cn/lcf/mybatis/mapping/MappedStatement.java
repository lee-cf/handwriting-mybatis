package cn.lcf.mybatis.mapping;

import lombok.Data;

/**
 * @author : lichaofeng
 * @date :2023/11/24 11:19
 * @description :
 * @modyified By:
 */
@Data
public class MappedStatement {
    private String id;
    private String sql;

    public MappedStatement(String id, String sql) {
        this.id = id;
        this.sql = sql;
    }
}