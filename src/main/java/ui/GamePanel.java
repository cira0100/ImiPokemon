package ui;

import javax.swing.JPanel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;


import models.Ability;
import models.AbilityType;
import models.MonsterViewModel;
import models.User;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
	public JTextField textFieldChat;
	public JButton btnAttack;
	public JButton btnSpecial;
	public JButton btnHeal;
	public JButton btnShield;
	public JLabel lblImageEnemy;
	public JLabel lblImageMe;
	public JLabel lblPokemonNameEnemy;
	public JProgressBar progressBarHpEnemy;
	public JLabel lblHpEnemy;
	public JLabel lblPokemonNameMe;
	public JProgressBar progressBarHpMe;
	public JLabel lblHpMe;
	public JButton btnSendText;
	public JScrollPane scrollPane;
	public JTextArea txtAreaChat;
	public long opponentId=-1;
	public String chat="";

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(null);
		
		btnAttack = new JButton("Attack");
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnAttack.setBounds(0, 239, 130, 30);
		add(btnAttack);
		
		btnSpecial = new JButton("Special");
		btnSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnSpecial.setBounds(130, 239, 130, 30);
		add(btnSpecial);
		
		btnHeal = new JButton("Heal");
		btnHeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btnHeal.setBounds(0, 272, 130, 30);
		add(btnHeal);
		
		btnShield = new JButton("Shield");
		btnShield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btnShield.setBounds(130, 272, 130, 30);
		add(btnShield);
		
		lblImageEnemy = new JLabel("imageEnemy");
		lblImageEnemy.setBounds(326, 29, 110, 110);
		add(lblImageEnemy);
		
		lblImageMe = new JLabel("imageMe");
		lblImageMe.setBounds(25, 118, 110, 110);
		add(lblImageMe);
		
		lblPokemonNameEnemy = new JLabel("PokemonNameEnemy");
		lblPokemonNameEnemy.setBounds(25, 11, 139, 14);
		add(lblPokemonNameEnemy);
		
		progressBarHpEnemy = new JProgressBar();
		progressBarHpEnemy.setBounds(21, 33, 146, 14);
		add(progressBarHpEnemy);
		
		lblHpEnemy = new JLabel("HpEnemy");
		lblHpEnemy.setBounds(104, 53, 200, 14);
		add(lblHpEnemy);
		
		lblPokemonNameMe = new JLabel("PokemonNameMe");
		lblPokemonNameMe.setBounds(232, 171, 139, 14);
		add(lblPokemonNameMe);
		
		progressBarHpMe = new JProgressBar();
		progressBarHpMe.setBounds(228, 193, 146, 14);
		add(progressBarHpMe);
		
		lblHpMe = new JLabel("HpMe");
		lblHpMe.setBounds(311, 213, 200, 14);
		add(lblHpMe);
		
		textFieldChat = new JTextField();
		textFieldChat.setBounds(486, 240, 188, 20);
		add(textFieldChat);
		textFieldChat.setColumns(10);
		
		btnSendText = new JButton("Send");
		btnSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendChat();
				
				
			}
		});
		btnSendText.setBounds(585, 266, 89, 23);
		add(btnSendText);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(486, 22, 188, 205);
		add(scrollPane);
		
		txtAreaChat = new JTextArea();
		txtAreaChat.setEditable(false);
		txtAreaChat.setLineWrap(true);
		txtAreaChat.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Est ultricies integer quis auctor elit sed. Urna nunc id cursus metus aliquam eleifend mi. Amet consectetur adipiscing elit pellentesque habitant morbi tristique senectus. Consequat interdum varius sit amet mattis vulputate enim nulla aliquet. Tristique magna sit amet purus gravida quis blandit. Eget gravida cum sociis natoque penatibus. Nec tincidunt praesent semper feugiat nibh. Maecenas sed enim ut sem. Pulvinar neque laoreet suspendisse interdum consectetur. Diam maecenas ultricies mi eget mauris pharetra. Sollicitudin aliquam ultrices sagittis orci. Nulla facilisi etiam dignissim diam quis.");
		scrollPane.setViewportView(txtAreaChat);

	}
	public void sendChat() {
		String message=textFieldChat.getText();
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, GamePanel.this);
		String sendMessage="CHATMESSAGE:"+opponentId+":"+message;
		ByteBuffer bb=ByteBuffer.wrap(sendMessage.getBytes());
		try {
			topFrame.client.write(bb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void refreshChat() {
		txtAreaChat.setText(chat);
	}
	public void loadElements() {
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, GamePanel.this);
		long you=-1;
		long enemy=-1;
		MonsterViewModel yourMonster=null;
		MonsterViewModel enemyMonster=null;
		int yourHp=0;
		int enemyHp=0;
		int yourShield=0;
		int enemyShield=0;
		boolean yourTurn=false;
		
		if(topFrame.userId==topFrame.game.player1Id) {
			you=topFrame.game.player1Id;
			enemy=topFrame.game.player2Id;
			opponentId=enemy;
			yourMonster=topFrame.game.monster1;
			enemyMonster=topFrame.game.monster2;
			yourHp=topFrame.game.currentHp1;
			enemyHp=topFrame.game.currentHp2;
			yourShield=topFrame.game.shield1;
			enemyShield=topFrame.game.shield2;
			if(topFrame.game.player1Turn) {
				yourTurn=true;
				btnAttack.setEnabled(true);
				btnSpecial.setEnabled(true);
				btnHeal.setEnabled(true);
				btnShield.setEnabled(true);
				
			}else {
				yourTurn=false;
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				
			}
		}else if(topFrame.userId==topFrame.game.player2Id) {
			you=topFrame.game.player2Id;
			enemy=topFrame.game.player1Id;
			opponentId=enemy;
			yourMonster=topFrame.game.monster2;
			enemyMonster=topFrame.game.monster1;
			yourHp=topFrame.game.currentHp2;
			enemyHp=topFrame.game.currentHp1;
			yourShield=topFrame.game.shield2;
			enemyShield=topFrame.game.shield1;
			if(topFrame.game.player1Turn) {
				yourTurn=false;
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				
			}else {
				yourTurn=true;
				btnAttack.setEnabled(true);
				btnSpecial.setEnabled(true);
				btnHeal.setEnabled(true);
				btnShield.setEnabled(true);
				
			}
		}
		
		for(Ability ability:yourMonster.abilities) {
			if(ability.type==AbilityType.ATTACK) {
				btnAttack.setText(ability.Name);
				
			}else if(ability.type==AbilityType.SPECIAL) {
				btnSpecial.setText(ability.Name);
				
			}else if(ability.type==AbilityType.HEAL) {
				btnHeal.setText(ability.Name);
				
			}else if(ability.type==AbilityType.SHIELD) {
				btnShield.setText(ability.Name);
				
			}
		}
		
		
		lblPokemonNameMe.setText(yourMonster.name);
		lblPokemonNameEnemy.setText(enemyMonster.name);
		
		lblHpMe.setText(yourHp+"("+yourShield+")"+"/"+yourMonster.hp);
		lblHpEnemy.setText(enemyHp+"("+enemyShield+")"+"/"+enemyMonster.hp);
		
		progressBarHpMe.setValue((yourHp/yourMonster.hp)*100);
		progressBarHpEnemy.setValue((enemyHp/enemyMonster.hp)*100);
		
		try {
			Image img=base64toImage(yourMonster.base64Image).getScaledInstance(lblImageMe.getWidth(), lblImageMe.getHeight(), Image.SCALE_SMOOTH);
			lblImageMe.setIcon(new ImageIcon(img));
		} catch (Exception e) {
		}
		try {
			Image img=base64toImage(enemyMonster.base64Image).getScaledInstance(lblImageMe.getWidth(), lblImageMe.getHeight(), Image.SCALE_SMOOTH);
			lblImageEnemy.setIcon(new ImageIcon(img));
		} catch (Exception e) {

		}
		//txtAreaChat.setText(null);
		
		
		
		
	}
	
	public BufferedImage base64toImage(String imgStr)throws Exception {
		BufferedImage image=null;
		byte[] imageByte;

		imageByte = Base64.getDecoder().decode(imgStr);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		return image;
		
	}

	public JTextArea getTxtAreaChat() {
		return txtAreaChat;
	}
	public void setTxtAreaChat(JTextArea txtAreaChat) {
		this.txtAreaChat = txtAreaChat;
	}
	public JTextField getTextFieldChat() {
		return textFieldChat;
	}

	public void setTextFieldChat(JTextField textFieldChat) {
		this.textFieldChat = textFieldChat;
	}

	public JButton getBtnAttack() {
		return btnAttack;
	}

	public void setBtnAttack(JButton btnAttack) {
		this.btnAttack = btnAttack;
	}

	public JButton getBtnSpecial() {
		return btnSpecial;
	}

	public void setBtnSpecial(JButton btnSpecial) {
		this.btnSpecial = btnSpecial;
	}

	public JButton getBtnHeal() {
		return btnHeal;
	}

	public void setBtnHeal(JButton btnHeal) {
		this.btnHeal = btnHeal;
	}

	public JButton getBtnShield() {
		return btnShield;
	}

	public void setBtnShield(JButton btnShield) {
		this.btnShield = btnShield;
	}

	public JLabel getLblImageEnemy() {
		return lblImageEnemy;
	}

	public void setLblImageEnemy(JLabel lblImageEnemy) {
		this.lblImageEnemy = lblImageEnemy;
	}

	public JLabel getLblImageMe() {
		return lblImageMe;
	}

	public void setLblImageMe(JLabel lblImageMe) {
		this.lblImageMe = lblImageMe;
	}

	public JLabel getLblPokemonNameEnemy() {
		return lblPokemonNameEnemy;
	}

	public void setLblPokemonNameEnemy(JLabel lblPokemonNameEnemy) {
		this.lblPokemonNameEnemy = lblPokemonNameEnemy;
	}

	public JProgressBar getProgressBarHpEnemy() {
		return progressBarHpEnemy;
	}

	public void setProgressBarHpEnemy(JProgressBar progressBarHpEnemy) {
		this.progressBarHpEnemy = progressBarHpEnemy;
	}

	public JLabel getLblHpEnemy() {
		return lblHpEnemy;
	}

	public void setLblHpEnemy(JLabel lblHpEnemy) {
		this.lblHpEnemy = lblHpEnemy;
	}

	public JLabel getLblPokemonNameMe() {
		return lblPokemonNameMe;
	}

	public void setLblPokemonNameMe(JLabel lblPokemonNameMe) {
		this.lblPokemonNameMe = lblPokemonNameMe;
	}

	public JProgressBar getProgressBarHpMe() {
		return progressBarHpMe;
	}

	public void setProgressBarHpMe(JProgressBar progressBarHpMe) {
		this.progressBarHpMe = progressBarHpMe;
	}

	public JLabel getLblHpMe() {
		return lblHpMe;
	}

	public void setLblHpMe(JLabel lblHpMe) {
		this.lblHpMe = lblHpMe;
	}

	public JButton getBtnSendText() {
		return btnSendText;
	}

	public void setBtnSendText(JButton btnSendText) {
		this.btnSendText = btnSendText;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}
