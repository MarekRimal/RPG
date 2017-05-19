package items;

import java.awt.Graphics;

import basic.Handler;
import bullets.Bullet;
import bullets.SMGBullet;

public class SMG extends Gun{
	
	private int direction;
	private Handler handler; //Dont know why is this important to have there...
	private float playerX, playerY;
	private int bulletSpeed;
	
	public SMG(Handler handler, int attackPower){
		super(handler, attackPower);
		this.handler = handler;
		
		bulletSpeed = 5;
	}
	
	@Override
	public void fire() {
		
		playerX = handler.getWorld().getEntityManager().getPlayer().getX();
		playerY = handler.getWorld().getEntityManager().getPlayer().getY();
		
		//Direction of the bullet
		if(handler.getKeyManager().aRight){ 
			direction = 1; 
			handler.getWorld().getBulletManager().addBullet(new SMGBullet(handler, playerX, playerY, direction, attackPower, bulletSpeed, true));
		}
		else if(handler.getKeyManager().aDown){ 
			direction = 0; 
			handler.getWorld().getBulletManager().addBullet(new SMGBullet(handler, playerX, playerY, direction, attackPower, bulletSpeed, true));
		}
		else if(handler.getKeyManager().aLeft){ 
			direction = 3; 
			handler.getWorld().getBulletManager().addBullet(new SMGBullet(handler, playerX, playerY, direction, attackPower, bulletSpeed, true));
		}
		else if(handler.getKeyManager().aUp){ 
			direction = 2; 
			handler.getWorld().getBulletManager().addBullet(new SMGBullet(handler, playerX, playerY, direction, attackPower, bulletSpeed, true));
		}
		else{
			return;
		}
	
		//Fire the bullets
			
	}

	public void tick() {
		
		if (handler == null) {
		    System.out.println("OSLA PRCAT");
		}
		else{
			fire();
		}
		
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
