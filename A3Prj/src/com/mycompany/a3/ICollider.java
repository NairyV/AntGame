/*
 * @author John Nairy Vardanyan
 * Student ID: 301471508
 * Class: CSC 133
 * Date: 20 November 23
 */
package com.mycompany.a3;

public interface ICollider {
	boolean collideWith(GameObject obj);
	void handleCollision(GameObject obj);
}
