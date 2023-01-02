package pokemon;


import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import models.Ability;
import models.AbilityType;
import models.GameStatus;
import models.MonsterViewModel;

public class Game implements Runnable, Serializable {
	
	public boolean player1Turn;
	public long player1Id;
	public long player2Id;
	public MonsterViewModel monster1;
	public MonsterViewModel monster2;
	public int currentHp1;
	public int currentHp2;
	public int shield1;
	public int shield2;
	GameStatus status;
	
	public Game() {
		super();
	}
	public void player1Move(long abilityId) {
		Ability a=null;
		player1Turn=false;
		double rand=Math.random();
		for(Ability temp :monster1.abilities) {
			if(temp.id==abilityId) {
				a=temp;
				break;
			}
		}
		int abilityResult=(int)(a.power*rand);
		if(a.getType()==AbilityType.ATTACK) {
			if(shield2>0) {
				if(shield2<abilityResult) {
					shield2=shield2-abilityResult;
				}
				else {
					currentHp2=currentHp2+shield2-abilityResult;
					shield2=0;
				}
				
			}else {
				currentHp2=currentHp2-abilityResult;
			}
			
		}else if(a.getType()==AbilityType.SPECIAL) {
			abilityResult=(int)(abilityResult*(1+Math.random()/2));
			if(shield2>0) {
				if(shield2<abilityResult) {
					shield2=shield2-abilityResult;
				}
				else {
					currentHp2=currentHp2+shield2-abilityResult;
					shield2=0;
				}
				
			}else {
				currentHp2=currentHp2-abilityResult;
			}
			
		}else if(a.getType()==AbilityType.HEAL) {
			if(currentHp1+abilityResult>=monster1.hp) {
				currentHp1=monster1.hp;
			}
			else {
				currentHp1=currentHp1+abilityResult;
			}
			
		}else if(a.getType()==AbilityType.SHIELD) {
			shield1=shield1+abilityResult;
			
		}
		
	}
	public void player2Move(long abilityId) {
		Ability a=null;
		player1Turn=true;
		double rand=Math.random();
		for(Ability temp :monster2.abilities) {
			if(temp.id==abilityId) {
				a=temp;
				break;
			}
		}
		int abilityResult=(int)(a.power*rand);
		if(a.getType()==AbilityType.ATTACK) {
			if(shield1>0) {
				if(shield1<abilityResult) {
					shield1=shield1-abilityResult;
				}
				else {
					currentHp1=currentHp1+shield1-abilityResult;
					shield1=0;
				}
				
			}else {
				currentHp1=currentHp1-abilityResult;
			}
			
		}else if(a.getType()==AbilityType.SPECIAL) {
			abilityResult=(int)(abilityResult*(1+Math.random()/2));
			if(shield1>0) {
				if(shield1<abilityResult) {
					shield1=shield1-abilityResult;
				}
				else {
					currentHp1=currentHp1+shield1-abilityResult;
					shield1=0;
				}
				
			}else {
				currentHp1=currentHp1-abilityResult;
			}
			
		}else if(a.getType()==AbilityType.HEAL) {
			if(currentHp2+abilityResult>=monster2.hp) {
				currentHp2=monster2.hp;
			}
			else {
				currentHp2=currentHp2+abilityResult;
			}
			
		}else if(a.getType()==AbilityType.SHIELD) {
			shield2=shield2+abilityResult;
			
		}
		
	}
	public int checkWin() {
		int res=0;
		if(currentHp1<=0) {
			res=1;
			status=GameStatus.PLAYER2WIN;
		}
		else if (currentHp2<=0){
			res=1;
			status=GameStatus.PLAYER1WIN;
		}
		
		return res;
		
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

	public boolean isPlayer1Turn() {
		return player1Turn;
	}

	public void setPlayer1Turn(boolean player1Turn) {
		this.player1Turn = player1Turn;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
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
