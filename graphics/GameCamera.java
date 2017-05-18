package graphics;

import basic.Game;
import basic.Handler;
import entities.Entity;
import tiles.Tile;

public class GameCamera {

	private Handler handler;
	private float xOffset, yOffset;
	
	//This offset tells us by how much pixels the object will be shifted
	public GameCamera(Handler handler, float xOffset, float yOffset){
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	//Check whether the camera is moving outside of the map (blankspace)
	//If so -> stop move
	public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		}
		if(xOffset > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth()){
			xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
		}
		
		if(yOffset < 0){
			yOffset = 0;
		}
		if(yOffset > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()){
			yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
		}
	}
	
	//Center the camera on our player
	public void centerOnEntity(Entity e){
		//If we would not divide it by two, the player will be on the right edge of the screen
		//The rest of the line is centering on the center of the player image
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2; 
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		
		//Before the end of the method we check the blank space
		checkBlankSpace();
	}

	//Move with our player until there is blankspace
	public void move(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace(); 
	}
	
	//GETTERS SETTERS
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	
}
