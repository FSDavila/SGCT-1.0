package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import controller.ControllerFuncionario;
import model.Funcionario;
import persistance.MapeadorFuncionario;

public class CadastroFuncionario extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField nomeFuncionario;
	private JTextField emailFuncionario;
	private JTextField cpfFuncionario;
	private JTextField loginFuncionario;
	private JPasswordField senhaFuncionario;

	ControllerFuncionario controlerFuncionario = new ControllerFuncionario();

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
	 * 
	 * 
	 */
	public CadastroFuncionario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel textFNome = new JLabel("Nome:");
		textFNome.setBounds(31, 73, 44, 23);
		contentPane.add(textFNome);

		nomeFuncionario = new JTextField();
		nomeFuncionario.setBounds(75, 74, 340, 20);
		contentPane.add(nomeFuncionario);
		nomeFuncionario.setColumns(10);

		JLabel textFEmail = new JLabel("Email:");
		textFEmail.setBounds(31, 107, 44, 14);
		contentPane.add(textFEmail);

		emailFuncionario = new JTextField();
		emailFuncionario.setBounds(75, 105, 340, 20);
		contentPane.add(emailFuncionario);
		emailFuncionario.setColumns(10);

		JLabel textFCpf = new JLabel("CPF:");
		textFCpf.setBounds(31, 139, 44, 14);
		contentPane.add(textFCpf);

		try {
			cpfFuncionario = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		textFLogin.setBounds(31, 170, 44, 14);
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
				Funcionario funcionario = null;
				String cpf = cpfFuncionario.getText().replaceAll(". -", "");
				boolean checkAdmin;
				Date dataAdmissao = new Date(System.currentTimeMillis());

				if (checkAdm.isSelected()) {
					checkAdmin = true;
				} else {
					checkAdmin = false;
				}

				boolean camposValido = ControllerFuncionario.getInstancia().validaCamposCadastro(
						nomeFuncionario.getText(), cpf, emailFuncionario.getText(), loginFuncionario.getText(),
						String.valueOf(senhaFuncionario.getPassword()));

				if (!camposValido) {
					JOptionPane.showMessageDialog(null, "Favor preencher todos os campos", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					funcionario = ControllerFuncionario.getInstancia().cadastrarFuncionario(nomeFuncionario.getText(),
							dataAdmissao, cpf, dataAdmissao, emailFuncionario.getText(), checkAdmin,
							loginFuncionario.getText(), String.valueOf(senhaFuncionario.getPassword()));

				}
				if (funcionario != null) {
					JOptionPane.showMessageDialog(null, "Funcionario Cadastrado com Sucesso", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					try {
						MapeadorFuncionario.getInstancia().persist();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login Inválido ", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});

		btnCadastrar.setBounds(326, 259, 89, 23);
		contentPane.add(btnCadastrar);

		JButton Alterar = new JButton("Alterar");
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				

			}
		});
		Alterar.setBounds(217, 259, 89, 23);
		contentPane.add(Alterar);

	}
}
