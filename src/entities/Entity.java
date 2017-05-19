package entities;
import java.awt.Graphics;
import java.awt.Rectangle;

import basic.Game;
import basic.Handler;

public abstract class Entity {
	
	public static final int DEFAULT_HEALTH = 10;
	
	//Extends has access
	protected Handler handler;
	protected float x, y;	//Position
	protected int width, height;   //Size
	protected int health;
	protected boolean alive = true;
	protected boolean isUnderAttack = false;

	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		
		//x and y is the length from the edges of the box to the boundary box
		//Width and height is the parameters of the bounding box	
		bounds = new Rectangle(0, 0, width, height);
	}
		
	public abstract void tick();
	public abstract void render(Graphics g);
	
	//This will return the bounding box of the Entity
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public abstract void die(); //Every creature will have heir own die method
	
	//If the entity is under attack
	public void hurt(int dmg){
		health -= dmg;
		isUnderAttack = true; //Animation if the entity takes dmg
		if(health <= 0){
			alive = false;
		}
		die(); 
	}
	
	public boolean checkEntityCollision(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)){
				continue;
			}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))){
				return true;
			}
		}
		return false;
	}

	//Getters and Setters
	
	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
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
