package pokemon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import database.Database;
import models.Ability;
import models.Monster;
import models.User;

public class Service extends UnicastRemoteObject implements IService  {

	protected Service() throws RemoteException {
		super();
	}

	@Override
	public boolean addUser(User user) throws RemoteException{
		Database db=Database.getInstance();
		return db.addUser(user);
	}

	@Override
	public User login(User user) throws RemoteException{
		Database db=Database.getInstance();
		return db.login(user);
	}

	@Override
	public long addMonster(Monster monster)throws RemoteException {
		Database db=Database.getInstance();
		return db.addMonster(monster);
	}

	@Override
	public boolean addAbility(Ability ability) throws RemoteException{
		Database db=Database.getInstance();
		return db.addAbility(ability);
	}

	@Override
	public ArrayList<Monster> getMonsters()throws RemoteException {
		Database db=Database.getInstance();
		return db.getMonsters();
	}

	@Override
	public Monster getUserMonster(String username) throws RemoteException {
		Database db=Database.getInstance();
		return db.getUserMonster(username);
	}

	@Override
	public void addMonsterToUser(long id, long monsterId) throws RemoteException {
		Database db=Database.getInstance();
		db.addMonsterToUser(id, monsterId);
		
	}
	

}
