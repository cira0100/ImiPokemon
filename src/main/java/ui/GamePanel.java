package ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class GamePanel extends JPanel {
	private JTextField textFieldChat;

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(null);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(0, 239, 89, 30);
		add(btnAttack);
		
		JButton btnSpecial = new JButton("Special");
		btnSpecial.setBounds(87, 239, 89, 30);
		add(btnSpecial);
		
		JButton btnHeal = new JButton("Heal");
		btnHeal.setBounds(0, 272, 89, 30);
		add(btnHeal);
		
		JButton btnShield = new JButton("Shield");
		btnShield.setBounds(87, 272, 89, 30);
		add(btnShield);
		
		JLabel lblImageEnemy = new JLabel("imageEnemy");
		lblImageEnemy.setBounds(333, 35, 50, 50);
		add(lblImageEnemy);
		
		JLabel lblImageMe = new JLabel("imageMe");
		lblImageMe.setBounds(21, 177, 50, 50);
		add(lblImageMe);
		
		JLabel lblPokemonNameEnemy = new JLabel("PokemonNameEnemy");
		lblPokemonNameEnemy.setBounds(25, 11, 139, 14);
		add(lblPokemonNameEnemy);
		
		JProgressBar progressBarHpEnemy = new JProgressBar();
		progressBarHpEnemy.setBounds(21, 33, 146, 14);
		add(progressBarHpEnemy);
		
		JLabel lblHpEnemy = new JLabel("HpEnemy");
		lblHpEnemy.setBounds(104, 53, 72, 14);
		add(lblHpEnemy);
		
		JLabel lblPokemonNameMe = new JLabel("PokemonNameMe");
		lblPokemonNameMe.setBounds(232, 171, 139, 14);
		add(lblPokemonNameMe);
		
		JProgressBar progressBarHpMe = new JProgressBar();
		progressBarHpMe.setBounds(228, 193, 146, 14);
		add(progressBarHpMe);
		
		JLabel lblHpMe = new JLabel("HpMe");
		lblHpMe.setBounds(311, 213, 72, 14);
		add(lblHpMe);
		
		textFieldChat = new JTextField();
		textFieldChat.setBounds(486, 240, 188, 20);
		add(textFieldChat);
		textFieldChat.setColumns(10);
		
		JButton btnSendText = new JButton("Send");
		btnSendText.setBounds(585, 266, 89, 23);
		add(btnSendText);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(486, 22, 188, 205);
		add(scrollPane);
		
		JTextArea txtrLoremIpsumDolor = new JTextArea();
		txtrLoremIpsumDolor.setEditable(false);
		txtrLoremIpsumDolor.setLineWrap(true);
		txtrLoremIpsumDolor.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Est ultricies integer quis auctor elit sed. Urna nunc id cursus metus aliquam eleifend mi. Amet consectetur adipiscing elit pellentesque habitant morbi tristique senectus. Consequat interdum varius sit amet mattis vulputate enim nulla aliquet. Tristique magna sit amet purus gravida quis blandit. Eget gravida cum sociis natoque penatibus. Nec tincidunt praesent semper feugiat nibh. Maecenas sed enim ut sem. Pulvinar neque laoreet suspendisse interdum consectetur. Diam maecenas ultricies mi eget mauris pharetra. Sollicitudin aliquam ultrices sagittis orci. Nulla facilisi etiam dignissim diam quis.");
		scrollPane.setViewportView(txtrLoremIpsumDolor);

	}
}
