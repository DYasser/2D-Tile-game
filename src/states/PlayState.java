package states;

import java.awt.Graphics2D;

import entity.ArenaOrb;
import entity.Chest;
import entity.Entity;
import entity.Healer;
import entity.MageNormal;
import entity.Notice;
import entity.Npc;
import entity.Phantom;
import entity.Player;
import entity.Skeleton;
import entity.ToDungeonEvent;
import entity.ToHouse1Event;
import entity.ToHouse2Event;
import entity.ToMainWorldEvent;
import entity.Traitor;
import graphics.Font;
import graphics.Sprite;
import tiles.TileManager;
import util.AABB;
import util.Camera;
import util.GamePanel;
import util.KeyHandler;
import util.MouseHandler;
import util.VectorXY;

public class PlayState{

	private Font font;

	private PauseState pause = new PauseState();
	
	//All entities needed no more
	private Player player;
	private Skeleton enemy;
	private Npc npc;
	private Traitor choiceNpc;
	private TileManager tm;
	//private TileManager tm2;	//next update
	private Camera cam; 
	private Phantom bull;
	private MageNormal tower;
	private Chest chest;
	private Healer npcHeal;
	private ArenaOrb orb;
	private Notice noticeClosed;

	public static VectorXY playerSpawn = new VectorXY(0 + (GamePanel.width/2)-32, 0+(GamePanel.height/2)-32 );
	public static boolean entered = false;			//entered house
	public static boolean changeStates = false;		//requierement to switch states
	
	public static enum STATES	//all ingame states
	{
		GAME, 
		PAUSE,
		INVENTORY
	}
 	
	public static STATES state = STATES.GAME; 
	
	//dialogues
	private String[] msg = new String[3];
	private String[] tips = new String[4];
	private String[] notice = new String[2];
	private String[] direction = new String[1];
	private String[] books = new String[1];
	private String[] picture1 = new String[1];
	private String[] book1 = new String[1];
	private String[] book2 = new String[2];
	private String[] diary = new String[5];
	private String[] clothes = new String[1];
	private String[] alarm1 = new String[1];
	private String[] alarm2 = new String[1];
	private String[] npc1 = new String[1];
	private String[] npc2 = new String[2];
	private String[] npc3 = new String[1];
	private String[] npc4 = new String[1];
	private String[] heal = new String[1];
	private String[] heal1 = new String[1];
	private String[] traitor = new String[1];
	//maps 
	public static VectorXY map;

	public static String dungeon = "tiles/dungeon.xml";
	public static String mainWorld = "tiles/MainWorld.xml";
	public static String house1 = "tiles/House1.xml";
	public static String house2 = "tiles/house2.xml";
	
	//public static String mainWorld2 = "tiles/MainWorld2.xml"; //future update

	public static String mapName = mainWorld;
	//public static String d3Map = mainWorld2;		//future update
	
