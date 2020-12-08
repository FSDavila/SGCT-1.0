package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import controller.ControllerContribuinte;
import model.Contribuinte;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadChatContribuinte extends JFrame {

	private JPanel contentPane;

	private static TelaCadChatContribuinte instancia; // singleton
	private JTextField textFieldLogin;
	private JTextField textFieldSenha;
	private JTextField textFieldCPF;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadChatContribuinte frame = new TelaCadChatContribuinte();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static TelaCadChatContribuinte getInstancia() {
		if (instancia == null) {
			instancia = new TelaCadChatContribuinte();
		}
		return instancia;
	}

	/**
	 * Create the frame.
	 */
	public TelaCadChatContribuinte() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 372, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(151, 104, 153, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		textFieldSenha = new JPasswordField();
		textFieldSenha.setBounds(151, 132, 153, 20);
		contentPane.add(textFieldSenha);
		textFieldSenha.setColumns(10);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(76, 107, 46, 14);
		contentPane.add(lblLogin);

		JLabel lblNewLabel = new JLabel("Senha:\r\n\r\n");
		lblNewLabel.setBounds(76, 135, 46, 14);
		contentPane.add(lblNewLabel);

		textFieldCPF = new JTextField();

		textFieldCPF.setBounds(152, 79, 102, 20);
		contentPane.add(textFieldCPF);
		textFieldCPF.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("CPF/CNPJ:\r\n\r\n");
		lblNewLabel_1.setBounds(76, 82, 66, 14);
		contentPane.add(lblNewLabel_1);

		JButton btnCadastrar = new JButton("Cadastrar\r\n");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Contribuinte contribuinte = null;
				
				if (textFieldCPF.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Favor inserir um CPF válido.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else if (textFieldSenha.getText().trim().equals("") || textFieldLogin.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Favor inserir um login e uma senha.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String cpf = textFieldCPF.getText();
				    contribuinte = ControllerContribuinte.getInstancia().getContribuinteById(cpf);
					
				}
				
				if (contribuinte != null) {
					ControllerContribuinte.getInstancia().alteraDados(contribuinte.getIdentificacao(),
							contribuinte.getNome(), contribuinte.getDNF(), textFieldLogin.getText(),
							textFieldSenha.getText(), contribuinte.getEmail(), new Date(System.currentTimeMillis()),
							contribuinte.isEhCNPJ());
					
					JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnCadastrar.setBounds(208, 182, 95, 23);
		contentPane.add(btnCadastrar);
		
		btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		btnNewButton.setBounds(76, 182, 89, 23);
		contentPane.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("Cadastrando contribuinte para o CHAT\r\n");
		lblNewLabel_2.setBounds(96, 35, 208, 14);
		contentPane.add(lblNewLabel_2);
	}
}
