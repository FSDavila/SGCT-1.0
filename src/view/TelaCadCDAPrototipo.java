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
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDayChooser;

import controller.ControllerCDA;
import controller.ControllerContribuinte;
import model.CDA;
import model.Contribuinte;
import model.Imposto;
import model.SITUACAO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.awt.event.ActionEvent;

public class TelaCadCDAPrototipo extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNCDA;
	private JTextField textFieldID;
	private JTextField textFieldTipo;
	private JTextField textFieldValor;
	private JTextField textFieldSituacao;
	private JDateChooser dateChooserDNF;
	private JButton btnConfirmar;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	
	private Integer estado; // 0 = estado inicial, 1 = cadastrando, 2 = alterando, 3 = consultando
	
	private static TelaCadCDAPrototipo instancia;
	private JButton btnAlterar;
	private JButton btnConsultar;
	private JLabel lblNewLabel_2;
	private JTextField textFieldDescricao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadCDAPrototipo frame = new TelaCadCDAPrototipo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaCadCDAPrototipo getInstancia() {
        if (instancia == null) {
            instancia = new TelaCadCDAPrototipo();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaCadCDAPrototipo() {
		this.estado = 0;
		setBounds(100, 100, 423, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DD/MM");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldNCDA = new JTextField();
		textFieldNCDA.setEnabled(false);
		textFieldNCDA.setBounds(10, 29, 86, 20);
		panel.add(textFieldNCDA);
		textFieldNCDA.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("nCDA");
		lblNewLabel.setBounds(10, 11, 86, 14);
		panel.add(lblNewLabel);
		
		textFieldID = new JTextField();
		textFieldID.setEnabled(false);
		textFieldID.setColumns(10);
		textFieldID.setBounds(106, 29, 200, 20);
		panel.add(textFieldID);
		
		JLabel lblO = new JLabel("Identifica\u00E7\u00E3o Contribuinte");
		lblO.setBounds(106, 11, 145, 14);
		panel.add(lblO);
		
		JLabel lblO_1 = new JLabel("Cod. Imposto");
		lblO_1.setBounds(10, 138, 145, 14);
		panel.add(lblO_1);

		textFieldTipo = new JTextField();
		textFieldTipo.setEnabled(false);
		textFieldTipo.setColumns(10);
		textFieldTipo.setBounds(10, 156, 64, 20);
		panel.add(textFieldTipo);

		btnConfirmar = new JButton("OK");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (estado == 1) { //modo cadastrar ativado
					try {
						int nCDA = Integer.parseInt(textFieldNCDA.getText());
						double valor = Double.parseDouble(textFieldValor.getText());
						int codImposto = Integer.parseInt(textFieldSituacao.getText());
						int codSituacao = Integer.parseInt(textFieldTipo.getText());

						Date dataVencimento = dateChooserDNF.getDate();
						Contribuinte devedor = MapeadorContribuinte.getInstancia().get(textFieldID.getText());

						CDA nova = ControllerCDA.getInstancia().cadastraCDA(nCDA, valor, codImposto, dataVencimento,
								"test", devedor, codSituacao);

						if (nova != null) {
							try {
								MapeadorCDA.getInstancia().persist();
								devedor.getCDAs().add(nova); // adiciona nova CDA ao array de CDAs daquele contribuinte
								JOptionPane.showMessageDialog(null, "CDA cadastrada com sucesso!", "Aviso",
										JOptionPane.INFORMATION_MESSAGE);
								textFieldNCDA.setEnabled(false);
								textFieldID.setEnabled(false);
								textFieldTipo.setEnabled(false);
								textFieldValor.setEnabled(false);
								textFieldSituacao.setEnabled(false);
								textFieldDescricao.setEnabled(false);
								dateChooserDNF.setEnabled(false);
								textFieldNCDA.setText("");
								textFieldID.setText("");
								textFieldTipo.setText("");
								textFieldValor.setText("");
								textFieldSituacao.setText("");
								textFieldDescricao.setText("");
								dateChooserDNF.setDate(null);
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
						JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.",
								"Aviso", JOptionPane.WARNING_MESSAGE);
					}
				}
				if (estado == 2) { //modo alterar ativado
					
					CDA alterar = MapeadorCDA.getInstancia().get(Integer.parseInt(textFieldNCDA.getText()));
					alterar.setValor(Double.parseDouble(textFieldValor.getText()));
					alterar.setTipoImposto(Integer.parseInt(textFieldTipo.getText()));
					alterar.setSituacaoCDA(Integer.parseInt(textFieldSituacao.getText()));
					alterar.setDataVencimento(dateChooserDNF.getDate());
					alterar.setDescricao(textFieldDescricao.getText());
					
                    try {
                    	MapeadorCDA.getInstancia().persist();
                    } catch (FileNotFoundException ex) {
                        System.out.println("Favor verificar o arquivo de serializacao.");
                    }
					
					textFieldNCDA.setEnabled(false);
					textFieldID.setEnabled(false);
					textFieldTipo.setEnabled(false);
					textFieldValor.setEnabled(false);
					textFieldSituacao.setEnabled(false);
					textFieldDescricao.setEnabled(false);
					dateChooserDNF.setEnabled(false);
					textFieldNCDA.setText("");
					textFieldID.setText("");
					textFieldTipo.setText("");
					textFieldValor.setText("");
					textFieldSituacao.setText("");
					textFieldDescricao.setText("");
					dateChooserDNF.setDate(null);
					btnConfirmar.setEnabled(false);
					btnCadastrar.setEnabled(true);
					btnCancelar.setEnabled(false);
					btnAlterar.setEnabled(true);
					btnConsultar.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Dados da CDA atualizados com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);
					estado = 0;
				}
			}
		});
			
        
		btnConfirmar.setBounds(261, 201, 126, 23);
		panel.add(btnConfirmar);
		
		dateChooserDNF = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooserDNF.setBounds(187, 156, 108, 20);
		dateChooserDNF.setEnabled(false);;
		panel.add(dateChooserDNF);

		
		JLabel lblNewLabel_1 = new JLabel("Dt. Vencimento CDA");
		lblNewLabel_1.setBounds(187, 138, 148, 14);
		panel.add(lblNewLabel_1);
		
		textFieldValor = new JTextField();
		textFieldValor.setEnabled(false);
		textFieldValor.setColumns(10);
		textFieldValor.setBounds(88, 156, 86, 20);
		panel.add(textFieldValor);
		
		JLabel lblLogin = new JLabel("Valor");
		lblLogin.setBounds(88, 138, 86, 14);
		panel.add(lblLogin);
		
		textFieldSituacao = new JTextField();
		textFieldSituacao.setEnabled(false);
		textFieldSituacao.setColumns(10);
		textFieldSituacao.setBounds(10, 202, 64, 20);
		panel.add(textFieldSituacao);
		
		JLabel lblSenha = new JLabel("Cod. Situa\u00E7\u00E3o");
		lblSenha.setBounds(10, 184, 86, 14);
		panel.add(lblSenha);
		
		btnCadastrar = new JButton("Cadastrar"); // botao cadastrar
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldNCDA.setEnabled(true);
				textFieldID.setEnabled(true);
				textFieldTipo.setEnabled(true);
				textFieldValor.setEnabled(true);
				textFieldSituacao.setEnabled(true);
				textFieldDescricao.setEnabled(true);
				dateChooserDNF.setEnabled(true);
				btnConfirmar.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnCadastrar.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnConsultar.setEnabled(false);
				estado = 1;
			}
		});
		btnCadastrar.setBounds(10, 228, 123, 23);
		panel.add(btnCadastrar);
		
		btnCancelar = new JButton("Cancelar"); // botao cancelar operacao
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldNCDA.setEnabled(false);
				textFieldID.setEnabled(false);
				textFieldTipo.setEnabled(false);
				textFieldValor.setEnabled(false);
				textFieldSituacao.setEnabled(false);
				dateChooserDNF.setEnabled(false);
				textFieldDescricao.setEnabled(false);
				textFieldNCDA.setText("");
				textFieldID.setText("");
				textFieldTipo.setText("");
				textFieldValor.setText("");
				textFieldSituacao.setText("");
				textFieldDescricao.setText("");
				dateChooserDNF.setDate(null);
				btnConfirmar.setEnabled(false);
				btnCadastrar.setEnabled(true);
				btnCancelar.setEnabled(false);
				btnAlterar.setEnabled(true);
				btnConsultar.setEnabled(true);
				
				estado = 0;
			}
		});
		btnCancelar.setBounds(142, 201, 109, 23);
		panel.add(btnCancelar);
		
		btnAlterar = new JButton("Alterar"); // botao alterar
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nCDA = JOptionPane.showInputDialog(null, "Insira o numero da CDA desejada:", "Alteracao de CDA", JOptionPane.INFORMATION_MESSAGE);
				CDA alterar = MapeadorCDA.getInstancia().get(Integer.parseInt(nCDA));
				if(alterar != null){
					textFieldNCDA.setText(Integer.toString(alterar.getNCDA()));
					textFieldID.setText(alterar.getTitular().getIdentificacao());
					textFieldTipo.setText(Integer.toString(alterar.getTipoImposto().getCodImposto()));
					textFieldValor.setText(Double.toString(alterar.getValor()));
					textFieldSituacao.setText(Integer.toString(alterar.getSituacaoCDA().getCodCDA()));
					textFieldDescricao.setText(alterar.getDescricao());
					dateChooserDNF.setDate(alterar.getDataVencimento());
					textFieldTipo.setEnabled(true);
					textFieldValor.setEnabled(true);
					textFieldSituacao.setEnabled(true);
					textFieldDescricao.setEnabled(true);
					dateChooserDNF.setEnabled(true);
					btnConfirmar.setEnabled(true);
					btnCancelar.setEnabled(true);
					btnCadastrar.setEnabled(false);
					btnAlterar.setEnabled(false);
					btnConsultar.setEnabled(false);
					estado = 2;
				}
				else{
					JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAlterar.setBounds(143, 228, 108, 23);
		panel.add(btnAlterar);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(263, 228, 124, 23);
		panel.add(btnConsultar);
		
		lblNewLabel_2 = new JLabel("Detalhamento da CDA");
		lblNewLabel_2.setBounds(10, 60, 221, 14);
		panel.add(lblNewLabel_2);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setEnabled(false);
		textFieldDescricao.setBounds(10, 85, 377, 42);
		panel.add(textFieldDescricao);
		textFieldDescricao.setColumns(10);
		
	}
}
