package entities;

import java.awt.Graphics;

import basic.Handler;
import graphics.Assets;
import items.Item;
import tiles.Tile;

public class Tree extends StaticEntity{
	
	public Tree(Handler handler, float x, float y){
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2); //2 * taller
		
		bounds.x = 10;
		bounds.y = 110;
		bounds.width = width - 20;
		bounds.height = 15;	
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub	
	}
	
	//Here you can implement what will happened when the tree die
	public void die(){
		//handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
	}

	@Override
	public void render(Graphics g) {
		
		//If it is under attack blink it red
		if(isUnderAttack){
			g.drawImage(Assets.dirt, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		}
		else{ //There put the red tree img
			g.drawImage(Assets.tree, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);	
		}
		
		isUnderAttack = false;
	}
}
