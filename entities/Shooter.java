package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import basic.Handler;
import graphics.Animation;
import graphics.Assets;

public class Shooter extends Creature {
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	private int i; //Just for longer lasting hurt animation
	
	//For player hunting
	private double playerX, playerY, distance;
	private boolean isHuntOn; //Whether they spotted you
	private boolean goIn; //If they go closer to you or away from you
	private boolean standStill;
	
	//Shooting
	private double bulletX, bulletY;
	private double vectorX, vectorY, c;
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight;
	
	//Attack speed
	private long lastAttackTime, attackSpeed = 500, attackTimer = attackSpeed;
	
	public Shooter(Handler handler, float x, float y) {
		
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGTH);
		
		bounds.x = 22; //from left
		bounds.y = 30; //from up 
		bounds.width = 20; //size of the boundary box
		bounds.height = 20;
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
	
	//This function will get the equation for the bullet
	public void getEquation(){
		
		playerX = handler.getWorld().getEntityManager().getPlayer().x;
		playerY = handler.getWorld().getEntityManager().getPlayer().y;
		
		//normal vector
		vectorX = this.y - playerY;
		vectorY = -(this.x - playerX);
		
		c = -(vectorX * this.x + vectorY * this.y);
		
		//Starting point for the bullet
		bulletX = this.x; 
		bulletY = this.y;
	}
	
	//Create an bullet
	public void fire(){
		
		handler.getWorld().getBulletManager().addBullet((new Bullet(handler, (float)bulletX, (float)bulletY, vectorX, vectorY, c, attackPower)));
	}
	
	//DIFF
	private void checkAttacks(){
		
		//Attack speed 
		attackTimer += System.currentTimeMillis() - lastAttackTime;
		lastAttackTime = System.currentTimeMillis();
		
		if(attackTimer > attackSpeed){
			
			//Get the direction of the bullet
			getEquation();
			fire();
			
			attackTimer = 0; //Null the timer
			
		}
		else{
			return;
		}
	}
	
	//If they spotted you
	public void checkIfHuntIsOn(){
		playerY = handler.getWorld().getEntityManager().getPlayer().y;
		playerX = handler.getWorld().getEntityManager().getPlayer().x;
		
		distance = Math.sqrt(Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2)); //Pythagoras
		
		if(distance < 450){
			isHuntOn = true;
		}
	}
	
	//If go for you or step back
	public void checkIfGoIn(){
		playerY = handler.getWorld().getEntityManager().getPlayer().y;
		playerX = handler.getWorld().getEntityManager().getPlayer().x;
		
		distance = Math.sqrt(Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2));
		
		if(distance > 350){
			goIn = true; //If you are too close, they will get closer
			standStill = false;
		}
		else if(distance < 280){
			goIn = false; //If you are too close, they will step back
			standStill = false;
		}
		else{
			standStill = true;
		}
	}
	
	//This is maybe too complicated, but works fine
	public void getDirection(){
		
		xMove = 0;
		yMove = 0;
		
		checkIfHuntIsOn();
		checkIfGoIn();
		
	if(isHuntOn && goIn && !standStill){
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
		else if(isHuntOn && !goIn && !standStill){
			if(this.y < playerY){
				yMove = -speed;
			}
			if(this.y > playerY){
				yMove = speed;
			}
			if(this.x < playerX){
				xMove = -speed;
			}
			if(this.x > playerX){
				xMove = speed;
			}
			if(this.y == playerY){
				yMove = 0;
			}
			if(this.x == playerX){
				xMove = 0;
			}
		}
		else if(standStill){
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
		
		bulletY = (float) ((vectorX * bulletX - c)/vectorY);
		
		//Detect if is under attack
		//There put a different image	
		g.drawImage(getCurrentAnimationFrame(isUnderAttack), (int)(this.x - handler.getGameCamera().getxOffset()), (int)(this.y - handler.getGameCamera().getyOffset()), width, height, null);
		this.i++;
		
		//This indicates the duration of the red flash
		if(i > 10){
			isUnderAttack = false;
			i = 0;
		}	
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
			System.out.println("YOU HAVE SLAIN A SHOOTER");
		}
	}
}
