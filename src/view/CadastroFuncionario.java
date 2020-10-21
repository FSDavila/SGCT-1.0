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
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

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

	private static CadastroFuncionario instancia;
	private Integer estado; // 0 = estado inicial, 1 = cadastrando, 2 = alterando, 3 = consultando

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

	public static CadastroFuncionario getInstancia() {
		if (instancia == null) {
			instancia = new CadastroFuncionario();
		}
		return instancia;
	}

	/**
	 * Create the frame.
	 * 
	 * 
	 */
	public CadastroFuncionario() {
		setTitle("Gest\u00E3o de Cadastros");
		this.estado = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel textFNome = new JLabel("Nome:");
		textFNome.setBounds(31, 73, 44, 23);
		contentPane.add(textFNome);

		nomeFuncionario = new JTextField();
		nomeFuncionario.setEnabled(false);
		nomeFuncionario.setBounds(75, 74, 340, 20);
		contentPane.add(nomeFuncionario);
		nomeFuncionario.setColumns(10);

		JLabel textFEmail = new JLabel("Email:");
		textFEmail.setBounds(31, 107, 44, 14);
		contentPane.add(textFEmail);

		emailFuncionario = new JTextField();
		emailFuncionario.setEnabled(false);
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
		cpfFuncionario.setEnabled(false);
		cpfFuncionario.setBounds(75, 136, 131, 20);
		contentPane.add(cpfFuncionario);
		cpfFuncionario.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Data de Nascimento");
		lblNewLabel_1.setBounds(216, 139, 131, 14);
		contentPane.add(lblNewLabel_1);

		JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooser.setEnabled(false);
		dateChooser.setBounds(335, 136, 80, 20);
		contentPane.add(dateChooser);

		JCheckBox checkAdm = new JCheckBox("Administrador");
		checkAdm.setEnabled(false);
		checkAdm.setBounds(75, 194, 131, 23);
		contentPane.add(checkAdm);

		JLabel textFLogin = new JLabel("Login:");
		textFLogin.setBounds(31, 170, 44, 14);
		contentPane.add(textFLogin);

		loginFuncionario = new JTextField();
		loginFuncionario.setEnabled(false);
		loginFuncionario.setBounds(75, 167, 131, 20);
		contentPane.add(loginFuncionario);
		loginFuncionario.setColumns(10);

		JLabel textFSenha = new JLabel("Senha:");
		textFSenha.setBounds(216, 170, 44, 14);
		contentPane.add(textFSenha);

		senhaFuncionario = new JPasswordField();
		senhaFuncionario.setEnabled(false);
		senhaFuncionario.setBounds(259, 167, 160, 20);
		contentPane.add(senhaFuncionario);

		JLabel lblNewLabel = new JLabel("Cadastros de Funcion\u00E1rios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(140, 21, 185, 30);
		contentPane.add(lblNewLabel);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				nomeFuncionario.setText("");
				nomeFuncionario.setEnabled(true);
				emailFuncionario.setText("");
				emailFuncionario.setEnabled(true);
				cpfFuncionario.setText("");
				cpfFuncionario.setEnabled(true);
				loginFuncionario.setText("");
				loginFuncionario.setEnabled(true);
				senhaFuncionario.setText("");
				senhaFuncionario.setEnabled(true);
				checkAdm.setEnabled(true);
				dateChooser.setEnabled(true);
				

			}

		});

		btnCadastrar.setBounds(63, 224, 99, 23);
		contentPane.add(btnCadastrar);

		JButton Alterar = new JButton("Alterar");
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = JOptionPane.showInputDialog(null, "Insira o cpf do funcionario",
						"Alteracao de Funcionario", JOptionPane.INFORMATION_MESSAGE);
				Funcionario alterar = ControllerFuncionario.getInstancia().getFuncionarioByCpf(cpf);

				if (alterar != null) {
					nomeFuncionario.setText(alterar.getNome());
					nomeFuncionario.setEnabled(true);
					emailFuncionario.setText(alterar.getEmail());
					emailFuncionario.setEnabled(true);
					cpfFuncionario.setText(alterar.getCPF());
					cpfFuncionario.setEnabled(false);
					loginFuncionario.setText("");
					loginFuncionario.setEnabled(true);
					senhaFuncionario.setText("");
					senhaFuncionario.setEnabled(true);

				}

			}
		});
		Alterar.setBounds(195, 224, 99, 23);
		contentPane.add(Alterar);

		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(316, 224, 99, 23);
		contentPane.add(btnNewButton);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = cpfFuncionario.getText().replaceAll(".", "").replaceAll("-", "");
				System.out.println(cpf);
				String cpfWarning = "";
				String loginWarning = "";
				boolean checkAdmin;
				Date dataAdmissao = new Date(System.currentTimeMillis());

				boolean camposValido = ControllerFuncionario.getInstancia().validaCamposNull(nomeFuncionario.getText(),
						cpf, emailFuncionario.getText(), loginFuncionario.getText(),
						String.valueOf(senhaFuncionario.getPassword()));

				boolean validacaoLogin = ControllerFuncionario.getInstancia().validaLogin(loginFuncionario.getText());
				boolean validacaoCpf = ControllerFuncionario.getInstancia().validaCpf(cpf);

				switch (estado) {

				case 1:
					Funcionario cadastrar = null;

					if (checkAdm.isSelected()) {
						checkAdmin = true;
					} else {
						checkAdmin = false;
					}

					if (!camposValido) {
						JOptionPane.showMessageDialog(null, "Favor preencher todos os campos", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);

					} else if (!validacaoLogin || !validacaoCpf) {

						if (!validacaoLogin)
							loginWarning = "Login inválido. Insira outro.";
						if (!validacaoCpf)
							cpfWarning = "CPF Inválido";

						JOptionPane.showMessageDialog(null, loginWarning + "\n" + cpfWarning, "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						cadastrar = ControllerFuncionario.getInstancia().cadastrarFuncionario(nomeFuncionario.getText(),
								dateChooser.getDate(), cpf, dataAdmissao, emailFuncionario.getText(), checkAdmin,
								loginFuncionario.getText(), String.valueOf(senhaFuncionario.getPassword()));
					}

					if (cadastrar != null) {
						JOptionPane.showMessageDialog(null, "Funcionario Cadastrado com Sucesso", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
						try {
							MapeadorFuncionario.getInstancia().persist();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				case 2:

				}

			}
		});
		btnOk.setBounds(316, 258, 99, 23);
		contentPane.add(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(195, 259, 99, 23);
		contentPane.add(btnCancelar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(63, 258, 99, 23);
		contentPane.add(btnVoltar);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
