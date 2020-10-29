package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class TelaParcelamento extends JFrame {
	
	private static TelaParcelamento instancia;

	private JPanel contentPane;
	private JTextField textFieldIdentificacao;

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
		setBounds(100, 100, 511, 601);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(416, 11, 69, 23);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				TelaMenuPrincipal.getInstancia().setVisible(true);
			}
		});
		contentPane.setLayout(null);
		
		JList<String> list = new JList<String>();
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
		scrollPane.setBounds(10, 74, 476, 134);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(scrollPane);
		
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(15, 39, 451, 121);
		contentPane.add(btnVoltar);
		
		textFieldIdentificacao = new JTextField();
		textFieldIdentificacao.setBounds(10, 28, 86, 20);
		contentPane.add(textFieldIdentificacao);
		textFieldIdentificacao.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF / CNPJ");
		lblNewLabel.setBounds(10, 11, 97, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnBuscarCDAs = new JButton("Buscar CDAs");
		btnBuscarCDAs.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnBuscarCDAs.setBounds(106, 27, 97, 21);
		contentPane.add(btnBuscarCDAs);
		
		JLabel lblNewLabel_1 = new JLabel("Lista de CDAs em aberto (Segure a tecla Ctrl e selecione com o mouse as que deseja incluir no parcelamento):");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(10, 59, 476, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnLimpar = new JButton("Limpar Tudo");
		btnLimpar.setBounds(7, 219, 115, 23);
		contentPane.add(btnLimpar);
		
		JButton btnNewButton = new JButton("Simular Parcelamento");
		btnNewButton.setBounds(132, 219, 177, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Confirmar Parcelamento");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(10, 528, 193, 23);
		contentPane.add(btnNewButton_1);
	}
}
