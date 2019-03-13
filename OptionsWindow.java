package snake;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsWindow extends JFrame{

	public OptionsWindow() {
		this.setBounds(100, 100, 900, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel Lbltitle = new JLabel("Please Choose Your Options");
		Lbltitle.setBounds(0, 0, 884, 29);
		Lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		Lbltitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		this.getContentPane().add(Lbltitle);
		
		JLabel lblFullScreen = new JLabel("Would You Like Full Screen?");
		lblFullScreen.setHorizontalAlignment(SwingConstants.CENTER);
		lblFullScreen.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFullScreen.setBounds(0, 181, 243, 29);
		this.getContentPane().add(lblFullScreen);
		
		JCheckBox cbxFullScreen = new JCheckBox();
		cbxFullScreen.setBounds(243, 181, 30, 30);
		this.getContentPane().add(cbxFullScreen);
		
		JLabel lblGameSpeed = new JLabel("Please Choose Game Speed");
		lblGameSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameSpeed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGameSpeed.setBounds(0, 220, 243, 29);
		this.getContentPane().add(lblGameSpeed);
		
		JComboBox cbbGameSpeed = new JComboBox();
		cbbGameSpeed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbbGameSpeed.addItem(0.5);
		cbbGameSpeed.addItem(1);
		cbbGameSpeed.addItem(2);
		cbbGameSpeed.addItem(4);
		cbbGameSpeed.setBounds(243, 220, 60, 30);
		this.getContentPane().add(cbbGameSpeed);
		
		JButton btnStart = new JButton();
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String speedString = String.valueOf(cbbGameSpeed.getSelectedItem());
				Double speed = Double.valueOf(speedString);
				if(cbxFullScreen.isSelected()) {
					SnakeGameScreen snakeGame = new SnakeGameScreen(true, speed);
				}else {
					SnakeGameScreen snakeGame = new SnakeGameScreen(false, speed);
				}
			}
		});
		btnStart.setText("Start Game");
		btnStart.setBounds(400, 400, 100, 30);
		this.getContentPane().add(btnStart);
		this.setVisible(true);
	}
}
