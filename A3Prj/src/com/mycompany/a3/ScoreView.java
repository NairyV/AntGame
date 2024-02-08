/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;

public class ScoreView extends Container implements Observer{
	private String soundOn = "OFF"; // Sound ON/OFF checkbox string (default OFF)
	GameWorld gw;
	Container topContainer = new Container(new GridLayout(1,1));
	Label displayInfo = new Label();
	public ScoreView(GameWorld gw) {
		this.gw = gw; // get GameWorld
	}
	@Override
	public void update(Observable observable, Object data) {
		if (gw.getLives() == 0) { // if No lives, exit
			gameOver();
		}
		if (Ant.getAnt().getLastFlagReached()==4 && gw.getLives()!=0) {
			gameWon();
		}
		displayInfo.setText(getDisplay()); // update text
	}
	// Displays contents of Score
	public String getDisplay() {
		return "Time:   "+gw.getGameClock()+"   Lives Left:    "+gw.getLives()+"   Last Flag Reached:    "+Ant.getAnt().getLastFlagReached()+"   Food Level:   "+Ant.getAnt().getFoodLevel()+
				"   Health Level:   "+Ant.getAnt().getHealthLevel()+"   Sound:   "+soundOn;
	}
	// styles the text and sets padding
	public Container scoreInfo() {
		revalidate();
		displayInfo.getAllStyles().setFgColor(ColorUtil.rgb(4, 28, 145));
		topContainer.add(displayInfo);
		topContainer.getAllStyles().setPadding(Component.LEFT,300);
		return topContainer;
	}
	public void setSoundOn(Boolean bVal) {
		if (bVal) {
			soundOn = "ON";
		} else {
			soundOn = "OFF";
			
		}
	}
	public String getSoundOFF() {
		return soundOn;
	}
	// Game Over display
	public void gameOver() {
		Dialog gameOver = new Dialog("Game Over");
		gameOver.add("Game Over, you failed!");
		Button cOk = new Button("OK");
		gameOver.add(cOk);
		cOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Close the dialog when "OK" is clicked
            	Display.getInstance().exitApplication();
            }
        });
		gameOver.show();
	}
	public void gameWon() {
		Dialog gameOver = new Dialog("Game over");
		gameOver.add("Game Over, you win!\n  Total time: "+gw.getGameClock());
		Button cOk = new Button("OK");
		gameOver.add(cOk);
		cOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Close the dialog when "OK" is clicked
            	Display.getInstance().exitApplication();
            }
        });
		gameOver.show();
	}
}
