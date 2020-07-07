package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.VectorXY;
 
public class Sprite {
	
	private BufferedImage SPRITESHEET = null;
	private BufferedImage[][] spriteArray;
	private final int TILE_SIZE = 32;
	public int w, h, wSprite, hSprite;
	
	public static Font currentFont;	//font used in all screens
	
	public Sprite(String file)	//default size
	{
		w = TILE_SIZE;
		h = TILE_SIZE; 
		
		System.out.println("File: " + file + " Loading..");
		SPRITESHEET = loadSprite(file); 
		 
		wSprite = SPRITESHEET.getWidth()/w;		//number of sprites in width
		hSprite = SPRITESHEET.getHeight()/h;	//number of sprites in height
		loadSpriteArray();
	}
	
	public Sprite(String file, int w, int h)	//personalized size
	{
		this.w = w;
		this.h = h;

		System.out.println("File: " + file + " Loading..");
		SPRITESHEET = loadSprite(file);

		wSprite = SPRITESHEET.getWidth()/w;
		hSprite = SPRITESHEET.getHeight()/h;
		loadSpriteArray();
	}
	
	//setters
	public void setSize(int w, int h)
	{
		setWidth(w);
		setHeight(h);
	}
	public void setWidth(int w)
	{
		this.w = w;
		wSprite = SPRITESHEET.getWidth()/w;
	}
	public void setHeight(int h)
	{
		this.h = h;
		hSprite = SPRITESHEET.getHeight()/h;
	}
	
	//getters
	public int getWidth(){return w;}
	public int getHeight(){return h;}
	public BufferedImage getSpriteSheet(){return SPRITESHEET;}
	public BufferedImage getSprite(int x, int y){return SPRITESHEET.getSubimage(x*w, y*h, w, h);}
	public BufferedImage[] getSpriteArray(int i){return spriteArray[i];}
	public BufferedImage[][] getSpriteArray2(int i){return spriteArray;}
	
	
	public BufferedImage loadSprite(String file)
	{
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
		}catch(Exception e) {
			System.out.println("ERROR: Can't load file: " + file);
		}
		
		return sprite;
	}
	
	public void loadSpriteArray()	//getting the character sheet into array
	{
		spriteArray = new BufferedImage[hSprite][wSprite];

		for(int col = 0; col < hSprite; col++)
			for(int row = 0; row < wSprite; row++)
			{
				spriteArray[col][row] = getSprite(row,col);
			}
	}
	
	public static void drawArray(Graphics2D g, String word, VectorXY pos, int size)
	{	drawArray(g, currentFont, word, pos, size, size, size, 0);}
	
	public static void drawArray(Graphics2D g, String word, VectorXY pos, int size, int xOffset)
	{	drawArray(g, currentFont, word, pos, size, size, xOffset, 0);}

	public static void drawArray(Graphics2D g, String word, VectorXY pos, int width, int height, int xOffset)
	{	drawArray(g, currentFont, word, pos, width, height, xOffset, 0);}
	
	public static void drawArray(Graphics2D g, Font font, String word, VectorXY pos, int size, int xOffset)
	{	drawArray(g, font, word, pos, size, size, xOffset, 0);}
	
	public static void drawArray(Graphics2D g, Font font, String word, VectorXY pos, int width, int height, int xOffset, int yOffset)
	{
		float x = pos.x;
		float y = pos.y;
		
		currentFont  = font;
		
		for(int i = 0; i < word.length(); i++)	//going through the characters
		{ 
			if(word.charAt(i) != 32)
			{
				g.drawImage(font.getFont(word.charAt(i)), (int)x, (int)y, width, height, null);	//draw each character of word
			}
			x+= xOffset;
			y+= yOffset;
		}
	}
}
