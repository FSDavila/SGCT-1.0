package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import javax.swing.JTextField;

public class TelaCadChatContribuinte extends JFrame {

	private JPanel contentPane;
	
	private static TelaCadChatContribuinte instancia; //singleton
	private JTextField textField;
	private JTextField textField_1;
	
	private JDateChooser dateChooserDtNasc = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadChatContribuinte frame = new TelaCadChatContribuinte();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static TelaCadChatContribuinte getInstancia(){
        if(instancia == null ){
            instancia = new TelaCadChatContribuinte();
        }
        return instancia;
    }

	/**
	 * Create the frame.
	 */
	public TelaCadChatContribuinte() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dateChooserDtNasc.setBounds(10, 228, 108, 20);
		contentPane.add(dateChooserDtNasc);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 53, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}
