package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaMenuPrincipal extends JFrame {

	private JPanel contentPane;
	private static TelaMenuPrincipal instancia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaMenuPrincipal frame = new TelaMenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaMenuPrincipal getInstancia() {
        if (instancia == null) {
            instancia = new TelaMenuPrincipal();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaMenuPrincipal() {
		setBounds(100, 100, 200, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Cadastro de Funcionarios"); //somente admin
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastroFuncionario.getInstancia().setVisible(true);
			}
		});
		btnNewButton_2.setBounds(10, 68, 161, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Menu Principal");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(43, 29, 93, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_5 = new JButton("Cadastro de Contribuinte"); // somente admin
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadContribuinte.getInstancia().setVisible(true);
			}
		});
		btnNewButton_5.setBounds(10, 101, 161, 23);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_4 = new JButton("Consulta de CDAs");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaConsultaCDA.getInstancia().setVisible(true);
			}
			
		});
		btnNewButton_4.setBounds(10, 135, 161, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_1 = new JButton("Cadastrar CDAs"); // somente admin
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadContribuinte.getInstancia().setVisible(true);
			}
		});
		btnNewButton_1.setBounds(10, 169, 161, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Sair do Sistema");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); //sair do sistema
			}
		});
		btnNewButton.setBounds(10, 203, 161, 23);
		contentPane.add(btnNewButton);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
