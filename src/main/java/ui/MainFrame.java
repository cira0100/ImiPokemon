package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.CONSTS;
import models.ComboBoxUser;
import models.GameStatus;
import models.User;
import models.UserListWrapper;
import pokemon.Game;

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
	public OpponentConfirmation opponentConfirmation=null;
	GamePanel gamePanel=null;
	public long userId=-1;
	public boolean run=true;
	public GameStatus status;
	public Game game;
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 727, 429);
		loginPanel=new LoginPanel();
		chooseOpponentPanel=new ChooseOpponentPanel();
		opponentConfirmation=new OpponentConfirmation();
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
				}else if(response[0].trim().equals("GAMEREQUEST")){
					System.out.println("Game Request");
					this.getContentPane().removeAll();
					this.opponentConfirmation.getBtnAccept().setVisible(true);
					this.opponentConfirmation.getBtnRefuse().setVisible(true);
					this.opponentConfirmation.lblNewLabel.setText("Game Request"+"id:"+response[1].trim());
					this.opponentConfirmation.setOpponentId(Long.parseLong(response[1].trim()));
					this.getContentPane().add(this.opponentConfirmation,BorderLayout.CENTER);
					SwingUtilities.updateComponentTreeUI(this);
				}else if(response[0].trim().equals("REFUSEGAME")){
					System.out.println("REFUSEGAME");
					this.getContentPane().removeAll();
					this.getContentPane().add(this.chooseOpponentPanel,BorderLayout.CENTER);
					SwingUtilities.updateComponentTreeUI(this);
				}else {
					//XML OBJECTS
					XMLDecoder decoder = null;
					decoder = new XMLDecoder(new ByteArrayInputStream(sb.toString().getBytes()));
					try {
						Object o=decoder.readObject();
						decoder.close();
						if(o instanceof UserListWrapper) {
							UserListWrapper wp=(UserListWrapper) o;
							chooseOpponentPanel.comboBox.removeAllItems();
							if(wp.getUsers()!=null)
							for(User user :wp.getUsers()) {
								if(userId!=user.id)
									chooseOpponentPanel.comboBox.addItem(new ComboBoxUser(user));
								
							}
						}else if(o instanceof Game) {
							Game game=(Game) o;
							decoder.close();
							this.getContentPane().removeAll();
							this.getContentPane().add(this.gamePanel,BorderLayout.CENTER);
							SwingUtilities.updateComponentTreeUI(this);
							
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
	public OpponentConfirmation getOpponentConfirmation() {
		return opponentConfirmation;
	}
	public void setOpponentConfirmation(OpponentConfirmation opponentConfirmation) {
		this.opponentConfirmation = opponentConfirmation;
	}
	public boolean isRun() {
		return run;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

}
