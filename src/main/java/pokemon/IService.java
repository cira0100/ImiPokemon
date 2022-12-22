package pokemon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import models.Ability;
import models.Monster;
import models.User;

public interface IService extends Remote {
	public boolean addUser(User user)throws RemoteException;
	public User login(User user)throws RemoteException;
	public long addMonster(Monster monster)throws RemoteException;
	public boolean addAbility(Ability ability)throws RemoteException;
	public ArrayList<Monster> getMonsters()throws RemoteException;

}
