package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

//This will hold all ui objects like buttons etc
//Its like a entity class
public abstract class UIObject {

	float x, y;
	int width, height;
	protected Rectangle bounds;
	//This indicates if the mouse is hovering above the button
	protected boolean hovering = false; 
	
	//In constructor we have the position and parameters of the object
	public UIObject(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	public abstract void tick();
	public abstract void render(Graphics g);
	
	//Every object will have a special onClick method
	// ..and every object/button do different things
	public abstract void onClick();
	
	//The UIManager is calling all these methods below
	
	public void onMouseMove(MouseEvent e){
		
		if(bounds.contains(e.getX(), e.getY()))
			hovering = true;
		else
			hovering = false;
	}
	
	public void onMouseRelease(MouseEvent e){
		if(hovering){
			onClick();
		}
	}
	
	//GETTERS SETTERS
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
}
