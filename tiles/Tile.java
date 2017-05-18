package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;

public class Tile {
	
		//This is just for the gateRock
		protected boolean isSolid;
	
		//STATIC STUFF HERE
	
		//This is for quick access from an array
		//Also for not creating billion instances of one Tile
		public static Tile[] tiles = new Tile[256];
		public static Tile grassTile = new GrassTile(0);
		public static Tile dirtTile = new DirtTile(1);
		public static Tile rockTile = new RockTile(2);
		public static Tile firstGateRockTile = new RockTile(3); //Fist gate to the room
		public static Tile zombieGateRockTile = new RockTile(4); //This will free the tut zombie
	
	
		//CLASS
	
		public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;
	
		protected BufferedImage texture;
		protected final int id;

		public Tile(BufferedImage texture, int id){
			
			this.texture = texture;
			this.id = id;
			isSolid = false;
			
			//The tile at the id = this Tile
			//ex. grassTile id = 0, so tiles[0] = grassTile
			//This is simply add the Tile to an array under index of his id
			tiles[id] = this;
		}

		public void tick(){
			
		}
		
		public void render(Graphics g, int x, int y){
			
			g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
		}
		
		//If you can step on this Tile
		public boolean isSolid(){
			return isSolid; //You are able to step on it
		}
		
		public int getId() {
			return id;
		}
		
		//For moving through
		public void setSolid(boolean solid){
			this.isSolid = solid;
		}
		
		//For lever action
		public void makeGrass(){
			this.texture = Assets.grass;
		}
		
		public void makeStone(){
			this.texture = Assets.stone;
		}
}
