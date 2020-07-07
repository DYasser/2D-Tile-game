package entity;

import java.awt.Graphics2D;

import graphics.Sprite;
import util.Camera;
import util.VectorXY;

public class Gold extends Entity{

	private Camera cam;
	
	public Gold(Camera cam, Sprite sprite, VectorXY orgin, int size) { 
		super(sprite, orgin, size);  
		this.cam = cam;   
		
		setAnimation(0, getSprite().getSpriteArray(0), 5);	
		
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
			
			if(!fallen && !stop) {	//not dead and not stoped while player dialogues 
				for(int i = 0; i < Player.players.size(); i++) //through all players
				{ 
					Player player = Player.players.get(i);
					
					if(cam.getBoundsOnScreen().collides(this.bounds)) {	//player inside sense
						super.update();
						
						if(bounds.collides(player.bounds)) //attack player
						{ 	
							fallen = true; 
						}
					} 
				}
			} 	
		}
		
	}
	
	@Override
	public void render(Graphics2D g) {  
		  
		if(cam.getBoundsOnScreen().collides(this.bounds) && !fallen) {	//inside camera and alive
			
			g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
	 
		}
	}
}
