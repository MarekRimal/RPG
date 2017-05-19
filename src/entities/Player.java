package entities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import basic.Handler;
import graphics.Animation;
import graphics.Assets;
import items.SMG;

public class Player extends Creature{
	
	private int i = 0;
	
	//Player proportions
	static int playerWidth = 80; 
	static int playerHeight = 80;
	
	//Guns
	private SMG smg;
	//private Sniper sniper;
	
	//Animations
	private Animation animStill, animDown, animUp, animLeft, animRight, animLeftComb, animRightComb;
	
	//Attack speed
	private long lastAttackTime, attackSpeed = 100, attackTimer = attackSpeed;

	private int weapon;
	
	public Player(Handler handler, float x, float y) {
		
		super(handler, x, y, playerWidth, playerHeight);
		
		bounds.x = 25; //from left
		bounds.y = 30; //from up 
		bounds.width = 35; //size of the boundary box
		bounds.height = 47;
		speed = 5; //I made him a bit faster
		health = 15;
		attackPower = 10;
		weapon = 1;
		createSMG();
		
		//Animations
		animStill = new Animation(200, Assets.player_still);
		animDown = new Animation(200, Assets.player_down);
		animUp = new Animation(200, Assets.player_up);
		animLeft = new Animation(200, Assets.player_left);
		animRight = new Animation(200, Assets.player_right);
		//CombatAnimation
		animLeftComb = new Animation(200, Assets.player_left_comb);
		animRightComb = new Animation(200, Assets.player_right_comb);
			
	}
	
	//Ensures the motion of the player
	@Override
	public void tick() {
		
		//Animations ticks
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		animLeftComb.tick();
		animRightComb.tick();
		
		//Movement
		getInput();
		move();
		
		//We want to center the camera on this entity (player)
		handler.getGameCamera().centerOnEntity(this); 
		
		//Attack
		
		//This depends on the weapon you have
		if(weapon == 0){ //Melee
			checkMeleeAttacks();
		}
		else if(weapon == 1){
			checkSMGAtack();
		}
		else if(weapon == 2){
			checkSniperAttack();
		}
	}
	
	//The attack method for melee combat
	private void checkMeleeAttacks(){
		
		//Attack speed 
		attackTimer += System.currentTimeMillis() - lastAttackTime;
		lastAttackTime = System.currentTimeMillis();
		
		if(attackTimer < attackSpeed){
			return;
		}
		else{
			
			//collision box of our player
			Rectangle cb = getCollisionBounds(0, 0);
			
			//Attack range
			Rectangle ar = new Rectangle(); 
			int arSize = 60; 
			ar.width = arSize;
			ar.height = arSize;
			
			if(handler.getKeyManager().aLeft){
				ar.x = cb.x - arSize; 
				ar.y = cb.y + (cb.height / 2) - (arSize / 2);
			}
			else if(handler.getKeyManager().aRight){
				ar.x = cb.x + cb.width; //Dont know why this shouldnt be there: + arSize
				ar.y = cb.y + (cb.height / 2) - (arSize / 2);
			}
			else{
				return;
			}
			
			attackTimer = 0; //Null the timer
			
			//Check if we hit
			for(Entity e : handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this)){ //We dont want hurt ourself
					continue;
				}
				if(e.getCollisionBounds(0, 0).intersects(ar)){
					e.hurt(attackPower);
					//return - that would mean that we could hurt just one entity at a time
					//So that could be a powerUp
				}
			}
		}
	}
	
	private void checkSMGAtack(){
		
		//Attack speed 
		attackTimer += System.currentTimeMillis() - lastAttackTime;
		lastAttackTime = System.currentTimeMillis();
		
		if(attackTimer < attackSpeed){
			return;
		}
		else{
			smg.tick();
		}
		
		attackTimer = 0; //Null the timer
		
	}
	
	private void checkSniperAttack(){
		
	}
	
	@Override
	public void die(){
		i++;
		if(health <= 0){
			System.out.println("GAME OVER");
		}
		else{
			System.out.println("hit: " + i);
		}
		
		//For just now
		if(health > 8){
			weapon = 1;
			createSMG();
		}
	}
	
	//There you are not changing the position directly
	private void getInput(){
		
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up){
			yMove = -speed;
		}
		if(handler.getKeyManager().down){
			yMove = speed;
		}
		if(handler.getKeyManager().left){
			xMove = -speed;
		}
		if(handler.getKeyManager().right){
			xMove = speed;
		}
		
	}
	
	//This just render the player
	@Override
	public void render(Graphics g) {
		//We need to apply the offset to the player also - otherwise he is not synchronized with the map
		g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		
		//This is the bounding box - just for visualizing
		//g.setColor(Color.blue);
		//x = position of the player, bounds.x = from left side of the player image to the boundary box
		//g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}	
	
	//This display the correspondent image of the player
	//All that crazy stuff is for combat animation
	private BufferedImage getCurrentAnimationFrame(){
		
		if(xMove < 0){
			if(handler.getKeyManager().aLeft){
				return animLeftComb.getCurrentFrame(false);
			}
			else if(handler.getKeyManager().aRight){
				return animRightComb.getCurrentFrame(false);
			}
			return animLeft.getCurrentFrame(false);
		}
		else if(xMove > 0){
			if(handler.getKeyManager().aLeft){
				return animLeftComb.getCurrentFrame(false);
			}
			else if(handler.getKeyManager().aRight){
				return animRightComb.getCurrentFrame(false);
			}
			return animRight.getCurrentFrame(false);
		}
		else if(yMove != 0){
			if(handler.getKeyManager().aLeft){
				return animLeftComb.getCurrentFrame(false);
			}
			else if(handler.getKeyManager().aRight){
				return animRightComb.getCurrentFrame(false);
			}
			return animDown.getCurrentFrame(false);
		}
		else{
			if(handler.getKeyManager().aLeft){
				return animLeftComb.getZeroFrame();
			}
			else if(handler.getKeyManager().aRight){
				return animRightComb.getZeroFrame();
			}
			return animStill.getZeroFrame();
		}
	}
	
	private void createSMG(){

		smg = new SMG(handler, attackPower);
		attackSpeed = 300; //This is fire rate	
	}
}
