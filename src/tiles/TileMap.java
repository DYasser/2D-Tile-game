package tiles;

import java.awt.Graphics2D;

import util.AABB;

public abstract class TileMap {	//abstract class
 
	public abstract void render(Graphics2D g, AABB cam);
}
