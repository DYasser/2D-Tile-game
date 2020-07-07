package tiles;

import java.awt.Graphics2D;

import graphics.Sprite;
import tiles.blocks.Block;
import tiles.blocks.HoleBlock;
import tiles.blocks.ObjBlock;
import util.AABB;
import util.VectorXY;

public class TileMapObj extends TileMap{	//Object Blocks
		
	public static Block[] eventBlocks;
	
	private int tileWidth, tileHeight;
	
	public static int height, width;
	
	public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight,int tileColumns)
	{ 
		Block tempBlock;
		eventBlocks = new Block[width*height];
	 	this.tileWidth = tileWidth;
		this.tileHeight = tileHeight; 
		 
	 	this.width = width;
		this.height = height;
		
		String[] block = data.split(",");			//split data
		for(int i = 0;  i < (width*height); i++)
		{
 			int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));		//get IDs
			if(temp != 0)
			{
				if(temp == 223)		//ID = 223 hole
				{
					tempBlock = new HoleBlock(sprite.getSprite((int)((temp-1) % tileColumns),(int)((temp-1) / tileColumns)),new VectorXY((int) ((i%width) * tileWidth), (int)((i/height) *tileHeight)), tileWidth, tileHeight);
				}
				else{		//else block object
					tempBlock = new ObjBlock(sprite.getSprite((int)((temp-1) % tileColumns),(int)((temp-1) / tileColumns)),new VectorXY((int) ((i%width) * tileWidth), (int)((i/height) *tileHeight)), tileWidth, tileHeight);
				}
			 
				eventBlocks[i] = tempBlock;	
			} 
			
		} 
	}
	
	public void render(Graphics2D g, AABB cam)
	{
		int x = (int) ((cam.getPos().getCamVar().x) / tileWidth);	//blocks
		int y = (int) ((cam.getPos().getCamVar().y) / tileHeight);
		
		for(int i = x; i < x + (cam.getWidth() / tileWidth); i++)
		{
			for(int j = y; j < y + (cam.getHeight() / tileHeight) ; j++) 
			{
				if(eventBlocks[i +(j*height)] != null) 
					eventBlocks[i+(j*height)].render(g);
			} 
		}
	}

}
