/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface IDrawable {
	void draw(Graphics g, Point pCmpRelPrnt);
	void highlightedDraw(Graphics g, Point pCmpRelPrnt);
}
