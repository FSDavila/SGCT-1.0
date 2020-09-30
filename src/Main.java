import javax.swing.JFrame;

import view.Login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Filipe
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	Login telaLogin = new Login();
    	frame.add(telaLogin);
    	frame.setSize(260, 330);
    	frame.setVisible(true);
    }
    
}
