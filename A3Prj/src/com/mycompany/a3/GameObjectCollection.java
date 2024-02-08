/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.ui.Graphics;
// Array list Collection of GameObjects 
public class GameObjectCollection {
	private ArrayList<GameObject> objCollection = new ArrayList<GameObject>();
	public GameObjectCollection() {
		objCollection = new ArrayList<GameObject>();
	}
	public void add(GameObject newObj) {
		objCollection.add(newObj);
	}
	public ArrayList<GameObject> getObjects() {
		return objCollection;
	}
	public int getSize() {
		return objCollection.size();
	}
	public GameObject getElem(int index) {
		return objCollection.get(index);
	}
	public ArrayList<GameObject> getCollection() {
		return objCollection;
	}
}
