package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import models.Ability;
import models.Monster;
import models.MonsterViewModel;
import models.User;

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
	public boolean addUser(User user) {
		boolean res=false;
		String sql="SELECT * FROM user where username=? ";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ResultSet result=ps.executeQuery();
			if(result.next()) {
				return res;
			}
			
			
			sql="INSERT INTO user(username,password,admin) values(?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			String hashedPassword=BcryptHelper.hashPasword(user.getPassword());
			ps.setString(2, hashedPassword);
			ps.setBoolean(3, user.isAdmin());
			if(ps.executeUpdate()==1) {
				res=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
		return res;
	}
	public User login(User user) {
		User tempUser=null;
		String sql="SELECT * FROM user where username=?";
		try {
			PreparedStatement pre=conn.prepareStatement(sql);
			pre.setString(1, user.getUsername());
			ResultSet res=pre.executeQuery();
			if(res.next()) {
				String hashedPw=res.getString("password");
				if(BcryptHelper.checkPassword(user.password, hashedPw))
				{
					tempUser=new User();
					tempUser.setId(res.getInt("id"));
					tempUser.setAdmin(res.getBoolean("admin"));
					tempUser.setUsername(user.getUsername());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tempUser;
		
	}
	public long addMonster(Monster monster) {
		long tempId=0;
		String sql=null;
		PreparedStatement ps;
		try {
			sql="INSERT INTO monster(name,description,hp,base64Image) values(?,?,?,?)";
			ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, monster.getName());
			ps.setString(2, monster.getDescription());
			ps.setInt(3, monster.getHp());
			ps.setString(4, monster.getBase64Image());
			if(ps.executeUpdate()==1) {
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                tempId=generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempId;
	}
	public boolean addAbility(Ability ability) {
		boolean result=false;
		String sql=null;
		PreparedStatement ps;
		try {
			sql="INSERT INTO ability(monsterId,name,description,type,power) values(?,?,?,?,?)";
			ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, ability.getMonsterId());
			ps.setString(2, ability.getName());
			ps.setString(3, ability.getDescription());
			ps.setInt(4, ability.getType().ordinal());
			ps.setInt(5, ability.getPower());
			if(ps.executeUpdate()==1) {
				result=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<Monster> getMonsters(){
		ArrayList<Monster> monsters=new ArrayList<Monster>();
		
		String sql="SELECT * FROM monster";
		try {
			Statement statement = conn.createStatement();
			ResultSet res=statement.executeQuery(sql);
			while(res.next()) {
				Monster m=new Monster();
				m.setId(res.getLong("id"));
				m.setName(res.getString("name"));
				m.setDescription(res.getString("description"));
				m.setHp(res.getInt("hp"));
				m.setBase64Image(res.getString("base64Image"));
				monsters.add(m);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monsters;
	}

}
