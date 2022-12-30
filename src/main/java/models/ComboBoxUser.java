package models;

public class ComboBoxUser {
	User user;
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
