package game_states;
import java.awt.Graphics;

import basic.Game;
import basic.Handler;

public abstract class State {
	
	//State manager
	
	//Holds the current state
	private static State currentState = null;
	
	//Set and get the current state
	
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//Class
	
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}
	
	//The states will have their own 
	public abstract void tick();
	public abstract void render(Graphics g);
	
}
