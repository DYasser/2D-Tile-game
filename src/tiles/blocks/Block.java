package tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.AABB;
import util.VectorXY;

public abstract class Block {	
	
	protected int w, h;
	protected BufferedImage img;
	protected VectorXY pos;
	
	public Block(BufferedImage img, VectorXY pos, int w, int h)
	{
		this.img = img;
		this.pos = pos; 
		this.w = w;
		this.h = h; 
	}
	
	public abstract boolean update(AABB p);
	public abstract boolean isInside(AABB p);
	
	public VectorXY getPos() 
	{
		return pos; 
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(img, (int)pos.getWorldVar().x, (int) pos.getWorldVar().y, w,h,null);
	}
}
  