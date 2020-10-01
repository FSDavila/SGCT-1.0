package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDayChooser;

import controller.ControllerCDA;
import controller.ControllerContribuinte;
import model.CDA;
import model.Contribuinte;
import model.Imposto;
import model.SITUACAO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;

import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.awt.event.ActionEvent;

public class TelaCadCDAPrototipo extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNCDA;
	private JTextField textFieldID;
	private JTextField textFieldTipo;
	private JTextField textFieldValor;
	private JTextField textFieldSituacao;
	private JDateChooser dateChooserDNF;
	private JButton btnConfirmar;
	private JButton btnCadastrar;
	private JButton btnCancelar;
	
	private static TelaCadCDAPrototipo instancia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadCDAPrototipo frame = new TelaCadCDAPrototipo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaCadCDAPrototipo getInstancia() {
        if (instancia == null) {
            instancia = new TelaCadCDAPrototipo();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaCadCDAPrototipo() {
		setBounds(100, 100, 350, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("DD/MM");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldNCDA = new JTextField();
		textFieldNCDA.setEnabled(false);
		textFieldNCDA.setBounds(10, 29, 86, 20);
		panel.add(textFieldNCDA);
		textFieldNCDA.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("nCDA");
		lblNewLabel.setBounds(10, 11, 86, 14);
		panel.add(lblNewLabel);
		
		textFieldID = new JTextField();
		textFieldID.setEnabled(false);
		textFieldID.setColumns(10);
		textFieldID.setBounds(106, 29, 200, 20);
		panel.add(textFieldID);
		
		JLabel lblO = new JLabel("Identifica\u00E7\u00E3o Contribuinte");
		lblO.setBounds(106, 11, 145, 14);
		panel.add(lblO);
		
		JLabel lblO_1 = new JLabel("Cod. Imposto");
		lblO_1.setBounds(10, 77, 145, 14);
		panel.add(lblO_1);
		
		textFieldTipo = new JTextField();
		textFieldTipo.setEnabled(false);
		textFieldTipo.setColumns(10);
		textFieldTipo.setBounds(10, 95, 64, 20);
		panel.add(textFieldTipo);
		
		
		btnConfirmar = new JButton("OK");
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
				int nCDA = Integer.parseInt(textFieldNCDA.getText());
				double valor = Double.parseDouble(textFieldValor.getText());
				int codImposto = Integer.parseInt(textFieldSituacao.getText());
				int codSituacao = Integer.parseInt(textFieldTipo.getText());
				
				Date dataVencimento = dateChooserDNF.getDate();
				Contribuinte devedor = MapeadorContribuinte.getInstancia().get(textFieldID.getText());

				CDA nova = ControllerCDA.getInstancia().cadastraCDA(nCDA, valor, codImposto, dataVencimento, "test", devedor, codSituacao);
				System.out.println(nova.getNCDA());
				
				if (nova != null) {
                    try {
                    MapeadorCDA.getInstancia().persist();
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    				textFieldNCDA.setEnabled(false);
    				textFieldID.setEnabled(false);
    				textFieldTipo.setEnabled(false);
    				textFieldValor.setEnabled(false);
    				textFieldSituacao.setEnabled(false);
    				dateChooserDNF.setEnabled(false);
    				textFieldNCDA.setText("");
    				textFieldID.setText("");
    				textFieldTipo.setText("");
    				textFieldValor.setText("");
    				textFieldSituacao.setText("");
    				dateChooserDNF.setDate(null);
    				btnConfirmar.setEnabled(false);
    				btnCancelar.setEnabled(false);
    				btnCadastrar.setEnabled(true);
                    } 
                    catch (FileNotFoundException ex) {
                        System.out.println("Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
                    }

				}
				else {
                    JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente erro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
				
				 }
                  catch (NullPointerException a) {
                     JOptionPane.showMessageDialog(null, "Verifique os dados digitados e tente novamente null.", "Aviso", JOptionPane.WARNING_MESSAGE);
                 }
			}
		});
			
			
        
		btnConfirmar.setBounds(242, 187, 64, 23);
		panel.add(btnConfirmar);
		
		dateChooserDNF = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		dateChooserDNF.setBounds(176, 95, 108, 20);
		dateChooserDNF.setEnabled(false);;
		panel.add(dateChooserDNF);

		
		JLabel lblNewLabel_1 = new JLabel("Dt. Vencimento CDA");
		lblNewLabel_1.setBounds(176, 77, 148, 14);
		panel.add(lblNewLabel_1);
		
		textFieldValor = new JTextField();
		textFieldValor.setEnabled(false);
		textFieldValor.setColumns(10);
		textFieldValor.setBounds(84, 95, 86, 20);
		panel.add(textFieldValor);
		
		JLabel lblLogin = new JLabel("Valor");
		lblLogin.setBounds(84, 77, 86, 14);
		panel.add(lblLogin);
		
		textFieldSituacao = new JTextField();
		textFieldSituacao.setEnabled(false);
		textFieldSituacao.setColumns(10);
		textFieldSituacao.setBounds(10, 156, 86, 20);
		panel.add(textFieldSituacao);
		
		JLabel lblSenha = new JLabel("Cod. Situa\u00E7\u00E3o");
		lblSenha.setBounds(10, 138, 86, 14);
		panel.add(lblSenha);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldNCDA.setEnabled(true);
				textFieldID.setEnabled(true);
				textFieldTipo.setEnabled(true);
				textFieldValor.setEnabled(true);
				textFieldSituacao.setEnabled(true);
				dateChooserDNF.setEnabled(true);
				btnConfirmar.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnCadastrar.setEnabled(false);
			}
		});
		btnCadastrar.setBounds(10, 187, 108, 23);
		panel.add(btnCadastrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldNCDA.setEnabled(false);
				textFieldID.setEnabled(false);
				textFieldTipo.setEnabled(false);
				textFieldValor.setEnabled(false);
				textFieldSituacao.setEnabled(false);
				dateChooserDNF.setEnabled(false);
				textFieldNCDA.setText("");
				textFieldID.setText("");
				textFieldTipo.setText("");
				textFieldValor.setText("");
				textFieldSituacao.setText("");
				dateChooserDNF.setDate(null);
				btnConfirmar.setEnabled(false);
				btnCadastrar.setEnabled(true);
				btnCancelar.setEnabled(false);
			}
		});
		btnCancelar.setBounds(143, 187, 89, 23);
		panel.add(btnCancelar);
		
	}
}
