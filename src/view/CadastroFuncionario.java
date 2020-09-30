package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.ControllerFuncionario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

import com.toedter.calendar.JDateChooser;

public class CadastroFuncionario extends JFrame {

	private JPanel contentPane;
	private JTextField nomeFuncionario;
	private JTextField emailFuncionario;
	private JTextField cpfFuncionario;
	private JTextField loginFuncionario;
	private JPasswordField senhaFuncionario;
	
	ControllerFuncionario funcionario = new ControllerFuncionario();
	Date dataAdmissao = new Date(System.currentTimeMillis());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroFuncionario frame = new CadastroFuncionario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public CadastroFuncionario() throws ParseException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel textFNome = new JLabel("Nome:");
		textFNome.setBounds(31, 73, 34, 23);
		contentPane.add(textFNome);
		
		nomeFuncionario = new JTextField();
		nomeFuncionario.setBounds(75, 74, 340, 20);
		contentPane.add(nomeFuncionario);
		nomeFuncionario.setColumns(10);
		
		JLabel textFEmail = new JLabel("Email:");
		textFEmail.setBounds(31, 107, 34, 14);
		contentPane.add(textFEmail);
		
		emailFuncionario = new JTextField();
		emailFuncionario.setBounds(75, 105, 340, 20);
		contentPane.add(emailFuncionario);
		emailFuncionario.setColumns(10);
		
		JLabel textFCpf = new JLabel("CPF:");
		textFCpf.setBounds(31, 139, 34, 14);
		contentPane.add(textFCpf);
		
		cpfFuncionario = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		cpfFuncionario.setBounds(75, 136, 131, 20);
		contentPane.add(cpfFuncionario);
		cpfFuncionario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data Nascimento");
		lblNewLabel_1.setBounds(216, 139, 89, 14);
		contentPane.add(lblNewLabel_1);
		
		JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooser.setBounds(307, 136, 108, 20);
		contentPane.add(dateChooser);
		
		JCheckBox checkAdm = new JCheckBox("Administrador");
		checkAdm.setBounds(75, 194, 97, 23);
		contentPane.add(checkAdm);
		
		JLabel textFLogin = new JLabel("Login:");
		textFLogin.setBounds(31, 170, 34, 14);
		contentPane.add(textFLogin);
		
		loginFuncionario = new JTextField();
		loginFuncionario.setBounds(75, 167, 131, 20);
		contentPane.add(loginFuncionario);
		loginFuncionario.setColumns(10);
		
		JLabel textFSenha = new JLabel("Senha:");
		textFSenha.setBounds(216, 170, 44, 14);
		contentPane.add(textFSenha);
		
		JLabel lblNewLabel = new JLabel("Cadastro Funcionario");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(146, 11, 160, 30);
		contentPane.add(lblNewLabel);
		
		senhaFuncionario = new JPasswordField();
		senhaFuncionario.setBounds(259, 167, 160, 20);
		contentPane.add(senhaFuncionario);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (funcionario.validaCamposCadastro(nomeFuncionario.getText(), Long.parseLong(cpfFuncionario.getText()), emailFuncionario.getText(), loginFuncionario.getText(),  String.valueOf(senhaFuncionario.getPassword()))) {
					JOptionPane.showMessageDialog(null, "Favor preencher todos os campos");
				} else {
					boolean checkBox = false;
					if (checkAdm.isSelected())
						checkBox = true;
						
					funcionario.cadastrarFuncionario(nomeFuncionario.getText(), dataAdmissao, Long.parseLong(cpfFuncionario.getText()), dataAdmissao, emailFuncionario.getText(), checkBox, loginFuncionario.getText(), String.valueOf(senhaFuncionario.getPassword()));
				}
				
				
			}
		});
		
		btnCadastrar.setBounds(326, 259, 89, 23);
		contentPane.add(btnCadastrar);
		
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(217, 259, 89, 23);
		contentPane.add(btnVoltar);
		
	
		
	}
}
