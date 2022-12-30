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

}
