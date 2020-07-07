package util;

import java.awt.Graphics2D;

import entity.Entity;
import states.PlayState;

public class Camera {

	private AABB collisionCam;
	private AABB bounds;
	private Entity e;
	
	private boolean up, down, left, right; 
	
	private float dx, dy;
	 
	public float maxSpeed = 4f; 
	public float acc = 1f; 
	private float deacc = 0.3f;
	 
	private int widthLimit, heightLimit; 
	
	public Camera(AABB collisionCam) 
	{ 
		this.collisionCam = collisionCam;
		float x = collisionCam.getPos().x; 
		float y = collisionCam.getPos().y;
		this.bounds = new AABB(new VectorXY(x,y), (int) collisionCam.getWidth(), (int) collisionCam.getHeight());
	}
 
	//getters
	public AABB getBoundsOnScreen(){return bounds;}
	public AABB getBounds(){return collisionCam;}
	
	//setters
	public void setLimit(int widthLimit, int heightLimit)
	{
		this.widthLimit = widthLimit;
		this.heightLimit = heightLimit;
	}
	
	public void target(Entity e)
	{
		this.e = e; 
		deacc = e.getDeacc();
		maxSpeed = e.getMaxSpeed();
	}
	
	public void input(MouseHandler mouse, KeyHandler key)	//get directions/ keys
	{	
		if(e == null) {
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
		}else 
		{
			if(PlayState.map.y + GamePanel.height/2 - e.getSize()/2 + dy > e.getBounds().getPos().y + e.getDy() + 2)
			{
				up = true;
				down = false;
			}else if(PlayState.map.y + GamePanel.height/2 - e.getSize()/2 + dy < e.getBounds().getPos().y + e.getDy() - 2)
			{
				down = true;
				up = false;
			}else
			{
				dy = 0 ;
				up = false;
				down = false;
			}
			
			if(PlayState.map.x + GamePanel.width/2 - e.getSize()/2 + dx > e.getBounds().getPos().x + e.getDx() + 2)
			{
				left = true;
				right = false;
			}else if(PlayState.map.x + GamePanel.width/2 - e.getSize()/2 + dx < e.getBounds().getPos().x + e.getDx() - 2)
			{
				right = true;
				left = false;
			}else
			{
				dx = 0 ;
				left = false;
				right = false;
			}
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
	
	public void update()
	{
		move();
		
		if(!e.xCol) {	//if not x collision limit
			if((e.getBounds().getPos().getWorldVar().x + e.getDx()) < (VectorXY.getWorldVarX(widthLimit - collisionCam.getWidth()/2) - 64)
						&& (e.getBounds().getPos().getWorldVar().x + e.getDx()) > (VectorXY.getWorldVarX(GamePanel.width/2)) -64)
			{ 
				PlayState.map.x += dx;
				bounds.getPos().x += dx;
			}
		} 
		if(!e.yCol) {	//if not y collision limit
			if((e.getBounds().getPos().getWorldVar().y + e.getDy()) < VectorXY.getWorldVarY(heightLimit - collisionCam.getHeight() /2) - 64
					&& (e.getBounds().getPos().getWorldVar().y + e.getDy()) > VectorXY.getWorldVarY(GamePanel.height/2) -64)
			{
				PlayState.map.y += dy;
				bounds.getPos().y += dy;
			}
		}
	} 
	
	public void render(Graphics2D g){}
}
