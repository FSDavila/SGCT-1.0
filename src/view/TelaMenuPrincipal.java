package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 250, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton_2 = new JButton("Gest\u00E3o de Funcionarios"); //somente admin
		btnNewButton_2.setBounds(21, 164, 189, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaGestaoFuncionario.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Menu Principal");
		lblNewLabel.setBounds(71, 25, 93, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_5 = new JButton("Gest\u00E3o de Contribuintes"); // somente admin
		btnNewButton_5.setBounds(21, 100, 189, 23);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadContribuinte.getInstancia().setVisible(true);
				instancia.setVisible(false);
			}
		});
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_4 = new JButton("Consulta de CDAs");
		btnNewButton_4.setBounds(21, 66, 189, 23);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaConsultaCDA.getInstancia().setVisible(true);
				setVisible(false);
			}
			
		});
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_1 = new JButton("Gest\u00E3o das CDAs"); // somente admin
		btnNewButton_1.setBounds(21, 134, 189, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadCDAPrototipo.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Sair do Sistema");
		btnNewButton.setBounds(21, 334, 189, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); //sair do sistema
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_3 = new JButton("Emiss\u00E3o de Boletos");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaEmissaoBoleto.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_3.setBounds(21, 198, 189, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_6 = new JButton("Relat\u00F3rio de Pagamentos");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaRelatorioPagamento.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_6.setBounds(21, 232, 189, 23);
		contentPane.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Atendimento ao Chat");
		btnNewButton_7.setBounds(21, 266, 189, 23);
		contentPane.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("Parcelamento de CDAs");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaParcelamento.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_8.setBounds(21, 300, 189, 23);
		contentPane.add(btnNewButton_8);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
