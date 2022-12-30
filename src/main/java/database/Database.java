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
import models.AbilityType;
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
			ps.setFloat(5, ability.getPower());
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
	public Monster getUserMonster(String username) {
		Monster monster=null;
		String sql="SELECT * FROM monster m JOIN user u ON u.username=? AND u.pokemonId=m.id";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet res=statement.executeQuery();
			if(res.next()) {
				 monster=new Monster();
				 monster.setId(res.getLong("m.id"));
				 monster.setName(res.getString("m.name"));
				 monster.setDescription(res.getString("m.description"));
				 monster.setHp(res.getInt("m.hp"));
				 monster.setBase64Image(res.getString("m.base64Image"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return monster;
		
	}
	public void addMonsterToUser(long id,long monsterId) {
		
		String sql="UPDATE user set pokemonId=?  where id=?;";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps=conn.prepareStatement(sql);
			ps.setLong(1,monsterId);
			ps.setLong(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public MonsterViewModel getMonsterViewModel(long id) {
		MonsterViewModel monster=null;
		
		String sql="SELECT * FROM monster m JOIN ability a ON m.id=? AND m.id=a.monsterId";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet res=statement.executeQuery();
			int i=0;
			while(res.next()) {
				if(i==0)
				{
					monster=new MonsterViewModel();
					monster.setId(res.getLong("m.id"));
					monster.setName(res.getString("m.name"));
					monster.setDescription(res.getString("m.description"));
					monster.setHp(res.getInt("m.hp"));
					monster.setBase64Image(res.getString("m.base64Image"));
					monster.abilities=new ArrayList<Ability>();
					i++;
				}
				Ability a=new Ability();
				a.setId(res.getLong("a.id"));
				a.setMonsterId(res.getLong("m.id"));
				a.setName(res.getString("a.name"));
				a.setDescription(res.getString("a.description"));
				a.setType(AbilityType.values()[res.getInt("a.type")]);
				a.setPower(res.getFloat("a.power"));
				monster.abilities.add(a);
				 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monster;
		
	}
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users=new ArrayList<User>();
		try {
			String sql="SELECT * FROM user";
			Statement statement = conn.createStatement();
			ResultSet res=statement.executeQuery(sql);
			while(res.next()) {
				User tempUser=new User();
				tempUser=new User();
				tempUser.setId(res.getLong("id"));
				tempUser.setAdmin(res.getBoolean("admin"));
				tempUser.setUsername(res.getString("username"));
				if(res.getObject("pokemonId")!=null)
					tempUser.setmonsterId(res.getLong("pokemonId"));
				else 
					tempUser.setmonsterId(-1);
				users.add(tempUser);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
		
	}
	public void deleteUser(long id) {
		String sql="DELETE FROM user WHERE id=?";
		try {
			PreparedStatement preStatement=conn.prepareStatement(sql);
			preStatement.setLong(1, id);
			preStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteMonster(long id) {
		try {
			conn.setAutoCommit(false);
			String sql="DELETE FROM ability WHERE monsterId=?";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setLong(1, id);
			pre.executeUpdate();
			 sql="DELETE FROM monster WHERE id=?";
			 pre = conn.prepareStatement(sql);
			pre.setLong(1, id);
			pre.executeUpdate();
			conn.commit();

		} catch (SQLException  e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public User getUserById(long id){
		User user=null;
		try {
			String sql="SELECT * FROM user WHERE id=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet res=statement.executeQuery();
			if(res.next()) {
				user=new User();
				user.setId(res.getLong("id"));
				user.setAdmin(res.getBoolean("admin"));
				user.setUsername(res.getString("username"));
				if(res.getObject("pokemonId")!=null)
					user.setmonsterId(res.getLong("pokemonId"));
				else 
					user.setmonsterId(-1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
		
	}

}
