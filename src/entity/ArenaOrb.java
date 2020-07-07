package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.VectorXY;

public class ArenaOrb extends Entity{
	
	private ArrayList<ArenaOrb> orbs = new ArrayList<>(); //List all orbs

	private VectorXY projectile;
	private AABB sense, sensePro; 
	private int r; 					//radius of sense
	private Camera cam;
	
	private float x,y;				//Coordinates
	private boolean getDistance;	//read distance
	
	private double cd = System.currentTimeMillis();	// cooldown
	private double now = System.currentTimeMillis();
	
	public ArenaOrb(Sprite sprite, VectorXY origin, int size, Camera cam) {
		super(sprite, origin, size);
		
		orbs.add(this);
		this.cam = cam;
		
		right = true;	//for animation
		
		dmg = 20;	//damage
		r = 500;  
		
		projectile = origin; 
		
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2), r, this);	//Orb Sense
		sensePro = new AABB(new VectorXY(projectile.x + size/2 - 100/2, projectile.y + size/2 - 100/2), 100, this);	//Projectile sense
		
		//Bounds dimensions 
		bounds.setWidth(42); 
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40); 
	} 
 
	public void update(){
		super.update();
		
		for(int i = 0; i < Player.players.size(); i++)  //Going through all players (future multiplayer)
		{
			Player player = Player.players.get(i);
			
			if(sense.colCircleBox(player.bounds)) {		//Player inside sense

				if(sensePro.colCircleBox(player.bounds))	//Player touched by projectile
				{
					player.isDmged(dmg*GamePanel.difficulty);
				}
				
				now = System.currentTimeMillis();
				if((now - cd)/1000 < 3) {		//if 3 seconds passed
					x = player.getPosX();
					y = player.getPosY();
				}
				else					  		// attack player
				{ 
					float diffx = Math.abs(sensePro.getPos().x -x);
					float diffy = Math.abs(sensePro.getPos().y -y);

					if(getDistance) { 	//gets distance and 1/10 becomes speed
						 dx = diffx/10; 
						 dy = diffy/10;
						 getDistance = false;
					}
					
					if(!(diffx < 5)) { 
						if(sensePro.getPos().x < x)
							sensePro.getPos().x+=dx;   
						else 
							sensePro.getPos().x-=dx; 
					} 
					 
					if(!(diffy < 5)) { 
						if(sensePro.getPos().y < y)
							sensePro.getPos().y+=dy;
						else 
							sensePro.getPos().y-=dy; 					
					}
					 
					if((diffx < 5 && diffy < 5) || ((now-cd)/1000 > 7))	
					{
						cd = System.currentTimeMillis();	//resets cooldown
						dx = dy = 0; 
						getDistance = true; 
						projectile = pos; 				//resets position
					}	
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if(cam.getBoundsOnScreen().collides(this.bounds)) {		//only render if it appears on screen
			g.drawImage(ani.getImage(), (int)pos.getWorldVarX(sensePro.getPos().x), (int)pos.getWorldVarY(sensePro.getPos().y), 100, 100 , null);	
		}
	}
}
