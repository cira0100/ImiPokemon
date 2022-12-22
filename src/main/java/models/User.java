package models;

import java.io.Serializable;

public class User implements Serializable{
	public long id;
	public String username;
	public String password;
	public boolean admin;
	public long monsterId;
	
	public long getmonsterId() {
		return monsterId;
	}

	public void setmonsterId(long monsterId) {
		this.monsterId = monsterId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public User() {
		super();
	}

}
