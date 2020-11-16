package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import controller.ControllerPagamento;
import model.CDA;
import model.Contribuinte;
import model.PCDA;
import model.Parcela;
import model.SITUACAO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import persistance.MapeadorPCDA;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class TelaEmissaoBoleto extends JFrame {
	
	private static TelaEmissaoBoleto instancia;

	private JPanel contentPane;
	private JTextField textFieldIdentificacao;
	private JTextField textFieldTitulo;
	
	private JDateChooser dateChooserVencimento = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
	
	private JCheckBox chckbxCNPJ = new JCheckBox("CNPJ");
	
	private JRadioButton rdbtnPagamentoIntegral = new JRadioButton("Pagamento Integral");
	private JRadioButton rdbtnParcelamento = new JRadioButton("Parcelamento");

	private JLabel labelTitularValue = new JLabel("-");
	private JLabel labelDocumentoValue = new JLabel("-");
	private JLabel labelValorValue = new JLabel("-");
	
	private JButton btnSalvar = new JButton("Confirmar e Salvar");
	private JButton btnGerarBoleto = new JButton("Gerar Boleto");
	
	private JComboBox comboBox = new JComboBox();
	
	private int estado = 0; // 0 = inicial, 1 = pgto integral, 2 = pgto parcelamento, 3 = escolhendo parcela


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEmissaoBoleto frame = new TelaEmissaoBoleto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static TelaEmissaoBoleto getInstancia() {
		if (instancia == null) {
			instancia = new TelaEmissaoBoleto();
		}
		return instancia;
	}

	/**
	 * Create the frame.
	 */
	public TelaEmissaoBoleto() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelIdentificacao = new JLabel("Identifica\u00E7\u00E3o do Contribuinte");
		labelIdentificacao.setBounds(10, 32, 192, 14);
		contentPane.add(labelIdentificacao);
		
		textFieldIdentificacao = new JTextField();
		textFieldIdentificacao.setBounds(10, 46, 141, 20);
		contentPane.add(textFieldIdentificacao);
		textFieldIdentificacao.setColumns(10);
		
		JLabel labelTitulo = new JLabel("n\u00BA do T\u00EDtulo");
		labelTitulo.setBounds(10, 72, 97, 14);
		contentPane.add(labelTitulo);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBounds(10, 86, 86, 20);
		contentPane.add(textFieldTitulo);
		
		dateChooserVencimento.setBounds(10, 228, 108, 20);
		contentPane.add(dateChooserVencimento);
		

		btnGerarBoleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gerarBoleto();
			}
		});
		btnGerarBoleto.setBounds(128, 227, 108, 23);
		contentPane.add(btnGerarBoleto);
		
		JLabel labelDataVencimento = new JLabel("Data de Vencimento");
		labelDataVencimento.setBounds(10, 214, 108, 14);
		contentPane.add(labelDataVencimento);
		
		chckbxCNPJ.setBounds(153, 45, 97, 23);
		contentPane.add(chckbxCNPJ);
		
		rdbtnParcelamento.setBounds(6, 139, 109, 23);
		contentPane.add(rdbtnParcelamento);		
		
		rdbtnPagamentoIntegral.setBounds(6, 113, 196, 23);
		contentPane.add(rdbtnPagamentoIntegral);
		
		ButtonGroup btnGrupoPagamento =	new ButtonGroup();
		btnGrupoPagamento.add(rdbtnPagamentoIntegral);
		btnGrupoPagamento.add(rdbtnParcelamento);
		
		JLabel labelDocumento = new JLabel("Documento:");
		labelDocumento.setBounds(314, 11, 97, 14);
		contentPane.add(labelDocumento);
		labelDocumentoValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		labelDocumentoValue.setBounds(296, 32, 87, 14);
		contentPane.add(labelDocumentoValue);
		
		JLabel labelTitular = new JLabel("Nome do Titular:");
		labelTitular.setBounds(298, 72, 99, 14);
		contentPane.add(labelTitular);
		
		JLabel labelValor = new JLabel("Valor:");
		labelValor.setBounds(320, 128, 46, 14);
		contentPane.add(labelValor);
		labelTitularValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		labelTitularValue.setBounds(240, 89, 195, 14);
		contentPane.add(labelTitularValue);
		labelValorValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelValorValue.setBounds(314, 143, 46, 14);
		contentPane.add(labelValorValue);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmarESalvar();
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(264, 228, 160, 23);
		contentPane.add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				labelDocumentoValue.setText("-"); // retorna tudo ao estado inicial
				labelValorValue.setText("-");
				labelTitularValue.setText("-");
				textFieldTitulo.setText("");
				textFieldIdentificacao.setText("");
				dateChooserVencimento.setDate(null);
				chckbxCNPJ.setText("");
				textFieldTitulo.setEnabled(true);
				textFieldIdentificacao.setEnabled(true);
				dateChooserVencimento.setEnabled(true);
				chckbxCNPJ.setEnabled(true);
				rdbtnPagamentoIntegral.setEnabled(true);
				rdbtnParcelamento.setEnabled(true);
				btnGerarBoleto.setEnabled(true);
				btnSalvar.setEnabled(false);
				estado = 0;
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		btnVoltar.setBounds(10, 7, 89, 23);
		contentPane.add(btnVoltar);
		
		
		comboBox.setEnabled(false);
		comboBox.setMaximumRowCount(60);
		comboBox.setBounds(10, 181, 141, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Parcela:");
		lblNewLabel.setBounds(10, 168, 71, 14);
		contentPane.add(lblNewLabel);
	}
	
	public void gerarBoleto() {
		if (ControllerPagamento.getInstancia().verificaDataVencimento(dateChooserVencimento.getDate())) { // verifica a data de vencimento
			if (rdbtnPagamentoIntegral.isSelected()) { // pgto integral
				JOptionPane.showMessageDialog(null, "Efetuando busca...", "Aviso", JOptionPane.WARNING_MESSAGE);
				try {
					Contribuinte devedor = MapeadorContribuinte.getInstancia()
							.get(textFieldIdentificacao.getText());
					CDA divida = MapeadorCDA.getInstancia().get(Integer.parseInt(textFieldTitulo.getText()));
					if(divida.getSituacaoCDA() == SITUACAO.EMABERTO) {

					labelDocumentoValue.setText(Integer.toString(divida.getNCDA()));
					labelValorValue.setText(Double.toString(divida.getValor()));
					labelTitularValue.setText(devedor.getNome());
					JOptionPane.showMessageDialog(null, "Titulo encontrado!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					btnSalvar.setEnabled(true);

					textFieldTitulo.setEnabled(false);
					textFieldIdentificacao.setEnabled(false);
					dateChooserVencimento.setEnabled(false);
					chckbxCNPJ.setEnabled(false);
					rdbtnPagamentoIntegral.setEnabled(false);
					rdbtnParcelamento.setEnabled(false);
					btnGerarBoleto.setEnabled(false);

					estado = 1; // pronto pra emitir boleto de pgto integral

					}
				} catch (NullPointerException k) {
					JOptionPane.showMessageDialog(null, "Não foi possível encontrar o número do título ou o contribuinte.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
			if (rdbtnParcelamento.isSelected()) { // pgto parcela
				if(ControllerPagamento.getInstancia().verificaDataVencimento(dateChooserVencimento.getDate())) {
				JOptionPane.showMessageDialog(null, "Efetuando busca...", "Aviso", JOptionPane.WARNING_MESSAGE);
				try {
					Contribuinte devedor = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
					PCDA parcelamento = MapeadorPCDA.getInstancia().get(Integer.parseInt(textFieldTitulo.getText()));
					System.out.println(devedor.getNome());
					System.out.println(parcelamento.getIdentificacao());
					Parcela[] parcelas = parcelamento.getParcelas();

					labelDocumentoValue.setText(Integer.toString(parcelamento.getIdentificacao()));
					labelValorValue.setText(Double.toString(parcelamento.getParcelas()[0].getValorParcela()));
					labelTitularValue.setText(devedor.getNome());
					JOptionPane.showMessageDialog(null, "Titulo encontrado!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					btnSalvar.setEnabled(true);

					textFieldTitulo.setEnabled(false);
					textFieldIdentificacao.setEnabled(false);
					dateChooserVencimento.setEnabled(false);
					chckbxCNPJ.setEnabled(false);
					rdbtnPagamentoIntegral.setEnabled(false);
					rdbtnParcelamento.setEnabled(false);
					btnGerarBoleto.setEnabled(false);
					
					String[] parcelasStr = new String[parcelas.length];
					for (int i = 0; i < parcelasStr.length; i++){
						parcelasStr[i] = Integer.toString(parcelas[i].getnParcela());
					}
					
					DefaultComboBoxModel dml= new DefaultComboBoxModel();
					for (int i = 0; i < parcelasStr.length; i++) {
					  dml.addElement(parcelasStr[i]);
					}

					comboBox.setModel(dml);
					comboBox.setEnabled(true);
					estado = 3; // pronto pra confirmar escolha de parcela

				} catch (NullPointerException k) {
					JOptionPane.showMessageDialog(null, "Não foi possível encontrar o número do título ou o contribuinte.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} catch (NumberFormatException num) {
					JOptionPane.showMessageDialog(null, "Digite o numero do documento do contribuinte.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
				/*
				} else {
					JOptionPane.showMessageDialog(null, "Insira data de vencimento no mês vigente.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} */


		}
		else {
			JOptionPane.showMessageDialog(null, "Favor inserir dia de vencimento no mês/ano vigentes.", "Aviso", JOptionPane.ERROR_MESSAGE);
		}
			}
		}
	}
	
	public void confirmarESalvar() {
		if(estado == 1) {// emite boleto de pagamento integral com todas as informacoes enviadas
			Contribuinte devedor = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
			CDA divida = MapeadorCDA.getInstancia().get(Integer.parseInt(textFieldTitulo.getText()));
			ControllerPagamento.getInstancia().emitirBoletoPgtoIntegral(dateChooserVencimento.getDate(), divida.getNCDA(), divida.getValor(), devedor.getNome(), devedor.getIdentificacao());
			JOptionPane.showMessageDialog(null, "Boleto gerado com sucesso e salvo localmente na pasta do aplicativo!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(estado == 3) { //parcela especfica
			Contribuinte devedor = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
			PCDA parcelamento = MapeadorPCDA.getInstancia().get(Integer.parseInt(textFieldTitulo.getText()));
			int integer = comboBox.getSelectedIndex();
			ControllerPagamento.getInstancia().emitirBoletoPgtoParcela(Integer.parseInt(textFieldTitulo.getText()), integer, dateChooserVencimento.getDate());
			JOptionPane.showMessageDialog(null, "Boleto da parcela gerado com sucesso e salvo localmente na pasta do aplicativo!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
		
		labelDocumentoValue.setText("-"); // retorna tudo ao estado inicial
		labelValorValue.setText("-");
		labelTitularValue.setText("-");
		textFieldTitulo.setText("");
		textFieldIdentificacao.setText("");
		dateChooserVencimento.setDate(null);
		chckbxCNPJ.setText("");
		textFieldTitulo.setEnabled(true);
		textFieldIdentificacao.setEnabled(true);
		dateChooserVencimento.setEnabled(true);
		chckbxCNPJ.setEnabled(true);
		rdbtnPagamentoIntegral.setEnabled(true);
		rdbtnParcelamento.setEnabled(true);
		btnGerarBoleto.setEnabled(true);
		btnSalvar.setEnabled(false);
		estado = 0;
	}
}
