package cn.lcf.mybatis.session.defaults;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.executor.Executor;
import cn.lcf.mybatis.mapping.Environment;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.SqlSession;
import cn.lcf.mybatis.session.SqlSessionFactory;
import cn.lcf.mybatis.session.TransactionIsolationLevel;
import cn.lcf.mybatis.transaction.Transaction;
import cn.lcf.mybatis.transaction.TransactionFactory;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:37
 * @description :
 * @modyified By:
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    private MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return openSessionFromDataSource(TransactionIsolationLevel.READ_COMMITTED,true);
    }

    private SqlSession openSessionFromDataSource(TransactionIsolationLevel level, boolean autoCommit) {
        final Environment environment = configuration.getEnvironment();
        final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
        Transaction tx  = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
        final Executor executor = configuration.newExecutor(tx);
        return new DefaultSqlSession(configuration, executor);
    }
    private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
        return environment.getTransactionFactory();
    }
}