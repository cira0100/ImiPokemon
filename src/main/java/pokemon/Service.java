package pokemon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import database.Database;
import models.Ability;
import models.Monster;
import models.MonsterViewModel;
import models.PokemonAddModel;
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

	@Override
	public MonsterViewModel getMonsterViewModel(long id) throws RemoteException {
		Database db=Database.getInstance();
		return db.getMonsterViewModel(id);
	}

	@Override
	public ArrayList<User> getAllUsers() throws RemoteException {
		Database db=Database.getInstance();
		return db.getAllUsers();
	}

	@Override
	public void deleteUser(long id) throws RemoteException {
		Database db=Database.getInstance();
		db.deleteUser(id);
		
	}
	public boolean addPokemonWithAbilities(PokemonAddModel pokemon)throws RemoteException {
		boolean res=false;
		Database db=Database.getInstance();
		long monsterId=db.addMonster(addModelToMonster(pokemon));
		for(Ability a:pokemon.abilities) {
			a.monsterId=monsterId;
			db.addAbility(a);
		}
		res=true;
		return res;
		
	}
	public Monster addModelToMonster(PokemonAddModel pokemon) {
		Monster newMonster=new Monster();
		newMonster.setName(pokemon.getName());
		newMonster.setDescription(pokemon.getDescription());
		newMonster.setHp(pokemon.getHp());
		newMonster.setBase64Image(pokemon.getBase64Image());
		return newMonster;
		
	}
	

}
