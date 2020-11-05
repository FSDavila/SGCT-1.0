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

public class TelaGestaoFuncionario extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField nomeFuncionario;
	private JTextField emailFuncionario;
	private JTextField cpfFuncionario;
	private JTextField loginFuncionario;
	private JPasswordField senhaFuncionario;
	private JButton btnOk;
	private JButton btnCancelar;

	private static TelaGestaoFuncionario instancia;
	private Integer estado; // 0 = estado inicial, 1 = cadastrando, 2 = alterando, 3 = consultando

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaGestaoFuncionario frame = new TelaGestaoFuncionario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static TelaGestaoFuncionario getInstancia() {
		if (instancia == null) {
			instancia = new TelaGestaoFuncionario();
		}
		return instancia;
	}

	/**
	 * Create the frame.
	 * 
	 * 
	 */
	public TelaGestaoFuncionario() {
		setTitle("Gestão de Funcionários");
		this.estado = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel textFNome = new JLabel("Nome:");
		textFNome.setBounds(31, 73, 44, 23);
		contentPane.add(textFNome);

		nomeFuncionario = new JTextField();
		nomeFuncionario.setEnabled(false);
		nomeFuncionario.setBounds(75, 74, 355, 20);
		contentPane.add(nomeFuncionario);
		nomeFuncionario.setColumns(10);

		JLabel textFEmail = new JLabel("Email:");
		textFEmail.setBounds(31, 107, 44, 14);
		contentPane.add(textFEmail);

		emailFuncionario = new JTextField();
		emailFuncionario.setEnabled(false);
		emailFuncionario.setBounds(75, 105, 355, 20);
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
		lblNewLabel_1.setBounds(216, 139, 116, 14);
		contentPane.add(lblNewLabel_1);

		JDateChooser dateChooser = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooser.setEnabled(false);
		dateChooser.setBounds(331, 136, 99, 20);
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

		JLabel lblNewLabel = new JLabel("Funcion\u00E1rios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(140, 21, 185, 30);
		contentPane.add(lblNewLabel);
		
//		if (estado == 1) {
//			lblNewLabel.setText("Cadastrando Funcion\u00E1rio");
//			this.repaint();
//		} else if (estado == 2) {
//			lblNewLabel.setText("Atualizando Funcion\u00E1rio");
//			this.repaint();
//		} else if (estado == 3) {
//			lblNewLabel.setText("Conusltando Funcion\u00E1rios ");
//			this.repaint();
//		} else {
//			lblNewLabel.setText("Funcion\u00E1rios");
//			this.repaint();
//		}

		senhaFuncionario = new JPasswordField();
		senhaFuncionario.setEnabled(false);
		senhaFuncionario.setBounds(259, 167, 171, 20);
		contentPane.add(senhaFuncionario);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				estado = 1;
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
				btnOk.setEnabled(true);
				btnCancelar.setEnabled(true);
				

			}
		});

		btnCadastrar.setBounds(63, 224, 99, 23);
		contentPane.add(btnCadastrar);

		JButton Alterar = new JButton("Alterar");
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = JOptionPane.showInputDialog(null, "Insira o cpf do funcionario", "Alterando",
						JOptionPane.INFORMATION_MESSAGE);
				Funcionario alterar = ControllerFuncionario.getInstancia().getFuncionarioByCpf(cpf);

				if (alterar != null) {
					estado = 2;
					nomeFuncionario.setText(alterar.getNome());
					nomeFuncionario.setEnabled(true);
					emailFuncionario.setText(alterar.getEmail());
					emailFuncionario.setEnabled(true);
					cpfFuncionario.setText(alterar.getCPF());
					cpfFuncionario.setEnabled(false);
					loginFuncionario.setText(alterar.getLogin());
					loginFuncionario.setEnabled(false);
					senhaFuncionario.setText("");
					senhaFuncionario.setEnabled(true);
					btnOk.setEnabled(true);
					btnCancelar.setEnabled(true);
					

				} else {
					JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		Alterar.setBounds(195, 224, 99, 23);
		contentPane.add(Alterar);
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = JOptionPane.showInputDialog(null, "Insira o cpf do funcionario", "Consultando",
						JOptionPane.INFORMATION_MESSAGE);
				Funcionario consultar = ControllerFuncionario.getInstancia().getFuncionarioByCpf(cpf);

				if (consultar != null) {
					estado = 3;
					
					nomeFuncionario.setText(consultar.getNome());
					nomeFuncionario.setEnabled(false);
					emailFuncionario.setText(consultar.getEmail());
					emailFuncionario.setEnabled(false);
					cpfFuncionario.setText(consultar.getCPF());
					cpfFuncionario.setEnabled(false);
					dateChooser.setDate(consultar.getDNF());
					dateChooser.setEnabled(false);
					loginFuncionario.setText(consultar.getLogin());
					loginFuncionario.setEnabled(false);
					senhaFuncionario.setText(consultar.getSenha());
					senhaFuncionario.setEnabled(false);
					checkAdm.setSelected(consultar.isEhAdm());
					btnOk.setEnabled(true);
					btnCancelar.setEnabled(true);

					
				} else {
					JOptionPane.showMessageDialog(null, "Funcionario não encontrado.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(316, 224, 99, 23);
		contentPane.add(btnNewButton);

		btnOk = new JButton("Ok");
		btnOk.setEnabled(false);
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
						JOptionPane.showMessageDialog(null, "Preencha os campos em branco", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);

					} else if (!validacaoLogin || !validacaoCpf) {

						if (!validacaoLogin)
							loginWarning = "Login inv�lido. Insira outro.";
						if (!validacaoCpf)
							cpfWarning = "CPF Inv�lido";

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

						nomeFuncionario.setText("");
						nomeFuncionario.setEnabled(false);
						emailFuncionario.setText("");
						emailFuncionario.setEnabled(false);
						cpfFuncionario.setText("");
						cpfFuncionario.setEnabled(false);
						loginFuncionario.setText("");
						loginFuncionario.setEnabled(false);
						senhaFuncionario.setText("");
						senhaFuncionario.setEnabled(false);
						checkAdm.setSelected(false);
						checkAdm.setEnabled(false);
						dateChooser.setDate(null);
						dateChooser.setEnabled(false);
						btnOk.setEnabled(false);
						btnCancelar.setEnabled(false);

						estado = 0;

					}

				case 2:

					if (checkAdm.isSelected()) {
						checkAdmin = true;
					} else {
						checkAdmin = false;
					}

					if (!camposValido) {
						JOptionPane.showMessageDialog(null, "Preencha os campos em branco", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						ControllerFuncionario.getInstancia().atualizarFuncionario(nomeFuncionario.getText(), cpf,
								emailFuncionario.getText(), checkAdmin);

						try {
							MapeadorFuncionario.getInstancia().persist();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						JOptionPane.showMessageDialog(null, "Funcionário Atualizado com sucesso", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);

						nomeFuncionario.setText("");
						nomeFuncionario.setEnabled(false);
						emailFuncionario.setText("");
						emailFuncionario.setEnabled(false);
						cpfFuncionario.setText("");
						cpfFuncionario.setEnabled(false);
						loginFuncionario.setText("");
						loginFuncionario.setEnabled(false);
						senhaFuncionario.setText("");
						senhaFuncionario.setEnabled(false);
						checkAdm.setSelected(false);
						checkAdm.setEnabled(false);
						dateChooser.setDate(null);
						dateChooser.setEnabled(false);
						btnOk.setEnabled(false);
						btnCancelar.setEnabled(false);

						estado = 0;

					}

				}

			}
		});
		btnOk.setBounds(316, 258, 99, 23);
		contentPane.add(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomeFuncionario.setText("");
				nomeFuncionario.setEnabled(false);
				emailFuncionario.setText("");
				emailFuncionario.setEnabled(false);
				cpfFuncionario.setText("");
				cpfFuncionario.setEnabled(false);
				loginFuncionario.setText("");
				loginFuncionario.setEnabled(false);
				senhaFuncionario.setText("");
				senhaFuncionario.setEnabled(false);
				checkAdm.setSelected(false);
				checkAdm.setEnabled(false);
				dateChooser.setDate(null);
				dateChooser.setEnabled(false);
				btnOk.setEnabled(false);
				btnCancelar.setEnabled(false);

				estado = 0;
			}
		});
		btnCancelar.setBounds(195, 259, 99, 23);
		btnCancelar.setEnabled(false);
		contentPane.add(btnCancelar);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(63, 258, 99, 23);
		contentPane.add(btnVoltar);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
