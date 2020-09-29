package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class TelaCadContribuinte extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		setBounds(100, 100, 421, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DD/MM");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 29, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Identifica\u00E7\u00E3o");
		lblNewLabel.setBounds(10, 11, 86, 14);
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(106, 29, 223, 20);
		panel.add(textField_1);
		
		JLabel lblO = new JLabel("Nome Contribuinte:");
		lblO.setBounds(106, 11, 145, 14);
		panel.add(lblO);
		
		JLabel lblO_1 = new JLabel("E-mail:");
		lblO_1.setBounds(10, 67, 145, 14);
		panel.add(lblO_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(10, 85, 223, 20);
		panel.add(textField_2);
		
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		JFormattedTextField frmtdtxtfldSadsad = new JFormattedTextField(format);
		frmtdtxtfldSadsad.setBounds(253, 85, 138, 20);
		panel.add(frmtdtxtfldSadsad);
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.setBounds(302, 169, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Data de Nascimento / Funda\u00E7\u00E3o:");
		lblNewLabel_1.setBounds(243, 67, 163, 14);
		panel.add(lblNewLabel_1);
	}
}
