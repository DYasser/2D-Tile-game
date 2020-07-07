package graphics;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;	//regrouping all frames
	private int currentFrame;	//current frame
	private int numFrames; 	//total frame
	
	private int count; 	//count of frames
	private int delay;	//between each frame
	 
	private int timesPlayed;	//how many times animation played
	
	public Animation() 
	{ 
		timesPlayed = 0; 
	}
	 
	public Animation(BufferedImage[] frames) 
	{
		timesPlayed = 0; 
		setFrames(frames);
	}	

	//setters
	public void setFrames(BufferedImage[] frames)	//initialization
	{
		this.frames = frames;		
		currentFrame = 0;
		count = 0;
		timesPlayed = 0; 
		delay = 2;
		numFrames = frames.length; 
	}
	public void setDelay(int i) {delay = i;}
	public void setFrame(int i) {currentFrame = i;}
	public void setNumFrames(int i) {numFrames= i;}
	
	//getters
	public int getDelay() {return delay;} 
	public int getFrame() {return currentFrame;}
	public int getNumFrames() {return numFrames;}
	public BufferedImage getImage() {return frames[currentFrame];}
	public boolean hasPlayedOnce() {return timesPlayed > 0;}
	public boolean hasPlayed(int i) {return timesPlayed == i;}
	 

	public void update()
	{
		if(delay == -1)
			return;
		
		count++; //speed of execution
		
		if(count == delay)
		{
			currentFrame++;	//frame count
			count = 0;
		}
		if(currentFrame == numFrames)	//end of frame
		{
			currentFrame = 0;		//reset to loop
			timesPlayed++;
		}
	}
}

