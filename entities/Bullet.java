package entities;

import java.awt.Color;
import java.awt.Graphics;

import basic.Handler;

public class Bullet extends StaticEntity{

	private double vectorX, vectorY, c;
	private int bulletSpeed;
	
	public Bullet(Handler handler, float x, float y, double vectorX, double vectorY, double c, int speed){
		super(handler, x, y, 10, 10); 
		
		bounds.x = 10;
		bounds.y = 10;
		bounds.width = 10;
		bounds.height = 10;	
		health = 100; 
		width = 20;
		height = 20;
		this.vectorX = vectorX;
		this.vectorY = vectorY;
		this.c = c;
		bulletSpeed = speed;
	}

	@Override
	public void tick() {
		
		//Collision with some solid tile should remove the bullet
		if(handler.getWorld().getTile((int) x, (int) y).isSolid()){
			handler.getWorld().getBulletManager().removeBullet(this);
		}
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.RED);
		g.fillOval((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);	
		
		
		//Tadyto by se melo jeste odecitat, podle toho, jestli jsem vpravo nebo levo
		x+=bulletSpeed;
		
		
		//If you modify this, it has no effect
		y = (float) ((vectorX * x - c)/vectorY); //The equation
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
}
