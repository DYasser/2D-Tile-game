package util;

import entity.Entity;

public class AABB {	//all bound properties

	private VectorXY pos;
	private float xOffset = 0, yOffset = 0;
	private float w, h;
	private float r; 
	private int size;
	private Entity e; 
	 
	public AABB(VectorXY pos, int w, int h)
	{
 		this.w = w; 
		this.h = h;
		this.pos = pos;
		
		size = Math.max(w,h); 
	}
	
	public AABB(VectorXY pos, int r, Entity e)
	{  
		this.r = r;
		this.pos = pos;
		
		this.e = e; 
		size = r;
	}
	
	//getters
	public VectorXY getPos() {return pos;}
	public float getRadius() {return r;}
	public float getWidth() {return w;} 
	public float getHeight() {return h;}
	public float getXOffset() {return xOffset;} 
	public float getYOffset() {return yOffset;}

	//setters
	public void setWidth(float f) { w = f;}
	public void setHeight(float f) { h = f;}
	public void setxOffset(float f) { xOffset = f;}
	public void setyOffset(float f) { yOffset = f;} 
	public void setX(float x) {pos.x= x;}
	public void setY(float y) {pos.y= y;}
	public void setBox(VectorXY pos, int w, int h)
	{
		this.pos = pos;
		this.w = w;
		this.h = h;
		
		size = Math.max(w, h);
	}
	public void setBox(VectorXY pos, int r)
	{
		this.pos = pos;
		this.r = r;
		
		size = r;
	} 

	//collisions 4 edges
	public boolean collides(AABB bBox) {
		float ax = ((pos.getWorldVar().x  + (xOffset)) + (this.w/2));
		float ay = ((pos.getWorldVar().y  + (yOffset)) + (this.h/2));
		float bx = ((bBox.getPos().getWorldVar().x  + (bBox.xOffset)) + (bBox.getWidth()/2));
		float by = ((bBox.getPos().getWorldVar().y  + (bBox.yOffset)) + (bBox.getHeight()/2));

		if(Math.abs(ax - bx) < (this.w / 2) + (bBox.getWidth()/2))
		{
			if(Math.abs(ay - by) < (this.h / 2) + (bBox.getHeight() / 2))
			{
				return true;
			}
		} 
		
		return false;
	}
	
	//collision circular
	public boolean colCircleBox(AABB aBox)
	{
		float dx = Math.max(aBox.pos.getWorldVar().x + aBox.getXOffset(), Math.min(pos.getWorldVar().x + (r/2), aBox.getPos().getWorldVar().x + aBox.getXOffset() + aBox.getWidth()));
		float dy = Math.max(aBox.pos.getWorldVar().y + aBox.getYOffset(), Math.min(pos.getWorldVar().y + (r/2), aBox.getPos().getWorldVar().y + aBox.getYOffset() + aBox.getHeight()));

		dx = pos.getWorldVar().x + (r/2) -dx;
		dy = pos.getWorldVar().y + (r/2) -dy;
		
		// (x – h)^2 + (y – k)^2 = r^2
		if(Math.sqrt(dx*dx + dy*dy) < r/2)	
		{
			return true;  
		}
		return false;
	}
	
	
	
}
