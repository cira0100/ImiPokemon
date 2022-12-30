package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
	public JTextField textFieldUsername;
	public JTextField textFieldPassword;
	public JButton btnLogIn;
	JTextArea warningTextArea;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(132, 78, 59, 14);
		add(lblNewLabel);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(205, 75, 100, 20);
		add(textFieldUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(132, 109, 59, 14);
		add(lblPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(205, 106, 100, 20);
		add(textFieldPassword);
		
		warningTextArea = new JTextArea();
		warningTextArea.setWrapStyleWord(true);
		warningTextArea.setLineWrap(true);
		warningTextArea.setToolTipText("");
		warningTextArea.setForeground(Color.RED);
		warningTextArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		warningTextArea.setEditable(false);
		warningTextArea.setBackground(SystemColor.menu);
		warningTextArea.setBounds(132, 134, 158, 36);
		add(warningTextArea);
		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, LoginPanel.this);
//				topFrame.getContentPane().removeAll();
//				topFrame.add(new GamePanel(),BorderLayout.CENTER);
//				SwingUtilities.updateComponentTreeUI(topFrame);
				Login();
			}
		});
		btnLogIn.setBounds(174, 181, 81, 23);
		add(btnLogIn);

	}
	
	public void Login() {
		String username=textFieldUsername.getText();
		String password=textFieldPassword.getText();
		if(!(username.trim().length()>0 && password.trim().length()>0))
		{
			warningTextArea.setText("Morate uneti login informacije");
			return;
		}
		String loginMsg="LOGIN:"+username+":"+password;
		ByteBuffer bbLogin=ByteBuffer.wrap(loginMsg.getBytes());
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, LoginPanel.this);
		try {
			SocketChannel client=topFrame.getClient();
			ByteBuffer readBuffer=topFrame.getReadBuffer();
			client.write(bbLogin);
			client.configureBlocking(true);
			readBuffer.clear();
			StringBuilder sb=new StringBuilder();
			while(client.read(readBuffer)>0) {
				readBuffer.flip();
				byte[] bytes = new byte[readBuffer.limit()];
				readBuffer.get(bytes);
				sb.append(new String(bytes));
				readBuffer.clear();
				client.configureBlocking(false);
			}
			String response[]=sb.toString().split(":");
			if(response[0].trim().equals("ACCEPTED")) {
				topFrame.setUserId(Long.parseLong(response[1].trim()));
				topFrame.getContentPane().removeAll();
				ChooseOpponentPanel panel=new ChooseOpponentPanel();
				topFrame.getContentPane().add(panel,BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(topFrame);
				panel.getUsers();
				
				
			}else {
				System.out.println("Bad Login");
				warningTextArea.setText("Pogresan Login");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
