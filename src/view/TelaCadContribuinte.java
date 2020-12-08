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
import model.CDA;
import model.Contribuinte;
import persistance.MapeadorCDA;
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
	private JCheckBox checkBoxEhCNPJ;
	private JDateChooser dateChooserDNF;
	private JButton btnConfirmar;
	private JButton btnCadastrar;
	private JButton btnCancelar;

	private Integer estado; // 0 = estado inicial, 1 = cadastrando, 2 = alterando, 3 = consultando

	private static TelaCadContribuinte instancia;
	private JButton btnNewButton;
	private JButton btnConsultar;
	private JButton btnAlterar;

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

	public static TelaCadContribuinte getInstancia() {
		if (instancia == null) {
			instancia = new TelaCadContribuinte();
		}
		return instancia;
	}

	/**
	 * Create the frame.
	 */
	public TelaCadContribuinte() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 421, 270);
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
				if (estado == 1) { // modo cadastrar
					try {
						String identificacao = textFieldIdentificacao.getText();
						String nome = textFieldNome.getText();
					
						String email = textFieldEmail.getText();
						boolean ehCNPJ = checkBoxEhCNPJ.isSelected();
						Date DNF = dateChooserDNF.getDate();

						Contribuinte novo = ControllerContribuinte.getInstancia().cadastraContribuinte(nome, DNF,
								identificacao, null, null, email, ehCNPJ);

						if (novo != null) {
							try {
								MapeadorContribuinte.getInstancia().persist();
								JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!", "Aviso",
										JOptionPane.INFORMATION_MESSAGE);
								textFieldIdentificacao.setEnabled(false);
								textFieldNome.setEnabled(false);
								textFieldEmail.setEnabled(false);
								
								checkBoxEhCNPJ.setEnabled(false);
								dateChooserDNF.setEnabled(false);
								textFieldIdentificacao.setText("");
								textFieldNome.setText("");
								textFieldEmail.setText("");
								
								dateChooserDNF.setDate(null);
								checkBoxEhCNPJ.setSelected(false);
								btnConfirmar.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnCadastrar.setEnabled(true);
								btnAlterar.setEnabled(true);
								btnConsultar.setEnabled(true);

							} catch (FileNotFoundException ex) {
								System.out.println(
										"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
							}

						} else {
							JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.",
									"Aviso", JOptionPane.WARNING_MESSAGE);
						}

					} catch (NullPointerException a) {
						JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (estado == 2) { // modo alterar
					try {
						Contribuinte alterar = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
						
						alterar.setNome(textFieldNome.getText());
						
						alterar.setEmail(textFieldEmail.getText());
						if(checkBoxEhCNPJ.isSelected()) {
							alterar.setEhCNPJ(true);
						}
						alterar.setDNF(dateChooserDNF.getDate());
						
						if (alterar != null) {
							try {
								MapeadorContribuinte.getInstancia().persist();
								JOptionPane.showMessageDialog(null, "Usuario atualizado com sucesso!", "Aviso",
										JOptionPane.INFORMATION_MESSAGE);

							} catch (FileNotFoundException ex) {
								System.out.println(
										"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
							}

						} else {
							JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.",
									"Aviso", JOptionPane.WARNING_MESSAGE);
						}

					} catch (NullPointerException a) {
						JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
					
					textFieldIdentificacao.setEnabled(false);
					textFieldNome.setEnabled(false);
					textFieldEmail.setEnabled(false);
					
					checkBoxEhCNPJ.setEnabled(false);
					dateChooserDNF.setEnabled(false);
					textFieldIdentificacao.setText("");
					textFieldNome.setText("");
					textFieldEmail.setText("");
					
					dateChooserDNF.setDate(null);
					checkBoxEhCNPJ.setSelected(false);
					
					btnConfirmar.setEnabled(false);
					btnCadastrar.setEnabled(true);
					btnAlterar.setEnabled(true);
					btnConsultar.setEnabled(true);
					btnCancelar.setEnabled(false);
				}
			}
		});

		btnConfirmar.setBounds(321, 187, 64, 23);
		panel.add(btnConfirmar);

		dateChooserDNF = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooserDNF.setBounds(10, 144, 108, 20);
		dateChooserDNF.setEnabled(false);
		;
		panel.add(dateChooserDNF);

		JLabel lblNewLabel_1 = new JLabel("Dt. Nascimento / Funda\u00E7\u00E3o");
		lblNewLabel_1.setBounds(10, 126, 148, 14);
		panel.add(lblNewLabel_1);

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
				
				checkBoxEhCNPJ.setEnabled(true);
				dateChooserDNF.setEnabled(true);
				btnConfirmar.setEnabled(true);
				btnCadastrar.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnConsultar.setEnabled(false);
				btnCancelar.setEnabled(true);
				estado = 1;
			}
		});
		btnCadastrar.setBounds(10, 187, 86, 23);
		panel.add(btnCadastrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldIdentificacao.setEnabled(false);
				textFieldNome.setEnabled(false);
				textFieldEmail.setEnabled(false);
				
				checkBoxEhCNPJ.setEnabled(false);
				dateChooserDNF.setEnabled(false);
				textFieldIdentificacao.setText("");
				textFieldNome.setText("");
				textFieldEmail.setText("");
				
				dateChooserDNF.setDate(null);
				checkBoxEhCNPJ.setSelected(false);
				btnConfirmar.setEnabled(false);
				btnCadastrar.setEnabled(true);
				btnAlterar.setEnabled(true);
				btnConsultar.setEnabled(true);
				btnCancelar.setEnabled(false);
			}
		});
		btnCancelar.setBounds(200, 187, 89, 23);
		panel.add(btnCancelar);

		btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		btnNewButton.setBounds(316, 7, 69, 23);
		panel.add(btnNewButton);

		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String identificacao = JOptionPane.showInputDialog(null, "Insira a identificação do Contribuinte:",
						"Consulta de Cadastro", JOptionPane.INFORMATION_MESSAGE);
				Contribuinte consultar = MapeadorContribuinte.getInstancia().get(identificacao);
				if (consultar != null) {
					textFieldIdentificacao.setText(consultar.getIdentificacao());
					textFieldNome.setText(consultar.getNome());
					textFieldEmail.setText(consultar.getEmail());
					
					dateChooserDNF.setDate(consultar.getDtCadastroChat());
					btnConfirmar.setEnabled(false);
					btnCancelar.setEnabled(true);
					btnCadastrar.setEnabled(false);
					btnAlterar.setEnabled(false);
					btnConsultar.setEnabled(false);
					estado = 3;
				} else {
					JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnConsultar.setBounds(200, 155, 89, 23);
		panel.add(btnConsultar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String identificacao = JOptionPane.showInputDialog(null, "Insira a identificação do contribuinte:",
						"Alteracao de Cadastro", JOptionPane.INFORMATION_MESSAGE);
				Contribuinte consultar = MapeadorContribuinte.getInstancia().get(identificacao);
				if (consultar != null) {
					textFieldIdentificacao.setText(consultar.getIdentificacao());
					textFieldNome.setText(consultar.getNome());
					textFieldEmail.setText(consultar.getEmail());
					
					dateChooserDNF.setDate(consultar.getDtCadastroChat());
					textFieldNome.setEnabled(true);
					textFieldEmail.setEnabled(true);
					
					dateChooserDNF.setEnabled(true);
					btnConfirmar.setEnabled(true);
					btnCancelar.setEnabled(true);
					btnCadastrar.setEnabled(false);
					btnAlterar.setEnabled(false);
					btnConsultar.setEnabled(false);
					estado = 2;
				} else {
					JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAlterar.setBounds(112, 187, 78, 23);
		panel.add(btnAlterar);
		// setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
