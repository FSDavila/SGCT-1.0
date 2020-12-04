package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistance.MapeadorChatLog;
import persistance.MapeadorContribuinte;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class TelaSelecaoAtendimento extends JFrame {
	
	private static TelaSelecaoAtendimento instancia; //singleton
	JComboBox comboBox = new JComboBox();
	

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSelecaoAtendimento frame = new TelaSelecaoAtendimento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaSelecaoAtendimento getInstancia(){
        if(instancia == null ){
            instancia = new TelaSelecaoAtendimento();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaSelecaoAtendimento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 172);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sele\u00E7\u00E3o de Atendimento - Lista de Contribuintes Online");
		lblNewLabel.setBounds(10, 20, 265, 14);
		contentPane.add(lblNewLabel);
		
		comboBox.setBounds(10, 45, 186, 22);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Atualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> listaOnlines = new ArrayList<>();
				
				//String[] listaOnlines = new String[MapeadorChatLog.getInstancia().getList().size()];
				ArrayList<Vector<Vector>> chatlogs =  MapeadorChatLog.getInstancia().getList(); //pega strings cpfs e cnpjs
				ArrayList<String> ids = MapeadorChatLog.getInstancia().getKeys(); //pega chatlogs
				for(int i = 0; i < ids.size(); i++) {
					if(chatlogs.get(i).get(0).get(2).equals("(Online)")) { // pega a celula onde mostra o status online ou offline no chatlog
						listaOnlines.add(ids.get(i)); //adiciona o id que consta como online na lista de usuarios online
					}
				}	
				
				DefaultComboBoxModel dml= new DefaultComboBoxModel();
				for (String str : listaOnlines) {
				  dml.addElement(str);
				}
				comboBox.setModel(dml);
				
			}
		});
		btnNewButton.setBounds(205, 45, 79, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Entrar na Sess\u00E3o");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaChatFuncionario.getInstancia().setUsuarioLogado(MapeadorContribuinte.getInstancia().get(comboBox.getSelectedItem().toString()));
				TelaChatFuncionario.getInstancia().setDadosChat(comboBox.getSelectedItem().toString()); //carrega o historico do chat do usuario onde parou
				TelaChatFuncionario.getInstancia().setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(10, 99, 115, 23);
		contentPane.add(btnNewButton_1);
	}
}
