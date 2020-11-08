package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import model.CDA;
import model.Contribuinte;
import model.PCDA;
import model.PagamentoIntegral;
import model.PagamentoParcela;
import model.Parcela;
import model.SITUACAO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import persistance.MapeadorPCDA;
import persistance.MapeadorPagamentoIntegral;
import persistance.MapeadorPagamentoParcela;

public class ControllerPagamento {

	private static ControllerPagamento instancia;
	
    public ControllerPagamento() {

  }

	public static ControllerPagamento getInstancia() {
		if (instancia == null) {
			instancia = new ControllerPagamento();
		}
		return instancia;
	}
	
	
	public PagamentoIntegral geraPagamentoIntegral(int nCDA, double valorPgto, Date dataPagamento) {
		PagamentoIntegral novo = new PagamentoIntegral(nCDA, valorPgto, dataPagamento);
		MapeadorPagamentoIntegral.getInstancia().put(novo);
		anexaPagamentoIntegral(novo);
		try {
		MapeadorPagamentoIntegral.getInstancia().persist();
		} catch(FileNotFoundException ex) {
			System.out.println(
					"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
		}	
		return novo;
	}
	
	public PagamentoParcela geraPagamentoParcela(int identificacaoPCDA, int nParcela, double valorParcela, Date dataPagamento) {
		PagamentoParcela novo = new PagamentoParcela(identificacaoPCDA, nParcela, valorParcela, dataPagamento);
		MapeadorPagamentoParcela.getInstancia().put(novo);
		anexaPagamentoParcela(novo);
		try {
			MapeadorPagamentoParcela.getInstancia().persist();
			} catch(FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}	
		return novo;
	}
	
	public void anexaPagamentoIntegral(PagamentoIntegral pagamento) {
		CDA procurada = MapeadorCDA.getInstancia().get(pagamento.getnCDA());
		procurada.setPagamento(pagamento);
		procurada.setSituacaoCDA(2); // seta como quitada
		try {
			MapeadorCDA.getInstancia().persist();
			} catch(FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}	

	}
	
	public void anexaPagamentoParcela(PagamentoParcela pagamento) {
		PCDA procurada = MapeadorPCDA.getInstancia().get(pagamento.getIdentificacaoPCDA()); //pega o parcelamento
		Parcela[] parcelas = procurada.getParcelas(); // pega o array de parcelas
		parcelas[ pagamento.getnParcela() -1 ].setPagamento(pagamento); // pega a parcela em si, -1 pois eh a posicao no array, que comeca em 0
		parcelas[ pagamento.getnParcela() -1 ].setQuitado(true); // seta como quitada
		try {
			MapeadorPCDA.getInstancia().persist();
			} catch(FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}
	}
	
    public void excluiPgtoIntegral(Long idPagamento) { //existe apenas para corrigir dados de forma interna, se necessario
    	PagamentoIntegral pgto = MapeadorPagamentoIntegral.getInstancia().get(idPagamento);
    	CDA procurada = MapeadorCDA.getInstancia().get(pgto.getnCDA());
    	procurada.setPagamento(null); // destroi a associacao entre a CDA e o pagamento a ser removido
    	MapeadorPagamentoIntegral.getInstancia().remove(idPagamento); // finalmente, remove o pgto da persistencia
    }
    
    public void excluiPgtoParcela(Long idPagamento) { //existe apenas para corrigir dados de forma interna, se necessario
    	PagamentoParcela pgto = MapeadorPagamentoParcela.getInstancia().get(idPagamento);
    	PCDA procurada = MapeadorPCDA.getInstancia().get(pgto.getIdentificacaoPCDA());
    	Parcela[] parcelas = procurada.getParcelas(); // pega o array de parcelas
    	parcelas[ pgto.getnParcela() -1 ].setPagamento(null); // destroi a associacao entre a parcela e o pagamento a ser removido
    	MapeadorPagamentoParcela.getInstancia().remove(idPagamento); // finalmente, remove o pgto da persistencia
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
                .comAgencia("1824").comDigitoAgencia("4")  
                .comCodigoBeneficiario("76000")  
                .comDigitoCodigoBeneficiario("5")  
                .comNumeroConvenio("1207113")  
                .comCarteira("18")  
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");  

        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro(" ")  
        		.comBairro(" ")  
        		.comCep(" ")  
        		.comCidade(" ")  
        		.comUf(" ");  
        
        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome(nomeContribuinte)  
                .comDocumento(identificacao)
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil(); // boleto eh sempre para BB 

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
        gerador.geraPDF(Integer.toString(nCDA) +".pdf");  // finalmente, gera o boleto em PDF com o nome do arquivo sendo a identificacao da divida

        // Para gerar um boleto em PNG  
        //gerador.geraPNG("TEST.png");  

        // Para gerar um array de bytes a partir de um PDF  
        //byte[] bPDF = gerador.geraPDF();  

        // Para gerar um array de bytes a partir de um PNG  
        //byte[] bPNG = gerador.geraPNG();
	}
	
public void emitirBoletoPgtoParcela(Integer nParcelamento, Integer parcela, Date dataVencimento) {
	
	PCDA parcelamento = MapeadorPCDA.getInstancia().get(nParcelamento);
	Parcela parcelaBoleto = parcelamento.getParcelas()[parcela];
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(dataVencimento);
	Calendar atual = Calendar.getInstance();
	atual.setTime(new Date());
	atual.get(Calendar.MONTH);
	Contribuinte contribuinte = parcelamento.getTitular();
	
	Double valorParcela = parcelamento.getParcelas()[parcela].getValorParcela();
	//if (calendar.get(Calendar.MONTH) > calendar.get(Calendar.MONTH)) {

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		int ano = calendar.get(Calendar.YEAR);

		Date dataAtual = java.util.Calendar.getInstance().getTime(); // pega data atual

		Calendar calendarAtual = Calendar.getInstance();
		calendarAtual.setTime(new Date());

		int diaEmissao = calendarAtual.get(Calendar.DAY_OF_MONTH);
		int mesEmissao = calendarAtual.get(Calendar.MONTH)+1;
		int anoEmissao = calendarAtual.get(Calendar.YEAR);
		
		String dataBoleto = dia + "/" + mes + "/" + ano;
		
		Calendar calendarOriginal = Calendar.getInstance();
		calendarAtual.setTime(parcelaBoleto.getDataVencimento());
		
		int diaOriginal = calendarOriginal.get(Calendar.DAY_OF_MONTH);
		int mesOriginal = calendarOriginal.get(Calendar.MONTH) +1; // +1 por causa do calendar
		int anoOriginal = calendarOriginal.get(Calendar.YEAR);
		
		String dataOriginal =  diaOriginal + "/" + mesOriginal + "/" + anoOriginal;

		Datas datas = Datas.novasDatas().comDocumento(diaEmissao, mesEmissao, anoEmissao)
				.comProcessamento(diaEmissao, mesEmissao, anoEmissao).comVencimento(dia, mes, ano);

		Endereco enderecoBeneficiario = Endereco.novoEndereco().comLogradouro("Av do Governo, 236").comBairro("Centro")
				.comCep("88001-000").comCidade("Florianopolis").comUf("SC");

		// Quem emite o boleto
		Beneficiario beneficiario = Beneficiario.novoBeneficiario()
				.comNomeBeneficiario("GOVERNO DO ESTADO DE SANTA CATARINA")
                .comAgencia("1824").comDigitoAgencia("4")  
                .comCodigoBeneficiario("76000")  
                .comDigitoCodigoBeneficiario("5")  
                .comNumeroConvenio("1207113")  
                .comCarteira("18")  
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");  

        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro(" ")  
        		.comBairro(" ")  
        		.comCep(" ")  
        		.comCidade(" ")  
        		.comUf(" ");  
        
        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome(contribuinte.getNome())  
                .comDocumento(contribuinte.getIdentificacao())
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil(); // boleto eh sempre para BB 

        Boleto boleto = Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto(Double.toString(valorParcela))  
                .comNumeroDoDocumento(Integer.toString(parcelamento.getIdentificacao()))  
                .comInstrucoes("Boleto da Parcela n. " + parcelaBoleto.getnParcela() + " - " + "Parcelamento n. " + parcelamento.getIdentificacao(), "Data de Vencimento Original: " + dataOriginal)
                .comLocaisDePagamento("Loterica", "Banco do Brasil");  

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);  

        // Para gerar um boleto em PDF  
       // gerador.geraPDF(Integer.toString(parcelamento.getIdentificacao()) + "_" + parcelaBoleto.getnParcela() + "_" + dia + mes + ano +".pdf");  // finalmente, gera o boleto em PDF com o nome do arquivo sendo a identificacao do parcelamento
        gerador.geraPDF(Integer.toString(parcelamento.getIdentificacao()) + "_" + parcelaBoleto.getnParcela() + "_" + dia + mes + ano +".pdf");

        // Para gerar um boleto em PNG  
        //gerador.geraPNG("TEST.png");  

        // Para gerar um array de bytes a partir de um PDF  
        //byte[] bPDF = gerador.geraPDF();  

        // Para gerar um array de bytes a partir de um PNG  
        //byte[] bPNG = gerador.geraPNG();
		//}
	}
	
	public ArrayList<PagamentoIntegral> listPagamentoIntegral() {
		return MapeadorPagamentoIntegral.getInstancia().getList();
	}
	
	public ArrayList<PagamentoParcela> listPagamentoParcela() {
		return MapeadorPagamentoParcela.getInstancia().getList();
	}
	
	
	
	
}
