package entities;

import java.awt.Graphics;

import basic.Handler;
import graphics.Assets;
import items.Item;
import tiles.Tile;

public class Lever extends StaticEntity{
	
	private boolean isOn;
	
	//This is the ID of the tile on which the lever will have effect
	private int tileID;
	
	public Lever(Handler handler, float x, float y, int tileID){
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT); //2 * taller
		
		bounds.x = 10;
		bounds.y = 10;
		bounds.width = width - 20;
		bounds.height = 15;	
		isOn = false;
		health = 100; 
		this.tileID = tileID;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub	
	}
	
	public void die(){
		//handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
		
		health+= 100; //I want to make the lever invincible
		
		if(isOn){
			isOn = false;
			
			//This will open the rock gate
			Tile.tiles[tileID].setSolid(true);
			Tile.tiles[tileID].makeStone();
		}
		else{
			isOn = true;
			
			//This will open the rock gate
			//The tile id is what is important - not the current tile alone
			Tile.tiles[tileID].setSolid(false);
			Tile.tiles[tileID].makeGrass();
		}	
	}

	@Override
	public void render(Graphics g) {
		
		//On and Of animation
		if(!isOn){
			g.drawImage(Assets.lever[0], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);	
		}
		else{
			g.drawImage(Assets.lever[1], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);	
		}
	}
}