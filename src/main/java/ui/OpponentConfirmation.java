package ui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.ComboBoxUser;
import models.User;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class OpponentConfirmation extends JPanel {
	public JLabel lblNewLabel;
	public JButton btnAccept;
	public JButton btnRefuse;
	public long opponentId;
	private JLabel lblToolTip;
	
	public OpponentConfirmation() {
		setLayout(null);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(95, 66, 208, 98);
		add(lblNewLabel);
		
		btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="ACCEPTOPPONENT:"+opponentId;
				ByteBuffer bb=ByteBuffer.wrap(msg.getBytes());
				MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, OpponentConfirmation.this);
				try {
					SocketChannel client=topFrame.getClient();
					client.write(bb);
					SwingUtilities.updateComponentTreeUI(topFrame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnAccept.setBounds(92, 194, 89, 23);
		add(btnAccept);
		
		btnRefuse = new JButton("Refuse");
		btnRefuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="REFUSEOPPONENT:"+opponentId;
				ByteBuffer bb=ByteBuffer.wrap(msg.getBytes());
				MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, OpponentConfirmation.this);
				try {
					SocketChannel client=topFrame.getClient();
					client.write(bb);
					SwingUtilities.updateComponentTreeUI(topFrame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRefuse.setBounds(214, 194, 89, 23);
		add(btnRefuse);
		
		lblToolTip = new JLabel("(?)");
		lblToolTip.setBounds(249, 41, 24, 14);
		lblToolTip.setToolTipText("<html>Ukoliko korisnik salje zahtev drugom korisniku za igru on ceka na odgovor korisnika<br> Ukoliko korisnik prima zahtev za igru od drugog korisnika <br> on odgovara na taj zahtev prihvacanjem igre ili odbijanjem </html>");
		add(lblToolTip);
		add(lblToolTip);

	}
	public long getOpponentId() {
		return opponentId;
	}
	public void setOpponentId(long opponentId) {
		this.opponentId = opponentId;
	}
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}
	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}
	public JButton getBtnAccept() {
		return btnAccept;
	}
	public void setBtnAccept(JButton btnAccept) {
		this.btnAccept = btnAccept;
	}
	public JButton getBtnRefuse() {
		return btnRefuse;
	}
	public void setBtnRefuse(JButton btnRefuse) {
		this.btnRefuse = btnRefuse;
	}
}
