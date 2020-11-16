package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import com.toedter.calendar.JDateChooser;

import controller.ControllerPagamento;
import model.PagamentoIntegral;
import model.PagamentoParcela;
import persistance.MapeadorPagamentoIntegral;

import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class TelaRelatorioPagamento extends JFrame {

	private JPanel contentPane;
	private JTable tabelaRelatorio;
	
	private static TelaRelatorioPagamento instancia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRelatorioPagamento frame = new TelaRelatorioPagamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaRelatorioPagamento getInstancia() {
        if (instancia == null) {
            instancia = new TelaRelatorioPagamento();
        }
        return instancia;
    }


	/**
	 * Create the frame.
	 */
	public TelaRelatorioPagamento() { 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		btnVoltar.setBounds(360, 11, 89, 23);
		contentPane.add(btnVoltar);

		JLabel labelRelatorioPagamento = new JLabel("Relat\u00F3rio de Pagamentos Efetuados");
		labelRelatorioPagamento.setHorizontalAlignment(SwingConstants.CENTER);
		labelRelatorioPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelRelatorioPagamento.setBounds(67, 26, 244, 22);
		contentPane.add(labelRelatorioPagamento);

		JRadioButton rdbtnPCDA = new JRadioButton("Parcela");
		rdbtnPCDA.setBounds(267, 68, 99, 23);
		contentPane.add(rdbtnPCDA);

		JRadioButton rdbtnCDA = new JRadioButton("CDA\r\n");
		rdbtnCDA.setBounds(182, 68, 53, 23);
		contentPane.add(rdbtnCDA);

		ButtonGroup btnGrupoTitulo = new ButtonGroup();
		btnGrupoTitulo.add(rdbtnCDA);
		btnGrupoTitulo.add(rdbtnPCDA);

		JDateChooser dateInicio = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateInicio.getCalendarButton().setToolTipText("Data Inicial\r\n");
		dateInicio.setBounds(32, 71, 110, 20);
		contentPane.add(dateInicio);

		JDateChooser dateFim = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateFim.getCalendarButton().setToolTipText("Data Final\r\n");
		dateFim.setBounds(32, 115, 110, 20);
		contentPane.add(dateFim);

		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(10, 141, 439, 215);
		scrollPaneTabela.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		tabelaRelatorio = new JTable();
		tabelaRelatorio.setBounds(32, 71, 110, 20);
		
		tabelaRelatorio.setModel(new DefaultTableModel(
				new Object[][] {
					{" - ", " - ", " - "},
				},
				new String[] {
						"nCDA", "Valor Pago", "Data do Pagamento" 
				}
			));
		
		scrollPaneTabela.setViewportView(tabelaRelatorio);
		contentPane.add(scrollPaneTabela);

		JButton btnGerar = new JButton("Gerar Relat\u00F3rio\r\n");
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Calendar cal = Calendar.getInstance();
				//cal.setTime(new Date());
				//cal.set(Calendar.MONTH, 6);
				//PagamentoIntegral pi = ControllerPagamento.getInstancia().geraPagamentoIntegral(111314, 6565.0, cal.getTime());
				
				Date dataInicial = dateInicio.getDate();
				Date dataFinal = dateFim.getDate();

				ArrayList<PagamentoIntegral> pagamentosIntegral = new ArrayList<>();
				ArrayList<PagamentoParcela> pagamentosParcelado = new ArrayList<>();

				if (dataInicial.equals(null) || dataFinal.equals(null)) {
					JOptionPane.showMessageDialog(null, "Selecione Uma Data Inicial e uma Data Final.", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else if (dataInicial.after(new Date(System.currentTimeMillis()))
						|| dataFinal.after(new Date(System.currentTimeMillis()))) {
					JOptionPane.showMessageDialog(null, "Não é possivel selecionar uma data posterior a data atual",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				} else if (!rdbtnCDA.isSelected() && !rdbtnPCDA.isSelected()) {
					JOptionPane.showMessageDialog(null, "Selecione um título para gerar o relatório", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (rdbtnCDA.isSelected()) {

						for (PagamentoIntegral pagInt : MapeadorPagamentoIntegral.getInstancia().getList()) {
							if (pagInt.getDataPagamento().compareTo(dataInicial)
									> pagInt.getDataPagamento().compareTo(dataFinal)) {
								pagamentosIntegral.add(pagInt);
							}

							if (pagInt.getDataPagamento().after(dataInicial)
									&& pagInt.getDataPagamento().before(dataFinal)) {
								pagamentosIntegral.add(pagInt);
							}
						}

						String[][] dados = new String[pagamentosIntegral.size()][3];
						Double valorTotal = 0.0;
						
						try {
							for (PagamentoIntegral pagI : pagamentosIntegral) {
								for (int i = 0; i < pagamentosIntegral.size(); i++) {

									dados[i][0] = Integer.toString(pagI.getnCDA());
									dados[i][1] = Double.toString(pagI.getValorPgto());
									dados[i][2] = pagI.getDataPagamento().toString();
									valorTotal += pagI.getValorPgto();

								}

							}
						} catch (ArrayIndexOutOfBoundsException ob) {
							JOptionPane.showMessageDialog(null,
									"Existe algum problema no arquivo de persistencia ou nao existem registros criados no banco de dados ate o momento.");
						}

						if (pagamentosIntegral != null) {
							tabelaRelatorio.setModel(new DefaultTableModel(dados,
									new String[] { "nCDA", "Valor Pago", "Data do Pagamento" }));
							JOptionPane.showMessageDialog(null, "Relatório Gerado");
						}

					} else if (rdbtnPCDA.isSelected()) {

						for (PagamentoParcela pagP : ControllerPagamento.getInstancia().listPagamentoParcela()) {
							if (pagP.getDataPagamento().equals(dataInicial)
									|| pagP.getDataPagamento().equals(dataFinal))
								pagamentosParcelado.add(pagP);

							if (pagP.getDataPagamento().after(dataInicial) && pagP.getDataPagamento().before(dataFinal))
								pagamentosParcelado.add(pagP);
						}

						String dados[][] = new String[pagamentosParcelado.size()][4];

						for (PagamentoParcela pagP : pagamentosParcelado) {
							for (int i = 0; i < pagamentosParcelado.size(); i++) {
								try {
								dados[i][0] = Integer.toString(pagP.getIdentificacaoPCDA());
								dados[i][1] = Integer.toString(pagP.getnParcela());
								dados[i][2] = Double.toString(pagP.getValorParcela());
								dados[i][3] = pagP.getDataPagamento().toString();
								} catch (ArrayIndexOutOfBoundsException ob) {
									JOptionPane.showMessageDialog(null, "Existe algum problema no arquivo de persistencia ou nao existem registros criados no banco de dados ate o momento.");
								}
							}
						}

						if (pagamentosParcelado != null) {
							tabelaRelatorio.setModel(new DefaultTableModel(dados, new String[] { "Numero pCDA",
									"Numero Parcela", "Valor Parcela", "Data do Pagamento" }));

							JOptionPane.showMessageDialog(null, "Relatório Gerado");
						}

					}

				}

			}
		});
		btnGerar.setBounds(182, 98, 160, 23);
		contentPane.add(btnGerar);
		
		JLabel lblNewLabel = new JLabel("Data Final");
		lblNewLabel.setBounds(29, 102, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDataInicial = new JLabel("Data Inicial");
		lblDataInicial.setBounds(29, 59, 77, 14);
		contentPane.add(lblDataInicial);
	}
}
