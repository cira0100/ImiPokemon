package ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;


import models.Ability;
import models.AbilityType;
import models.GameStatus;
import models.MonsterViewModel;
import models.User;
import pokemon.RegexHelper;

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
	public MonsterViewModel yourMonster=null;
	public MonsterViewModel enemyMonster=null;
	public JButton btnBack;
	private JLabel lblToolTip;

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(null);
		
		btnAttack = new JButton("Attack");
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMove(AbilityType.ATTACK);
				
				
				
				
			}
		});
		btnAttack.setBounds(0, 239, 130, 30);
		add(btnAttack);
		
		btnSpecial = new JButton("Special");
		btnSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMove(AbilityType.SPECIAL);
				
				
			}
		});
		btnSpecial.setBounds(130, 239, 130, 30);
		add(btnSpecial);
		
		btnHeal = new JButton("Heal");
		btnHeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMove(AbilityType.HEAL);
				
				
				
			}
		});
		btnHeal.setBounds(0, 272, 130, 30);
		add(btnHeal);
		
		btnShield = new JButton("Shield");
		btnShield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMove(AbilityType.SHIELD);
				
				
				
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
		txtAreaChat.setText("");
		scrollPane.setViewportView(txtAreaChat);
		
		btnBack = new JButton("Return to choose opponent");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(false);
				returnBack();
				
			}
		});
		btnBack.setBounds(353, 276, 188, 23);
		btnBack.setVisible(false);
		add(btnBack);
		
		lblToolTip = new JLabel("(?)");
		lblToolTip.setBounds(180, 105, 23, 14);
		lblToolTip.setToolTipText("<html>Igra<br> Korisnici se bore po potezima. Koristeci izabrane napade <br>Prikazane su sve potrebne informacije za vodjenje igre.<br> Korisnici mogu koristiti chat za komunikaciju.<br> Po zavrsetku igre korisnici dobijaju obavestenje o rezultatu igre i<br> opciju da se vrate na stranicu za biranje protivnika<br>U chatu su validni svi karakeri osim :</html>");
		add(lblToolTip);
		add(lblToolTip);

	}
	public void sendChat() {
		String message=textFieldChat.getText();
		if(!RegexHelper.checkChatInput(message)) {
			return;
			
		}
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
		yourMonster=null;
		enemyMonster=null;
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
			lblPokemonNameMe.setText(yourMonster.name);
			lblPokemonNameEnemy.setText(enemyMonster.name);
			
			lblHpMe.setText(yourHp+"("+yourShield+")"+"/"+yourMonster.hp);
			lblHpEnemy.setText(enemyHp+"("+enemyShield+")"+"/"+enemyMonster.hp);
			
			progressBarHpMe.setValue((int)(((yourHp*1.0)/yourMonster.hp)*100));
			progressBarHpEnemy.setValue((int)(((enemyHp*1.0)/enemyMonster.hp)*100));
			if(topFrame.game.getStatus()==GameStatus.PLAYER1WIN) {
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				int optionres=JOptionPane.showConfirmDialog(null, "YOU WIN!!! Do you want to return to choose opponent page", "Result:"+you, JOptionPane.YES_NO_OPTION);
				if(optionres==JOptionPane.OK_OPTION) {
					returnBack();
					
				}
				else
				 btnBack.setVisible(true);
				
			}else if(topFrame.game.getStatus()==GameStatus.PLAYER2WIN) {
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				int optionres=JOptionPane.showConfirmDialog(null, "YOU LOSE!!! Do you want to return to choose opponent page", "Result"+you, JOptionPane.YES_NO_OPTION);
				if(optionres==JOptionPane.OK_OPTION) {
					returnBack();
					
				}
				else
					btnBack.setVisible(true);
				
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
			lblPokemonNameMe.setText(yourMonster.name);
			lblPokemonNameEnemy.setText(enemyMonster.name);
			
			lblHpMe.setText(yourHp+"("+yourShield+")"+"/"+yourMonster.hp);
			lblHpEnemy.setText(enemyHp+"("+enemyShield+")"+"/"+enemyMonster.hp);
			
			progressBarHpMe.setValue((int)(((yourHp*1.0)/yourMonster.hp)*100));
			progressBarHpEnemy.setValue((int)(((enemyHp*1.0)/enemyMonster.hp)*100));
			if(topFrame.game.getStatus()==GameStatus.PLAYER1WIN) {		
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				int optionres=JOptionPane.showConfirmDialog(null, "YOU LOSE!!! Do you want to return to choose opponent page", "Result"+you, JOptionPane.YES_NO_OPTION);
				if(optionres==JOptionPane.OK_OPTION) {
					returnBack();
					
				}
				else
					btnBack.setVisible(true);
				
			}else if(topFrame.game.getStatus()==GameStatus.PLAYER2WIN) {
				btnAttack.setEnabled(false);
				btnSpecial.setEnabled(false);
				btnHeal.setEnabled(false);
				btnShield.setEnabled(false);
				int optionres=JOptionPane.showConfirmDialog(null, "YOU WIN!!! Do you want to return to choose opponent page", "Result:"+you, JOptionPane.YES_NO_OPTION);
				if(optionres==JOptionPane.OK_OPTION) {
					returnBack();
					
				}
				else
					btnBack.setVisible(true);
				
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
		
		
		
		
	}
	public void sendMove(AbilityType type) {
		long abilityId=-1;
		for(Ability a:yourMonster.abilities) {
			if(a.type==type) {
				abilityId=a.id;
				break;
			}
		}
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, GamePanel.this);
		String sendMessage="GAMEPLAY:"+abilityId;
		ByteBuffer bb=ByteBuffer.wrap(sendMessage.getBytes());
		try {
			topFrame.client.write(bb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public void returnBack() {
		MainFrame topFrame=(MainFrame) SwingUtilities.getAncestorOfClass(MainFrame.class, GamePanel.this);
		topFrame.getContentPane().removeAll();
		topFrame.getContentPane().add(topFrame.chooseOpponentPanel,BorderLayout.CENTER);
		topFrame.gamePanel.txtAreaChat.setText(null);//clear chat between games
		topFrame.gamePanel.chat="";
		SwingUtilities.updateComponentTreeUI(topFrame);
		
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
