package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import graphics.Sprite;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.VectorXY;

public class MageNormal extends Entity{
	
	private ArrayList<MageNormal> mages = new ArrayList<>();	//mages list
	
	private int r;
	private AABB sense, sensePro;	//sense and projectile sense
	private Camera cam;
	
	private float x,y;		//coordinates
	private boolean getDistance;
	
	private double cd = System.currentTimeMillis();	//cooldown properties
	private double now = System.currentTimeMillis();
	
	public MageNormal(Sprite sprite, VectorXY origin, int size, Camera cam) {
		super(sprite, origin, size);
		mages.add(this);

		this.cam = cam;
		right = true;	//initial animation
		
		dmg = 15;
		pos = origin;
		
		r = 700;
		sense = new AABB(new VectorXY(origin.x + size/2 - r/2, origin.y + size/2 - r/2), r, this);
		sensePro = new AABB(new VectorXY(origin.x + size/2 - 10/2, origin.y + size/2 - 10/2), 10, this);
		
		//Bounds dimension
		bounds.setWidth(42);
		bounds.setHeight(20);
		bounds.setxOffset(12);
		bounds.setyOffset(40);
	} 
  
	public void update()
	{	super.update();
		
		for(int i = 0; i < Player.players.size(); i++)  //loop through players (multiplayer)
		{
			Player player = Player.players.get(i);
			if(sense.colCircleBox(player.bounds)) {	//inside range

				now = System.currentTimeMillis();
				if((now - cd)/1000 < 3) {	//locks the player in
					x = player.getPosX() + player.size/2;
					y = player.getPosY() + player.size/2;
				}
 				else if((now-cd)/1000 > 3)  //launches projectile
				{
					float diffx = Math.abs(sensePro.getPos().x -x);
					float diffy = Math.abs(sensePro.getPos().y -y);

					if(getDistance) { 	//gets distance and speed of projectile
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
					
					if(sensePro.colCircleBox(player.bounds)) 	//projectile touches player
						player.isDmged(dmg*GamePanel.difficulty);
					
					if((diffx < 5 && diffy < 5) || ((now-cd)/1000 > 7))	//resets
					{
						cd = System.currentTimeMillis();
						dx = dy = 0;
						getDistance = true; 
						sensePro.getPos().x = sense.getPos().x + r/2 - 10/2;
						sensePro.getPos().y = sense.getPos().y + r/2 - 10/2; 
					}
				}
			}
			else
				cd = System.currentTimeMillis();
		}
	}
	 
	@Override
	public void render(Graphics2D g) {
		for(int i = 0; i < Player.players.size(); i++)
		{
			Player player = Player.players.get(i);
			
			/*
			//bounds
			g.setColor(Color.blue);
			g.drawOval((int) (sense.getPos().getWorldVar().x) +25, (int) (sense.getPos().getWorldVar().y), r, r);
			*/
			
			if(sense.colCircleBox(player.bounds)) {	//charges attack
				if((now-cd)/1000 < 1)
				{
					g.setColor(Color.green);
					g.drawOval((int)pos.getWorldVarX(pos.x)  +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
				}
				else if((now-cd)/1000 < 2)
				{
					g.setColor(Color.yellow);
					g.drawOval((int)pos.getWorldVarX(pos.x) +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
					
				}else if((now-cd)/1000 < 3)
 				{
					g.setColor(Color.red);
					g.drawOval((int)pos.getWorldVarX(pos.x) +6,(int)pos.getWorldVarY(pos.y) + 25, 50, 50);
				} 
			}

			g.setColor(new Color(113,54,95)); 
			g.drawImage(ani.getImage(), (int) sense.getPos().getWorldVarX(sense.getPos().x) + r/2 - size/2, (int) sense.getPos().getWorldVarY(sense.getPos().y) -size/2 + r/2, size, size , null);

			//projectile
			g.fillOval((int) (sensePro.getPos().getWorldVar().x), (int) (sensePro.getPos().getWorldVar().y), 10, 10);
			g.setColor(Color.white);
			g.drawOval((int) (sensePro.getPos().getWorldVar().x), (int) (sensePro.getPos().getWorldVar().y), 10, 10);
			
			//g.fillOval((int)pos.getWorldVarX(projectile.x) + size/2 , (int)pos.getWorldVarY(projectile.y) +size/2, 10, 10);
		}
	}
	
}
