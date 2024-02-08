/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.TextHolder;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

import java.lang.String;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Form implements Runnable{
	private GameWorld gw; // GameWorld object
	private MapView mv;
	private ScoreView sv;
	//private BGSound BGMusic;
	private Button tmp = new Button("Button One");
	UITimer timer = new UITimer(null);
	Timer gameClockTimer;
	Game() {
		Game currGame = this;
		gw = new GameWorld(); // create Observable GameWorld
		sv = new ScoreView(gw); // create an Observer for the game/ant state data
		gw.addObserver(sv); // register the score observer
		mv = new MapView(gw); // create an Observer for the map
		gw.addObserver(mv); // register the map observer

		gw.init(); // initialize world
		gw.notifyObservers();
		// Tick timer
		timer = new UITimer(currGame);
		timer.schedule(120, true, currGame); // 1s = 1 tick
		// Game Clock timer
		gameClockTimer = new Timer();
		gameClockTimer.schedule(new TimerTask() {
            @Override
            public void run() {
               gw.gameClock();
            }
        }, 0, 1000);
		
		currGame.setLayout(new BorderLayout());
		currGame.add(BorderLayout.NORTH,sv.scoreInfo()); // Add updated (observer) method for Score View to North Border
		currGame.add(BorderLayout.WEST,leftContainer()); // Add left container method to West Border
		currGame.add(BorderLayout.EAST,rightContainer()); // Add right container method to East Border
		currGame.add(BorderLayout.CENTER,mv.getMapView()); // Add map info (Blank Screen) container method to Center Border
		
		
		
		// Game Title
		Form titleLabel = new Form("The Journey Game",new BoxLayout(BoxLayout.Y_AXIS));
		titleLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		
		// Tool Bar
		Toolbar myToolBar = new Toolbar();
		currGame.setToolbar(myToolBar);
		Command sideMenuItem1 = new Command("Side Menu"); // Side Menu Command
		myToolBar.addCommandToSideMenu(sideMenuItem1); // Add Command to tool bar
		
		Command accel2Command = new Command("Accelerate") {
			public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
            	gw.accelerate();
            }
		};
		Button accel2 = new Button(accel2Command);
		accel2 = buttonSpecs(accel2);				// "" "" ""
        // Set Style to Acceleration located in side menu
		accel2.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		accel2.getAllStyles().setFgColor(ColorUtil.BLACK);
		accel2.getAllStyles().setPadding(Component.TOP, 3);
		accel2.getAllStyles().setPadding(Component.BOTTOM, 3);
		// Help Button
		Command infoButton = new Command("Help") { // Info button Command with the action event
            @Override
            public void actionPerformed(ActionEvent evt) { // Displays controls and what each button invokes
                // Code to be executed when the Command is invoked
            	Dialog.show("Help", 
          			  "a - Accelerate:  Increases the speed of the Ant.\n"
          			+ "b - Break: Reduces the speed of the Ant.\n"
          			+ "l - Left: Changing the direction of the Ant to the left.\n"
          			+ "r - Right: Changing the direction of the Ant to the Right.\n"
          			+ "Collide With Flag: Type a number 1 through 4 to in incremental order to reach last Flag.\n"
          			+ "g - Collide With Spider: Ant collides with spider losing health and speed.\n"
          			+ "f - Collide With Food Station: Repleneshes your Food level.\n"
          			+ "t -Tick: Ticks through the game increasing speed and decrease food level.\n", 
          			"Cancel", "Ok");
            }
        };
		CheckBox soundCheckBox = new CheckBox("Turn the sound ON or OFF"); // Sound check box
		soundCheckBox.getAllStyles().setBgTransparency(255); // check box style
		soundCheckBox.getAllStyles().setBgColor(ColorUtil.LTGRAY); // check box style
		
		// Exit Command calling exit method
		Command exitButton = new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
            	exitGame();
            }
        };
        // About command displaying information about Author and course
		Command aboutButton = new Command("About") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
            	Dialog.show("About", 
            			  "Author Name: John Nairy Vardanyan\n"
            			+ "Course Name: CSC 133:  Object-Oriented Computer Graphics Programming\n"
            			+ "Version: 2", 
            			"Cancel", "Ok");
            }
        };
        // Adding buttons/labels to tool bar
		myToolBar.addCommandToRightBar(infoButton); // add action
		myToolBar.addComponentToSideMenu(soundCheckBox);
		myToolBar.setTitleComponent(titleLabel);
		myToolBar.addCommandToSideMenu(aboutButton); // add action
		myToolBar.addCommandToSideMenu(exitButton); // add action
		myToolBar.addComponentToSideMenu(accel2);
		soundCheckBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		        if (soundCheckBox.isSelected()) { // Check if the checkbox is checked
		        	gw.BGMusic.play();
					gw.setSound(true);
					sv.setSoundOn(true);
		        } else {
		        	gw.BGMusic.pause(); // Pause the background music
		            gw.setSound(false); // Update sound status in gw
		            sv.setSoundOn(false); // Update sound status in sv
		        }
		    }
		});
		//bot Container with the GridLayout positioned on the north
		Container botContainer = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
		botContainer.getAllStyles().setPadding(Component.LEFT,500);		
		Button posButton = new Button("Position");
		botContainer.add(positionSpecs(posButton));
		Button Pause = buttonSpecs(new Button("Pause"));
		botContainer.add(Pause);
		Button Play = buttonSpecs(new Button("Play"));
		Pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				timer.cancel(); // Stops timer
				gameClockTimer.cancel();
				buttonSpecs(posButton); // Changes color of position button
				Pause.remove(); // removes Pause button
				botContainer.add(Play); // adds Play button in same spot
				gw.BGMusic.pause();
				gw.setSound(false);
				sv.setSoundOn(false);
				soundCheckBox.setSelected(false);
				gw.setPause(true);
			}
		});
		Play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				timer.schedule(120,true,currGame); // Continues timer
				gameClockTimer = new Timer();
				gameClockTimer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		               gw.gameClock();
		            }
		        }, 0, 1000);
				posButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						// TODO Auto-generated method stub
						// Add object selection here.
					}
				});
				positionSpecs(posButton); // Changes color of position button
				Play.setEnabled(true);
				Play.remove(); // remove Play button
				botContainer.add(Pause); // add Pause button
				gw.setPause(false);
			}
		});
		currGame.add(BorderLayout.SOUTH,botContainer); // Add Bottom container method to South Border
		
		this.show();
		gw.createGameWorldSounds();
		revalidate();
	}
	// Asks user to confirm to exit application
	public void exitGame() {
		Command cCancel = new Command("Cancel");
		boolean c = Dialog.show("Confirmation","Are you sure you want to exit?", "Cancel", "Ok");
		if (!c) {
			Display.getInstance().exitApplication();
		}
	}
	// Left container with Accelerate and Left buttons and it's proper styling
	public Container leftContainer() {
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.getAllStyles().setPadding(Component.TOP, 100);
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(4,
				ColorUtil.GRAY));
		// Accelerate command
		Command accelCommand = new Command("Accelerate") {
			public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
				gw.accelerate();
            }
		};
		// Left command
		Command leftCommand = new Command("Left") {
			public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
				gw.antHeading(false);
            }
		};
		// Key bindings
		addKeyListener('a',accelCommand);
		addKeyListener('l',leftCommand);
		// Add buttons to left container
		leftContainer.add(buttonSpecs(new Button(accelCommand)));
		leftContainer.add(buttonSpecs(new Button(leftCommand)));
		
		return leftContainer;
	}
	// Right container with Break and Right buttons and it's proper styling
	public Container rightContainer() {
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		rightContainer.getAllStyles().setPadding(Component.TOP, 100);
		rightContainer.getAllStyles().setBorder(Border.createLineBorder(4,
				ColorUtil.GRAY));
		// Break Command
		Command breakCommand = new Command("Break") {
			public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
				gw.reduceSpeed();
            }
		};
		// Right Command
		Command rightCommand = new Command("Right") {
			public void actionPerformed(ActionEvent evt) {
                // Code to be executed when the Command is invoked
				gw.antHeading(true);
            }
		};
		// Key bindings
		addKeyListener('b',breakCommand);
		addKeyListener('r',rightCommand);
		// Add commands to right container
		rightContainer.add(buttonSpecs(new Button(breakCommand)));
		rightContainer.add(buttonSpecs(new Button(rightCommand)));
		
		return rightContainer;
	}
	// Buttons design method
	public Button buttonSpecs(Button b) {
		b.getUnselectedStyle().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		b.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		b.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		b.getSelectedStyle().setBgColor(ColorUtil.GRAY);
		b.getAllStyles().setPadding(Component.TOP, 5);
		b.getAllStyles().setPadding(Component.BOTTOM, 5);
		return b;
	}
	public Button positionSpecs(Button b) {
		b.getUnselectedStyle().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.WHITE);
		b.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		b.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		b.getSelectedStyle().setBgColor(ColorUtil.GRAY);
		b.getAllStyles().setPadding(Component.TOP, 5);
		b.getAllStyles().setPadding(Component.BOTTOM, 5);
		return b;
	}
	@Override
	public void run() {
		gw.tick();
	}
}
