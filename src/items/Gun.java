package items;

import java.awt.Graphics;

import basic.Handler;
import bullets.BulletManager;

public abstract class Gun {
	
	protected Handler handler;
	protected float x, y;
	protected BulletManager bulletManager;
	protected int bulletSpeed, fireRate, attackPower;
	
	public Gun(Handler handler, int attackPower){
		this.attackPower = attackPower;
		this.x = x;
		this.y = y;
	}
	
	public abstract void fire();
	public abstract void tick();
	public abstract void render(Graphics g);
}
