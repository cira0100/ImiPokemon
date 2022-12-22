package models;

import java.io.Serializable;
import java.util.ArrayList;

public class PokemonAddModel implements Serializable{
	public String name;
	public String description;
	public int hp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public void setAbilities(ArrayList<Ability> abilities) {
		this.abilities = abilities;
	}
	public String base64Image;
	public ArrayList<Ability> abilities;
	public PokemonAddModel() {
		super();
	}

}
