package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Animation;
import graphics.Sprite;
import util.AABB;
import util.KeyHandler;
import util.MouseHandler;
import util.TileCollision;
import util.VectorXY;
 
public abstract class Entity {

	public static ArrayList<Entity> entity = new ArrayList<>();	//List for all Entities
	
	protected final int UP = 3, DOWN = 2, RIGHT = 0, LEFT = 1, FALLEN = 4, ATTACKR = 5, ATTACKL = 6, ATTACKD = 7, ATTACKU = 8;	//Animation fixed
	protected int currentAnimation;
	 
	protected Sprite sprite; 
	protected Animation ani;  
	protected VectorXY pos;  

	protected boolean up, down, right, left, attack;	//clicked 

	public boolean interact;

	protected boolean fallen;
	protected boolean faceR, faceL, faceD, faceU;						//player facing
	
	//Properties
	protected boolean hurted = false;

	protected AABB hitBounds;
	protected AABB bounds;
	protected TileCollision tc;

	protected int size, attackSpeed, attackDuration;
	protected float hp = 100, dmg, maxSpeed = 4f, acc = 3f, deacc = 0.5f;
	
	protected double cdHurt = System.currentTimeMillis();
	
	public boolean xCol = false, yCol = false;	//colision x/ y
	public static boolean stop = false;			//pause
	
	public float dx, dy;	//add x/ y	
	 
	public int drop, gold;	//dropped gold and gold

	
	public Entity(Sprite sprite, VectorXY origin, int size)
	{
		this.sprite = sprite;  
		entity.add(this);
		
		pos = origin; 
		this.size = size;
		
		bounds = new AABB(origin, size, size);
		hitBounds = new AABB(origin, size, size);
		hitBounds.setxOffset(size);
		hitBounds.setyOffset(size);
		
		ani = new Animation();
		setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);	//initial Animation
		
		tc = new TileCollision(this);	
	}
	
	//Setters
	public void setSprite(int i) {size = i;}
	public void setFallen(boolean b) {fallen = b;}
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	public void setAnimation(int i, BufferedImage[] frames, int delay)
	{
		currentAnimation = i; 
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	private void setHitBoxDirection()
	{
		if(up)
		{
			hitBounds.setWidth(size);
			hitBounds.setHeight(size/2);
			hitBounds.setyOffset(-size/4);
			hitBounds.setxOffset(0);
		}
		else if(down)
		{
		 	hitBounds.setWidth(size);
			hitBounds.setHeight(size/2);
			hitBounds.setyOffset(size/2 + 10);
			hitBounds.setxOffset(0);
		}
		else if(left)
		{ 
			hitBounds.setWidth(size/2);
			hitBounds.setHeight(size);
			hitBounds.setxOffset(-size/4);
			hitBounds.setyOffset(0);
		}
		else if(right)
		{
			hitBounds.setWidth(size/2);
			hitBounds.setHeight(size);
			hitBounds.setxOffset(size/2 + 10);
			hitBounds.setyOffset(0);
		}
	}
	
	//getters
	public AABB getBounds() {return bounds;}
 	public Animation getAnimation() {return ani;}
	public Sprite getSprite() {return sprite;}
	public float getDeacc() {return deacc;}
	public float getMaxSpeed() {return maxSpeed;}
	public float getDx() {return dx;}
	public float getDy() {return dy;} 
	public int getSize() {return size;}

	public void animate()
	{	
		if(!attack && (up || down || right || left || fallen)) {
			if(up)
			{
				faceU = true;
				faceD = false; 
				faceR = false;
				faceL = false;
				if(currentAnimation != UP || ani.getDelay() == -1)
				{
					setAnimation(UP, getSprite().getSpriteArray(UP), 5);
				}
			}
			
			else if(down)
			{
				faceD = true;
				faceU = false;
				faceR = false;
				faceL = false;
				if(currentAnimation != DOWN || ani.getDelay() == -1)
				{
					setAnimation(DOWN, getSprite().getSpriteArray(DOWN), 5);
				} 
			}
			 
			else if(right) 
			{
				faceR = true;
				faceD = false;
				faceU = false;
				faceL = false;
				if(currentAnimation != RIGHT || ani.getDelay() == -1)
				{
					setAnimation(RIGHT, getSprite().getSpriteArray(RIGHT), 5);
				} 
			}
			
			else if(left)
			{
				faceL = true;
				faceD = false;
				faceR = false;
				faceU = false;
				if(currentAnimation != LEFT || ani.getDelay() == -1)
				{
					setAnimation(LEFT, getSprite().getSpriteArray(LEFT), 5);
				} 
			} 
			else if(fallen)
			{
				if((currentAnimation != FALLEN || ani.getDelay() == -1) && this instanceof Player)
				{
					setAnimation(FALLEN, getSprite().getSpriteArray(FALLEN), 15);	
				}
			} 
		}
		else if(attack && faceR) 
		{
			if((currentAnimation != ATTACKR || ani.getDelay() == -1) && this instanceof Player)
			{
				setAnimation(ATTACKR, getSprite().getSpriteArray(ATTACKR), 5);	 
			}
		}
		else if(attack && faceL)
		{
			if((currentAnimation != ATTACKL || ani.getDelay() == -1) && this instanceof Player)
			{
				setAnimation(ATTACKL, getSprite().getSpriteArray(ATTACKL), 5);	
			}
		} 
		else if(attack && faceD)
		{
			if((currentAnimation != ATTACKD || ani.getDelay() == -1) && this instanceof Player)
			{
				setAnimation(ATTACKD, getSprite().getSpriteArray(ATTACKD), 5);	
			}
		}
		else if(attack && faceU)
		{
			if((currentAnimation != ATTACKU || ani.getDelay() == -1) && this instanceof Player)
			{
				setAnimation(ATTACKU, getSprite().getSpriteArray(ATTACKU), 5);	 
			}
		}
		else
		{
			setAnimation(currentAnimation, getSprite().getSpriteArray(currentAnimation), -1);
		}
	}
	
	public void update()
	{
		if(!stop) {  	//not paused
			animate();
			setHitBoxDirection();
			ani.update();
		}
	}
	
	public void isDmged(float dmg) 
	{ 
		if(!hurted) {	
			cdHurt = System.currentTimeMillis();
			hurted = true;
			hp -= dmg; 
		} 
	}
	
	public void isInvu(float s)	//is invulnerable 
	{
		double now = System.currentTimeMillis();
		if(hurted && (now - cdHurt)/1000 > s)
			hurted = false;
	}
	
	public abstract void render(Graphics2D g);
	public  void input(KeyHandler key, MouseHandler mouse) {}

	public boolean getInteract() {
		return interact;
	}
}
