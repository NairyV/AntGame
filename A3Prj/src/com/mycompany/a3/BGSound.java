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

public class BGSound implements Runnable{
	private Media m;
	BGSound (String fileName) {
		if (Display.getInstance().getCurrent() == null){
			System.out.println("Error: Create sound objects after calling show()!");
			System.exit(0);}
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),
			"/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav", this);
			System.out.println("Passed");
		} catch (IOException e) {
			e.printStackTrace();}
		}
	public void pause(){ m.pause();} //pause playing the sound
	public void play(){ m.play();} //continue playing from where we have left off

	@Override
	public void run() {
		m.setTime(0);
		m.play();
	}
}