	private void dungeonSpawn()	//special spawn if player in dungeon
	{
		mapName = dungeon;
		npc1[0] = "Oh thankfully the hero is here, please helpour families they have been abducted";
		npc2[0] = "Oh hi! I was just going to attack them by  myself, I gathered 2 of my companions";
		npc2[1] = "with me, go to the left side u will see    them";
		npc3[0] = "Almost there, the great evil is there";
		npc4[0] = "I never doubted the hero! let's get out of here";
		heal[0] = "I am a healer! to heal your injuries come  to me I will help you for 50 coins";
		heal1[0] = "last go before helping everyone, do you   wanna heal your injuries for 50 golds?";
		traitor[0] = "Do you think the skeletons are uncool?";
		
		chest = new Chest(new Sprite("entity/chest.png", 16,16), new VectorXY(1022, 1344), 64, cam);
		chest = new Chest(new Sprite("entity/chest.png", 16,16), new VectorXY(1984, 768), 64, cam);
		chest = new Chest(new Sprite("entity/chest.png", 16,16), new VectorXY(2944, 2560), 64, cam);
		chest = new Chest(new Sprite("entity/chest.png", 16,16), new VectorXY(1984, 2752), 64, cam);
		npcHeal = new Healer(cam, new Sprite("entity/healer.png",32,32), new VectorXY(192,1664),heal, 64);//I am a healer, if u would like to heal your injuries come to me I will help you for 50 coins
		npcHeal = new Healer(cam, new Sprite("entity/healer2.png",32,32), new VectorXY(2048,192),heal1, 64);//last go before helping everyone, for 50 gold would you like to recover from ur injuries ?
		npc = new Npc(cam, new Sprite("entity/feNpc.png",32,32), new VectorXY(192,1920),npc1, 64); //Oh thankfully the hero is here, please help our families they have been abducted
		npc = new Npc(cam, new Sprite("entity/feNpc.png",32,32), new VectorXY(1024,1856),npc2, 64); //Oh hi, I was just going to attack them by myself, I gathered 2 of my companions with me, go to the left side u will see them
		npc = new Npc(cam, new Sprite("entity/maNpc.png",32,32), new VectorXY(2624,512),npc3, 64); //Almost there, the great evil is there
		npc = new Npc(cam, new Sprite("entity/feNpc2.png",32,32), new VectorXY(128,576),npc4, 64); //End Npc, event 

		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(1088,2688), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(1792,1792), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(1728,1280), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(2496,2432), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(2368,2560), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(2432,2304), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(2624,2560), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(1344,512), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(1216,256), 64);
		enemy = new Skeleton(cam, new Sprite("entity/skeleton.png",32,32), new VectorXY(768,448), 64);

