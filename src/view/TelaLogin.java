package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ControllerContribuinte;
import controller.ControllerFuncionario;
import model.Contribuinte;
import model.Funcionario;
import persistance.MapeadorChatLog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	
	private static TelaLogin instancia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaLogin getInstancia() {
        if (instancia == null) {
            instancia = new TelaLogin();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		setBounds(100, 100, 250, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Criar conta de acesso ao chat");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setBounds(10, 238, 226, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Logar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Contribuinte cprocurado = ControllerContribuinte.getInstancia().getContribuinteByLogin(textField.getText());
				Funcionario fprocurado = ControllerFuncionario.getInstancia().getFuncionarioByLogin(textField.getText());
				if(fprocurado != null && cprocurado == null) {
					JOptionPane.showMessageDialog(null, "Funcionario logado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					TelaMenuPrincipal.getInstancia().setVisible(true);
					setVisible(false);
				}
				else if(cprocurado != null && fprocurado == null) {
					//entrar no chat
					JOptionPane.showMessageDialog(null, "Contribuinte logado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					TelaChatContribuinte.getInstancia().setUsuarioLogado(cprocurado);//loga usuario no chat
					if(MapeadorChatLog.getInstancia().get(cprocurado.getIdentificacao()) != null) {
						TelaChatContribuinte.getInstancia().setDadosChat(cprocurado.getIdentificacao()); //carrega o historico do chat do usuario onde parou
					}
					TelaChatContribuinte.getInstancia().setUsuarioOnline();
					TelaChatContribuinte.getInstancia().setVisible(true);
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		btnNewButton.setBounds(79, 184, 89, 23);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(43, 147, 154, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Sistema de Gest\u00E3o e Cobran\u00E7a Tributaria");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(31, 36, 180, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("SGCT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(103, 11, 29, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(30);
		textField.setBounds(43, 89, 154, 20);
		contentPane.add(textField);
		
		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(44, 72, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("Senha:");
		lblNewLabel_4.setBounds(44, 133, 46, 14);
		contentPane.add(lblNewLabel_4);
	}

}
