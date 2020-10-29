package controller;

import java.util.Calendar;
import java.util.Date;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

public class ControllerPagamento {
	
	private static ControllerPagamento instancia;

	public static ControllerPagamento getInstancia() {
		if (instancia == null) {
			instancia = new ControllerPagamento();
		}
		return instancia;
	}
	
	public boolean verificaDataVencimento(Date dataVencimento) { // retorna true se o dia for o atual ou apos, e mes e ano iguais ao atual		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataVencimento);
		
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int ano = calendar.get(Calendar.YEAR);
		
		Date dataAtual = java.util.Calendar.getInstance().getTime(); // pega data atual
		
		Calendar calendarAtual = Calendar.getInstance();
		calendarAtual.setTime(dataAtual);
		
		int diaAtual = calendarAtual.get(Calendar.DAY_OF_MONTH);
		int mesAtual = calendarAtual.get(Calendar.MONTH);
		int anoAtual = calendarAtual.get(Calendar.YEAR);
		
		if(dia >= diaAtual) {
			if(mes == mesAtual) {
				if(ano == anoAtual) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void emitirBoletoPgtoIntegral(Date dataVencimento, Integer nCDA, double valor, String nomeContribuinte, String identificacao) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataVencimento);
		
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int ano = calendar.get(Calendar.YEAR);
		
		Date dataAtual = java.util.Calendar.getInstance().getTime(); // pega data atual
		
		Calendar calendarAtual = Calendar.getInstance();
		calendarAtual.setTime(dataAtual);
		
		int diaEmissao = calendarAtual.get(Calendar.DAY_OF_MONTH);
		int mesEmissao = calendarAtual.get(Calendar.MONTH);
		int anoEmissao = calendarAtual.get(Calendar.YEAR);
		
		Datas datas = Datas.novasDatas()
                .comDocumento(diaEmissao, mesEmissao, anoEmissao)
                .comProcessamento(diaEmissao, mesEmissao, anoEmissao)
                .comVencimento(dia, mes, ano);  

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
        		.comLogradouro("-")  
        		.comBairro("-")  
        		.comCep("-")  
        		.comCidade("-")  
        		.comUf("-");  
        
        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome(nomeContribuinte)  
                .comDocumento(identificacao)
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();  

        Boleto boleto = Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto(Double.toString(valor))  
                .comNumeroDoDocumento(Integer.toString(nCDA))  
                .comInstrucoes("NAO RECEBER APOS O VENCIMENTO")  
                .comLocaisDePagamento("Loterica", "Banco do Brasil");  

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);  

        // Para gerar um boleto em PDF  
        gerador.geraPDF("TEST.pdf");  

        // Para gerar um boleto em PNG  
        gerador.geraPNG("TEST.png");  

        // Para gerar um array de bytes a partir de um PDF  
        byte[] bPDF = gerador.geraPDF();  

        // Para gerar um array de bytes a partir de um PNG  
        byte[] bPNG = gerador.geraPNG();
	}
}
