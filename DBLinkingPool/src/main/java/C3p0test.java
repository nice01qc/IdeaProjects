import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3p0test {

	public static void main(String[] args) throws PropertyVetoException, SQLException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.jdbc.Driver" );        
		cpds.setJdbcUrl( "jdbc:mysql://localhost/imooc" );
		cpds.setUser("root");                                  
		cpds.setPassword("1234"); 
		
		
		Connection conn=cpds.getConnection();
		Statement st=conn.createStatement();
		String sql="SELECT * FROM customers";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("cust_name"));
		}
		cpds.close();

		ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
			@Override
			protected Integer initialValue() {
				return super.initialValue();
			}

			@Override
			public Integer get() {
				return super.get();
			}

			@Override
			public void set(Integer value) {
				super.set(value);
			}

			@Override
			public void remove() {
				super.remove();
			}
		};
	}

}
