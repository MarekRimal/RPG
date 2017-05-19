package basic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import controls.KeyManager;
import controls.MouseManager;
import game_states.GameState;
import game_states.MenuState;
import game_states.State;
import graphics.Assets;
import graphics.GameCamera;
import graphics.ImageLoader;

public class Game implements Runnable {

	private Display display;	
	private int width, height; 
	public String title;	
	
	public boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State gameState;
	public State menuState;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	//Constructor
	public Game(String title, int width, int height){
		
		////This is for Display
		this.width = width;
		this.height = height;
		this.title = title;
		
		//For interaction
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	//Set the game window and start the game
	private void init(){
		
		//Set Display
		display = new Display(title, width, height);
		
		display.getFrame().addKeyListener(keyManager); //Key input
		
		//We have to check both Frame and Canvas
		display.getFrame().addMouseListener(mouseManager); //Mouse input
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		//We are sending game to handler for quick access to all objects
		handler = new Handler(this);
		
		//Normal position without shifting
		gameCamera = new GameCamera(handler, 0, 0); 
		
		//It is possible to declare it like a State and than do this
		gameState = new GameState(handler); 
		menuState = new MenuState(handler); 
		
		//Start in menuState
		State.setState(menuState);
			
	}
	
	//Update of all
	private void tick(){

		keyManager.tick(); 
		
		if(State.getState() != null){
			State.getState().tick(); 
		}
		
	}
	
	//Rewrite
	private void render(){
		
		//Canvas -> Buffer -> Screen
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		//Clear Screen after you
		g.clearRect(0, 0, width, height);
		
		//Redraw 
		if(State.getState() != null){
			State.getState().render(g);
		}
		
		bs.show(); //Show the buffer on the screen
		g.dispose(); //Ensure that everything were displayed correctly
	}
	
	
	public void run(){
		
		init(); //Init is called first - initialize
		
		//Game loop
		while(running){

			tick();
			render();
			
			//Sleep of the thread = render speed
			try {
				thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		stop(); 
	}
	
	//Start of the thread
	public synchronized void start(){
		
		if(running){
			return;
		}
		
		running = true;
		thread = new Thread(this); 
		thread.start(); //This calls the run()
	}
	
	//End of the thread
	public synchronized void stop(){
		
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	//GETTERS 
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	
}
