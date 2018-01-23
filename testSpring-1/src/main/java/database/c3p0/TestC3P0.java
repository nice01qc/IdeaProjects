package database.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;


import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TestC3P0 {
    public static void main(String[] args) throws PropertyVetoException, SQLException {
        // 先加载一个properties
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("c3p0.properties");
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("jdbcUrl"));

        // 通过setter一个一个进行配置
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(properties.getProperty("driverClass"));
        cpds.setJdbcUrl(properties.getProperty("jdbcUrl"));
        cpds.setUser(properties.getProperty("user"));
        cpds.setPassword(properties.getProperty("password"));


        Connection conn = cpds.getConnection();
        System.out.println(conn);


    }
}
