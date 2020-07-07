package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.VectorXY;

public class Npc extends Entity{

	private Camera cam; 
	private String[] msg;
	private AABB sense;
	private int r;
	
	private boolean interact = false, display, pressedBefore = false;
	private int len, count = -1;
	
	public Npc(Camera cam, Sprite sprite, VectorXY origin, String[] msg, int size) {
		super(sprite, origin, size);

		this.cam = cam;
		
		this.msg = msg;
		len = msg.length;
		
		r = 150; 
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2 +10), r, this);
		
		//Bounds dimension
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40);
	}

	public void update()
	{
		for(int i = 0; i < Player.players.size(); i++)	//through all players
		{
			Player player = Player.players.get(i);
			
			if(cam.getBoundsOnScreen().collides(this.bounds)) {	//inside screen
				
				if(sense.colCircleBox(player.getBounds())) //player inside sense
				{
					interact = true; 	//show interaction
					if(display)
					{
						Entity.stop = true; 	//stop other entities while talking
					}
					else
						Entity.stop = false;
					
					if(player.getInteract() && count < len-1) //inside dialogue
					{
						pressedBefore = true;
					}
					else if(!player.getInteract() && count < len-1 && pressedBefore) {
						count++;
						display = true;
						pressedBefore = false; 
					}
					else if(player.getInteract() && count == len-1)
					{
						interact = false;
						display = false;
					}else if(!player.getInteract() && count == len-1 && !display)
					{
						count = -1;
					}
				} 
				else {
					count = -1;
					interact = false;
					display = false;
				}
			}
		}
	}
	 
	@Override
	public void render(Graphics2D g) { 
		if(cam.getBoundsOnScreen().collides(this.bounds)) {	//inside camera
			/*
			//show bounds 
			g.setColor(Color.green);
			
			g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
			
			g.setColor(Color.blue);
			g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);
			*/
			
			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
	
			if(interact) 
			{
				getSprite().drawArray(g, "E to interact", new VectorXY(pos.getWorldVarX(pos.x) - 40,pos.getWorldVarY(pos.y) -20), 24,24,13);
				
			}
		}
		 
		if(display) //dialogue
		{
			g.setColor(Color.black);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			g.fillRect(50, GamePanel.height - 150, GamePanel.width-100, 120);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			if(msg[count].length() < 42)
			{
				Sprite.drawArray(g, msg[count], new VectorXY(100, GamePanel.height - 100), 24, 24, 16);				
			}
			else 
			{
				String part1 = msg[count].substring(0,43);
				String part2 = msg[count].substring(43);
				
				Sprite.drawArray(g, part1, new VectorXY(100, GamePanel.height - 120), 24, 24, 16);	

				Sprite.drawArray(g, part2, new VectorXY(100, GamePanel.height - 80), 24, 24, 16);	
				
			}
			if(count != len-1)
				Sprite.drawArray(g, "> next", new VectorXY(GamePanel.width-160, GamePanel.height - 60), 24, 24, 16);	
			else
				Sprite.drawArray(g, "> end", new VectorXY(GamePanel.width-145, GamePanel.height - 60), 24, 24, 16);	
		}
	}

	
}
