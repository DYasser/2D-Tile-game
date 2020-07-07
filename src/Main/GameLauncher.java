package Main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GameLauncher {

	public static Window win;
	public static Clip bgm;
	public static Clip btn;
	public static FloatControl gainControl;
	
	public static void main(String[] args)
	{
		try {
			//theme song
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/sound/arabia.wav").getAbsoluteFile());
			bgm = AudioSystem.getClip();
	        bgm.open(audioInputStream);
	        gainControl = 
	        	    (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
	        
	        bgm.loop(bgm.LOOP_CONTINUOUSLY);
	        
	        //menu select sound
	        audioInputStream = AudioSystem.getAudioInputStream(new File("res/sound/menuSlct.wav").getAbsoluteFile());
	        btn = AudioSystem.getClip();
	        btn.open(audioInputStream);
	        
	    } catch(Exception ex) {System.out.println("Error with playing sound.");}
		
		new GameLauncher();
	} 

	public GameLauncher()
	{
		win = new Window();
	}
	
}
