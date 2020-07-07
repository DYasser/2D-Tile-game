package tiles;

import java.awt.Graphics2D;

import graphics.Sprite;
import tiles.blocks.Block;
import tiles.blocks.NormBlock;
import util.AABB;
import util.Camera;
import util.VectorXY;

public class TileMapNorm extends TileMap{	//normal blocks

	private Block[] blocks;
	
	private int tileWidth, tileHeight, height;
	
	
	public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight,int tileColumns)
	{ 
		 blocks = new Block[width * height];
		 
		 this.tileWidth = tileWidth;
		 this.tileHeight = tileHeight;
		  
		 this.height = height;
		 
		 String[] block = data.split(",");			//split data from xml file
		 for(int i = 0;  i < (width*height); i++) 
		 {
			 int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));	//get all ID numbers
			 
			 if(temp != 0)	//add the not null blocks
			 {
				 blocks[i] = new NormBlock(sprite.getSprite((int)((temp-1) % tileColumns),(int)((temp-1) / tileColumns)),new VectorXY((int) ((i%width) * tileWidth), (int)((i/height) *tileHeight)), tileWidth, tileHeight);
			 }
		 }
	} 

	public void render(Graphics2D g, AABB cam)
	{
		int x = (int) ((cam.getPos().getCamVar().x) / tileWidth);	//get blocks
		int y = (int) ((cam.getPos().getCamVar().y) / tileHeight);
		
		for(int i = x; i < x + (cam.getWidth() / tileWidth); i++)
		{
			for(int j = y; j < y + (cam.getHeight() / tileHeight) ; j++)
			{
				if(blocks[i +(j*height)] != null)
					blocks[i+(j*height)].render(g);
			}
		}
	}
}
