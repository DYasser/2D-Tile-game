package Main;
import javax.swing.JFrame;

import util.GamePanel;

public class Window extends JFrame{

	public Window()
	{
		setTitle("Dazzling Quest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(new GamePanel(900,600));
		pack();
		setLocationRelativeTo(null); 
		setResizable(false);
		setVisible(true);
	}
}
 