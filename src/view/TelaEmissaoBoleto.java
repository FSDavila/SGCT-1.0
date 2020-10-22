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

public class TelaEmissaoBoleto extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Identifica\u00E7\u00E3o do Contribuinte:");
		lblNewLabel.setBounds(10, 11, 192, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 25, 141, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNDoTtulo = new JLabel("n\u00BA do T\u00EDtulo");
		lblNDoTtulo.setBounds(10, 72, 97, 14);
		contentPane.add(lblNDoTtulo);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 86, 86, 20);
		contentPane.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 228, 108, 20);
		contentPane.add(dateChooser);
		
		JButton btnNewButton = new JButton("Gerar Boleto");
		btnNewButton.setBounds(128, 227, 108, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Data de Vencimento");
		lblNewLabel_2.setBounds(10, 214, 108, 14);
		contentPane.add(lblNewLabel_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("CNPJ");
		chckbxNewCheckBox.setBounds(153, 24, 97, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Parcelamento");
		rdbtnNewRadioButton.setBounds(6, 139, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Pagamento Integral");
		rdbtnNewRadioButton_1.setBounds(6, 113, 126, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
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
		
		JButton btnNewButton_1 = new JButton("Confirmar e Salvar");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(298, 228, 126, 23);
		contentPane.add(btnNewButton_1);
	}
}
