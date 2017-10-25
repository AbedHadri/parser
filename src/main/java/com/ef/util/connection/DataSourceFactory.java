package com.ef.util.connection;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Abed
 */
public class DataSourceFactory {

    public static DataSource getDataSource() {
        Properties props = new Properties();
        InputStream fis = null;
        BasicDataSource dataSource = new BasicDataSource();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            fis = classloader.getResourceAsStream("application.properties");
            props.load(fis);
            dataSource.setUrl(props.getProperty("connection.url"));
            dataSource.setUsername(props.getProperty("connection.username"));
            dataSource.setPassword(props.getProperty("connection.password"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
