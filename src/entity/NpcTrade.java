package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.VectorXY;

public class NpcTrade extends Entity{

	private String[] msg; 
	private Camera cam;
	
	private AABB sense;
	private int r;
	
	private boolean interact = false, display, pressedBefore = false;
	private int prompt, count = -1, choice = 0;
	
	public NpcTrade(Camera cam, Sprite sprite, VectorXY origin, String[] msg, int size) {
		super(sprite, origin, size);
		
		this.msg = msg;
		this.cam = cam;
		prompt = msg.length;
		
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
			
			if(cam.getBoundsOnScreen().collides(this.bounds)) {	//inside camera
				
				if(sense.colCircleBox(player.getBounds()))	//player inside sense
				{
					interact = true; 
					if(display)			//display msg
					{
						if(player.up)	//player presses up
						{
							choice = 1;
						}
						if(player.down)	//player presses down
						{
							choice = 0;
						}
						Entity.stop = true;	//stop all entities
					}
					else
						Entity.stop = false;
					
					if(player.getInteract() && count < prompt)	//presses
					{
						pressedBefore = true; 
					}
					else if(!player.getInteract() && count < prompt && pressedBefore) {	//in dialogue
						count++;
						display = true;
						pressedBefore = false;  
					}
					else if(player.getInteract() && count == prompt)
					{
						interact = false;
						display = false;
					}else if(!player.getInteract() && count == prompt && !display) //Decision and action
					{ 
						count = -1;
						if(choice == 1)
						{ 
							
						}
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
		
		if(cam.getBoundsOnScreen().collides(this.bounds) && !fallen) {
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
		 
		if(display)	//show dialogue
		{
			g.setColor(Color.black);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			g.fillRect(50, GamePanel.height - 150, GamePanel.width-100, 120);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
			if(count < prompt)
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
			
			if(count == prompt)	//Choices
			{ 
				String str1 = "Yes";
				String str2 = "No";
				
				Sprite.drawArray(g, str1, new VectorXY(100, GamePanel.height - 120), 24, 24, 16);	
				Sprite.drawArray(g, str2, new VectorXY(100, GamePanel.height - 80), 24, 24, 16);	
			}
			
			if(count != prompt)
				Sprite.drawArray(g, "> next", new VectorXY(GamePanel.width-160, GamePanel.height - 60), 24, 24, 16);	
			else
				switch(choice)
				{
				case 1:
					Sprite.drawArray(g, ">", new VectorXY(70, GamePanel.height - 120), 24, 24, 16);	
					break;
				default:
					Sprite.drawArray(g, ">", new VectorXY(70, GamePanel.height - 80), 24, 24, 16);
				}	
		}
	} 
}