package cn.lcf.mybatis.transaction.jdbc;

import cn.lcf.mybatis.session.TransactionIsolationLevel;
import cn.lcf.mybatis.transaction.Transaction;
import cn.lcf.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author : lichaofeng
 * @date :2023/11/24 17:10
 * @description :
 * @modyified By:
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }

    @Override
    public void setProperties(Properties props) {

    }
}