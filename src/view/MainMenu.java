package view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class MainMenu extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainMenu() {
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Cadastro de Funcionarios");
		btnNewButton_2.setBounds(33, 70, 161, 23);
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Consulta de CDAs");
		btnNewButton_3.setBounds(33, 104, 161, 23);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("Sair do Sistema");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.exit(0); //sair do sistema
			}
		});
		btnNewButton.setBounds(33, 138, 161, 23);
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Menu Principal");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(69, 11, 93, 14);
		add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Tela Prototipo Cad.");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadContribuinte telaPrototype = new TelaCadContribuinte();
				telaPrototype.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(33, 36, 161, 23);
		add(btnNewButton_1);

	}
}
