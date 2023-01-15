package ui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pokemon.RegexHelper;

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
	public JPasswordField textFieldPassword;
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
		
		textFieldPassword = new JPasswordField();
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
		
		JLabel lblToolTip = new JLabel("(?)");
		lblToolTip.setBounds(338, 106, 25, 14);
		lblToolTip.setToolTipText("<html>Prijavljivanje korisnika. <br> Polja ne smeju biti prazna i unos mora biti alfanumericki. <br>Ukoliko korisnik nema izabranog pokemona nije mu dozvoljeno da se prijavi </html>");
		add(lblToolTip);

	}
	
	public void Login() {
		System.out.println("SEND LOGIN");
		String username=textFieldUsername.getText();
		String password=new String(textFieldPassword.getPassword());
		if(!(username.trim().length()>0 && password.trim().length()>0))
		{
			warningTextArea.setText("Morate uneti login informacije");
			return;
		}
		if(!RegexHelper.checkAlphaNumericInput(username)) {
			warningTextArea.setText("Username mora sadrzati alfanumericke karaktere");
			return;
		}
		if(!RegexHelper.checkAlphaNumericInput(password)) {
			warningTextArea.setText("Password mora sadrzati alfanumericke karaktere");
			return;
		}
		String loginMsg="LOGIN:"+username+":"+password;
		ByteBuffer bbLogin=ByteBuffer.wrap(loginMsg.getBytes());
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, LoginPanel.this);
		try {
			SocketChannel client=topFrame.getClient();
			client.write(bbLogin);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
