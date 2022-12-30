package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import models.ComboBoxUser;
import models.User;
import models.UserListWrapper;

import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ChooseOpponentPanel extends JPanel {

	JButton btnSendGameRequest;
	JComboBox comboBox;
	public ChooseOpponentPanel() {
		setLayout(null);
		
		btnSendGameRequest = new JButton("Send Game Request");
		btnSendGameRequest.setBounds(134, 153, 166, 23);
		add(btnSendGameRequest);
		
		comboBox = new JComboBox();
		comboBox.setBounds(134, 101, 166, 22);
		add(comboBox);

	}
	
	public void getUsers() {
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, ChooseOpponentPanel.this);
		try {
			SocketChannel client=topFrame.getClient();
			ByteBuffer readBuffer=topFrame.getReadBuffer();
			ByteBuffer buff = ByteBuffer.wrap("REQUESTUSERS".getBytes());
			client.write(buff);
			client.configureBlocking(true);
			readBuffer.clear();
			StringBuilder sb=new StringBuilder();
			while(client.read(readBuffer)>0) {
				System.out.println("uslo");
				readBuffer.flip();
				byte[] bytes = new byte[readBuffer.limit()];
				readBuffer.get(bytes);
				sb.append(new String(bytes));
				readBuffer.clear();
				client.configureBlocking(false);
			}
			XMLDecoder decoder = null;
			decoder = new XMLDecoder(new ByteArrayInputStream(sb.toString().getBytes()));
			UserListWrapper wp=(UserListWrapper) decoder.readObject();
			decoder.close();
			comboBox.removeAllItems();
			for(User user :wp.getUsers()) {
				comboBox.addItem(new ComboBoxUser(user));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
