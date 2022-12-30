package models;

public class ComboBoxUser {
	User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return user.getUsername();
	}
	public ComboBoxUser() {
		
	}
	public ComboBoxUser(User user) {
		this.user=user;
	}

}
