package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.BorderLayout;

public class MainFrame extends JFrame implements Runnable {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public LoginPanel loginPanel=null;
	public ChooseOpponentPanel chooseOpponentPanel=null;
	GamePanel gamePanel=null;
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 727, 429);
		loginPanel=new LoginPanel();
		chooseOpponentPanel=new ChooseOpponentPanel();
		gamePanel=new GamePanel();
		getContentPane().setLayout(new BorderLayout(0, 0));
		add(loginPanel,BorderLayout.CENTER);
		setVisible(true);
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
				System.out.println("testClient");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
