package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import basic.Handler;
import bullets.SMGBullet;
import graphics.Animation;
import graphics.Assets;

public class Shooter extends Creature {
	
	private int i; //Just for longer lasting hurt animation
	
	//For player hunting
	private double playerX, playerY, distance;
	private boolean isHuntOn; //Whether they spotted you
	private boolean goIn; //If they go closer to you or away from you
	private boolean standStill;
	
	//Shooting
	private double dx, dy;
	private int direction;
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight;
	
	//Attack speed
	private long lastAttackTime, attackSpeed = 3000, attackTimer = attackSpeed;
	
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
		if (isHuntOn){ //Thanks to this they will not fire at the same time
			checkAttacks();
		}
	}
	
	//Create an bullet
	public void fire(){
		
		dx = playerX - this.x; 
		dy = playerY - this.y;
		
		//Direction of the bullet
		if(dy > 0){
			if(Math.abs(dy) > Math.abs(dx)){
				direction = 0; // 0 = down
			}
		}
		if(dx > 0){ 
			if(Math.abs(dx) > Math.abs(dy)){
				direction = 1; // 1 = right
			}
		}
		if(dy < 0){
			if(Math.abs(dy) > Math.abs(dx)){
				direction = 2; // 2 = up
			}
		}
		if(dx < 0){ 
			if(Math.abs(dx) > Math.abs(dy)){
				direction = 3; // 3 = left
			}
		}
		
		//Fire the bullet
		handler.getWorld().getBulletManager().addBullet(new SMGBullet(handler, x, y, direction, attackPower, 3, false));
		
	}
	
	private void checkAttacks(){
		
		//Attack speed 
		attackTimer += System.currentTimeMillis() - lastAttackTime;
		lastAttackTime = System.currentTimeMillis();
		
		if(attackTimer > attackSpeed){
			
			//Fire bullet at appropriate angle
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
		
		if(distance < 400){
			isHuntOn = true;
		}
	}
	
	//If we dont have diagonal shooting, this is useless
	//If go for you or step back
	public void checkIfGoIn(){
		playerY = handler.getWorld().getEntityManager().getPlayer().y;
		playerX = handler.getWorld().getEntityManager().getPlayer().x;
		
		distance = Math.sqrt(Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2));
		
		if(distance > 300){
			goIn = true; //If you are too close, they will get closer
			standStill = false;
		}
		else if(distance < 295){
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
		//checkIfGoIn();
		
		if(isHuntOn){
			if(dy > 0){
				if(dx > 0){
					if(Math.abs(dy) < 10 || Math.abs(dx) < 10){
						xMove = 0;
						yMove = 0;
					}
					else if(Math.abs(dy) > Math.abs(dx)){
						xMove = speed;
					}
					else{
						yMove = speed;
					}
				}
				else{
					if(Math.abs(dy) < 10 || Math.abs(dx) < 10){
						xMove = 0;
						yMove = 0;
					}
					else if(Math.abs(dy) > Math.abs(dx)){
						xMove = -speed;
					}
					else{
						yMove = speed;
					}
				}
			}
			else{
				if(dx < 0){
					if(Math.abs(dy) < 10 || Math.abs(dx) < 10){
						xMove = 0;
						yMove = 0;
					}
					else if(Math.abs(dy) > Math.abs(dx)){
						xMove = speed;
					}
					else{
						yMove = -speed;
					}
				}
				else{
					if(Math.abs(dy) < 10 || Math.abs(dx) < 10){
						xMove = 0;
						yMove = 0;
					}
					else if(Math.abs(dy) > Math.abs(dx)){
						xMove = -speed;
					}
					else{
						yMove = -speed;
					}
				}
			}
		}
		else{
			return;
		}
		
		//If we run the diagonal shooting...
		/*
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
		*/
	}
	
	@Override
	public void render(Graphics g) {
		
		//bulletY = (float) ((vectorX * bulletX - c)/vectorY);
		
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










