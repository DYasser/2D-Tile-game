package entity;

import java.awt.Graphics2D;

import graphics.Sprite;
import states.PlayState;
import util.AABB;
import util.Camera;
import util.VectorXY;

public class ToDungeonEvent extends Entity{
	
	private Camera cam; 
	private String notice;
	private AABB sense;
	private int r;
	
	public ToDungeonEvent(Camera cam, VectorXY origin,String notice, int size) {
		super(new Sprite("entity/nothing.png",16 ,16), origin, size);	//no sprite, transparent/ nothing
		
		this.notice = notice;
		this.cam = cam;
		
		acc = 0; 
		maxSpeed = 0; 
		
		r = 150; 
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2 +10), r, this);
	}

	public void update()
	{
		for(int i = 0; i < Player.players.size(); i++)	//go through players
		{
			Player player = Player.players.get(i);
		
			if(cam.getBoundsOnScreen().collides(this.bounds)) {	//inside camera
				
				if(sense.colCircleBox(player.getBounds())) //player inside sense
				{
					interact = true; 
					if(player.getInteract())	//presses interact, change maps
					{
						PlayState.mapName = PlayState.dungeon;
						PlayState.changeStates = true;
						player.interact = false;
					}
				} 
				else {
					interact = false;
				}
			}
		}
	}
	 
	@Override
	public void render(Graphics2D g) { 
		if(cam.getBoundsOnScreen().collides(this.bounds)) { //inside camera
			
			/*
			//show bounds 
			g.setColor(Color.green);
			
			g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
			
			g.setColor(Color.blue);
			g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);
			*/
			if(getInteract()) 
			{
				getSprite().drawArray(g, notice, new VectorXY(pos.getWorldVarX(pos.x) - 40,pos.getWorldVarY(pos.y) -20), 24,24,13);
				
			}
		}
	}

	
}
