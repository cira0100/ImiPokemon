package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.CONSTS;
import models.ComboBoxUser;
import models.User;
import models.UserListWrapper;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
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
	public boolean run=true;
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
		      try {
		    	  run=false;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		 try{
			 while(run) {
				StringBuilder sb=new StringBuilder();
				client.configureBlocking(true);
				readBuffer.clear();
				
				while(run && client.read(readBuffer)>0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.limit()];
					readBuffer.get(bytes);
					sb.append(new String(bytes));
					readBuffer.clear();
					client.configureBlocking(false);
				}
				if(!run)
				{
					client.close();
					System.exit(EXIT_ON_CLOSE);
				}
				String response[]=sb.toString().split(":");
				if(response[0].trim().equals("ACCEPTED")) {
					this.setUserId(Long.parseLong(response[1].trim()));
					this.getContentPane().removeAll();
					this.getContentPane().add(chooseOpponentPanel,BorderLayout.CENTER);
					SwingUtilities.updateComponentTreeUI(this);
					
					
				}else if(response[0].trim().equals("BADLOGIN")){
					System.out.println("Bad Login");
					this.loginPanel.warningTextArea.setText("Pogresan Login");
				}else if(response[0].trim().equals("BADLOGINADMIN")){
					System.out.println("Bad Login");
					this.loginPanel.warningTextArea.setText("Admin nema pristup aplikaciji");
				}else {
					//XML OBJECTS
					XMLDecoder decoder = null;
					decoder = new XMLDecoder(new ByteArrayInputStream(sb.toString().getBytes()));
					try {
					UserListWrapper wp=(UserListWrapper) decoder.readObject();
					decoder.close();
					chooseOpponentPanel.comboBox.removeAllItems();
					if(wp.getUsers()!=null)
					for(User user :wp.getUsers()) {
						if(userId!=user.id)
							chooseOpponentPanel.comboBox.addItem(new ComboBoxUser(user));
						
					}
					
				} catch (Exception e) {
					System.out.println("Nije refresh");
				}
					
				}
				
			

		}
		 } catch (Exception e) {
				e.printStackTrace();
			}
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(EXIT_ON_CLOSE);
		
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
