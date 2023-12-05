package cn.lcf.mybatis.executor.statement;

import cn.lcf.mybatis.executor.Executor;
import cn.lcf.mybatis.executor.parameter.ParameterHandler;
import cn.lcf.mybatis.executor.resultset.ResultSetHandler;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.reflection.factory.ObjectFactory;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.ResultHandler;
import cn.lcf.mybatis.session.RowBounds;
import cn.lcf.mybatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : lichaofeng
 * @date :2023/12/1 9:02
 * @description :
 * @modyified By:
 */
public abstract class BaseStatementHandler implements StatementHandler {
    protected Configuration configuration;
    protected ObjectFactory objectFactory;
    protected TypeHandlerRegistry typeHandlerRegistry;
    protected ResultSetHandler resultSetHandler;
    protected ParameterHandler parameterHandler;

    protected Executor executor;
    protected MappedStatement mappedStatement;
    protected RowBounds rowBounds;

    protected BoundSql boundSql;



    protected BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;

        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        this.objectFactory = configuration.getObjectFactory();

        this.boundSql = boundSql;

        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);
    }

    @Override
    public BoundSql getBoundSql() {
        return boundSql;
    }

    @Override
    public ParameterHandler getParameterHandler() {
        return parameterHandler;
    }

    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
            setStatementTimeout(statement, transactionTimeout);
            setFetchSize(statement);
            return statement;
        } catch (SQLException e) {
            closeStatement(statement);
            throw e;
        } catch (Exception e) {
            closeStatement(statement);
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

    protected void setStatementTimeout(Statement stmt, Integer transactionTimeout) throws SQLException {
        if(transactionTimeout != null){
            stmt.setQueryTimeout(transactionTimeout);
        }

    }
    protected void setFetchSize(Statement stmt) throws SQLException {

    }

    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //ignore
        }
    }
}