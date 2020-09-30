package view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.text.ParseException;
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
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastroFuncionario telaCDA = null;
				try {
					telaCDA = new CadastroFuncionario();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				telaCDA.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(36, 50, 161, 23);
		add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Sair do Sistema");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.exit(0); //sair do sistema
			}
		});
		btnNewButton.setBounds(36, 185, 161, 23);
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Menu Principal");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(69, 11, 93, 14);
		add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Cadastrar CDAs");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadContribuinte telaPrototype = new TelaCadContribuinte();
				telaPrototype.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(36, 151, 161, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("Consulta de CDAs");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaConsultaCDA telaCDA = new TelaConsultaCDA();
				telaCDA.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(36, 117, 161, 23);
		add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Cadastro de Contribuinte");
		btnNewButton_5.setBounds(36, 83, 161, 23);
		add(btnNewButton_5);

	}
}
