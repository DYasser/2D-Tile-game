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

public class Difficulty {	//select between difficulties

	private Camera cam;
	private TileManager tm;
	
	private int dx = 1, dy = 1;	//background speed x, y
	
	public Difficulty()
	{ 
		cam = new Camera(new AABB(new VectorXY(0,0),GamePanel.width+64,GamePanel.height+64));
		tm = new TileManager("tiles/tilemap.xml", cam);
	}
	   
	public void input(MouseHandler mouse, KeyHandler key)
	{
		cam.input(mouse, key);
	}
	 
	public void update()
	{
		//background move
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

 		Sprite.drawArray(g, "DIFFICULTY", new VectorXY(GamePanel.width/2-230,20), 72, 48);
 		Sprite.drawArray(g, "BACK", new VectorXY(30,10), 32, 20);
 		Sprite.drawArray(g, "EASY", new VectorXY(GamePanel.width/2-40,200), 48, 34);
 		Sprite.drawArray(g, "MEDIUM", new VectorXY(GamePanel.width/2-80,300), 48, 34);
 		Sprite.drawArray(g, "HARD", new VectorXY(GamePanel.width/2-42,400), 48, 34);
	
 		switch(GamePanel.selected)
 		{
 		case 0:
 			Sprite.drawArray(g, ">", new VectorXY(0,10), 32, 22);
 			break;
 		case 1:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-70,210), 32, 22);
 			break;
 		case 2:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-120,310), 32, 22);
 			break;
 		case 3:
 			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-70,410), 32, 22);
 			break;
 		}
	}
}
