package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import model.CDA;
import model.Contribuinte;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;

public class TelaParcelamento extends JFrame {
	
	private static TelaParcelamento instancia;
	
	private int estado = 0; // 0 = inicial, 1 = selecionando CDA e numero de parcelas, 2 = verificando se a simulacao esta OK, 3 = Parcelamento OK

	private JPanel contentPane;
	
	private JButton btnSimularParcelamento = new JButton("Simular Parcelamento");
	private JButton btnBuscarCDAs = new JButton("Buscar CDAs");
	private JButton btnConfirmarParcelamento = new JButton("Confirmar Parcelamento");
	
	private boolean elegivel =  false;
	
	private int[] counters = new int[6]; /* 	    
		ICMS(1), 
	    ICMSSIMPLES(2), 
	    ITCMD(3), 
	    FATMA(4), 
	    PMAMBIENTAL(5), 
	    TCE(6);  */
	
	private ArrayList<CDA> cdasTabela = new ArrayList<>(); //cdas que irao aparecer no jtable de selecao
	
	private JTable table;
	
	private JList<String> list = new JList<String>();
	
	private JTextField textFieldIdentificacao;
	private JTextField textFieldParcelas;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaParcelamento frame = new TelaParcelamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static TelaParcelamento getInstancia() {
		if (instancia == null) {
			instancia = new TelaParcelamento();
		}
		return instancia;
	}
	/**
	 * Create the frame.
	 */
	public TelaParcelamento() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 601);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(507, 10, 67, 23);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		contentPane.setLayout(null);
		
		
		list.setVisibleRowCount(30);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"CDA 1", "CDA 2", "CDA 3", "CDA 4", "CDA 5", "CDA 6", "CDA 7", "CDA 8", "CDA 9", "CDA 10", "CDA 11", "CDA 12", "CDA 13", "CDA 14", "CDA 15", "CDA 16"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 67, 552, 170);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(scrollPane);
		
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(15, 39, 451, 121);
		contentPane.add(btnVoltar);
		
		textFieldIdentificacao = new JTextField();
		textFieldIdentificacao.setBounds(16, 24, 86, 20);
		contentPane.add(textFieldIdentificacao);
		textFieldIdentificacao.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF / CNPJ");
		lblNewLabel.setBounds(16, 10, 86, 14);
		contentPane.add(lblNewLabel);
		
		
		btnBuscarCDAs.setBounds(112, 25, 99, 19);
		btnBuscarCDAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Buscando CDAs...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				if (MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText()) != null) {
					Contribuinte titular = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
					if (titular.getCDAs().size() < 3) {
						JOptionPane.showMessageDialog(null,
								"O contribuinte selecionado não possui CDAs elegíveis para parcelamento.", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (CDA cda : titular.getCDAs()) {
							if (cda.getSituacaoCDA().getCodCDA() == 1) { // apenas cdas em aberto
								int tipoImposto = cda.getTipoImposto().getCodImposto(); // pega o tipo do imposto para
																						// verificar condicoes
																						// posteriormente
								counters[tipoImposto - 1]++; // adiciona mais 1 no contador do tipo de cda em questao
								cdasTabela.add(cda); // insere na tabela que ira aparecer com as cdas para selecao
							}
						}
						for (int i = 0; i < counters.length; i++) {
							if (counters[i] > 2) { // pelo menos algum dos counters tem que estar acima de 3 para
													// considerar que existe algum parcelamento possivel
								elegivel = true;
							}
						}
					}
					if (elegivel) {
						String[] valores = new String[cdasTabela.size()];
						for (int i = 0; i < valores.length; i++) {
							CDA cdaAtual = cdasTabela.get(i);
							String valor = cdaAtual.getNCDA() + " - Imposto: " + cdaAtual.getTipoImposto().getTipoAsString() + " - Valor: "
									+ cdaAtual.getValor() + " - Data de Vencimento: " +  cdaAtual.getDataVencimento().getDate() + "/" + cdaAtual.getDataVencimento().getMonth() + "/" + cdaAtual.getDataVencimento().getYear();
							valores[i] = valor;
						}

						JOptionPane.showMessageDialog(null,
								"Listando CDAs, favor selecionar as CDAs a serem parceladas.", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
						list.setModel(new AbstractListModel() {
							String[] values = valores;

							public int getSize() {
								return values.length;
							}

							public Object getElementAt(int index) {
								return values[index];
							}
						});
						
						btnBuscarCDAs.setEnabled(false);
						textFieldParcelas.setEnabled(true);
						btnSimularParcelamento.setEnabled(true);
						
						estado = 1;//selecionando quais cdas parcelar e digitando numero de parcelas
						
					} else {
						JOptionPane.showMessageDialog(null,
								"Existe algum problema no cadastro ou não existem CDAs elegíveis.", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
			
		});
		btnBuscarCDAs.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btnBuscarCDAs);
		
		JLabel lblNewLabel_1 = new JLabel("Lista de CDAs em aberto (Segure a tecla Ctrl e selecione com o mouse as que deseja incluir no parcelamento):");
		lblNewLabel_1.setBounds(16, 55, 456, 11);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(lblNewLabel_1);
		
		JButton btnLimpar = new JButton("Limpar Tudo");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				estado = 0;
				textFieldIdentificacao.setText("");
				textFieldParcelas.setText("");
				cdasTabela = new ArrayList<CDA>();
				elegivel =  false;
				counters = new int[6]; 
				table.setModel(new DefaultTableModel(
						new Object[][] {
							{" - ", " - ", " - "},
						},
						new String[] {
							"Nº Parcela", "Valor", "Data de Vencimento"
						}
					));
				
				JOptionPane.showMessageDialog(null,
						"Todos os campos foram limpos.", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnLimpar.setBounds(313, 263, 114, 23);
		contentPane.add(btnLimpar);
		
		
		btnSimularParcelamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				counters = new int[6]; //reseta os contadores para verificarmos os tipos de imposto das cdas selecionadas e elegiveis
				
				ArrayList<CDA> cdas  = new ArrayList<>();
				List<String> strCdas = list.getSelectedValuesList();
				for(String strCDA : strCdas) {
					String str = strCDA.substring(0, 6);
					CDA procurada = MapeadorCDA.getInstancia().get(Integer.parseInt(str));
					cdas.add(procurada);
					int tipoImposto = procurada.getTipoImposto().getCodImposto();
					counters[tipoImposto - 1]++;
				}
				int impostoSelecionadoVlr = 0;
				boolean impostoSelecionado = false;
				if (Integer.parseInt(textFieldParcelas.getText()) > 2) { // pelo menos 3 parcelas
					for (int i = 0; i < counters.length; i++) {
						if (!impostoSelecionado && counters[i] > 0) {
							impostoSelecionadoVlr = i;
							impostoSelecionado = true;
						} else if (counters[i] > 0) { // se qualquer outro contador fora o selecionado tiver acima de
														// zero eh porque selecionaram mais de 1 tipo de cda
							JOptionPane.showMessageDialog(null,
									"Existem CDAs de tipos diferentes de Imposto selecionadas. Selecione CDAs de apenas 1 (um) tipo de Imposto.",
									"Aviso", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Insina número de parcelas igual ou superior a 3 e inferior ao número maximo permitido para o tipo de Imposto.",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
				int maxParcelasParaTipo = 0;
				
				switch(impostoSelecionadoVlr+1) { //+1 porque ele eh um indice de array - verifica qual tipo de imposto eh e aplica o maximo de parcelas na variavel de verificacao
				case 1:
					maxParcelasParaTipo  = 60; //60 para ICMS
					break;
				case 2:
					maxParcelasParaTipo = 60; // ICMS Simples
					break;
				case 3:
					maxParcelasParaTipo = 24; // ITCMD
					break;
				case 4:
					maxParcelasParaTipo = 48; // multa FATMA
					break;
				case 5:
					maxParcelasParaTipo = 48; // multa PM Ambiental
					break;
				case 6:
					maxParcelasParaTipo = 48; // multa Tribunal de Contas
					break;
				}
				
				if(Integer.parseInt(textFieldParcelas.getText()) < maxParcelasParaTipo) { // finalmente, compara se o numero de parcelas eh aceitavel pro tipo de imposto
					//deu certo
					btnSimularParcelamento.setEnabled(false);
					btnConfirmarParcelamento.setEnabled(true);
					cdasTabela = cdas; //armazena as cdas a serem parceladas na variavel que sera usada pra gerar a pcda no final do processo
					String[][] dadosTabela = new String[cdas.size()][3];
					for(CDA cda : cdas) {
						
					}
					estado = 2;
				} else {
					JOptionPane.showMessageDialog(null,
							"Insina número de parcelas inferior ao número maximo permitido para o tipo de Imposto.",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSimularParcelamento.setEnabled(false);
		btnSimularParcelamento.setBounds(61, 263, 242, 23);
		contentPane.add(btnSimularParcelamento);
		btnConfirmarParcelamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		btnConfirmarParcelamento.setBounds(16, 528, 183, 23);
		btnConfirmarParcelamento.setEnabled(false);
		contentPane.add(btnConfirmarParcelamento);
		
		
		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(16, 297, 552, 193);
		scrollPaneTabela.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		list.setLayoutOrientation(JList.VERTICAL);

		table = new JTable();
		table.setBounds(16, 323, 552, 194);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{" - ", " - ", " - "},
			},
			new String[] {
				"Nº Parcela", "Valor", "Data de Vencimento"
			}
		));
		
		scrollPaneTabela.setViewportView(table);
		contentPane.add(scrollPaneTabela);
		
		textFieldParcelas = new JTextField();
		textFieldParcelas.setEnabled(false);
		textFieldParcelas.setBounds(16, 263, 35, 23);
		contentPane.add(textFieldParcelas);
		textFieldParcelas.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Parcelas:");
		lblNewLabel_2.setBounds(16, 248, 54, 14);
		contentPane.add(lblNewLabel_2);
	}
}
