package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Contribuinte;
import model.Funcionario;
import persistance.MapeadorChatLog;
import persistance.MapeadorContribuinte;
import persistance.MapeadorFuncionario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TelaChatContribuinte extends JFrame {
	
    private static TelaChatContribuinte instancia; //singleton
	
    private Contribuinte usuarioLogado = null; //MapeadorContribuinte.getInstancia().get("04104104141");
    private Funcionario funcionarioAtendente = null;

	private JPanel contentPane;
	private JTable tabelaChat;
	private JTextField textFieldMensagem;
	
	private JLabel lblNomeContribuinte = new JLabel(" - ");
	
	private DefaultTableModel dadosChat = new DefaultTableModel(
			new Object[][] {
				{ "Usuario", " - ", "(Online)"},
			},
			new String[] {
				"Usuario", "Mensagem", "Enviada em"
			}
		);
	
    public static TelaChatContribuinte getInstancia(){
        if(instancia == null ){
            instancia = new TelaChatContribuinte();
        }
        return instancia;
    }


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaChatContribuinte frame = new TelaChatContribuinte();
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
	public TelaChatContribuinte() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnVoltar = new JButton("Sair da Sess\u00E3o");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dadosChat.setValueAt("(Offline)", 0, 2);
				usuarioLogado = null;
				setVisible(false);
				TelaLogin.getInstancia().setVisible(true);
			}
		});
		contentPane.setLayout(null);
		btnVoltar.setBounds(543, 4, 125, 20);
		contentPane.add(btnVoltar);
		
		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(10, 39, 658, 211);
		scrollPaneTabela.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		tabelaChat = new JTable();
		tabelaChat.setBounds(32, 71, 110, 20);
		
		tabelaChat.setModel(dadosChat);
		tabelaChat.getColumnModel().getColumn(1).setPreferredWidth(300);
		
		scrollPaneTabela.setViewportView(tabelaChat);
		contentPane.add(scrollPaneTabela);
		
		textFieldMensagem = new JTextField();
		textFieldMensagem.setBounds(10, 329, 573, 20);
		contentPane.add(textFieldMensagem);
		textFieldMensagem.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuario = usuarioLogado.getNome();
				usuario = usuario.substring(0,usuario.indexOf(' '));
				String mensagem = textFieldMensagem.getText();
				Date dataMsg = new Date();
				dadosChat.insertRow(dadosChat.getRowCount(), new Object[] { usuario, mensagem, dataMsg.toString() });
				textFieldMensagem.setText("");
				try {
					MapeadorChatLog.getInstancia().put(usuarioLogado.getIdentificacao(), dadosChat.getDataVector());
					MapeadorChatLog.getInstancia().persist();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Houve algum problema na hora de salvar os dados das mensagens. Tente novamente mais tarde.", "Aviso",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(593, 328, 75, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Digite sua mensagem:");
		lblNewLabel.setBounds(10, 312, 207, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Atendimento ao Contribuinte - Logado como:");
		lblNewLabel_1.setBounds(10, 7, 285, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Conectado com Funcion\u00E1rio:");
		lblNewLabel_2.setBounds(172, 253, 174, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNomeFuncionario = new JLabel("-");
		lblNomeFuncionario.setBounds(350, 253, 318, 14);
		contentPane.add(lblNomeFuncionario);
		
		
		lblNomeContribuinte.setBounds(279, 7, 254, 14);
		contentPane.add(lblNomeContribuinte);
	}
	
	public void setUsuarioLogado(Contribuinte usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public void setFuncionariofuncionarioAtendente(Funcionario funcionarioAtendente) {
		this.funcionarioAtendente = funcionarioAtendente;
	}
	
	public void setDadosChat(String CPF) {
		Vector<String> dados = new Vector<String>(3);
		dados.add("Usuario");
		dados.add("Mensagem");
		dados.add("Enviada em");
		dadosChat.setDataVector(MapeadorChatLog.getInstancia().get(CPF), dados);
	}
	
	public void setUsuarioOnline(){
		dadosChat.setValueAt("(Online)", 0, 2);
		lblNomeContribuinte.setText(usuarioLogado.getIdentificacao());
	}
}
