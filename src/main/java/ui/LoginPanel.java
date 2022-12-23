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
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
	public JTextField textFieldUsername;
	public JTextField textFieldPassword;

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
		
		JTextArea warningTextArea = new JTextArea();
		warningTextArea.setToolTipText("");
		warningTextArea.setText("fsdfsdfd fdsfsdfsd");
		warningTextArea.setForeground(Color.RED);
		warningTextArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		warningTextArea.setEditable(false);
		warningTextArea.setBackground(SystemColor.menu);
		warningTextArea.setBounds(132, 134, 158, 36);
		add(warningTextArea);
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, LoginPanel.this);
				topFrame.getContentPane().removeAll();
				topFrame.add(new GamePanel(),BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(topFrame);
			}
		});
		btnLogIn.setBounds(174, 181, 81, 23);
		add(btnLogIn);

	}

}
