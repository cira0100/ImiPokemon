package ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ChooseOpponentPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ChooseOpponentPanel() {
		setLayout(null);
		
		JButton btnSendGameRequest = new JButton("Send Game Request");
		btnSendGameRequest.setBounds(134, 153, 166, 23);
		add(btnSendGameRequest);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(134, 101, 166, 22);
		add(comboBox);

	}

}
