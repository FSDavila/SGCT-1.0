package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaEmissaoBoleto extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdentificacao;
	private JTextField textFieldTitulo;

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
		
		JLabel labelIdentificacao = new JLabel("Identifica\u00E7\u00E3o do Contribuinte:");
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
		
		JDateChooser dateChooserVencimento = new JDateChooser();
		dateChooserVencimento.setBounds(10, 228, 108, 20);
		contentPane.add(dateChooserVencimento);
		
		JButton btnGerarBoleto = new JButton("Gerar Boleto");
		btnGerarBoleto.setBounds(128, 227, 108, 23);
		contentPane.add(btnGerarBoleto);
		
		JLabel labelDataVencimento = new JLabel("Data de Vencimento");
		labelDataVencimento.setBounds(10, 214, 108, 14);
		contentPane.add(labelDataVencimento);
		
		JCheckBox chckbxCNPJ = new JCheckBox("CNPJ");
		chckbxCNPJ.setBounds(153, 45, 97, 23);
		contentPane.add(chckbxCNPJ);
		
		JRadioButton rdbtnParcelamento = new JRadioButton("Parcelamento");
		rdbtnParcelamento.setBounds(6, 139, 109, 23);
		contentPane.add(rdbtnParcelamento);
		
		JRadioButton rdbtnPagamentoIntegral = new JRadioButton("Pagamento Integral");
		rdbtnPagamentoIntegral.setBounds(6, 113, 126, 23);
		contentPane.add(rdbtnPagamentoIntegral);
		
		JLabel labelDocumento = new JLabel("Documento:");
		labelDocumento.setBounds(314, 11, 97, 14);
		contentPane.add(labelDocumento);
		
		JLabel labelDocumentoValue = new JLabel("-");
		labelDocumentoValue.setBounds(324, 28, 87, 14);
		contentPane.add(labelDocumentoValue);
		
		JLabel labelTitular = new JLabel("Nome do Titular:");
		labelTitular.setBounds(306, 73, 99, 14);
		contentPane.add(labelTitular);
		
		JLabel labelValor = new JLabel("Valor:");
		labelValor.setBounds(317, 127, 46, 14);
		contentPane.add(labelValor);
		
		JLabel labelTitularValue = new JLabel("-");
		labelTitularValue.setBounds(324, 89, 46, 14);
		contentPane.add(labelTitularValue);
		
		JLabel labelValorValue = new JLabel("-");
		labelValorValue.setBounds(327, 143, 46, 14);
		contentPane.add(labelValorValue);
		
		JButton btnSalvar = new JButton("Confirmar e Salvar");
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(298, 228, 126, 23);
		contentPane.add(btnSalvar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		btnVoltar.setBounds(10, 7, 89, 23);
		contentPane.add(btnVoltar);
	}
}
