package util;

import entity.Entity;
import tiles.TileMapObj;
import tiles.blocks.Block;
import tiles.blocks.HoleBlock;

public class TileCollision {

	private Entity e;
	
	public TileCollision(Entity e)
	{
		this.e = e; 
	}  
	
	public boolean collisionTile(float ax, float ay)
	{ 
		for(int c=0; c<4;c++)
		{
			int xt = (int) ((e.getBounds().getPos().x + ax) + (c%2)*e.getBounds().getWidth() + e.getBounds().getXOffset()) /64;
			int yt = (int) ((e.getBounds().getPos().y + ay) + ((int) c/2)*e.getBounds().getHeight() + e.getBounds().getYOffset()) /64;

			if(TileMapObj.eventBlocks[xt + (yt*TileMapObj.height)] instanceof Block)
			{
				Block block = TileMapObj.eventBlocks[xt + (yt*TileMapObj.height)]; 
				if(block instanceof HoleBlock)
				{  
					return collisionHole(ax, ay, xt, yt, block);
				} 
				return block.update(e.getBounds()); 
			}
		}
		
		return false;
	}
 
	private boolean collisionHole(float ax, float ay, float xt, float yt, Block block)
	{
		int nextXt = (int) ((((e.getBounds().getPos().x + ax) + e.getBounds().getWidth() + e.getBounds().getXOffset())/64));
		int nextYt = (int) ((((e.getBounds().getPos().y + ay) + e.getBounds().getHeight() + e.getBounds().getYOffset())/64));

		if(block.isInside(e.getBounds()))
		{
			e.setFallen(true);
			return false; 
		}
		else if((nextYt == yt + 1) || (nextXt == xt +1) || (nextYt == yt - 1) || (nextXt == xt -1))
			if(TileMapObj.eventBlocks[nextXt + (nextYt*TileMapObj.height)] instanceof HoleBlock)
			{
				if(e.getBounds().getPos().x > block.getPos().x)
				{
					e.setFallen(true); 
				}
				return false;
			} 
		e.setFallen(false);
		return false;
	}
}
