package graphics;

import java.awt.image.BufferedImage;

import basic.Handler;

public class Animation {
	
	private int speed, index; //Speed = fps 
	private long lastTime, timer;
	private BufferedImage[] frames;
	
	public Animation(int speed, BufferedImage[] frames){
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	//This is changing the images - animation
	public void tick(){
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length){
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame(boolean underAttack){
		if(underAttack){
			return Assets.player_down[index];
		}
		return frames[index];
	}
	
	public BufferedImage getZeroFrame(){
		return frames[0];
	}
}