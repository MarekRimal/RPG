package entities;

import basic.Handler;

//Entities that does not move - its like Creature for not moving Entities
public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height){
		super(handler, x, y, width, height);
	}
}
