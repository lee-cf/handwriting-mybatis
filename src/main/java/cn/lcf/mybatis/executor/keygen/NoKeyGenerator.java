package cn.lcf.mybatis.executor.keygen;

import cn.lcf.mybatis.executor.Executor;
import cn.lcf.mybatis.mapping.MappedStatement;

import java.sql.Statement;

/**
 * @author : lichaofeng
 * @date :2023/12/1 9:40
 * @description :
 * @modyified By:
 */
public class NoKeyGenerator implements KeyGenerator{
    public static final NoKeyGenerator INSTANCE = new NoKeyGenerator();

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

}