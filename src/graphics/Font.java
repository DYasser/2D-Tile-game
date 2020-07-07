package graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Font {

	private BufferedImage FONTSHEET = null;
	private BufferedImage[][] spriteArray;
	private final int TILE_SIZE = 32;
	public int w, h, wLetter, hLetter;
	
	public Font(String file)
	{
		w = TILE_SIZE;	//default
		h = TILE_SIZE;
		
		//System.out.println("File: " + file + " Loading..");
		FONTSHEET = loadFont(file);
		
		wLetter = FONTSHEET.getWidth()/w;
		hLetter = FONTSHEET.getHeight()/h;
		loadSpriteArray();
	}
	
	public Font(String file, int w, int h)
	{
		this.w = w;	//custom
		this.h = h;

		//System.out.println("File: " + file + " Loading..");
		FONTSHEET = loadFont(file);

		wLetter = FONTSHEET.getWidth()/w;
		hLetter = FONTSHEET.getHeight()/h;
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
		wLetter = FONTSHEET.getWidth()/w;
	}
	public void setHeight(int h)
	{
		this.h = h;
		hLetter = FONTSHEET.getHeight()/h;
	}
	
	//getters
	public int getWidth() {return w;}
	public int getHeight() {return h;}
	public BufferedImage getFontSheet() {return FONTSHEET;}
	public BufferedImage getLetter(int x, int y) {return FONTSHEET.getSubimage(x*w, y*h, w, h);}
	public BufferedImage getFont(char letter)
	{
		int value = letter;
		
		int x = value % wLetter;
		int y = value / wLetter;
		
		return getLetter(x,y);
	}
	
	//load font from file
	public BufferedImage loadFont(String file)
	{
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
		}catch(Exception e) {
			System.out.println("ERROR: Can't load file: " + file);
		}
		
		return sprite;
	}
	
	//array of characters font
	public void loadSpriteArray()
	{
		spriteArray = new BufferedImage[wLetter][hLetter];
		
		for(int x = 0; x < wLetter; x++)		//go through the font image
			for(int y = 0; y < hLetter; y++)	//find the character
			{
				spriteArray[x][y] = getLetter(x,y);
			}
	}
	
	
}
