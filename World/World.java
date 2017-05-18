package World;

//Load the world map from file
import java.awt.Graphics;

import basic.Game;
import basic.Handler;
import entities.BulletManager;
import entities.EntityManager;
import entities.Lever;
import entities.Player;
import entities.Shooter;
import entities.Tree;
import entities.Zombie;
import items.ItemManager;
import tiles.Tile;
import utils.Utils;

public class World {

	private Handler handler;
	private int width, height; //Size of map
	private int spawnX, spawnY; //Where the player will spawn
	private int[][] tiles; //Hold the id of the tile on some position (x and y coordinate)
	
	//Entities
	private EntityManager entityManager;
	
	//Items
	private ItemManager itemManager;
	
	//Bullets
	private BulletManager bulletManager;
	
	public World(Handler handler, String path){
		
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));	
		itemManager = new ItemManager(handler);
		bulletManager = new BulletManager(handler);
		
		//There you can put some object to game
		entityManager.addEntity(new Tree(handler, 100, 250));
		entityManager.addEntity(new Tree(handler, 320, 350));
		entityManager.addEntity(new Tree(handler, 500, 450));
		entityManager.addEntity(new Tree(handler, 700, 450));
		entityManager.addEntity(new Tree(handler, 900, 150));
		entityManager.addEntity(new Tree(handler, 900, 250));
		entityManager.addEntity(new Lever(handler, 1100, 300, 3));
		entityManager.addEntity(new Lever(handler, 600, 150, 4));
		entityManager.addEntity(new Zombie(handler, 600, 400)); 
		entityManager.addEntity(new Zombie(handler, 620, 420));
		entityManager.addEntity(new Zombie(handler, 585, 380));
		entityManager.addEntity(new Shooter(handler, 1400, 820));
		entityManager.addEntity(new Shooter(handler, 1200, 310));
		entityManager.addEntity(new Shooter(handler, 1500, 750));
		
		
		//Load world from a file
		loadWorld(path);	
		
		//Set player spawn
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		
	}

	
	public void tick(){
		entityManager.tick();
		itemManager.tick();
		bulletManager.tick();
	}
	
	public void render(Graphics g){
		
		//This variables are for rendering efficiency because the for loops down there
		//going throw every single tile every time the render() method is called even
		//through the tiles which are not visible on the screen
		//So lets not go from x = 0 to x = width, but just from the xStart, which is
		//the first visible tile on the screen, to the xEnd (the last one)
		
		//We dont want to xStart be negative number -> 0 is the leftmost tile in our game
		int xStart = (int)Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH); //For better understanding add +1 there :)
		int xEnd = (int)Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int)Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int)Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		
		for(int y = yStart; y < yEnd; y++){ //Be careful because x is always width 
			for(int x = xStart; x < xEnd; x++){
				//We multiply it because otherwise each tile will be shifted only by one pixel
				//Multiplying by static variable TILE_WIDT will shift it by the width of the tile
				getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()), (int)(y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		//Renders
		itemManager.render(g);
		entityManager.render(g);
		bulletManager.render(g);
	}
	
	public Tile getTile(int x, int y){
		
		//Game wont crash if the player somehow get out of bounds
		if(x < 0 || y < 0 || x >= width || y >= height){
			return Tile.grassTile;
		}
		
		//tiles[x][y] give us the id and the tiles[id] is the texture
		Tile t = Tile.tiles[tiles[x][y]]; 
		if(t == null){
			//If you try to access undefined tile index, return dirt tile as default
			return Tile.dirtTile; 
		}
		else{
			return t;
		}
	}
	
	private void loadWorld(String path){
		
		String file = Utils.loadFileAsString(path);
		// "\\s+" separates the symbols by any blank space or new line or...
		String[] tokens = file.split("\\s+"); 
		width = Utils.parseInt(tokens[0]); //First number
		height = Utils.parseInt(tokens[1]); //Second
		spawnX = Utils.parseInt(tokens[2]); 
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
	
		//There it is scanning the world from the file
		for(int y = 0; y < height; y++){ 
			for(int x = 0; x < width; x++){
				//Converting from 2d to 1d - the 4 because we need to skip first 4 numbers
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	//GETTERS SETTERS

	public int getWidth() {
		return width;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}


	public void setBulletManager(BulletManager bulletManager) {
		this.bulletManager = bulletManager;
	}


	public Handler getHandler() {
		return handler;
	}


	public void setHandler(Handler handler) {
		this.handler = handler;
	}


	public ItemManager getItemManager() {
		return itemManager;
	}


	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}


	public int getHeight() {
		return height;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
