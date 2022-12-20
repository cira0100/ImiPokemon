package pokemon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private Connection conn;
	static Database instance=null;
	
	private Database() {
		String connString="jdbc:mysql://localhost:3306/pokemon?user=root&password=";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(connString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Database getInstance() {
		if(instance==null)
			instance=new Database();
		return instance;
	}
	public String getUsername() {
		return "test123";
	}

}
