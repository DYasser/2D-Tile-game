package entity;

import java.awt.Graphics2D;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.VectorXY;

public class Chest extends Entity{

	private Camera cam;
	private int r;
	private AABB sense;
	private boolean opened = false;
	
	public Chest(Sprite sprite, VectorXY origin, int size, Camera cam) {
		super(sprite, origin, size);
		
		this.cam = cam;
		
		r = 100;	//radius
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2), r, this);	//circular sense
		
		//bounds dimension
		bounds.setWidth(64);
		bounds.setHeight(64); 
		bounds.setxOffset(0);  
		bounds.setyOffset(0);
		 
	}

	public void update() 
	{
		for(int i = 0; i < Player.players.size(); i++)	//loop through all players (multiplayer)
		{
			Player player = Player.players.get(i); 
			
			if(cam.getBoundsOnScreen().collides(this.bounds)) { 	//inside the screen
				if(new AABB(new VectorXY(player.getPosX() + player.dx + player.bounds.getXOffset() , player.getPosY() + player.bounds.getYOffset()), 32,0).collides(bounds))
				{ 			//player touches chest, block
					player.pos.x -= player.dx; 
				}
				if(new AABB(new VectorXY(player.getPosX() + player.bounds.getXOffset() , player.getPosY() + player.dy + player.bounds.getYOffset()), 32,0).collides(bounds)) 
				{ 			//player touches chest, block
					player.pos.y -= player.dy;
				}
				
				if(sense.colCircleBox(player.bounds) && !opened) // player inside sense
				{
					interact = true;  	// can interact with it
					if(player.getInteract())
					{					//player interacted
						opened = true;	
						setAnimation(0, getSprite().getSpriteArray(1), 0);	//change to opened
						interact = false;
						player.gold += 40;	//player gets gold
					}
				}
				else 
					interact = false;
				
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {

		if(cam.getBoundsOnScreen().collides(this.bounds)) {	//only appear if it appears on screen
			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
			if(getInteract())
				getSprite().drawArray(g, "E to interact", new VectorXY(pos.getWorldVarX(pos.x) - 55,pos.getWorldVarY(pos.y) -40), 24,24,13);
		}
	}

}
