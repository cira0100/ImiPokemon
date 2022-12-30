package models;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UserListWrapper {
	public ArrayList<User> users=new ArrayList<>();
	public UserListWrapper() {
		
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	@Override
	public String toString()
	{
		XMLEncoder coder = null;
		String xmlString = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try
		{
			coder = new XMLEncoder(baos);
			coder.writeObject(this);
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			coder.close();
		}
		
		xmlString = new String(baos.toByteArray());
		
		return xmlString.replace("\n", " ");
	}

}
