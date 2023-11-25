package cn.lcf.mybatis.datasource.druid;

import cn.lcf.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author : lichaofeng
 * @date :2023/11/24 17:08
 * @description :
 * @modyified By:
 */
public class DruidDataSourceFactory implements DataSourceFactory {
    @Override
    public DataSource getDataSource() {
        return null;
    }

    @Override
    public void setProperties(Properties props) {

    }
}