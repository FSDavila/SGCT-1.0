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

import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

		JLabel labelRelatorioPagamento = new JLabel("Relat\u00F3rio de Pagamentos\r\n");
		labelRelatorioPagamento.setHorizontalAlignment(SwingConstants.CENTER);
		labelRelatorioPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelRelatorioPagamento.setBounds(75, 26, 201, 22);
		contentPane.add(labelRelatorioPagamento);

		JRadioButton rdbtnPCDA = new JRadioButton("pCDA\r\n");
		rdbtnPCDA.setBounds(267, 68, 58, 23);
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
		dateFim.setBounds(32, 95, 110, 20);
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
				Date dataInicial = dateInicio.getDate();
				Date dataFinal = dateFim.getDate();

				ArrayList<PagamentoIntegral> pagamentosIntegral = null;
				ArrayList<PagamentoParcela> pagamentosParcelado = null;

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

						for (PagamentoIntegral pagInt : ControllerPagamento.getInstancia().listPagamentoIntegral()) {
							if (pagInt.getDataPagamento().equals(dataInicial)
									|| pagInt.getDataPagamento().equals(dataFinal))
								pagamentosIntegral.add(pagInt);

							if (pagInt.getDataPagamento().after(dataInicial)
									&& pagInt.getDataPagamento().before(dataFinal))
								pagamentosIntegral.add(pagInt);
						}

						String[][] dados = new String[3][pagamentosIntegral.size()];
						Double valorTotal = null;

						for (PagamentoIntegral pagI : pagamentosIntegral) {
							for (int i = 0; i < pagamentosIntegral.size(); i++) {
								dados[i][0] = Integer.toString(pagI.getnCDA());
								dados[i][1] = Double.toString(pagI.getValorPgto());
								dados[i][2] = pagI.getDataPagamento().toString();

								valorTotal += pagI.getValorPgto();
							}
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

						String dados[][] = new String[4][pagamentosParcelado.size()];

						for (PagamentoParcela pagP : pagamentosParcelado) {
							for (int i = 0; i < pagamentosParcelado.size(); i++) {
								dados[i][0] = Integer.toString(pagP.getIdentificacaoPCDA());
								dados[i][1] = Integer.toString(pagP.getnParcela());
								dados[i][2] = Double.toString(pagP.getValorParcela());
								dados[i][3] = pagP.getDataPagamento().toString();
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
		btnGerar.setBounds(182, 98, 143, 23);
		contentPane.add(btnGerar);
	}
}
