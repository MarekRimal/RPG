package basic;

import World.World;
import controls.KeyManager;
import controls.MouseManager;
import graphics.GameCamera;

//This class allows us to access all the object we need
//This will be the class with will be handling collision detection
public class Handler {
	
	private Game game; //Game with all its variables
	private World world; //

	public Handler(Game game){
		
		this.game = game;
	}
	
	//GETTERS SETTERS
	
	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	
}
