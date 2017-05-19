package entities;

import basic.Game;
import basic.Handler;
import tiles.Tile;

public abstract class Creature extends Entity{

	//Default statistics
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64;
	public static final int DEFAULT_CREATURE_HEIGTH = 64;
	public static final int DEFAULT_CREATURE_AD = 5;
		
	protected float speed;
	protected int attackPower;
	protected float xMove, yMove;
	protected boolean isUnderAttack;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		attackPower = DEFAULT_CREATURE_AD;
		xMove = 0;
		yMove = 0;
		isUnderAttack = false;
	}
	
	//This will be calling by the tick() of extends classes
	public void move(){	
		
		//This will check if it is colliding with someone
		if(!checkEntityCollision(xMove, 0f)){
			moveX();
		}
		if(!checkEntityCollision(0f, yMove)){
			moveY();
		}
	}
	
	public void moveX(){
		if(xMove > 0){ //Move right
			
			//Gives us the coordinate of the tile we are trying to move to
			int tx = (int)((x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH);
			
			
			//Now we are checking the upper right corner of our bounds
			if(!collisionWithTile(tx, (int)((y + bounds.y) / Tile.TILE_HEIGHT))
					//And the lower right corner
					&& !collisionWithTile(tx, (int)((y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))){
	
				x += xMove;
			}	
			//For perfect collision 
			else{
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1; 
			}
		}
		else if(xMove < 0){ //Move left
			
			int tx = (int)((x + xMove + bounds.x) / Tile.TILE_WIDTH);
			
			
			if(!collisionWithTile(tx, (int)((y + bounds.y) / Tile.TILE_HEIGHT))
					&& !collisionWithTile(tx, (int)((y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))){
	
				x += xMove;
			} 
			else{
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x; 
			}
		}	
	}

	public void moveY(){	
		
		if(yMove < 0){ //Move up
			
			int ty = (int)((y + yMove + bounds.y) / Tile.TILE_HEIGHT);
				
			if(!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
	
				y += yMove;
			}
			else{
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y; 
			}
		}
		else if(yMove > 0){ //Move down
			
			int ty = (int)((y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT);
			
			if(!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty)
					&& !collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
	
				y += yMove;
			}
			else{
				y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1; 
			}
		}	
	}
	
	//Detect collision
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}

	//GETTERS AND SETTERS
	
	public int getHealth() {
		return health;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}		
}
