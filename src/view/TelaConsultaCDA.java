package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.CDA;
import model.PCDA;

import javax.swing.UIManager;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import persistance.MapeadorPCDA;

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
	
	private JRadioButton rdbtnPCDA = new JRadioButton("pCDA");
	private JRadioButton rdbtnCDA = new JRadioButton("CDA");
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
		setBounds(100, 100, 550, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCDA = new JLabel("CDA / pCDA:");
		labelCDA.setBounds(10, 14, 80, 14);
		contentPane.add(labelCDA);

		textFieldCDA = new JTextField();
		textFieldCDA.setBounds(95, 11, 152, 20);
		textFieldCDA.setColumns(10);
		contentPane.add(textFieldCDA);
		
		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(10, 75, 514, 371);
		scrollPaneTabela.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		tabelaResultados = new JTable();
		tabelaResultados.setBounds(16, 323, 552, 194);
		tabelaResultados.setModel(new DefaultTableModel(
			new Object[][] {
				{" - ", " - ", " - "},
			},
			new String[] {
					 "nº Titulo", "Tipo Imposto", "Valor", "Status" 
			}
		));
		
		scrollPaneTabela.setViewportView(tabelaResultados);
		contentPane.add(scrollPaneTabela);


		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(257, 35, 100, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnCDA.isSelected()) {
					String nCDA = textFieldCDA.getText();
					String id = textFieldID.getText();
					if (!nCDA.equals("") && !id.equals("")) {
						JOptionPane.showMessageDialog(null, "Favor usar apenas um criterio para pesquisa!");
					} else if (!nCDA.equals("") && id.equals("")) {
						try {
							CDA procurada = MapeadorCDA.getInstancia().get(Integer.parseInt(nCDA));
							tabelaResultados.setModel(new DefaultTableModel(
									new Object[][] { { procurada.getNCDA(), procurada.getTipoImposto(),
											procurada.getValor(), procurada.getSituacaoCDA() }, },
									new String[] { "nº Titulo", "Tipo Imposto", "Valor", "Status"  }));
							JOptionPane.showMessageDialog(null, "Consulta efetuada.");
						} catch (NullPointerException k) {
							JOptionPane.showMessageDialog(null, "Informe um numero de CDA Existente.", "Aviso",
									JOptionPane.WARNING_MESSAGE);
						}

					} else if (nCDA.equals("") && !id.equals("")) {
						try {
							ArrayList<CDA> cdas = MapeadorContribuinte.getInstancia().get(id).getCDAs();
							String[][] dados = new String[cdas.size()][4];
							for (int i = 0; i < cdas.size(); i++) {
									dados[i][0] = Integer.toString(cdas.get(i).getNCDA());
									dados[i][1] = cdas.get(i).getTipoImposto().getTipoAsString();
									dados[i][2] = Double.toString(cdas.get(i).getValor());
									dados[i][3] = Integer.toString(cdas.get(i).getSituacaoCDA().getCodCDA());
							}
							if (cdas != null) {
								tabelaResultados.setModel(new DefaultTableModel(dados, new String[] { "nº Titulo", "Tipo Imposto", "Valor", "Status"  }));
								JOptionPane.showMessageDialog(null, "Consulta efetuada.");
							}
						} catch (NullPointerException k) {
							JOptionPane.showMessageDialog(null, "Informe um numero de CPF / CNPJ Valido.", "Aviso",
									JOptionPane.WARNING_MESSAGE);
						}

					}
				}
				if(rdbtnPCDA.isSelected()) { // pcda
					String pCDA = textFieldCDA.getText();
					String id = textFieldID.getText();
					if (!pCDA.equals("") && !id.equals("")) {
						JOptionPane.showMessageDialog(null, "Favor usar apenas um criterio para pesquisa!");
					} else if (!pCDA.equals("") && id.equals("")) {
						try {
							PCDA procurada = MapeadorPCDA.getInstancia().get(Integer.parseInt(pCDA));
							tabelaResultados.setModel(new DefaultTableModel(
									new Object[][] { { Integer.toString(procurada.getIdentificacao()), procurada.getTipoImposto(),
										Double.toString(procurada.getValor()), Integer.toString(procurada.getParcelas().length) }, },
									new String[] { "nº Titulo", "Tipo Imposto", "Valor", "nº Parcelas"}));
							JOptionPane.showMessageDialog(null, "Consulta efetuada.");
						} catch (NullPointerException k) {
							JOptionPane.showMessageDialog(null, "Informe um numero de CDA Existente.", "Aviso",
									JOptionPane.WARNING_MESSAGE);
						}

					} else if (pCDA.equals("") && !id.equals("")) {
						try {
							ArrayList<PCDA> parcelamentos = MapeadorContribuinte.getInstancia().get(id).getParcelamentos();
							String[][] dados = new String[parcelamentos.size()][4];
							for (int i = 0; i < parcelamentos.size(); i++) {
									dados[i][0] = Integer.toString(parcelamentos.get(i).getIdentificacao());
									dados[i][1] = parcelamentos.get(i).getTipoImposto().getTipoAsString();
									dados[i][2] = Double.toString(parcelamentos.get(i).getValor());
									dados[i][3] = Integer.toString(parcelamentos.get(i).getParcelas().length);
							}
							if (parcelamentos != null) {
								tabelaResultados.setModel(new DefaultTableModel(dados, new String[] { "nº Titulo", "Tipo Imposto", "Valor", "nº Parcelas" }));
								JOptionPane.showMessageDialog(null, "Consulta efetuada.");
							}
						} catch (NullPointerException k) {
							JOptionPane.showMessageDialog(null, "Informe um numero de CPF / CNPJ Valido.", "Aviso",
									JOptionPane.WARNING_MESSAGE);
						}

					}
				}
			}
		});
		contentPane.add(btnNewButton);

		JLabel LabelCPF = new JLabel("CPF / CNPJ: ");
		LabelCPF.setBounds(10, 39, 80, 14);
		contentPane.add(LabelCPF);

		textFieldID = new JTextField();
		textFieldID.setBounds(95, 36, 152, 20);
		textFieldID.setColumns(10);
		contentPane.add(textFieldID);
		rdbtnCDA.setBounds(253, 10, 62, 23);
		contentPane.add(rdbtnCDA);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(444, 16, 80, 23);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				textFieldID.setText("");
				textFieldCDA.setText("");	
				rdbtnPCDA.setSelected(false);
				rdbtnCDA.setSelected(false);
				tabelaResultados.setModel(new DefaultTableModel(
						new Object[][] {
							{" - ", " - ", " - "},
						},
						new String[] {
								 "nº Titulo", "Tipo Imposto", "Valor" 
						}
					));	
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		contentPane.add(btnVoltar);
		rdbtnPCDA.setBounds(317, 10, 66, 23);
		contentPane.add(rdbtnPCDA);
		
		ButtonGroup btnGrupoTitulo = new ButtonGroup();
		btnGrupoTitulo.add(rdbtnCDA);
		btnGrupoTitulo.add(rdbtnPCDA);
		
		


	}
}
