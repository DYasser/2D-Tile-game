package util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import states.Difficulty;
import states.GameOverState;
import states.MainMenu;
import states.OptionState;
import states.PlayState;

public class GamePanel extends JPanel implements Runnable{

	public static int width, height;
	
	public static int selected = -1;	//option selected on screen
	public static int difficulty = 1;	//game difficulty

	public static enum States{	//out of game states
		PLAYSTATE,
	 	GAMEOVER,
		MAINMENU,
		DIFFICULTY,
		OPTION
	}
	
	public static States state = States.MAINMENU;
	
	//all menus
	private MainMenu mainM;
	public static PlayState play;
	private GameOverState GO; 
	private OptionState option;
	private Difficulty diff;
	
	//handlers
	private MouseHandler mouse;
	private KeyHandler key;
	
	//game thread properties
	private boolean running = false;
	private Thread thread;
	private BufferedImage img;
	private Graphics2D g;
	public static int oldFrameCount;
	 
	public GamePanel(int w, int h)
	{
		width = w;
		height = h;
		setPreferredSize(new Dimension(w, h));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify()
	{
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this,"GameThread");
			thread.start();
		}
	}

	public void init()	//initializition
	{
		running = true;

		mainM = new MainMenu(this);
		play = new PlayState();
		GO = new GameOverState();
		option = new OptionState(this);
		diff = new Difficulty();
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		
		mouse = new MouseHandler(this);
		key = new KeyHandler(this);

	}
	
	@Override
	public void run() {	//game loop
		init();
		
		final double GAME_HERTZ = 60.0;
		final double TBU = 1000000000 / GAME_HERTZ;
		
		final int MUBR = 5;
		
		double lastUpdate = System.nanoTime();
		double lastRender; 
		
		final double TARGET_FPS = 60.0; 
		final double TTBR = 1000000000 / TARGET_FPS;
		
		int frameCount = 0;
		int lastSecond = (int) (lastUpdate/1000000000);
		oldFrameCount = 0;
		
		while(running)
		{
			double now =System.nanoTime();
			int updateCount = 0;
			while((now-lastUpdate > TBU) && (updateCount < MUBR))
			{
				update();
				input(mouse, key);
				lastUpdate += TBU;
				updateCount++;
			}
			
			if(now-lastUpdate > TBU)
			{
				lastUpdate = now - TBU;
			}
			
			input(mouse, key);
			render();
			draw();
			lastRender = now;
			frameCount++;

			int thisSecond = (int) (lastUpdate/1000000000);
			if(thisSecond > lastSecond)
			{
				if(frameCount != oldFrameCount)
				{
					//System.out.println("FRAME: " + thisSecond + " - " + frameCount);
					oldFrameCount = frameCount;
				}
				frameCount = 0;
				lastSecond = thisSecond;
			}
			
			while(now - lastRender < TTBR && now - lastUpdate < TBU)
			{
				Thread.yield();
				now = System.nanoTime();
			}
			
		}
	}
	
	public void input(MouseHandler mouse, KeyHandler key)
	{
		switch(state)
		{
		case MAINMENU:
			mainM.input(mouse, key);
			mouse.update();
			break;
		case PLAYSTATE:
			play.input(mouse, key);
			break;
		case GAMEOVER:
			GO.input(mouse, key);
			mouse.update();
			break;
		case OPTION:
			option.input(mouse, key);
			mouse.update();
			break;
		case DIFFICULTY:
			diff.input(mouse, key);
			mouse.update();
			break;
		}
	}
	
	public void update()
	{
		switch(state)
		{
		case MAINMENU:
			mainM.update();
			break;
		case PLAYSTATE:
			play.update();
			break;
		case GAMEOVER:
			GO.update();
			break;
		case OPTION:
			option.update();
			break;
		case DIFFICULTY:
			diff.update();
			break;
		}
	}
	
	public void render()
	{
		if(g!=null)
		{
			g.setColor(Color.black); 
			g.fillRect(0, 0, width, height);
			switch(state)
			{
			case MAINMENU:
				mainM.render(g);
				break;
			case PLAYSTATE:
				play.render(g);
				break;
			case GAMEOVER:
				GO.render(g);
				break;
			case OPTION:
				option.render(g);
				break;
			case DIFFICULTY:
				diff.render(g);
				break;
			}
		}
	}
	
	public void draw()
	{
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
	}
} 


