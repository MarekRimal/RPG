package game_states;
import java.awt.Graphics;

import World.World;
import basic.Game;
import basic.Handler;
import entities.EntityManager;
import entities.Player;
import entities.Tree;
import entities.Zombie;
import graphics.Assets;
import tiles.Tile;

public class GameState extends State{

	//There are all the variables we want in the gameState
	private Player player;
	private Zombie zombie;
	private World world;
	private Tree tree;
	
	//Create the GameState a initialize the player
	public GameState(Handler handler){
		
		super(handler); 
		
		world = new World(handler, "res/worlds/world1.txt"); //Load world we want
		handler.setWorld(world);
		
		//The entities
		player = new Player(handler, 100, 100);
		zombie = new Zombie(handler, 100, 120);
		tree = new Tree(handler, 100, 200);
		
	}
	
	@Override
	public void tick() {
		
		world.tick(); //tick() all ma
	}

	@Override
	public void render(Graphics g) {
		
		//The render order determine what will be draw "on top"
		world.render(g); 
			
	}	
}
