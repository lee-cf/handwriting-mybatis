package cn.lcf.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceFactory {
    DataSource getDataSource();

    void setProperties(Properties props);
}
