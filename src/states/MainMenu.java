package states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Skeleton;
import graphics.Sprite;
import tiles.TileManager;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class MainMenu {

	private Camera cam;
	private TileManager tm;
	private int dx = 1, dy = 1;
	private GamePanel game;

	private BufferedImage img, img2;
	private Image sword = null, shield = null;
	
	public MainMenu(GamePanel game)
	{ 
		
		cam = new Camera(new AABB(new VectorXY(0,0),GamePanel.width+64,GamePanel.height+64));
	 	tm = new TileManager("tiles/tilemap.xml", cam);
		this.game = game;
		try {
			img = ImageIO.read(new File("res/entity/Sword.png"));
			img2 = ImageIO.read(new File("res/entity/shield.png"));
		} catch (IOException e) {System.out.println("ERROR: Images not loaded correctly");}
		sword = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		shield = img2.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	}
	   
	public void input(MouseHandler mouse, KeyHandler key)
	{
		cam.input(mouse, key);
	}
	 
	public void update()
	{
		//moving background
		cam.getBounds().getPos().worldX += dx;
		cam.getBounds().getPos().worldY += dy;
		
		//limits
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

		g.drawImage(sword, 770, 150, game);
		g.drawImage(shield, 50,145, game);
 		Sprite.drawArray(g, "DAZZLING QUEST", new VectorXY(GamePanel.width/2-340,150), 72, 48);
 		Sprite.drawArray(g, "PLAY", new VectorXY(GamePanel.width/2-40,300), 32, 20);
 		Sprite.drawArray(g, "OPTION", new VectorXY(GamePanel.width/2-60,350), 32, 20);
 		Sprite.drawArray(g, "QUIT", new VectorXY(GamePanel.width/2-42,400), 32, 22);
	
 		switch(GamePanel.selected)
 		{
 		case 0:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-70,300), 32, 22);
 			break;
 		case 1:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-90,350), 32, 22);
 			break;
 		case 2:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-70,400), 32, 22);
 			break;
 		}
	}
}
