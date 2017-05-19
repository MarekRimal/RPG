package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import basic.Handler;
import graphics.Animation;
import graphics.Assets;

public class Zombie extends Creature {
	
	private int i; //Just for longer lasting hurt animation
	
	//For player hunting
	private double playerX, playerY, distance;
	private boolean isHuntOn;
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight;
	
	//Attack speed
	private long lastAttackTime, attackSpeed = 500, attackTimer = attackSpeed;
	
	public Zombie(Handler handler, float x, float y) {
		
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGTH);
		
		bounds.x = 22; //from left
		bounds.y = 0; //from up 
		bounds.width = 20; //size of the boundary box
		bounds.height = 40;
		speed = 2; 
		isUnderAttack = false;
		i = 0;
		health = 50;
		isHuntOn = false;
		
		//Animations
		animDown = new Animation(200, Assets.zombie_down);
		animUp = new Animation(200, Assets.zombie_up);
		animLeft = new Animation(200, Assets.zombie_left);
		animRight = new Animation(200, Assets.zombie_right);
	}
	
	//Ensures the motion of the player
	public void tick() {
		
		//Animations ticks
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		
		//Movement 
		getDirection();
		move(); 
		
		//Attack
		checkAttacks();
	}
	
	private void checkAttacks(){
		
		//Attack speed 
		attackTimer += System.currentTimeMillis() - lastAttackTime;
		lastAttackTime = System.currentTimeMillis();
		
		if(attackTimer > attackSpeed){
			
			//collision box of our Zombie
			Rectangle cb = getCollisionBounds(0, 0);
			
			//Attack range
			Rectangle ar = new Rectangle(); 
			int arSize = 40; 
			
			//They are attacking to all directions
			ar.width = arSize * 2 + bounds.width;
			ar.height = arSize * 2 + bounds.height;
			ar.x = cb.x - arSize;
			ar.y = cb.y - arSize;
			
			if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar)){
				handler.getWorld().getEntityManager().getPlayer().hurt(attackPower);
				
			}
			
			attackTimer = 0; //Null the timer
		}
		else{
			return;
		}
	}
	
	public void checkIfHuntIsOn(){
		playerY = handler.getWorld().getEntityManager().getPlayer().y;
		playerX = handler.getWorld().getEntityManager().getPlayer().x;
		
		distance = Math.sqrt(Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2));
		
		if(distance < 350){
			isHuntOn = true;
		}
	}
	
	//The zombie should follow you
	public void getDirection(){
		
		xMove = 0;
		yMove = 0;
		
		checkIfHuntIsOn();
		
		if(isHuntOn){
			if(this.y < playerY){
				yMove = speed;
			}
			if(this.y > playerY){
				yMove = -speed;
			}
			if(this.x < playerX){
				xMove = speed;
			}
			if(this.x > playerX){
				xMove = -speed;
			}
			if(this.y == playerY){
				yMove = 0;
			}
			if(this.x == playerX){
				xMove = 0;
			}
		}
		else{
			return;
		}
	}
	
	@Override
	public void render(Graphics g) {
		
		//Detect if is under attack
		//There put a different image	
		g.drawImage(getCurrentAnimationFrame(isUnderAttack), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		
		this.i++;
		
		//This indicates the duration of the red flash
		if(i > 10){
			isUnderAttack = false;
			i = 0;
		}
		
		//If you want to see the boundary box
		//g.setColor(Color.blue);
		//x = position of the player, bounds.x = from left side of the player image to the boundary box
		//g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		
		
	}
	
	//This display the correspondent image of the Zombie
	private BufferedImage getCurrentAnimationFrame(boolean underAttack){
		
		if(xMove < 0){
			return animLeft.getCurrentFrame(underAttack);
		}
		else if(xMove > 0){
			return animRight.getCurrentFrame(underAttack);
		}
		else if(yMove < 0){
			return animUp.getCurrentFrame(underAttack);
		}
		else if(yMove > 0){
			return animDown.getCurrentFrame(underAttack);
		}
		else{
			return animDown.getZeroFrame();
		}
	}
	
	@Override
	public void die(){
		
		isUnderAttack = true;
		
		if(this.health <= 0){
			System.out.println("YOU HAVE SLAIN A ZOMBIE");
		}
	}
}
