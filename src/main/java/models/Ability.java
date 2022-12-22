package models;

import java.io.Serializable;

public class Ability implements Serializable {
	public long id;
	public long monsterId;
	public String Name;
	public String description;
	public AbilityType type;
	public float power;
	
	
	public Ability() {
		super();
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(long monsterId) {
		this.monsterId = monsterId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AbilityType getType() {
		return type;
	}
	public void setType(AbilityType type) {
		this.type = type;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	
}
