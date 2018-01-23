package database.c3p0;

import com.mchange.v2.c3p0.DataSources;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class C3p0ConnUtil {
    private static final String JDBC_DRIVER = "driverClass";
    private static final String JDBC_URL = "jdbcUrl";
    private static final String prefix = "c3p0";

    private static DataSource ds;

    static{
        initDBSource();
    }

    /**
     * 初始化c3p0连接池
     */
    private static final void initDBSource() {
        Properties c3p0Properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("c3p0.properties");
        try {
            c3p0Properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("加载c3p0文件失败 ！"+e.getMessage());
        }

        // 加载Mysql驱动类  -----> 不加载也是可以的
        String driverClass = c3p0Properties.getProperty(JDBC_DRIVER);
        if (driverClass != null){
            try{
                Class.forName(driverClass);
            }catch (ClassNotFoundException e){
                System.out.println("加载MySQL驱动类失败 ！ "+e.getMessage());
            }
        }

        // 常规数据库链接属性
        Properties jdbcProPerties = new Properties();
        // 链接池配置属性
        Properties c3p0PooledProp = new Properties();
        for (Object key : c3p0Properties.keySet()){
            String skey = (String) key;
            if (skey.startsWith(prefix)){
                c3p0PooledProp.put(skey,c3p0Properties.getProperty(skey));
            }else{
                jdbcProPerties.put(skey,c3p0Properties.getProperty(skey));
            }
        }

        try{
            // 建立连接池
            DataSource unPooled = DataSources.unpooledDataSource(c3p0Properties.getProperty(JDBC_URL),jdbcProPerties);
            ds = DataSources.pooledDataSource(unPooled,c3p0PooledProp);
        }catch (SQLException e){
            System.out.println("建立连接池失败！" + e.getMessage());
        }

    }

    public static DataSource getDataSource() {
        return ds;
    }
}
