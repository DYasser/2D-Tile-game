package util;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import Main.GameLauncher;
import states.OptionState;
import states.PlayState;

public class MouseHandler implements MouseListener, MouseMotionListener{

	public static boolean gameOver = false;
	
	private static int mouseX = -1, mouseY = -1, mouseB = -1;
	private static int x;
	private static int y;
	
	private boolean clicked1 = false, released1 = false, clicked2 = false, released2 = false;
	 
	public MouseHandler(GamePanel game)
	{
		game.addMouseListener(this);
	} 
	
	public int getX()
	{
		return mouseX;
	}
	
	public int getY()
	{
		return mouseY;
	}
	
	public int getButton()
	{
		return mouseB;
	}
	
	public void update()
	{ 
		x = MouseInfo.getPointerInfo().getLocation().x -7 - GameLauncher.win.getX();
		y = MouseInfo.getPointerInfo().getLocation().y -31 - GameLauncher.win.getY();
	
		if(GamePanel.state == GamePanel.States.OPTION) {
			if(clicked1)
			{
				OptionState.xSound = x -20;
				if(OptionState.xSound<=284)
					OptionState.xSound = 284;

				if(OptionState.xSound>=600)
					OptionState.xSound = 600;
			}
			else if(clicked2)
			{
				OptionState.xBrightness = x -20;
				if(OptionState.xBrightness<=284)
					OptionState.xBrightness = 284;

				if(OptionState.xBrightness>=600)
					OptionState.xBrightness = 600;
			}
			
			if(isIn(GamePanel.width/2-60,500, 150, 40)) {
				GamePanel.selected = 0; 
				GameLauncher.btn.loop(0);
			}
			else if(isIn(10,10,120,40)) {
				GamePanel.selected = 1;
				GameLauncher.btn.loop(0);
			}
			else {
				GamePanel.selected = -1;
				GameLauncher.btn.setMicrosecondPosition(0);
				}
		}
		
		else if(GamePanel.state == GamePanel.States.MAINMENU)
		{
			if(isIn(GamePanel.width/2-40,300, 100,40)) {
				GamePanel.selected = 0;
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-40,350, 100,40)) {
				GamePanel.selected = 1;
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-40,400, 100,40)) {
				GamePanel.selected = 2;
				GameLauncher.btn.loop(0);
			}
			else
			{
				GamePanel.selected = -1;
				GameLauncher.btn.setMicrosecondPosition(0);
			}
		}
		
		else if(GamePanel.state == GamePanel.States.DIFFICULTY)
		{
			if(isIn(30,10, 100,40)) {
				GamePanel.selected = 0;
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-40,200, 140,50)) {
				GamePanel.selected = 1;
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-40,300, 190,50)) {
				GamePanel.selected = 2;
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-40,400, 140,50)) {
				GamePanel.selected = 3;
				GameLauncher.btn.loop(0);
			}
			else
			{
				GamePanel.selected = -1;
				GameLauncher.btn.setMicrosecondPosition(0);
			}
		}
		
		else if(GamePanel.state == GamePanel.States.GAMEOVER) {
			if(isIn(GamePanel.width/2-158,422, 100, 43)){
				GameLauncher.btn.loop(0);
			} 
			else if(isIn(GamePanel.width/2+82,420, 70, 43))
			{
				GameLauncher.btn.loop(0);
			}
			else if(isIn(GamePanel.width/2-62,510, 140, 43))
			{
				GameLauncher.btn.loop(0);
			}
			else
			{
				GameLauncher.btn.setMicrosecondPosition(0);
			}
		} 
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) { 
	}

	@Override
	public void mouseClicked(MouseEvent e) { 
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();

		if(GamePanel.state == GamePanel.States.OPTION) {
			if(isIn(OptionState.xSound, 267, 48,48))
			{
				clicked1 = true;
				released1 = false;
			}
			else if(isIn(OptionState.xBrightness, 416, 48,48))
			{
				clicked2 = true;
				released2 = false;
			}
		}
		else if(GamePanel.state == GamePanel.States.DIFFICULTY)
		{
			if(isIn(30,10, 100,40)) {
				GamePanel.state = GamePanel.States.MAINMENU;
			}
			else if(isIn(GamePanel.width/2-40,200, 140,50)) {
				GamePanel.difficulty = 1;
				GamePanel.state = GamePanel.States.PLAYSTATE;
			}
			else if(isIn(GamePanel.width/2-40,300, 190,50)) {
				GamePanel.difficulty = 2;
				GamePanel.state = GamePanel.States.PLAYSTATE;
			}
			else if(isIn(GamePanel.width/2-40,400, 140,50)) {
				GamePanel.difficulty = 3;
				GamePanel.state = GamePanel.States.PLAYSTATE;
			}
		}
		//System.out.println(x + " - " + y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;

		if(GamePanel.state == GamePanel.States.OPTION) {
			clicked1 = false;
			released1 = true;
			clicked2 = false;
			released2 = true;
			
			if(isIn(40,16,84,16)){ 
				GamePanel.state = GamePanel.States.MAINMENU;
				}
			else if(isIn(396,504,153,19)){ 
				OptionState.xBrightness = OptionState.xSound = 450;
				}
		}
		
		if(GamePanel.state == GamePanel.States.GAMEOVER) {
			if(isIn(GamePanel.width/2-158,422, 100, 43)){
				GamePanel.state = GamePanel.States.PLAYSTATE;
				gameOver = true;
			} 
			else if(isIn(GamePanel.width/2+82,420, 70, 43))
			{
				GamePanel.state = GamePanel.States.MAINMENU;
				PlayState.playerSpawn = new VectorXY(0 + (GamePanel.width/2)-32, 0+(GamePanel.height/2)-32);
				PlayState.mapName = PlayState.mainWorld;
				PlayState.changeStates = true;
			}
			else if(isIn(GamePanel.width/2-62,510, 140, 43))
			{
				System.exit(0);
			}
		} 
		if(GamePanel.state == GamePanel.States.MAINMENU)
		{
			if(isIn(GamePanel.width/2-40,300,90,30)){ 
				GamePanel.state = GamePanel.States.DIFFICULTY;
				gameOver = true;
				}
			else if(isIn(GamePanel.width/2-60,350,130,30)){ 
				GamePanel.state = GamePanel.States.OPTION;
				}
			else if(isIn(GamePanel.width/2-42,400,100,30)){  
				System.exit(0); 
				}
		} 
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	public static boolean isIn(int x2, int y2, int w, int h)
	{
		if(x >= x2 && y >= y2)
			if(x <= x2 + w && y <= y2 + h) 
			{
				return true;
			}
		return false;
	}
	
}
