package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.CDA;

import javax.swing.UIManager;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class TelaConsultaCDA extends JFrame {

	private static TelaConsultaCDA instancia;

	private JPanel contentPane;
	private JTextField textFieldID;
	private JTextField textFieldCDA;
	private JTable tabelaResultados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsultaCDA frame = new TelaConsultaCDA();
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

	public static TelaConsultaCDA getInstancia() {
		if (instancia == null) {
			instancia = new TelaConsultaCDA();
		}
		return instancia;
	}

	public TelaConsultaCDA() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCDA = new JLabel("CDA / pCDA:");
		labelCDA.setBounds(47, 11, 84, 14);
		contentPane.add(labelCDA);

		textFieldCDA = new JTextField();
		textFieldCDA.setBounds(141, 8, 130, 20);
		textFieldCDA.setColumns(10);
		contentPane.add(textFieldCDA);

		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(281, 34, 121, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nCDA = textFieldCDA.getText();
				String id = textFieldID.getText();
				if (!nCDA.equals("") && !id.equals("")) {
					JOptionPane.showMessageDialog(null, "Favor usar apenas um criterio para pesquisa!");
				} else if (!nCDA.equals("") && id.equals("")) {
					try {
						CDA procurada = MapeadorCDA.getInstancia().get(Integer.parseInt(nCDA));
						tabelaResultados.setModel(new DefaultTableModel(
								new Object[][] {
										{ procurada.getNCDA(), procurada.getTipoImposto(), procurada.getValor() }, },
								new String[] { "nCDA", "Tipo Imposto", "Valor" }));
						JOptionPane.showMessageDialog(null, "Consulta efetuada.");
					} catch (NullPointerException k) {
						JOptionPane.showMessageDialog(null, "Informe um numero de CDA Existente.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}

				} else if (nCDA.equals("") && !id.equals("")) {
					try {
						ArrayList<CDA> cdas = MapeadorContribuinte.getInstancia().get(id).getCDAs();
						String[][] dados = new String[3][cdas.size()];
						for (CDA cda : cdas) {
							for (int i = 0; i < cdas.size(); i++) {
								dados[i][0] = Integer.toString(cda.getNCDA());
								dados[i][1] = Integer.toString(cda.getTipoImposto().getCodImposto());
								dados[i][2] = Double.toString(cda.getValor());
							}
						}
						if (cdas != null) {
							tabelaResultados.setModel(new DefaultTableModel(dados, // tabela com as cdas
									new String[] { "nCDA", "Tipo Imposto", "Valor" }));
							JOptionPane.showMessageDialog(null, "Consulta efetuada.");
						}
					} catch (NullPointerException k) {
						JOptionPane.showMessageDialog(null, "Informe um numero de CPF / CNPJ Valido.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		});
		contentPane.add(btnNewButton);

		JLabel LabelCPF = new JLabel("CPF / CNPJ: ");
		LabelCPF.setBounds(47, 36, 84, 14);
		contentPane.add(LabelCPF);

		textFieldID = new JTextField();
		textFieldID.setBounds(141, 35, 130, 20);
		textFieldID.setColumns(10);
		contentPane.add(textFieldID);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("CDA");
		rdbtnNewRadioButton_1.setBounds(277, 7, 68, 23);
		contentPane.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("pCDA");
		rdbtnNewRadioButton.setBounds(347, 7, 68, 23);
		contentPane.add(rdbtnNewRadioButton);

		tabelaResultados = new JTable();
		tabelaResultados.setEnabled(false);
		tabelaResultados.setRowSelectionAllowed(false);
		tabelaResultados.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "CDA / pCDA", "Tipo Imposto", "Valor" }));
		// tabelaResultados.getModel().
		tabelaResultados.setBorder(UIManager.getBorder("ComboBox.border"));
		// contentPane.add(tabelaResultados);
		tabelaResultados.setVisible(true);

	}
}
