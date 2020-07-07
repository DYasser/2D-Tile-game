package states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import graphics.Sprite;
import tiles.TileManager;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class GameOverState {
	
	private TileManager tm;
	private Camera cam;
	
	public static VectorXY map;
	
	public GameOverState() {
		cam = new Camera(new AABB(new VectorXY(0,0),GamePanel.width+64,GamePanel.height+64));
		tm = new TileManager("tiles/dungeon.xml", cam);
	}

	public void update() {}
 
	public void render(Graphics2D g) {
		cam.render(g);
		tm.render(g);
		
		for(int i = 0; i<Entity.entity.size(); i++)
		{
			Entity.entity.get(i).render(g);
		}
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f));
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		Sprite.drawArray(g, "GAME OVER", new VectorXY(GamePanel.width/2-243,150), 72, 56);
		Sprite.drawArray(g, "RETRY ?", new VectorXY(GamePanel.width/2-113,350), 48, 32);

		Sprite.drawArray(g, "YES", new VectorXY(GamePanel.width/2-160,420), 48, 32);
		Sprite.drawArray(g, "NO", new VectorXY(GamePanel.width/2+80,420), 48, 32);	
		Sprite.drawArray(g, "QUIT", new VectorXY(GamePanel.width/2-64,510), 48, 32); 
		
		if(MouseHandler.isIn(GamePanel.width/2-158,422, 100, 43)){
			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-190,420), 48, 32);
		}
		else if(MouseHandler.isIn(GamePanel.width/2+82,420, 70, 43)){
			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2+50,420), 48, 32);
		}
		else if(MouseHandler.isIn(GamePanel.width/2-62,510, 140, 43)){
			Sprite.drawArray(g, ">", new VectorXY(GamePanel.width/2-94,510), 48, 32);
		}

	}

	public void input(MouseHandler mouse, KeyHandler key) {}

}
