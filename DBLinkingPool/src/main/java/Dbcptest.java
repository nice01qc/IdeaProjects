import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbcptest {

	public static void main(String[] args) throws SQLException {
		Connection conn=DBManager.getConn();
		Statement st=conn.createStatement();
		String sql="SELECT * FROM student";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("name"));
		}
		DBManager.closeConn(conn);

	}

}
