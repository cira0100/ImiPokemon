package pokemon;

import java.awt.BorderLayout;

import javax.swing.SwingUtilities;

import ui.MainFrame;
import ui.ChooseOpponentPanel;
import ui.GamePanel;
import ui.LoginPanel;

public class Client {

	public static void main(String[] args) {
		
		MainFrame frame=new MainFrame();
		LoginPanel p=new LoginPanel();
		frame.add(p,BorderLayout.CENTER);
		frame.setVisible(true);
		//SwingUtilities.updateComponentTreeUI(frame);
		
	}

}
