/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
@SuppressWarnings("static-access")
public class GameWorld extends Observable{
	private float MIN = 0; // min location
	private float MAX = 1000; // max location
	private int colorChange = 0;
	private int gameClock = 0; // game clock (ticks)
	private int lives = 3; // starting lives
	private boolean soundOn = false;
	private boolean pause = false;
	private Random rand = new Random();
	// Game object list class called GameObjectCollection
	GameObjectCollection gameObjList = new GameObjectCollection();
	Sound spiderSound, flagSound, foodSound;
	BGSound BGMusic;
	public void init(){
		int tmp = 4;
		setChanged();
		GameObject flag1 = new Flag(100, 100, 100, ColorUtil.rgb(3, 157, 252), 1);
		GameObject flag2 = new Flag(100, 400, 700, ColorUtil.rgb(3, 157, 252), 2);
		GameObject flag3 = new Flag(100, 700, 300, ColorUtil.rgb(3, 157, 252), 3);
		GameObject flag4 = new Flag(100, 800, 600, ColorUtil.rgb(3, 157, 252), 4);
		gameObjList.add(flag1);
		gameObjList.add(flag2);
		gameObjList.add(flag3);
		gameObjList.add(flag4);
		gameObjList.add(flag4);
		Ant player1 = Ant.getAnt();
		gameObjList.add(player1);
		// Spiders [unfilled isosceles triangles] drawPolygon()
		GameObject spider1 = new Spider((rand.nextInt(100)+30), rand.nextInt(1001), 
				rand.nextInt(1001), ColorUtil.BLACK,rand.nextInt(360),rand.nextInt(6)+5);
		GameObject spider2 = new Spider((rand.nextInt(100)+30), rand.nextInt(1001), 
				rand.nextInt(1001), ColorUtil.BLACK,rand.nextInt(360),rand.nextInt(6)+5);		
		gameObjList.add(spider1);
		gameObjList.add(spider2);
		
		//FOODSTATIONS [Filled squares]
		GameObject food1 = new FoodStation(rand.nextInt(21)+10, rand.nextInt(1001), rand.nextInt(1001), ColorUtil.rgb(0,255,0));
		GameObject food2 = new FoodStation(rand.nextInt(21)+10, rand.nextInt(1001), rand.nextInt(1001), ColorUtil.rgb(0,255,0));
		gameObjList.add(food1);
		gameObjList.add(food2);
		
		notifyObservers();
	}
	// Restarts World
	public void restartWorld() {
		System.out.println("Restarting world...");
		map();
		Ant.getAnt().restartAnt();
		new GameWorld();
		createGameWorldSounds();
	}
	//Tick
	public void tick() {
		setChanged();
		spiderHeading(); // change spider's heading
		//map();
		// loop through the game objects
		for(int i = 0; i < gameObjList.getSize(); i++) {
			// if instance of movable
			if(gameObjList.getElem(i) instanceof Movable) {
				move(gameObjList.getElem(i));
			}	
		}
		for(int i = 0; i < gameObjList.getSize(); i++) {
			if(Ant.getAnt()!=gameObjList.getElem(i)) {
				if(Ant.getAnt().collideWith(gameObjList.getElem(i)) && 
						!Ant.getAnt().getCollisionVector().contains(gameObjList.getElem(i)) &&
						!gameObjList.getElem(i).getCollisionVector().contains(Ant.getAnt())) {
					gameObjList.getElem(i).handleCollision(Ant.getAnt());
					Ant.getAnt().getCollisionVector().add(gameObjList.getElem(i));
					gameObjList.getElem(i).getCollisionVector().add(gameObjList.getElem(i));
					if(gameObjList.getElem(i) instanceof FoodStation) {
						foodSound.play();
						addFood();						
					}else
					if(gameObjList.getElem(i) instanceof Spider) {
						spiderSound.play();
					}else
					if(gameObjList.getElem(i) instanceof Flag && ((Flag) gameObjList.getElem(i)).getSequenceNumber() == 1) {
						continue;
					} else {
						flagSound.play();
					}
				}
			}
		}
		
		if(Ant.getAnt().getHealthLevel() <= 0 || Ant.getAnt().getFoodLevel() <= 0) {
			lives--; // reduce lives by 1
			restartWorld();
		}
		if(Ant.getAnt().getSpeed() <= 0) {
			Ant.getAnt().setSpeed(0);
		}
		notifyObservers();
		
	}
	public boolean addFood() {
		if(true) {
			gameObjList.add(new FoodStation(rand.nextInt(21)+10, rand.nextInt(1001), rand.nextInt(1001), ColorUtil.rgb(0,255,0)));
			return false;
		}
		return false;
	}
	// set food consumption rate
	public void setFoodConsumptionRate(int val) {
		// set food consumption with the passed value
		Ant.getAnt().setFoodConsumptionRate(val);
		System.out.println("Food consumption rate has been set to: "+val);
	}
	// Increase speed by 1
	public void accelerate() {
		System.out.println("Speed increased");
		Ant.getAnt().setSpeed(Ant.getAnt().getSpeed()+1);
		// If ant's speed is greater than max speed, set speed to its current max speed
		if(Ant.getAnt().getSpeed() >= Ant.getAnt().getMaximumSpeed()) {
			Ant.getAnt().setSpeed(Ant.getAnt().getMaximumSpeed());
		}
	}
	// Reduce the speed of the ant by 1
	public void reduceSpeed() {
		System.out.println("Speed Reduced");
		// reduce speed by 1
		Ant.getAnt().setSpeed(Ant.getAnt().getSpeed()-1);
		if(Ant.getAnt().getSpeed() <= 0) {
			Ant.getAnt().setSpeed(0);
		}
	}
	// Changes spider's heading
	public void spiderHeading() {
		boolean dir = true;	
		for(int i = 0; i < gameObjList.getSize(); i++) {
			// If instance of spider
			if(gameObjList.getElem(i) instanceof Spider) {
				// If spider heads too far east
				if(gameObjList.getElem(i).getLocX() >= MAX) {
					((Movable) gameObjList.getElem(i)).setHeading(270); // head west
					dir = false;
				}
				// If spider heads too far north
				if(gameObjList.getElem(i).getLocY() >= MAX) {
					((Movable) gameObjList.getElem(i)).setHeading(180);// go south
					dir = true;
				}
				// if spider heads too far west
				if(gameObjList.getElem(i).getLocX() <= MIN) {
					((Movable) gameObjList.getElem(i)).setHeading(90); // head east
					dir = false;
				}
				// if spider heads too far south
				if(gameObjList.getElem(i).getLocY() <= MIN) {
					((Movable) gameObjList.getElem(i)).setHeading(0); // head north
					dir = false;
				}
				// if spider is not too far east or north
				if (dir) {
					// reduce heading by 5
					((Movable) gameObjList.getElem(i)).setHeading(((Movable) gameObjList.getElem(i)).getHeading()-5);
				// if spider is too fast west or south
				} else if (!dir){
					// increase heading by 5
					((Movable) gameObjList.getElem(i)).setHeading(((Movable) gameObjList.getElem(i)).getHeading()+5);
				}
			}
			
		}
		
	}
	// Change ant's heading. Passes a boolean variable: True = right, False = left
	public void antHeading(boolean dir) {
		if (dir) {
			System.out.println("Right");
			Ant.getAnt().setHeading(Ant.getAnt().getHeading()+5);
		}
		else if (!dir) {
			System.out.println("left");
			Ant.getAnt().setHeading(Ant.getAnt().getHeading()-5);
		}
		
	}
	public void move(Object obj) {
		if(obj instanceof Movable) {
			float deltaX = (float) Math.cos((float) Math.toRadians(90-((Movable) obj).getHeading()))*((Movable) obj).getSpeed();
			float deltaY = (float) Math.sin((float) Math.toRadians(90-((Movable) obj).getHeading()))*((Movable) obj).getSpeed();
			((Movable) obj).setLocX(((Movable) obj).getLocX() + deltaX);
			((Movable) obj).setLocY(((Movable) obj).getLocY() + deltaY);
			if(((Movable) obj).getLocX() > MAX) {
				((Movable) obj).setLocX(MAX);
			}
			if(((Movable) obj).getLocY() > MAX) {
				((Movable) obj).setLocY(MAX);
			}
			if(((Movable) obj).getLocX() < MIN) {
				((Movable) obj).setLocX(MIN);
			}
			if(((Movable) obj).getLocY() < MIN) {
				((Movable) obj).setLocY(MIN);
			}
		}
	}
	// Displays contents of object
	public void map() {
		for(int i = 0; i < gameObjList.getSize(); i++) {
			System.out.println(gameObjList.getElem(i).toString());
		}
	}
	// Get lives
	public int getLives() {
		return this.lives;
	}
	// returns Game object list
	public GameObjectCollection getCollection() {
		return gameObjList;
	}
	public void gameClock() {
		// reduce food every tick.
		Ant.getAnt().setFoodLevel(Ant.getAnt().getFoodLevel()-Ant.getAnt().getFoodConsumptionRate());
		gameClock = gameClock+1;
		map();
	}
	// gets game clock
	public int getGameClock() {
		return gameClock;
	}
	public void createGameWorldSounds() {
		BGMusic = new BGSound("CSC133BGMusic.wav");
		foodSound = new Sound("foodCollideSound.wav");
		spiderSound = new Sound("spiderCollideSound.wav");
		flagSound = new Sound("flagCollideSound.wav");
		flagSound = new Sound("flagCollideSound.wav");
	}
	public boolean getSound() {
		return soundOn;
	}
	public void setSound(boolean b) {
		soundOn = b;
	}
	public boolean getPause() {
		return pause;
	}
	public void setPause(boolean b) {
		pause = b;
	}
}
