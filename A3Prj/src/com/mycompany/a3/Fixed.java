/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

public class Fixed extends GameObject implements ISelectable{
	Fixed(int size, float locX, float locY, int color) {
		super(size, locX, locY, color);
	}
	// empty methods. Can't set location once initialized.
	public void setLocX(float locX) {}
	public void setLocY(float locY) {}
	public void setLoc(float locX, float locY) {}
	
	public String toString() {
		return super.toString()+" size="+getSize();
	}
}
