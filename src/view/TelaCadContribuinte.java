package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDayChooser;

import controller.ControllerContribuinte;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadContribuinte extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdentificacao;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JTextField textFieldLogin;
	private JTextField textFieldSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadContribuinte frame = new TelaCadContribuinte();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadContribuinte() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DD/MM");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldIdentificacao = new JTextField();
		textFieldIdentificacao.setBounds(10, 29, 86, 20);
		panel.add(textFieldIdentificacao);
		textFieldIdentificacao.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF / CNPJ");
		lblNewLabel.setBounds(10, 11, 86, 14);
		panel.add(lblNewLabel);
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(106, 29, 223, 20);
		panel.add(textFieldNome);
		
		JLabel lblO = new JLabel("Nome Contribuinte:");
		lblO.setBounds(106, 11, 145, 14);
		panel.add(lblO);
		
		JLabel lblO_1 = new JLabel("E-mail:");
		lblO_1.setBounds(10, 77, 145, 14);
		panel.add(lblO_1);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(10, 95, 172, 20);
		panel.add(textFieldEmail);
		
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//int motiveCounter = ControllerContribuinte.getInstancia().cadastraContribuinte(nome, DNF, identificacao, login, senha, email, dtCadastroChat, ehCNPJ)
			}
		});
		btnConfirmar.setBounds(10, 187, 108, 23);
		panel.add(btnConfirmar);
		
		JDateChooser dateChooserDNF = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooserDNF.setBounds(10, 144, 108, 20);
		panel.add(dateChooserDNF);
		
		JLabel lblNewLabel_1 = new JLabel("Dt. Nascimento / Funda\u00E7\u00E3o");
		lblNewLabel_1.setBounds(10, 126, 148, 14);
		panel.add(lblNewLabel_1);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(220, 95, 86, 20);
		panel.add(textFieldLogin);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(220, 77, 86, 14);
		panel.add(lblLogin);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(220, 144, 86, 20);
		panel.add(textFieldSenha);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(220, 126, 86, 14);
		panel.add(lblSenha);
		
		JCheckBox checkBoxEhCNPJ = new JCheckBox("Cliente Pessoa Jur\u00EDdica");
		checkBoxEhCNPJ.setBounds(6, 56, 149, 23);
		panel.add(checkBoxEhCNPJ);
		
	}
}
