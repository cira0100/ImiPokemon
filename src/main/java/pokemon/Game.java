package pokemon;


import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import models.MonsterViewModel;

public class Game implements Runnable, Serializable {
	
	boolean player1Turn;
	long player1Id;
	long player2Id;
	MonsterViewModel monster1;
	MonsterViewModel monster2;
	int currentHp1;
	int currentHp2;
	int shield1;
	int shield2;
	SocketChannel []players;
	
	public Game() {
		super();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public long getPlayer1Id() {
		return player1Id;
	}


	public void setPlayer1Id(long player1Id) {
		this.player1Id = player1Id;
	}


	public long getPlayer2Id() {
		return player2Id;
	}


	public void setPlayer2Id(long player2Id) {
		this.player2Id = player2Id;
	}


	public MonsterViewModel getMonster1() {
		return monster1;
	}


	public void setMonster1(MonsterViewModel monster1) {
		this.monster1 = monster1;
	}


	public MonsterViewModel getMonster2() {
		return monster2;
	}


	public void setMonster2(MonsterViewModel monster2) {
		this.monster2 = monster2;
	}


	public int getCurrentHp1() {
		return currentHp1;
	}


	public void setCurrentHp1(int currentHp1) {
		this.currentHp1 = currentHp1;
	}


	public int getCurrentHp2() {
		return currentHp2;
	}


	public void setCurrentHp2(int currentHp2) {
		this.currentHp2 = currentHp2;
	}


	public int getShield1() {
		return shield1;
	}


	public void setShield1(int shield1) {
		this.shield1 = shield1;
	}


	public int getShield2() {
		return shield2;
	}


	public void setShield2(int shield2) {
		this.shield2 = shield2;
	}


}
