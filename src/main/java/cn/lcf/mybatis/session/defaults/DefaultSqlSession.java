package cn.lcf.mybatis.session.defaults;

import cn.lcf.mybatis.binging.MapperRegistry;
import cn.lcf.mybatis.mapping.BoundSql;
import cn.lcf.mybatis.mapping.Environment;
import cn.lcf.mybatis.mapping.MappedStatement;
import cn.lcf.mybatis.session.Configuration;
import cn.lcf.mybatis.session.SqlSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author : lichaofeng
 * @date :2023/11/17 16:36
 * @description :
 * @modyified By:
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }


    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            Environment environment = configuration.getEnvironment();

            Connection connection = environment.getDataSource().getConnection();

            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
            return objList.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }
}