package bullets;

import java.awt.Color;
import java.awt.Graphics;

import basic.Handler;
import entities.Entity;
import tiles.Tile;

public class SMGBullet extends Bullet{

	private int bulletSpeed, attackPower;
	private int direction; 
	private boolean isFriendly; //If Hero shoot it
	
	public SMGBullet(Handler handler, float x, float y, int direction, int attackPower, int bulletSpeed, boolean isFriendly){
		super(handler, x, y); 
		
		bounds.x = 10;
		bounds.y = 10;
		bounds.width = 10;
		bounds.height = 10;	
		 
		width = 20;
		height = 20;
		
		this.attackPower = attackPower;
		this.bulletSpeed = bulletSpeed;
		this.isFriendly = isFriendly;
		
		this.direction = direction;
	}

	@Override
	public void tick() {
		
		move();
		checkHit();
		
		//Collision with some solid tile should remove the bullet
		if(handler.getWorld().getTile((int) x / Tile.TILE_WIDTH, (int) y/ Tile.TILE_HEIGHT).isSolid()){
			handler.getWorld().getBulletManager().removeBullet(this);
			System.out.println("Ahoj zmrde");
		}
	}
	
	public void render(Graphics g) {
	
		//System.out.println("KONDOOOOO");
		g.setColor(Color.RED);
		g.fillOval((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);		
	}
	
	public void checkHit(){
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(isFriendly){
				if(e.equals(handler.getWorld().getEntityManager().getPlayer())){
					continue;
				}
				if(e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))){
					e.hurt(attackPower);
					handler.getWorld().getBulletManager().removeBullet(this); 	
				}
			}
			else{
				if(!e.equals(handler.getWorld().getEntityManager().getPlayer())){
					continue;
				}
				if(e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))){
					e.hurt(attackPower);
					handler.getWorld().getBulletManager().removeBullet(this); 	
				}
			}	
		}
	}
	
	public void move(){
		
		if(direction == 0){
			y += bulletSpeed;
		}
		if(direction == 1){
			x += bulletSpeed;
		}
		if(direction == 2){
			y -= bulletSpeed;
		}
		if(direction == 3){
			x -= bulletSpeed;
		}
	}
}
