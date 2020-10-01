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
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class TelaConsultaCDA extends JFrame {

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
	public TelaConsultaCDA() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 321, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane barraRolagem = new JScrollPane(tabelaResultados);
		contentPane.add(barraRolagem);
		
		JLabel labelCDA = new JLabel("n\u00BA CDA:");
		contentPane.add(labelCDA);
		
		textFieldCDA = new JTextField();
		textFieldCDA.setColumns(10);
		contentPane.add(textFieldCDA);
		
		JLabel LabelCPF = new JLabel("CPF / CNPJ");
		contentPane.add(LabelCPF);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		contentPane.add(textFieldID);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("CDA");
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("pCDA");
		contentPane.add(rdbtnNewRadioButton);
		
		tabelaResultados = new JTable();
		tabelaResultados.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"nCDA", "Tipo Imposto", "Valor"
			}
		));
		tabelaResultados.setColumnSelectionAllowed(true);
		tabelaResultados.setBorder(UIManager.getBorder("ComboBox.border"));
		tabelaResultados.setCellSelectionEnabled(true);
		contentPane.add(tabelaResultados);
		tabelaResultados.setVisible(true);
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nCDA = textFieldCDA.getText();
				String id = textFieldID.getText();
				if(!nCDA.equals("") && !id.equals("")) {
					JOptionPane.showMessageDialog(null, "Favor usar apenas um criterio para pesquisa!");
				}
				else if(!nCDA.equals("") && id.equals("")){
					CDA procurada = MapeadorCDA.getInstancia().get(Integer.parseInt(nCDA));
					if(procurada != null) {
						tabelaResultados.setModel(new DefaultTableModel(
								new Object[][] {
									{procurada.getNCDA(), procurada.getTipoImposto(), procurada.getValor()},
								},
								new String[] {
									"nCDA", "Tipo Imposto", "Valor"
								}
							));
						JOptionPane.showMessageDialog(null, "Consulta efetuada.");
					}
			
				}
				else if(nCDA.equals("") && !id.equals("")){
					try {
					CDA[] cdas = MapeadorContribuinte.getInstancia().get(id).getCDAs().toArray(new CDA[MapeadorContribuinte.getInstancia().get(id).getCDAs().size()]);
					String[][] dados = new String[3][cdas.length];
					for(int i = 0; i == cdas.length; i++ ) {
						dados[i][0] = Integer.toString(cdas[i].getNCDA());
						dados[i][1] = Integer.toString(cdas[i].getTipoImposto().getCodImposto());
						dados[i][2] = Double.toString(cdas[i].getValor());			
					}
					if(cdas != null) {
						tabelaResultados.setModel(new DefaultTableModel(
								dados, //tabela com as cdas
								new String[] {
									"nCDA", "Tipo Imposto", "Valor"
								}
							));
						JOptionPane.showMessageDialog(null, "Consulta efetuada.");
					}
					
				
				else {
					JOptionPane.showMessageDialog(null, "Existem erros de digitação ou os dados não existem. Verifique o campo digitado.");
				}
			} catch(Exception e) {
				
			}
		});
		contentPane.add(btnNewButton);
	}
}
