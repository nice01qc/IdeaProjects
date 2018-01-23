import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3p0test1 {
	//????c3p0??c3p0-config.xml????
	public static void main(String[] args) throws SQLException {
		DataSource datasource=new ComboPooledDataSource("c3p0");
		
		Connection conn=datasource.getConnection();
		Statement st=conn.createStatement();
		String sql="SELECT * FROM student";
		ResultSet rs=st.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString("name"));
		}
		
		rs.close();
		st.close();
		conn.close();
	}

}
