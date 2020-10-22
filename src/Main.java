import javax.swing.JFrame;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
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
    	
    	Datas datas = Datas.novasDatas()
                .comDocumento(1, 5, 2008)
                .comProcessamento(1, 5, 2008)
                .comVencimento(2, 5, 2008);  

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("Av do Governo, 236")  
        		.comBairro("Centro")  
        		.comCep("88001-000")  
        		.comCidade("Florianopolis")  
        		.comUf("SC");  

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()  
                .comNomeBeneficiario("GOVERNO DO ESTADO DE SANTA CATARINA")  
                .comAgencia("0001").comDigitoAgencia("1")  
                .comCodigoBeneficiario("76000")  
                .comDigitoCodigoBeneficiario("5")  
                .comNumeroConvenio("1207113")  
                .comCarteira("18")  
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");  

        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro("Av dos testes, 111 apto 333")  
        		.comBairro("Bairro Teste")  
        		.comCep("01234-111")  
        		.comCidade("São Paulo")  
        		.comUf("SP");  
        
        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome("Contribuilson De Vedor")  
                .comDocumento("111.222.333-12")
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();  

        Boleto boleto = Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto("736.00")  
                .comNumeroDoDocumento("111111")  
                .comInstrucoes("NAO RECEBER APOS O VENCIMENTO")  
                .comLocaisDePagamento("Loterica", "Banco do Brasil");  

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);  

        // Para gerar um boleto em PDF  
        gerador.geraPDF("BancoDoBrasil.pdf");  

        // Para gerar um boleto em PNG  
        gerador.geraPNG("BancoDoBrasil.png");  

        // Para gerar um array de bytes a partir de um PDF  
        byte[] bPDF = gerador.geraPDF();  

        // Para gerar um array de bytes a partir de um PNG  
        byte[] bPNG = gerador.geraPNG();
    }
    
}
