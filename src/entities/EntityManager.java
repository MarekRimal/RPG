package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import basic.Handler;

//Has tick() and render() for all the entities
//Also hold all of them
public class EntityManager {

	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	public Comparator<Entity> renderSorter = new Comparator<Entity>(){
		
	//This method is for correct render order
	//For ex. If the player is behind the tree, the tree will render first
	@Override
	public int compare(Entity a, Entity b){
		if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
			return -1;
		}
		return 1;
	}
	};
	
	public EntityManager(Handler handler, Player player){
		
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player); //Player will tick() with other entities
	}
	
	//Tick all entities
	public void tick(){
		
		Iterator<Entity> it = entities.iterator();
		
		while(it.hasNext()){
			
			//This update the array
			Entity e = it.next(); // AKA Entity e = entities[i];
			e.tick();
			if(!e.isAlive()){
				it.remove();
			}
		}
		entities.sort(renderSorter);
	}
	
	//Render all entities
	public void render(Graphics g){
		
		//This will redraw all the entities 
		for(Entity e : entities){
			e.render(g);
		}
	}
	
	//For adding entities to the game
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//GETTERS SETTERS
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
}
