/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;
import java.io.IOException;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
public class Sound {
	Sound collidedSound;
	private Media m;
	public Sound(String fileName) {
		if (Display.getInstance().getCurrent() == null){
			System.out.println("Error: Create sound objects after calling show()!");
			System.exit(0);
		}
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),
			"/"+fileName);
			if (is == null) {
	            System.out.println("Error loading resource: " + fileName);
	            return;
	        }
			m = MediaManager.createMedia(is, "audio/wav");
			if (m == null) {
	            System.out.println("Error creating media for: " + fileName);
	            return;
	        }
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void play() {
	//start playing the sound from time zero (beginning of the sound file)
		m.setTime(0);
		m.play();
	}
}
