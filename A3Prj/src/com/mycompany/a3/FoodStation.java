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

public class FoodStation extends Fixed{
	private int capacity;
	int colorChange = 0;
	FoodStation(int size, float locX, float locY, int color) {
		super(size, locX, locY, color);
		capacity = size;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String toString() {
		return "FoodStation: "+super.toString()+" capacity="+getCapacity();
	}
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int) pPtrRelPrnt.getX(); // pointer location relative to
		int py = (int) pPtrRelPrnt.getY(); // parent origin
		int xLoc = (int) (pCmpRelPrnt.getX() + (getLocX()));
		int yLoc = (int) (pCmpRelPrnt.getY() + (getLocY()));
		if ( (px >= xLoc) && (px <= (xLoc+getSize()*2)) && (py >= yLoc) && (py <= (yLoc+getSize()*2))) {
			return true;
		} else {
			return false;
		}
	}
	// Filled squares
	public void draw(Graphics g, Point pCmpRelPrnt) {
		if(this.isSelected()) {
			highlightedDraw(g, pCmpRelPrnt);
		} else {
			drawNormal(g, pCmpRelPrnt);
		}
	}
	public void drawNormal(Graphics g, Point pCmpRelPrnt) {
		String textLabel = String.valueOf(capacity);
		int rectSize = super.getSize();
		int pointX = (int)(getLocX()-(getSize()/2) + pCmpRelPrnt.getX());
		int pointY = (int)(getLocY()-(getSize()/2) + pCmpRelPrnt.getY());
		g.setColor(super.getColor());
		g.fillRect(pointX, pointY, rectSize*4, rectSize*4);	
		g.setColor(ColorUtil.BLACK);
		g.drawString(textLabel,pointX, pointY);
	}
	public void highlightedDraw(Graphics g, Point pCmpRelPrnt) {
		String textLabel = String.valueOf(capacity);
		int rectSize = super.getSize();
		int pointX = (int)(getLocX()-(getSize()/2) + pCmpRelPrnt.getX());
		int pointY = (int)(getLocY()-(getSize()/2) + pCmpRelPrnt.getY());
		g.setColor(super.getColor());
		g.drawRect(pointX, pointY, rectSize*4, rectSize*4);
		g.setColor(ColorUtil.BLACK);
		g.drawString(textLabel,pointX, pointY);
	}
	public void handleCollision(GameObject otherObj) {
		System.out.println("Ant collided with Food");
		if(getCapacity() >= 1) {
			Ant.getAnt().setFoodLevel(Ant.getAnt().getFoodLevel()+ getCapacity());
			setCapacity(0);
			setColor(0,255,100);
		}
	}
}
