package bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import basic.Handler;

public abstract class Bullet{

	protected Handler handler;
	protected float x, y;
	protected int width, height;  
	protected int bulletSpeed, attackPower, health;
	protected Rectangle bounds;
	protected BulletManager bulletManager;
	
	public Bullet(Handler handler, float x, float y){
		
		this.handler = handler;
		
		this.x = x;
		this.y = y;
		
		health = 100;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void move();

	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public void die() {
		// TODO Auto-generated method stub
		
	}
}

