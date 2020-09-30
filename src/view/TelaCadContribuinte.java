package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDayChooser;

import controller.ControllerContribuinte;
import model.Contribuinte;
import persistance.MapeadorContribuinte;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.awt.event.ActionEvent;

public class TelaCadContribuinte extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdentificacao;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JTextField textFieldLogin;
	private JTextField textFieldSenha;
	private JCheckBox checkBoxEhCNPJ;
	private JDateChooser dateChooserDNF;
	private JButton btnConfirmar;
	private JButton btnCadastrar;
	private JButton btnCancelar;

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
		setBounds(100, 100, 350, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DD/MM");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldIdentificacao = new JTextField();
		textFieldIdentificacao.setEnabled(false);
		textFieldIdentificacao.setBounds(10, 29, 86, 20);
		panel.add(textFieldIdentificacao);
		textFieldIdentificacao.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF / CNPJ");
		lblNewLabel.setBounds(10, 11, 86, 14);
		panel.add(lblNewLabel);
		
		textFieldNome = new JTextField();
		textFieldNome.setEnabled(false);
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(106, 29, 200, 20);
		panel.add(textFieldNome);
		
		JLabel lblO = new JLabel("Nome Contribuinte");
		lblO.setBounds(106, 11, 145, 14);
		panel.add(lblO);
		
		JLabel lblO_1 = new JLabel("E-mail");
		lblO_1.setBounds(10, 77, 145, 14);
		panel.add(lblO_1);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setEnabled(false);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(10, 95, 172, 20);
		panel.add(textFieldEmail);
		
		
		btnConfirmar = new JButton("OK");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
				String identificacao = textFieldIdentificacao.getText();
				String nome = textFieldNome.getText();
				String login = textFieldLogin.getText();
				String senha = textFieldSenha.getText();
				String email = textFieldEmail.getText();
				boolean ehCNPJ = checkBoxEhCNPJ.isSelected();
				Date DNF = dateChooserDNF.getDate();
				
				Contribuinte novo = ControllerContribuinte.getInstancia().cadastraContribuinte(nome, DNF, identificacao, login, senha, email, ehCNPJ);
				
				if (novo != null) {
                    try {
                    MapeadorContribuinte.getInstancia().persist();
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    				textFieldIdentificacao.setEnabled(false);
    				textFieldNome.setEnabled(false);
    				textFieldEmail.setEnabled(false);
    				textFieldLogin.setEnabled(false);
    				textFieldSenha.setEnabled(false);
    				checkBoxEhCNPJ.setEnabled(false);
    				dateChooserDNF.setEnabled(false);
    				textFieldIdentificacao.setText("");
    				textFieldNome.setText("");
    				textFieldEmail.setText("");
    				textFieldLogin.setText("");
    				textFieldSenha.setText("");
    				dateChooserDNF.setDate(null);
    				checkBoxEhCNPJ.setSelected(false);
    				btnConfirmar.setEnabled(false);
    				btnCancelar.setEnabled(false);
    				btnCadastrar.setEnabled(true);

    				

                    
                    } 
                    catch (FileNotFoundException ex) {
                        System.out.println("Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
                    }

				}
				else {
                    JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
				
				 }
                  catch (NullPointerException a) {
                     JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                 }
			}
		});
			
			
        
		btnConfirmar.setBounds(242, 187, 64, 23);
		panel.add(btnConfirmar);
		
		dateChooserDNF = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooserDNF.setBounds(10, 144, 108, 20);
		dateChooserDNF.setEnabled(false);;
		panel.add(dateChooserDNF);

		
		JLabel lblNewLabel_1 = new JLabel("Dt. Nascimento / Funda\u00E7\u00E3o");
		lblNewLabel_1.setBounds(10, 126, 148, 14);
		panel.add(lblNewLabel_1);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setEnabled(false);
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(220, 95, 86, 20);
		panel.add(textFieldLogin);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(220, 77, 86, 14);
		panel.add(lblLogin);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setEnabled(false);
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(220, 144, 86, 20);
		panel.add(textFieldSenha);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(220, 126, 86, 14);
		panel.add(lblSenha);
		
		checkBoxEhCNPJ = new JCheckBox("Cliente Pessoa Jur\u00EDdica");
		checkBoxEhCNPJ.setEnabled(false);
		checkBoxEhCNPJ.setBounds(6, 56, 245, 23);
		panel.add(checkBoxEhCNPJ);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldIdentificacao.setEnabled(true);
				textFieldNome.setEnabled(true);
				textFieldEmail.setEnabled(true);
				textFieldLogin.setEnabled(true);
				textFieldSenha.setEnabled(true);
				checkBoxEhCNPJ.setEnabled(true);
				dateChooserDNF.setEnabled(true);
				btnConfirmar.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnCadastrar.setEnabled(false);
			}
		});
		btnCadastrar.setBounds(10, 187, 108, 23);
		panel.add(btnCadastrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldIdentificacao.setEnabled(false);
				textFieldNome.setEnabled(false);
				textFieldEmail.setEnabled(false);
				textFieldLogin.setEnabled(false);
				textFieldSenha.setEnabled(false);
				checkBoxEhCNPJ.setEnabled(false);
				dateChooserDNF.setEnabled(false);
				textFieldIdentificacao.setText("");
				textFieldNome.setText("");
				textFieldEmail.setText("");
				textFieldLogin.setText("");
				textFieldSenha.setText("");
				dateChooserDNF.setDate(null);
				checkBoxEhCNPJ.setSelected(false);
				btnConfirmar.setEnabled(false);
				btnCadastrar.setEnabled(true);
				btnCancelar.setEnabled(false);
			}
		});
		btnCancelar.setBounds(143, 187, 89, 23);
		panel.add(btnCancelar);
		
	}
}
