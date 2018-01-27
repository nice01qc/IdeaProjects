package javaMydql;

import java.sql.*;

public class MainTest {

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc","root","1234");
        } catch (SQLException e) { e.printStackTrace();}

        Statement statement = null;
        try {
        statement = connection.createStatement();
        } catch (SQLException e) { e.printStackTrace(); }

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

        System.out.println("<============================================================>");



    }
}
