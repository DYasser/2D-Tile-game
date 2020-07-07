package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.VectorXY; 

//Bird goes through the player
//Bull kicks the player with it's speed 

public class Phantom extends Entity{ 

	public static ArrayList<Entity> phantoms = new ArrayList<>(); 	//phantoms list
	
	private Camera cam;
	private AABB sense;
	private int r;
	
	private double cd = System.currentTimeMillis();	//cooldown
	private double now = System.currentTimeMillis();
	
	private float x, y;
	private boolean getDistance = true;	
	private boolean goldspawn = false;
	
	public Phantom(Sprite sprite, VectorXY origin, int size, Camera cam) {
		super(sprite, origin, size);
		phantoms.add(this);

		this.cam = cam;
		right = true;
		
		dmg = 10;	//damage
		
		acc = 3f;
		maxSpeed= 4f;	
		
		drop = 20; 	//gold dropped
		
		r = 900;
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2), r, this);
		
		//bounds dimension
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40); 
	} 
 
	public void update()
	{
		if(cam.getBoundsOnScreen().collides(this.bounds) && !stop && hp > 0) {  //inside camera
			super.update(); 
			
			isInvu(1f);	//not attackable for 1 sec if attacked
			
			
			for(int i = 0; i < Player.players.size(); i++)
			{ 
				Player player = Player.players.get(i);  
				
				if(sense.colCircleBox(player.bounds)) {	//player inside sense
					
					now = System.currentTimeMillis(); 	
					
					if(bounds.collides(player.bounds))	//attacked player
					{  
						player.isDmged(dmg*GamePanel.difficulty); 
					}
					
					if((now-cd)/1000 < 4) //locking in player
					{  
						x = player.getPosX(); 
						y = player.getPosY();  
					}
	 				else if((now-cd)/1000 > 4)	//Dash into player
					{   
						float diffx = Math.abs(pos.x -x);
						float diffy = Math.abs(pos.y -y);
						
						if(getDistance) { //gets distance and speed
							 dx = diffx/10;
							 dy = diffy/10;
							 getDistance = false;
						}
						
						if(!(diffx < 5)) { 
							if(pos.x < x) {
								pos.x+=dx; 
								sense.getPos().x+=dx;
							}
							else { 
								pos.x-=dx; 
								sense.getPos().x-=dx;
							}
						} 
						
						if(!(diffy < 5)) { 
							if(pos.y < y) {
								pos.y+=dy;
								sense.getPos().y+=dy;
							}
							else { 
								pos.y-=dy; 
								sense.getPos().y-=dy;
							}
			 			}
	
						if((diffx < 10 && diffy < 10))	//resets
						{
							cd = System.currentTimeMillis();
							dx = dy = 0;
							getDistance = true;  
						} 
					}
				}
				else
					cd = System.currentTimeMillis();
			}
		}if(drop != 0 && hp <=0)	//dead and drop still not taken
		{
			for(int i = 0; i < Player.players.size(); i++)
			{
				Player player = Player.players.get(i);
				if(cam.getBoundsOnScreen().collides(this.bounds)) {
					if(bounds.collides(player.bounds))	//player takes drop
					{ 
						player.gold += drop;
						drop = 0;
					}
				}
			}
		}
	}
	 
	@Override
	public void render(Graphics2D g) {
		if(cam.getBoundsOnScreen().collides(this.bounds) && hp >0) {	//inside camera and alive
			for(int i = 0; i < Player.players.size(); i++) 
			{
				Player player = Player.players.get(i);
				
				/*
				//show bounds
				g.setColor(Color.green);
				g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
				
				g.setColor(Color.white);
				g.drawLine((int)pos.getWorldVarX(pos.x) + size/2 ,(int)pos.getWorldVarY(pos.y) +size/2,(int)player.pos.getWorldVarX(x) + player.size/2, (int)player.pos.getWorldVarY(y) + player.size/2);
				g.setColor(Color.blue);
				g.drawOval((int) (sense.getPos().getWorldVar().x) +25, (int) (sense.getPos().getWorldVar().y), r, r);
				*/
				
				if(hurted) 	//indicate that being attacked
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
				
				if(sense.colCircleBox(player.bounds)) //charging 
					if((now-cd)/1000 < 1)
					{
						g.setColor(Color.green);
						g.drawOval((int)pos.getWorldVarX(pos.x)  +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
					}
					else if((now-cd)/1000 < 3)
					{
						g.setColor(Color.yellow);
						g.drawOval((int)pos.getWorldVarX(pos.x) +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
						
					}else if((now-cd)/1000 < 4)
	 				{
			 			g.setColor(Color.red);
						g.drawOval((int)pos.getWorldVarX(pos.x) +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
					} 
				
			 	g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) pos.getWorldVar().y, size, size , null);
			
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
				g.setColor(Color.white); 
				g.drawRect((int) (pos.getWorldVarX(pos.x)+7), (int) (pos.getWorldVarY(pos.y)-1), 50, 5);
				g.setColor(Color.green);
				g.fillRect((int) (pos.getWorldVarX(pos.x)+8), (int) (pos.getWorldVarY(pos.y)), (int) hp/2, 4);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}	
		}
		else if(fallen && !goldspawn)	//dead but drop available
		{ 
			//Display gold
			new Gold(cam, new Sprite("entity/goldCoin.png",16,16), pos, 32);
			goldspawn = true;
		}
	}
}
