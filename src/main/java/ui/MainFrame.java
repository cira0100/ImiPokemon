package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.CONSTS;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.awt.BorderLayout;

public class MainFrame extends JFrame implements Runnable {

	
	
	
	public static void main(String[] args) {

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
	}
	public ByteBuffer readBuffer=ByteBuffer.allocate(1024);
	public SocketChannel client;
	public LoginPanel loginPanel=null;
	public ChooseOpponentPanel chooseOpponentPanel=null;
	GamePanel gamePanel=null;
	public long userId=-1;
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 727, 429);
		loginPanel=new LoginPanel();
		chooseOpponentPanel=new ChooseOpponentPanel();
		gamePanel=new GamePanel();
		getContentPane().setLayout(new BorderLayout(0, 0));
		add(loginPanel,BorderLayout.CENTER);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e)
		    {
		      System.out.println("EXITED");
		      
		      try {
		    	  client.close();
				Thread.sleep(500);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      System.exit(EXIT_ON_CLOSE);
		    }
			
		});
		
		try {
			client=SocketChannel.open(new InetSocketAddress(CONSTS.socketPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true) {
			
		}
		
	}
	public SocketChannel getClient() {
		return client;
	}
	public void setClient(SocketChannel client) {
		this.client = client;
	}
	public LoginPanel getLoginPanel() {
		return loginPanel;
	}
	public void setLoginPanel(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}
	public ChooseOpponentPanel getChooseOpponentPanel() {
		return chooseOpponentPanel;
	}
	public void setChooseOpponentPanel(ChooseOpponentPanel chooseOpponentPanel) {
		this.chooseOpponentPanel = chooseOpponentPanel;
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}
	public void setReadBuffer(ByteBuffer readBuffer) {
		this.readBuffer = readBuffer;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

}
