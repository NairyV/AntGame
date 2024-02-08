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

public class Spider extends Movable{
	//private Sound spiderSound;
	Spider(int size, float locX, float locY, int color, int heading, int speed) {
		super(size, locX, locY, color, heading, speed);
	}
	// Empty methods. Can't set once initiated.
	public void setFoodConsumption(int val) {}
	public void setLoc(float locX, float locY) {}
	public void setColor(int r, int g, int b) {}
	public void setFoodLevel() {}
	public void setSpeed(int speed) {}
	public void setFoodLevel(int foodLevel) {}
	public String toString() {
		return "Spider: "+super.toString();
	}
	// Unfilled Isosceles Triangle
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int nPoints = 3;
		int x1 = (int) ((getLocX() - getSize()/2) + pCmpRelPrnt.getX());
		int x2 = (int) ((getLocX() + getSize()/2) + pCmpRelPrnt.getX());
		int x3 = (int) ((getLocX()) + pCmpRelPrnt.getX());
		int y1 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y2 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y3 = (int) ((getLocY() + getSize()/2) + pCmpRelPrnt.getY());
		int[] xPoints = {x1,x2,x3}; // base and height of 50
        int[] yPoints = {y1,y2,y3}; // base and height of 50
		g.setColor(super.getColor());
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	public void highlightedDraw(Graphics g, Point pCmpRelPrnt) {
		int nPoints = 3;
		int x1 = (int) ((getLocX() - getSize()/2) + pCmpRelPrnt.getX());
		int x2 = (int) ((getLocX() + getSize()/2) + pCmpRelPrnt.getX());
		int x3 = (int) ((getLocX()) + pCmpRelPrnt.getX());
		int y1 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y2 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y3 = (int) ((getLocY() + getSize()/2) + pCmpRelPrnt.getY());
		int[] xPoints = {x1,x2,x3}; // base and height of 50
        int[] yPoints = {y1,y2,y3}; // base and height of 50
		g.setColor(super.getColor());
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	public void handleCollision(GameObject obj) {
		// If collide with spider
		System.out.println("Ant collided with Spider");
		Ant.getAnt().setColor(255, Ant.getAnt().getColor()+100, 0);
		// Reduce ant's health by 1 (10% less)
		Ant.getAnt().setHealthLevel(Ant.getAnt().getHealthLevel()-1);
		System.out.println("Speed Reduced");
		// reduce speed by 1
		Ant.getAnt().setSpeed(Ant.getAnt().getSpeed()-1);
	}
}
