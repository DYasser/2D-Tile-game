package entity;

import java.awt.AlphaComposite; 
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.Sprite;
import states.PlayState;
import util.Camera;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class Player extends Entity{

	public static ArrayList<Player> players = new ArrayList<>(); //Players list
	
	private Camera cam;
	private float lastHp = hp;
	private boolean dash = true;
	private double now = System.currentTimeMillis(), lastT, lastD;
	
	public Player(Sprite sprite, VectorXY origin, int size, Camera cam) {
		super(sprite, origin, size);
		this.cam = cam;
		players.add(this);
		
		dmg = 30;	//damage
		
		gold = 0;	//initial gold
	 	
		acc = 3f; //acceleration and maximum speed
		maxSpeed= 4f;
		
		//bounds dimension
		bounds.setWidth(30);
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40);
	}

	private void resetPosition()
	{  
		//System.out.println("Reseting Player..."); 
		cam.getBoundsOnScreen().getPos().x = 0; 
		cam.getBoundsOnScreen().getPos().y = 0; 
		pos.x = GamePanel.width / 2 - 32; 
		PlayState.map.x = 0;
		
		pos.y = GamePanel.height / 2 - 32;
		PlayState.map.y = 0; 
		
		setAnimation(RIGHT, getSprite().getSpriteArray(RIGHT), 10); 
	}

	public float getPosX() {return pos.x;} 
	public float getPosY() {return pos.y;}
	  
	public void update()
	{ 
			super.update();  
			
			isInvu(3); //if attacked, 3 sec not attackable 
			
			for(int i = 0; i < Entity.entity.size(); i++) //go through all enemies
			{
				Entity enemy = Entity.entity.get(i); 
				
				if(hitBounds.collides(enemy.getBounds()) && attack && (enemy instanceof Skeleton || enemy instanceof Phantom))
				{			//damage to enemy, depending on difficulty
					enemy.isDmged(dmg * (1f/GamePanel.difficulty) );
				}
			}   
			
			if(attack)   //attack until animation finished
			{
				if(ani.hasPlayedOnce())
				{
					attack = false;
		 		}
			}
			else if(!fallen && !stop && hp>0) {	//still alive
				
				move();
				
				if(!tc.collisionTile(dx, 0))	//collide with object
				{
					pos.x += dx;
					xCol = false;
				}
				else
				{
					xCol = true;
				}
				if(!tc.collisionTile(0, dy)) {	//collide with object
					pos.y += dy;
					yCol = false;
				} 
				else
				{
					yCol = true;
				}
			}else if(fallen) 	// fell 
			{
				if(ani.hasPlayedOnce())
				{
					resetPosition();
					dx = 0;
					dy = 0;
					fallen = false;
					hp = 100;
					GamePanel.state = GamePanel.States.GAMEOVER;
				}   
			}else if(hp <= 0)	//dead not fell 
			{
				resetPosition(); 
				hp = 100;
				GamePanel.state = GamePanel.States.GAMEOVER;
			}
		
	}
 
	private void move() 
	{
		if(up)
		{ 
			dy -=acc;
			if(dy < -maxSpeed)
				dy = -maxSpeed;
		}else
		{
			if(dy < 0) {
				dy += deacc;
				if(dy > 0)
					dy = 0; 
			}
		}
		
		if(down)
		{
			dy +=acc; 
			if(dy > maxSpeed)
				dy = maxSpeed;
		}else
		{
			if(dy > 0) {
				dy -= deacc;
				if(dy < 0)
					dy = 0;
			}
		}
		
		if(left)
		{
			dx -=acc;
			if(dx < -maxSpeed)
				dx = -maxSpeed;
		}else
		{
			if(dx < 0) { 
				dx += deacc;
				if(dx > 0)
					dx = 0;
			} 
		}
		
		if(right)
		{
			dx +=acc;
			if(dx > maxSpeed)
				dx = maxSpeed;
		}else
		{
			if(dx > 0) {
				dx -= deacc;
				if(dx < 0)
					dx = 0;
			}
		}
	}
	
	public void input(MouseHandler mouse, KeyHandler key)
	{
		if((lastD - now)/1000 <= 6)		//dash bar cooldown
			lastD = System.currentTimeMillis();  
		
		if(key.dash.down && dash)	// if possible to dash and dash
		{ 
			if((lastD - now)/1000 >6) 
			{ 
				acc = 10f;
	 			maxSpeed = 13f;
				cam.acc = 10f;
				cam.maxSpeed = 13f;
				dash = false;  
				lastT = System.currentTimeMillis();
			} 
		}
		else if(!dash)
		{
			now = System.currentTimeMillis();
			if((now-lastT)/1000 > 0.2f)
			{
				acc = 3f;
				maxSpeed = 4f;
				cam.acc = 3f;
				cam.maxSpeed = 4f;
				dash = true;
				lastT = System.currentTimeMillis();
			}
		}
		
		if(!fallen && hp > 0) {
			if(key.up.down)
			{
				up = true;
			}else
			{
				up = false;
			}
			
			if(key.down.down)
			{
				down = true;
			}else
			{
				down = false;
			}
			
			if(key.left.down)
			{
				left = true;
			}else
			{
				left = false;
			}
			 
			if(key.right.down)
			{
				right = true;
			}else
			{
				right = false;
			}
 
			if(key.attack.down)
			{
				attack = true;
			}
			
			if(key.interact.down)
			{
				interact = true;
			}else
			{
				interact = false; 
			} 
			
			if(up && down)
			{
				up = false;
				down = false;
			}
			
			if(left && right)
			{
				left = false;
				right= false;
			}
		}else
		{
			up = false;
			down = false;
			left = false;
			right = false;
		}
	}
	
	@Override 
	public void render(Graphics2D g) {
		double cd = (lastD - now)/1000; 
		
		/*
		//show bounds
		g.setColor(Color.blue); 
		g.drawRect((int)(pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + (int)bounds.getYOffset()), (int)bounds.getWidth(), (int) bounds.getHeight());
		
		if(attack)
		{ 
			g.setColor(Color.red);
			g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int)hitBounds.getWidth(), (int)hitBounds.getHeight());
		}
		*/
		
		if(lastHp != hp) 
		{
			//Screen Damage
			g.setColor(Color.red);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			g.fillRect(0, 0, GamePanel.width, GamePanel.height);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		if(hurted) // show being attacked
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
		 
		g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) pos.getWorldVar().y, size, size , null);
	  
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g.setColor(Color.white); 
		g.drawRect((int) (pos.getWorldVarX(pos.x)+7), (int) (pos.getWorldVarY(pos.y)-1), 50, 5); 
		
		if(cd >= 6)
			g.setColor(Color.yellow); 
		//dash bar
		g.fillRect((int) (pos.getWorldVarX(pos.x) + 7), (int) (pos.getWorldVarY(pos.y)+70), (int)(cd*8), 4);
		g.setColor(Color.green);
		//heal point bar
		g.fillRect((int) (pos.getWorldVarX(pos.x)+8), (int) (pos.getWorldVarY(pos.y)), (int) hp/2, 4);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		lastHp = hp;
	}
}
