package tiles;

import java.awt.image.BufferedImage;

import graphics.Assets;

public class GrassTile extends Tile {

	public GrassTile(int id){
		super(Assets.grass, id); //We already knows the texture..
	}	
	
	//If you call isSolid method on this class it will 
	//return false - the default isSolid() method of super class
}
