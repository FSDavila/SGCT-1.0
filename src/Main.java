import java.util.Date;

import javax.swing.JFrame;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import controller.ControllerPagamento;
import model.PagamentoIntegral;
import model.PagamentoParcela;
import view.TelaLogin;

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
    	TelaLogin.getInstancia().setVisible(true);
    }
}
