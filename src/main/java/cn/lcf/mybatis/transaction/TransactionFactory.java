package cn.lcf.mybatis.transaction;

import cn.lcf.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public interface TransactionFactory {
    Transaction newTransaction(Connection conn);

    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

    void setProperties(Properties props);
}
