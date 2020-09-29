package view;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class Login extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public Login() {
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\bruno\\Desktop\\SGCT\\icone 32x32 bandeira.png"));
		lblNewLabel_4.setBounds(47, 11, 46, 35);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("SGCT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(103, 32, 29, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sistema de Gest\u00E3o e Cobran\u00E7a Tributaria");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(31, 57, 180, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(44, 93, 46, 14);
		add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(43, 110, 154, 20);
		add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(43, 168, 154, 20);
		add(passwordField);
		
		JLabel lblNewLabel_2_2 = new JLabel("Senha:");
		lblNewLabel_2_2.setBounds(42, 150, 46, 14);
		add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3 = new JLabel("Criar conta de acesso ao chat");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setBounds(10, 259, 226, 14);
		add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Logar");
		btnNewButton.setBounds(79, 205, 89, 23);
		add(btnNewButton);

	}
}
