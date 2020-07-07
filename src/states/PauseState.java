package states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Sprite;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class PauseState{

	public PauseState() {}

	public void update() {}

	public void render(Graphics2D g) {
		
		g.setColor(Color.black); 
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .7f));
		g.fillRect(0, 0, GamePanel.width, GamePanel.height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		Sprite.drawArray(g, "E to unpause", new VectorXY(350,230), 20, 16);
		Sprite.drawArray(g, "Paused", new VectorXY(290,250), 64, 50);

		Sprite.drawArray(g, "WASD to move", new VectorXY(350,350), 20, 16);
		Sprite.drawArray(g, "Space to attack", new VectorXY(330,380), 20, 16);
		Sprite.drawArray(g, "Ctrl to dash", new VectorXY(350,410), 20, 16);
		Sprite.drawArray(g, "E to interact", new VectorXY(340,440), 20, 16);
		
	}

	public void input(MouseHandler mouse, KeyHandler key) {}

}
