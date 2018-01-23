package database.jdbc;

import java.sql.*;

public class TestJdbc {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/imooc","root","1234");
//            statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
//            String sql = "SELECT cust_id AS id ,cust_name AS name ,cust_city AS city FROM customers;";
//            ResultSet resultSet = statement.executeQuery(sql);
//            while(resultSet.next()){
//                System.out.println(resultSet.getInt(1)+";"+resultSet.getString(2));
//            }
//            String sql = "SELECT cust_id AS id ,cust_name AS name ,cust_city AS city FROM customers where cust_id < ?";
//            PreparedStatement prep = conn.prepareStatement(sql);
//            prep.setObject(1,10005);
//            ResultSet resultSet = prep.executeQuery();
//            while(resultSet.next()){
//                System.out.println(resultSet.getInt(1)+";"+resultSet.getString(2));
//            }

            String sql = "{call pro6(?,?)}";
            CallableStatement call = conn.prepareCall(sql);
            call.setObject(1,88);
            call.registerOutParameter(2, JDBCType.BIGINT,"y");
            boolean result = call.execute();

            while(result){
                ResultSet resultSet = call.getResultSet();
                while(resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                }
                result = call.getMoreResults();
            }

            System.out.println(call.getInt(2));



            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
