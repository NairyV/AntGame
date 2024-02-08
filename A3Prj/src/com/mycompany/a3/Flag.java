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

public class Flag extends Fixed{
	private int sequenceNumber;
	Flag(int size, float locX, float locY, int color, int seqNumber) {
		super(size, locX, locY, color);
		sequenceNumber = seqNumber;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public String toString() {
		return "Flag: "+super.toString()+" seqNum="+getSequenceNumber();
	}
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int) pPtrRelPrnt.getX(); // pointer location relative to
		int py = (int) pPtrRelPrnt.getY(); // parent origin
		int xLoc = (int) (pCmpRelPrnt.getX() + (getLocX()));
		int yLoc = (int) (pCmpRelPrnt.getY() + (getLocY()));
		if ( (px >= xLoc) && (px <= (xLoc+getSize())) && (py >= yLoc) && (py <= (yLoc+getSize()))) {
			return true;
		} else {
			return false;
		}
	}
	public void setColor(int r, int g, int b) { }
	public void draw(Graphics g, Point pCmpRelPrnt) {
		if(this.isSelected()) {
			highlightedDraw(g, pCmpRelPrnt);
		} else {
			drawNormal(g, pCmpRelPrnt);
		}
	}
	public void drawNormal(Graphics g, Point pCmpRelPrnt) {
		String textLabel = String.valueOf(sequenceNumber);
		int nPoints = 3;
		int x1 = (int) ((getLocX() - getSize()/2) + pCmpRelPrnt.getX());
		int x2 = (int) ((getLocX() + getSize()/2) + pCmpRelPrnt.getX());
		int x3 = (int) ((getLocX()) + pCmpRelPrnt.getX());
		int y1 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y2 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y3 = (int) ((getLocY() + getSize()/2) + pCmpRelPrnt.getY());
		int stringX = (int)((x1+x2+x3)/3);
		int stringY = (int)((y1+y2+y3)/3);
		int[] xPoints = {x1,x2,x3}; // base and height of 50
        int[] yPoints = {y1,y2,y3}; // base and height of 50
		g.setColor(super.getColor());
		g.fillPolygon(xPoints, yPoints, nPoints); // Draw a filled triangle
		g.setColor(ColorUtil.BLACK);
		g.drawString(textLabel,stringX, stringY);
	}
	public void highlightedDraw(Graphics g, Point pCmpRelPrnt) {
		String textLabel = String.valueOf(sequenceNumber);
		int nPoints = 3;
		int x1 = (int) ((getLocX() - getSize()/2) + pCmpRelPrnt.getX());
		int x2 = (int) ((getLocX() + getSize()/2) + pCmpRelPrnt.getX());
		int x3 = (int) ((getLocX()) + pCmpRelPrnt.getX());
		int y1 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y2 = (int) ((getLocY() - getSize()/2) + pCmpRelPrnt.getY());
		int y3 = (int) ((getLocY() + getSize()/2) + pCmpRelPrnt.getY());
		int stringX = (int)((x1+x2+x3)/3);
		int stringY = (int)((y1+y2+y3)/3);
		int[] xPoints = {x1,x2,x3}; // base and height of 50
        int[] yPoints = {y1,y2,y3}; // base and height of 50
		g.setColor(super.getColor());
		g.drawPolygon(xPoints, yPoints, nPoints);
		g.setColor(ColorUtil.BLACK);
		g.drawString(textLabel,stringX, stringY);
	}
	public void handleCollision(GameObject otherObj) {
		// If collide with flag
		System.out.println("Ant collided with Flag");
		if(Ant.getAnt().getLastFlagReached()+1 == getSequenceNumber()) {
			Ant.getAnt().setLastFlagReached(Ant.getAnt().getLastFlagReached()+1);
			System.out.println("Ant collided with Flag Number: "+ Ant.getAnt().getLastFlagReached());
		}
	}
}