		choiceNpc = new Traitor(cam, new Sprite("entity/feNpc2.png",32,32), new VectorXY(2112,2752),traitor, 64);

		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(2944,1216), 64, cam);
		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(2368,1024), 64, cam);
		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(1024,128), 64, cam);
		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(960,1024), 64, cam);
		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(128,256), 64, cam);
		bull = new Phantom(new Sprite("entity/phantom.png", 32,32), new VectorXY(384,640), 64, cam);
		
		tower = new MageNormal(new Sprite("entity/mage.png", 32,32), new VectorXY(2624, 1344), 64, cam);	
		tower = new MageNormal(new Sprite("entity/mage.png", 32,32), new VectorXY(2176, 2496), 64, cam);	
		tower = new MageNormal(new Sprite("entity/mage.png", 32,32), new VectorXY(576, 448), 64, cam);
		
		player = new Player(new Sprite("entity/player.png", 32, 32), new VectorXY(1118, 1664), 64, cam);
		player.setAnimation(2, player.getSprite().getSpriteArray(2), 2);
	} 
	
	private void mainSpawn() 	//spawn in player in main world
	{
		mapName = mainWorld; 
		tips[0] = "USE WASD TO MOVE AROUND THE WORLD, SPACE TO ATTACK MONSTERS";
		tips[1] = "CTRL TO DASH, YOU CAN USE IT EACH TIME THE YELLOW BAR IS FULL";
		tips[2] = "E TO INTERACT AND ESC TO PAUSE THE GAME";
		tips[3] = "HAVE FUN!";
		notice[0] = "WELCOME TO DAZZLING VILLAGE!";
		notice[1] = "EXPLORE AND FIND THE HOUSE OF THE CHIEF IF YOU SEEK FOR EXCITEMENT";
		direction[0] = "Turn left, then right to find the chief's  house.";
		npc = new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY((GamePanel.width/2)-32 +30, 0+(GamePanel.height/2)-32 +365),tips, 64);
		npc = new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(256, 0+(GamePanel.height/2)-32 +365),notice, 64);
		npc = new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(768,1792),direction, 64);
		new Notice(cam, new VectorXY(990,640), "Closed", 64);
		new Notice(cam, new VectorXY(1630,640), "Closed", 64);
		new Notice(cam, new VectorXY(2270,640), "Closed", 64);
		new Notice(cam, new VectorXY(2910,640), "Closed", 64); 
		new Notice(cam, new VectorXY(478,1152), "Closed", 64); 
		new Notice(cam, new VectorXY(990,1536), "Closed", 64);
		new Notice(cam, new VectorXY(1630,1536), "Closed", 64);
	 	new Notice(cam, new VectorXY(2270,1536), "Closed", 64);
	 	new Notice(cam, new VectorXY(2910,1536), "Closed", 64);
	 	new Notice(cam, new VectorXY(1054,2304), "Chief", 64);
	 	new Notice(cam, new VectorXY(606,2304), "Cherif", 64);
		new ToHouse1Event(cam, new VectorXY(1118,2184), "E to enter", 64);
		if(!entered)
			new Notice(cam, new VectorXY(705,2166), "Closed", 64);
		else
			new ToHouse2Event(cam, new VectorXY(705,2166), "E to enter", 64);

		player = new Player(new Sprite("entity/player.png", 32, 32),playerSpawn , 64, cam);
	}
	
	private void house1Spawn() 	//spawn if player in house 1
	{
		books[0] = "Looks like there are tons of interesting to read books";
		picture1[0] = "What a beautiful drawing, it's as if it was an actual picture";
		book1[0] = "The whole serial of Harry potter, he must  be a lover of it";
		book2[0] = "It isn't normal to have all of the books   thrown like this, there has been a fight";
		book2[1] = "here. Most likely!";
		clothes[0] = "Some old person's clothes, maybe I should  look too much into it";
		alarm1[0] = "Set up for 7 AM";
		alarm2[0] = "Set up for 3 PM... Even with that he might not wake up";
		diary[0] = "'Dear diary, today was a horrible day.      Monsters appeared in the village and";
		diary[1] = "kidnapped all the kids. Shortly after, all  adults went after them, but never came back.";
		diary[2] = "I am the only survivor of that battle; or   should I say massacreToday is the day I go ";
		diary[3] = "after them, the hero we sent a request to   should arrive soon, I will win as much time as";
		diary[4] = "possible. WE WILL TRIOMPH'";
		mapName = house1; 
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(990-448,900-64), books, 64);
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(2174-448,836-64), picture1, 64);
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(2174-448,1028-64), book1, 64); // harry potter
		new Notice(cam,new VectorXY(1856-448,1028-64), "Some book", 64); // shows some book
		new Notice(cam, new VectorXY(1600-448,1028-64), "Other books", 64); // more books
	 	new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1280-448,1156-64), book2, 64); // strange for so many books to be thrown like this, something might have happened
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1374-448,1728-64), clothes, 64); // old persons clothes, maybe I shouldn't look 
		new Notice(cam, new VectorXY(1088-448,1792-64), "Math books", 64); // he must like maths
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1088-448,2048-64), alarm1, 64); // setted to ring a 7am
	 	new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1280-448,2048-64), alarm2, 64); // setted to ring a 3pm ...
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1856-448,1728-64), diary, 64); // diary, tells where he is, check other house

		entered = true;
		new ToMainWorldEvent(cam, new VectorXY(1110,2184), "E to go out", 64);

		player = new Player(new Sprite("entity/player.png", 32, 32), new VectorXY(1118,2220), 64, cam);
		player.setAnimation(3, player.getSprite().getSpriteArray(3), 5);
	}
		
	private void house2Spawn() 		//spawn in player in house 2
	{
		clothes[0] = "It seems desert";
		diary[0] = "'Dear diary, today was a horrible day.      Monsters appeared in the village and";
		diary[1] = "DEATH !";
		diary[2] = "DEATH DEATH !!";
		diary[3] = "DEATH DEATH DEATH !!!";
	 	diary[4] = "NEVER TRY  TO OPEN THAT DOOR !!!!";
		mapName = house2; 
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1374-896,1728), clothes, 64); // old persons clothes, maybe I shouldn't look 
		new Notice(cam, new VectorXY(1088-896,1792), "'Demon Slayer'", 64); // he must like maths
		new Npc(cam, new Sprite("entity/nothing.png",16 ,16), new VectorXY(1856-896,1728), diary, 64); // diary, tells where he is, check other house
		new ToDungeonEvent(cam, new VectorXY(1994-896,1664), "Risk it?!", 64); // Door

		entered = true;
		new Notice(cam, new VectorXY(1560-890,2000+(GamePanel.height/2)-32), "NO RETURN", 64);

		player = new Player(new Sprite("entity/player.png", 32, 32), new VectorXY(1150-896 + (GamePanel.width/2)-32 , 2000+(GamePanel.height/2)-32), 64, cam);
		player.setAnimation(3, player.getSprite().getSpriteArray(3), 5);
	}
	
	public void restart()	
	{
		//remove all entities registered
		while(!Entity.entity.isEmpty())
		{
			Entity.entity.remove(0);
		}

		while(!Player.players.isEmpty())
		{
			Player.players.remove(0);
		}
		
		//set spawn
		if(mapName.compareTo(mainWorld) == 0)
			mainSpawn();
		else if(mapName.compareTo(house1) == 0)
			house1Spawn();
		else if(mapName.compareTo(house2) == 0)
			house2Spawn();
		else
			dungeonSpawn();	 
		
		//tm2 = new TileManager(d3Map, cam); 	//future update
		tm = new TileManager(mapName, cam);
		cam.target(player);
	}
	
	
	public PlayState()
	{	//initialization
		cam = new Camera(new AABB(new VectorXY(0,0),GamePanel.width+64,GamePanel.height+64));
		font = new Font("font/font.png", 10, 10);
		Sprite.currentFont = font;
		
		map = new VectorXY();
		VectorXY.setWorldVar(map.x, map.y);
		
		restart();
	}
  
	public void update() {		
		if(MouseHandler.gameOver) {
			restart();
			MouseHandler.gameOver = false;
		}
		
		else if(changeStates) {
			restart();
			changeStates = false;
		}
		
		VectorXY.setWorldVar(map.x, map.y);
		
		switch(state)	//update specific state
		{
			case GAME:
				for(int i = 0; i<Entity.entity.size(); i++)
				{
					Entity.entity.get(i).update();
				}
		  		cam.update();
				
				break;
			case PAUSE:
				break;
			case INVENTORY:
				break;
		}
	}

	public void render(Graphics2D g) {
		cam.render(g);
		switch(state)	//render specific state
		{
			case GAME:
				//tm2.render(g);	//future update
			 	tm.render(g);
		 		for(int i = 0; i<Entity.entity.size(); i++)
				{
					Entity.entity.get(i).render(g);
				}
				Sprite.drawArray(g, GamePanel.oldFrameCount + " FPS", new VectorXY(GamePanel.width - 140,10), 20, 16);
			 	Sprite.drawArray(g, player.gold + " Gold", new VectorXY(GamePanel.width - 150,40), 20, 16);
	 			break; 
			case PAUSE:
				for(int i = 0; i<Entity.entity.size(); i++)
				{
					Entity.entity.get(i).render(g);
				}
				pause.render(g);
				break;
			case INVENTORY: 
				break;
		}	
	} 

	public void input(MouseHandler mouse, KeyHandler key) {
		switch(state)	//get input of specific state
		{
			case GAME:  
				player.input(mouse, key); 
				cam.input(mouse, key);
				mouse.update();
				if(key.escape.down)
					state = STATES.PAUSE;
				break;
			case PAUSE:
				if(key.interact.down)
					state = STATES.GAME; 
				break;
			case INVENTORY:
				break;
		}
	}
}
