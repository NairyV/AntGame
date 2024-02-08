/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;

public class GameObject extends Container implements IDrawable, ICollider,ISelectable{
	private int SIZE;
	private Point loc;
	private int color;
	private boolean selected;
	Vector<GameObject> collisionVector = new Vector<GameObject>();
	GameObject(int size, float locX, float locY, int color) {
		SIZE = size;
		loc = new Point(locX,locY); // Sets Point x and y to passed values
		this.color = color;
	}
	public int getSize() {
		return SIZE;
	}
	public Point getLoc() {
		return loc;
	}
	public float getLocX() {
		return loc.getX();
	}
	public float getLocY() {
		return loc.getY();
	}
	public int getColor() {
		return color;
	}
	public void clearVector() {
		collisionVector = new Vector<GameObject>();
	}
	public Vector<GameObject> getCollisionVector() {
		return collisionVector;
	}
	public void setLocX(float locX) {
		if(locX >= 1000) {
			loc.setX(1000);
		}
		if(locX <= 0) {
			loc.setX(0);
		}
		loc.setX(locX);
	}
	public void setLocY(float locY) {
		if(locY >= 1000) {
			loc.setY(1000);
		}
		if(locY <= 0) {
			loc.setY(0);
		}
		loc.setY(locY);
	}
	public void setLoc(float locX, float locY) {
		loc = new Point(locX,locY);
	}
	public void setColor(int r, int g, int b) {
		color = ColorUtil.rgb(r, g, b);
	}
	public String toString() {
		return "loc="+ Math.round(getLocX()*10.0)/10.0 +","+Math.round(getLocY()*10.0)/10.0+" color=["+ColorUtil.red(color)+","+ColorUtil.green(color)+","+ColorUtil.blue(color)+"]";
	}
	public void draw(Graphics g, Point pCmpRelPrnt) {}
	public void highlightedDraw(Graphics g, Point pCmpRelPrnt) {}
	public boolean collideWith(GameObject obj) {
		boolean result = false;
		// find center
		int thisCenterX = (int) (getLocX() + (getSize()/2));
		int thisCenterY = (int) (getLocY() + (getSize()/2));
		int otherCenterX = (int) (obj.getLocX() + (obj.getSize()/2));
		int otherCenterY = (int) (obj.getLocY() + (obj.getSize()/2));
		// find dist between centers (use square, to avoid taking roots)
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		// find square of sum of radii
		int thisRadius = getSize()/2;
		int otherRadius = (obj.getSize())/2;
		
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius
				+ otherRadius*otherRadius);
		
		if (distBetweenCentersSqr+15 <= radiiSqr) { result = true ; }
		return result ;
	}
	public void handleCollision(GameObject obj) { }
	public void setSelected(boolean b) { this.selected = b; }
	public boolean isSelected() { return this.selected; }
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) { return false; }

}
