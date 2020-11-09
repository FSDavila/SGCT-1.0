package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import controller.ControllerPCDA;
import controller.ControllerPagamento;
import model.CDA;
import model.Contribuinte;
import model.PCDA;
import model.SITUACAO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import persistance.MapeadorPCDA;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;

public class TelaParcelamento extends JFrame {
	
	private static TelaParcelamento instancia;

	private double valorParcela;
	private double valorTotal;
	private int tipoImposto;
	private Calendar dtVencimento = java.util.Calendar.getInstance();
	
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
		btnVoltar.setBounds(499, 10, 75, 23);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		contentPane.setLayout(null);
		
		
		list.setVisibleRowCount(30);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {" - "};
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
		
		//btnBuscarCDAs.doClick();
		btnBuscarCDAs.setBounds(112, 25, 99, 19);
		btnBuscarCDAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //buscar cdas
				buscarCdas();
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
				btnSimularParcelamento.setEnabled(false);
				btnBuscarCDAs.setEnabled(true);
				btnConfirmarParcelamento.setEnabled(false);
				textFieldIdentificacao.setText("");
				textFieldParcelas.setText("");
				cdasTabela = new ArrayList<CDA>();
				elegivel =  false;
				counters = new int[6]; 
				list.setModel(new AbstractListModel() {
					String[] values = new String[] {" - "};
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
				table.setModel(new DefaultTableModel(
						new Object[][] {
							{" - ", " - ", " - "},
						},
						new String[] {
								"N\u00BA Parcela", "Data de Vencimento", "Valor"
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
			public void actionPerformed(ActionEvent arg0) { //simular parcelamento
				simularParcelamento();
			}
		});
		btnSimularParcelamento.setEnabled(false);
		btnSimularParcelamento.setBounds(61, 263, 242, 23);
		contentPane.add(btnSimularParcelamento);
		btnConfirmarParcelamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //confirmar parcelamento
				
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
				"N\u00BA Parcela", "Data de Vencimento", "Valor"
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
	//metodos pra cada actionperformed
	public void buscarCdas() {
		JOptionPane.showMessageDialog(null, "Buscando CDAs...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		if (MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText()) != null) {
			if (MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText()).getParcelamentos().size() < 3) { // ja tem 3 parcelamentos
			Contribuinte titular = MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText());
			if (titular.getCDAs().size() < 3) {
				JOptionPane.showMessageDialog(null,
						"O contribuinte selecionado não possui CDAs elegíveis para parcelamento.", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				for(CDA cdaCliente : titular.getCDAs()) {
					cdaCliente = MapeadorCDA.getInstancia().get(cdaCliente.getNCDA());
				}
				for (CDA cda : titular.getCDAs()) {
					System.out.println("cod situacao da " + cda.getNCDA() + " " + cda.getSituacaoCDA());
					if (cda.getSituacaoCDA() == SITUACAO.EMABERTO) { // apenas cdas em aberto
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
		} else {
			JOptionPane.showMessageDialog(null,
					"Contribuinte ja possui o maximo de 3 parcelamentos!", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
		}
		} else {
			JOptionPane.showMessageDialog(null,
					"Contribuinte nao encontrado no cadastro.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE); 
	}	
	}
	
	public void simularParcelamento() {
		if (list.getSelectedValuesList().size() > 2) { // pelo menos 3 cdas selecionadas
			try {
				counters = new int[6]; // reseta os contadores para verificarmos os tipos de imposto das cdas
										// selecionadas e elegiveis

				ArrayList<CDA> cdas = new ArrayList<>();
				List<String> strCdas = list.getSelectedValuesList();
				for (String strCDA : strCdas) {
					String str = strCDA.substring(0, 6);
					CDA procurada = MapeadorCDA.getInstancia().get(Integer.parseInt(str));
					cdas.add(procurada);
					int tipoImposto = procurada.getTipoImposto().getCodImposto();
					counters[tipoImposto - 1]++;
				}

				int impostoSelecionadoVlr = 0;
				boolean impostoSelecionado = false;

				boolean impostoOk = true;
				boolean parcelasOk = false;

				if (Integer.parseInt(textFieldParcelas.getText()) > 2) { // pelo menos 3 parcelas
					parcelasOk = true;
					for (int i = 0; i < counters.length; i++) {
						if (!impostoSelecionado && counters[i] > 0) {
							impostoSelecionadoVlr = i;
							impostoSelecionado = true;
						} else if (counters[i] > 0) { // se qualquer outro contador fora o selecionado tiver
														// acima de zero eh porque selecionaram mais de 1 tipo
														// de cda
							impostoOk = false;
							JOptionPane.showMessageDialog(null,
									"Existem CDAs de tipos diferentes de Imposto selecionadas. Selecione CDAs de apenas 1 (um) tipo de Imposto.",
									"Aviso", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Insira número de parcelas igual ou superior a 3 e inferior ao número maximo permitido para o tipo de Imposto.",
							"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}

				if (impostoOk && parcelasOk) {
					int maxParcelasParaTipo = 0;

					switch (impostoSelecionadoVlr + 1) { // +1 porque ele eh um indice de array - verifica qual
															// tipo de imposto eh e aplica o maximo de parcelas
															// na variavel de verificacao
					case 1:
						maxParcelasParaTipo = 60; // 60 para ICMS
						tipoImposto = 1;
						break;
					case 2:
						maxParcelasParaTipo = 60; // ICMS Simples
						tipoImposto = 2;
						break;
					case 3:
						maxParcelasParaTipo = 24; // ITCMD
						tipoImposto = 3;
						break;
					case 4:
						maxParcelasParaTipo = 48; // multa FATMA
						tipoImposto = 4;
						break;
					case 5:
						maxParcelasParaTipo = 48; // multa PM Ambiental
						tipoImposto = 5;
						break;
					case 6:
						maxParcelasParaTipo = 48; // multa Tribunal de Contas
						tipoImposto = 6;
						break;
					}

					if (Integer.parseInt(textFieldParcelas.getText()) <= maxParcelasParaTipo) { // verifica se o numero de parcelas esta ok
						// deu certo
						btnSimularParcelamento.setEnabled(false);
						btnConfirmarParcelamento.setEnabled(true);
						cdasTabela = cdas; // armazena as cdas a serem parceladas na variavel que sera usada pra
											// gerar a pcda no final do processo
						String[][] dadosTabela = new String[Integer.parseInt(textFieldParcelas.getText())][3];

						valorTotal = 0.0; // valor total parcelado

						for (CDA cda : cdas) {
							valorTotal += cda.getValor();
						}

						valorParcela = valorTotal / Integer.parseInt(textFieldParcelas.getText());
						// valorParcela = Math.round(100 * (valorTotal /
						// Integer.parseInt(textFieldParcelas.getText()))) / 100; valor da parcela

						dtVencimento.setTime(java.util.Calendar.getInstance().getTime());
						dtVencimento.add(Calendar.DAY_OF_MONTH, 5); // coloca o vencimento para 5 dias apos o
																	// dia atual

						int dia1 = dtVencimento.get(Calendar.DAY_OF_MONTH);
						int mes1 = dtVencimento.get(Calendar.MONTH) + 1;
						int ano1 = dtVencimento.get(Calendar.YEAR);
						// monta a primeira parcela como modelo para as demais parcelas
						dadosTabela[0][0] = "1";
						dadosTabela[0][1] = dia1 + "/" + mes1 + "/" + ano1;
						dadosTabela[0][2] = Double.toString(valorParcela);

						for (int i = 1; i < dadosTabela.length; i++) {
							dtVencimento.add(Calendar.MONTH, 1); // soma 1 mes para os subsequentes
							int dia = dtVencimento.get(Calendar.DAY_OF_MONTH);
							int mes = dtVencimento.get(Calendar.MONTH) + 1; // +1 porque os meses no Calendar
																			// comecam em 0
							int ano = dtVencimento.get(Calendar.YEAR);

							dadosTabela[i][0] = Integer.toString(i + 1);
							dadosTabela[i][1] = dia + "/" + mes + "/" + ano;
							dadosTabela[i][2] = Double.toString(valorParcela); // insere valor da parcela na
																				// tabela
						}

						table.setModel(new DefaultTableModel(dadosTabela,
								new String[] { "N\u00BA Parcela", "Data de Vencimento", "Valor" }));

						estado = 2;
					} else {
						JOptionPane.showMessageDialog(null,
								"Insira número de parcelas inferior ao número maximo permitido para o tipo de Imposto.",
								"Aviso", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (NumberFormatException k) {
				JOptionPane.showMessageDialog(null, "Informe um numero de parcelas válido.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione pelo menos 3 CDAs de um mesmo tipo de imposto.",
					"Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void confirmarParcelamento() {
		String chrVlr = Double.toString(valorTotal).substring(0, 2);
		double random = Math.random();
		String rng = Double.toString(random).substring(0, 1);
		String id = "2" + tipoImposto + "0" + textFieldParcelas.getText() + chrVlr + rng;
		String msg = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 5);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int ano = calendar.get(Calendar.YEAR);
		PCDA parcelamento = ControllerPCDA.getInstancia().geraParcelamento(Integer.parseInt(textFieldParcelas.getText()), MapeadorContribuinte.getInstancia().get(textFieldIdentificacao.getText()), Integer.parseInt(id), tipoImposto, cdasTabela, calendar.getTime());
		try {
			MapeadorCDA.getInstancia().persist();
			MapeadorContribuinte.getInstancia().persist();
			} catch(FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}
		msg = "Parcelamento gerado com sucesso - PCDA: " + parcelamento.getIdentificacao() + " - PDF da primeira parcela gerado localmente com Data de Vencimento: " + dia + "/" + mes + "/" + ano;
		JOptionPane.showMessageDialog(null, msg ,
				"Aviso", JOptionPane.INFORMATION_MESSAGE);
		ControllerPagamento.getInstancia().emitirBoletoPgtoParcela(Integer.parseInt(id), 1, calendar.getTime());
	}
}
