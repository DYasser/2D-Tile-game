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

public class Skeleton extends Entity{

	private AABB sense;
	private Camera cam;
	private int r;
	private float dmgx, dmgy; //judge distance knocked back
	private boolean goldspawn = false;
	
	public static ArrayList<Skeleton> skeletons = new ArrayList<>();	//Skeletons list
	
	public Skeleton(Camera cam, Sprite sprite, VectorXY orgin, int size) { 
		super(sprite, orgin, size);  
		this.cam = cam;   
		
		skeletons.add(this);
		
		dmg = 5;	//damage
		
		acc = .8f; //acceleration and maximum speed
		maxSpeed = 3.2f;
		
		r = 350;   
		drop = 20;	//gold dropped
		sense = new AABB(new VectorXY(orgin.x + size/2 - r/2, orgin.y + size/2 - r/2), r, this);
		
		//bounds dimension
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40); 
	} 

	public float getPosX() {return pos.x;} 
	public float getPosY() {return pos.y;} 
	 
	public void update() 
	{ 
		if(cam.getBoundsOnScreen().collides(this.bounds) && !fallen) { //inside camera and not dead
			
			isInvu(.2f);	//not attackable for 0.2 seconds
			
	 		if(hp <= 0)	//dead
			{
				fallen = true;
			}
			
			if(!fallen && !stop) {	//not dead and not stoped while player dialogues 
				for(int i = 0; i < Player.players.size(); i++) //through all players
				{ 
					Player player = Player.players.get(i);
					
					if(cam.getBoundsOnScreen().collides(this.bounds)) {	//player inside sense
						super.update();
						
						if(bounds.collides(player.bounds)) //attack player
						{ 	
							player.isDmged(dmg*GamePanel.difficulty);  
						}
						
						if(!hurted) { //player not attacked him
							move(player);
							dmgx = (pos.x - player.getPosX()) / 50;
							dmgy = (pos.y - player.getPosY()) / 50;
						}
						else //was attacked
						{		//knocked back
							dx = 0;
							dy = 0;
							pos.x += dmgx;
							pos.y += dmgy;
							sense.getPos().x += dmgx;
							sense.getPos().y += dmgy;
						} 
						
						if(!fallen) { 	//not dead
							if(!tc.collisionTile(dx, 0))
							{
								sense.getPos().x += dx;
								pos.x += dx;
							}
							if(!tc.collisionTile(0, dy))
							{
								sense.getPos().y += dy;
								pos.y += dy	;
							}
						}
					} 
				}
			} 	
		}
		if(drop != 0 && hp <=0)	//dead but drop still available
		{
			for(int i = 0; i < Player.players.size(); i++)
			{
				Player player = Player.players.get(i);
				if(cam.getBoundsOnScreen().collides(this.bounds)) {
					if(bounds.collides(player.bounds))	//player picks drop
					{ 	
						player.gold += drop;
						drop = 0;
					}
				}
	 		}
		}
	}
	
	private void move(Entity player) 
	{
		if(sense.colCircleBox(player.getBounds()))	//follows the player inside sense range
		{
			if(pos.y > player.pos.y+1)
			{ 
				up = true;
				right = false;
				dy -=acc;
				if(dy < -maxSpeed)
					dy = -maxSpeed;
			}
			else if(pos.y < player.pos.y-1)
			{
				up = false;
				right = true;
				dy +=acc;
				if(dy > maxSpeed)
					dy = maxSpeed;
			}
			else
			{
				dy = 0;
				up = false;
				right = false; 
			}
			
			if(pos.x > player.pos.x+1)
			{
				left = true;
				down = false;
				dx -=acc;
				if(dx < -maxSpeed)
					dx = -maxSpeed;
			}
			else if(pos.x < player.pos.x-1)
			{
				left = false;
				down = true;
				dx +=acc;
				if(dx > maxSpeed)
					dx = maxSpeed;
			}else {
				dx = 0;
				down = false;
				left = false;
			}
		}else
		{
			up = false; 
			down = false;
			left = false;
			right = false;
			dx = 0;
			dy = 0;
		}  
	}
	 
	@Override
	public void render(Graphics2D g) {  
		  
		if(cam.getBoundsOnScreen().collides(this.bounds) && !fallen) {	//inside camera and alive
			
			/*
			//show bounds 
			g.setColor(Color.green);
			g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
			
			g.setColor(Color.blue);
			g.drawOval((int) (sense.getPos().getWorldVar().x), (int) (sense.getPos().getWorldVar().y), r, r);
			*/
			
			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
	 
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
			//health bar
			g.setColor(Color.white); 
			g.drawRect((int) (pos.getWorldVarX(pos.x)+7), (int) (pos.getWorldVarY(pos.y)-1), 50, 5);
			g.setColor(Color.green);
			g.fillRect((int) (pos.getWorldVarX(pos.x)+8), (int) (pos.getWorldVarY(pos.y)), (int) hp/2, 4);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		else if(fallen && !goldspawn)	//dead but drop available
		{ 
			//Display gold
			new Gold(cam, new Sprite("entity/goldCoin.png",16,16), pos, 32);
			goldspawn = true;
		}
	}
}
