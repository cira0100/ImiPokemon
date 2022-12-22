package pokemon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import models.Ability;
import models.Monster;
import models.MonsterViewModel;
import models.User;

public interface IService extends Remote {
	public boolean addUser(User user)throws RemoteException;
	public User login(User user)throws RemoteException;
	public long addMonster(Monster monster)throws RemoteException;
	public boolean addAbility(Ability ability)throws RemoteException;
	public ArrayList<Monster> getMonsters()throws RemoteException;
	public Monster getUserMonster(String username) throws RemoteException;
	public void addMonsterToUser(long id,long monsterId)throws RemoteException;
	public MonsterViewModel getMonsterViewModel(long id)throws RemoteException;
	public ArrayList<User> getAllUsers()throws RemoteException;
	public void deleteUser(long id)throws RemoteException;

}
