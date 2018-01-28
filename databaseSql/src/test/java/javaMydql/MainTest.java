package javaMydql;

import org.junit.Test;

import java.sql.*;

public class MainTest {

    @Test
    public void testThreeMethod() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc?useSSL=false","root","1234");

        Statement statement = connection.createStatement();

        String sql = "select * from orders";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            System.out.println(resultSet.getInt("cust_id"));
        }

        statement.close();
        System.out.println("<============================================================>");
        // -------------------------------------------------------
        String sql1 = "select cust_id,cust_city,cust_zip from customers where cust_id > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);

        preparedStatement.setObject(1,"10002");

        ResultSet resultSet1 = preparedStatement.executeQuery();
        while (resultSet1.next()){

            System.out.println(resultSet1.getObject(1));
        }
        preparedStatement.close();

        System.out.println("<============================================================>");
        // 调用存储过程,完成了输入输出，还有输出列表

        CallableStatement callableStatement = connection.prepareCall("{call proc1(?,?)}");

        callableStatement.setInt(1,32323);
        callableStatement.registerOutParameter(2,Types.INTEGER);    // 保证了注册和获取编号一致就好了，还有类型

        callableStatement.execute();

        int i = callableStatement.getInt(2);    // 保证了注册和获取编号一致就好了，还有类型
        System.out.println(i);
        ResultSet resultSet2 = callableStatement.getResultSet();
        while(resultSet2.next()){
            System.out.println(resultSet2.getObject(1)+"; "+resultSet2.getObject(4));
        }

        callableStatement.close();
        connection.close();



        // http://blog.csdn.net/u013132035/article/details/53266094
    }

    // jdbc事务处理
    @Test
    public void testjdbcAffairs() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc?useSSL=false","root","1234");
        //begin....
        Savepoint sp1 = null;   // 回滚保存点1
        Savepoint sp2 = null;   // 回滚保持点2
        Statement statement = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);  // 取消自动提交事务

        String sql = "select * from orders";
        try {
            sp1 = connection.setSavepoint();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            sp2 =connection.setSavepoint();
            System.out.println(sp1.hashCode()+";"+sp2.hashCode());
            while (resultSet.next()){
                System.out.println(resultSet.getInt("cust_id"));
            }
        } catch (SQLException e) {

            connection.rollback(sp1);   // 遇到错误就回滚

            System.out.println("出错了");

            e.printStackTrace();
        }finally {
            connection.commit();
            resultSet.close();  // 最好做些判断
            statement.close();
            connection.close();
        }
        System.out.println("交易成功！");

    }
}
