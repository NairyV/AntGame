/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable{
	private int maximumSpeed;
	private int lastFlagReached, foodConsumptionRate, colorChange;
	private static Ant ant;
	private Ant(int size, float locX, float locY, int color, int heading, int speed) {
		super(size, locX, locY, color,heading, speed);
		maximumSpeed = 20; // Default values
		lastFlagReached = 1;
		foodConsumptionRate = 1;
	}
	public static Ant getAnt() {
		if(ant == null) {
			ant = new Ant(30,100,100,ColorUtil.rgb(255, 0, 0),0,5);
		} 
		return ant;
	}
	public void restartAnt() {
		Ant.getAnt().setHealthLevel(10);
		Ant.getAnt().setFoodLevel(20);
		Ant.getAnt().setLocX(100);
		Ant.getAnt().setLocY(100);
		Ant.getAnt().setSpeed(5);
		Ant.getAnt().setHeading(0);
		Ant.getAnt().setLastFlagReached(1);
		Ant.getAnt().clearVector();
	}
	public int getMaximumSpeed() {
		return maximumSpeed;
	}
	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}
	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}
	public void setFoodConsumptionRate(int foodConsumptionRate) {
		this.foodConsumptionRate = foodConsumptionRate;
	}
	public void setLastFlagReached(int flagNumber) {
		lastFlagReached = flagNumber;
	}
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	public String toString() {
		return "Ant: "+super.toString()+"\n maxSpeed="+getMaximumSpeed()+" foodConsumptionRate="+getFoodConsumptionRate();
	}
	// Filled Circle
		// To draw a filled circle with radius r at location (x,y)use fillArc(x, y, 2*r, 2*r, 0, 360)
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		g.fillArc((int)(getLocX()-(getSize()/2)+pCmpRelPrnt.getX()), (int)(getLocY()-(getSize()/2)+pCmpRelPrnt.getY()), getSize()*2, getSize()*2, 0, 360);
	}
	public void highlightedDraw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		g.fillArc((int)(getLocX()-(getSize()/2)+pCmpRelPrnt.getX()), (int)(getLocY()-(getSize()/2)+pCmpRelPrnt.getY()), getSize()*2, getSize()*2, 0, 360);
	}
	public void handleCollision(GameObject obj) {}

}
