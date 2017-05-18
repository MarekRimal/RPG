package entities;

import java.awt.Graphics;
import java.util.LinkedList;

import basic.Handler;

public class BulletManager {

	private Handler handler;

	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	
	//Actual one bullet
	private Bullet tempBullet;
	
	public BulletManager(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		for(int i = 0; i < bullets.size(); i++){
			tempBullet = bullets.get(i);
			
			tempBullet.tick();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < bullets.size(); i++){
			tempBullet = bullets.get(i);
			
			tempBullet.render(g);
		}
	}
	
	public void addBullet(Bullet bullet){
		bullets.add(bullet);
	}
	
	public void removeBullet(Bullet bullet){
		bullets.remove(bullet);
	}

}
