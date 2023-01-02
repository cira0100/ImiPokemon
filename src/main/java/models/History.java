package models;

import java.io.Serializable;
import java.sql.Date;

public class History implements Serializable {
	
	public MonsterViewModel pokemon;
	public Date time;
	public int result;
	
	
	public History() {
		
		
		
	}
	public MonsterViewModel getPokemon() {
		return pokemon;
	}
	public void setPokemon(MonsterViewModel pokemon) {
		this.pokemon = pokemon;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	

}
