/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer{
	GameObjectCollection gameObjList = new GameObjectCollection();
	private GameWorld gw = new GameWorld();
	private Point origin = new Point(getAbsoluteX(),getAbsoluteY());
	public MapView(GameWorld gw) {
		this.gw = gw;
		this.gameObjList = gw.getCollection();
		getAllStyles().setBgTransparency(255);
		getAllStyles().setBgColor(ColorUtil.WHITE);
		getAllStyles().setBorder(Border.createLineBorder(4,
				ColorUtil.rgb(255, 0, 0)));
	}
	public void update(Observable observable, Object data) {
		repaint();
	}
	// override update
	@Override
	public void paint(Graphics g) {
		revalidate();
		origin = new Point(getX(),getY());
		for(int i=0; i< gameObjList.getSize(); i++) {
			gameObjList.getElem(i).draw(g, origin);
		}
	}
	public Container getMapView() {
		return this;
	}
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		for(int i = 0; i < gameObjList.getSize(); i++) {
			if(gameObjList.getElem(i).contains(pPtrRelPrnt, pCmpRelPrnt)) {
				if(gw.getPause())
					gameObjList.getElem(i).setSelected(true);
			} else {
				gameObjList.getElem(i).setSelected(false);
			}
		}
		repaint();
	}
}
