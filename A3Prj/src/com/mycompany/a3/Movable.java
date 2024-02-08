/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

public class Movable extends GameObject{
	private int heading, foodLevel,healthLevel;
	private int speed;
	Movable(int size, float locX, float locY, int color, int heading, int speed) {
		super(size, locX, locY, color);
		this.heading = heading;
		this.speed = speed;
		foodLevel = 20; //default values
		healthLevel = 10;
	}

	public void setFoodConsumption(int val) {
		// change food level (by setting food consumption rate and waiting for next clock tick)
		foodLevel = val;
	}
	public int getHeading() {
		return heading;
	}
	public int getSpeed() {
		return speed;
	}
	public int getFoodLevel() {
		return foodLevel;
	}
	// Headings 0-270
	public void setHeading(int heading) {
		if(heading > 359) { //When heading reaches higher than 270 (e.g. 275 = 5)
			heading = heading - 359;
		} else if (heading < 0) {
			heading = heading + 359;
		}
		this.heading = heading;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setFoodLevel(int foodLevel) { 
		if(foodLevel <= 0) { // Food doesn't go negative
			foodLevel = 0;
		}
		this.foodLevel = foodLevel;
	}
	public void setHealthLevel(int healthLevel) {
		if(healthLevel <= 0) { // Health doesn't go negative
			healthLevel = 0;
		}
		this.healthLevel = healthLevel;
	}
	public int getHealthLevel() {
		return healthLevel;
	}
	public String toString() {
		return super.toString()+" heading="+getHeading()+" speed="+getSpeed()+" size="+getSize();
	}

}
