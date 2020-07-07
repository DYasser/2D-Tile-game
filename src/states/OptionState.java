package states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GameLauncher;
import graphics.Sprite;
import tiles.TileManager;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class OptionState {

	private Camera cam;
	private TileManager tm;
	private int dx = 1, dy = 1;
	private GamePanel game;
	public static int xSound = 450, xBrightness = 450;

	private BufferedImage img, img2;
	private Image bar = null, selecter = null;
	
	public OptionState(GamePanel game)
	{
		cam = new Camera(new AABB(new VectorXY(0,0),GamePanel.width+64,GamePanel.height+64));
		tm = new TileManager("tiles/tilemap.xml", cam);
		this.game = game;
		try {
			img = ImageIO.read(new File("res/entity/bar.png"));
			img2 = ImageIO.read(new File("res/entity/selecter.png"));
		} catch (IOException e) {System.out.println("ERROR: Images not loaded correctly");}
		
		bar = img.getScaledInstance(384, 48, Image.SCALE_SMOOTH);
		selecter = img2.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
	}
	   
	public void input(MouseHandler mouse, KeyHandler key)
	{
		cam.input(mouse, key);
		mouse.update();
	}
	
	public void update()
	{	
		GameLauncher.gainControl.setValue(( (xSound-284)/4.5f ) - 70); //Sets the volume
		
		//move background
		cam.getBounds().getPos().worldX += dx;
		cam.getBounds().getPos().worldY += dy;
		
	 	if(cam.getBounds().getPos().worldX == 2200)
			dx = -1;
		else if(cam.getBounds().getPos().worldX == 0)
			dx = 1;
		
		if(cam.getBounds().getPos().worldY == 2400)
			dy = -1;
		else if(cam.getBounds().getPos().worldY == 100 )
	 		dy = 1;
	}
	
	public void render(Graphics2D g)
	{
 		cam.render(g);
		tm.render(g);

 		g.setColor(Color.black);  
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .7f));
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
 		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

 		Sprite.drawArray(g, "OPTION", new VectorXY(GamePanel.width/2-140,50), 72, 48);
 		Sprite.drawArray(g, "SOUND", new VectorXY(GamePanel.width/2-40,200), 32, 20);
		g.setColor(Color.gray);
 		g.fillRect(284, 267, xSound - 264, 17); 
 		g.drawImage(bar, 270,250, game);
		g.drawImage(selecter, xSound, 250, game);
		
 		Sprite.drawArray(g, "BRIGHTNESS", new VectorXY(GamePanel.width/2-90,350), 32, 20);
 		g.fillRect(284, 416, xBrightness - 264, 17);
 		g.drawImage(bar, 270,400, game);
		g.drawImage(selecter, xBrightness, 400, game);
 		
 		Sprite.drawArray(g, "DEFAULT", new VectorXY(GamePanel.width/2-60,500), 32, 22);
 		Sprite.drawArray(g, "BACK", new VectorXY(30,10), 32, 22);
 		
 		switch(GamePanel.selected)
 		{
 		case 0:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-90,500), 32, 22);
 			break;
 		case 1:
 			Sprite.drawArray(g, ">", new VectorXY(0,10), 32, 22);
 			break;
 		}
 		
 	}
}