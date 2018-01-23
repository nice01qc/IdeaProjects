package database.c3p0;


import com.mysql.jdbc.DatabaseMetaData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class TestC3p0ConnUtil {
    public static void main(String[] args) throws SQLException {
        String sql = "select * from customers order by cust_name";

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:jdbc-1.xml");

        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");

        System.out.println(jdbcTemplate);

    }
}
