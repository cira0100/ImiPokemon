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
		
		JLabel lblToolTip = new JLabel("(?)");
		lblToolTip.setBounds(330, 105, 27, 14);
		lblToolTip.setToolTipText("<html>Izbor protivnika. Lista se refreshuje svaki put kada se korisnik prijavi na sistem,<br> zapocne nova igra, zavrsi igra, igrac izadje iz igrice.</html>");
		add(lblToolTip);
		
		btnSendGameRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount()==0)
					return;
				User opponent=((ComboBoxUser)comboBox.getSelectedItem()).getUser();
				String msg="SELECTOPPONENT:"+opponent.id;
				ByteBuffer bb=ByteBuffer.wrap(msg.getBytes());
				MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, ChooseOpponentPanel.this);
				try {
					SocketChannel client=topFrame.getClient();
					client.write(bb);
					topFrame.getContentPane().removeAll();
					topFrame.opponentConfirmation.getBtnAccept().setVisible(false);
					topFrame.opponentConfirmation.getBtnRefuse().setVisible(false);
					topFrame.opponentConfirmation.setOpponentId(opponent.id);
					topFrame.opponentConfirmation.lblNewLabel.setText("Wait for opponent");
					topFrame.getContentPane().add(topFrame.opponentConfirmation,BorderLayout.CENTER);
					SwingUtilities.updateComponentTreeUI(topFrame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});

	}

}
