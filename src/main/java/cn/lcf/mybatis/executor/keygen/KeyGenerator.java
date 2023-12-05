package cn.lcf.mybatis.executor.keygen;

import cn.lcf.mybatis.executor.Executor;
import cn.lcf.mybatis.mapping.MappedStatement;

import java.sql.Statement;

public interface KeyGenerator {

    void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

    void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter);
}
