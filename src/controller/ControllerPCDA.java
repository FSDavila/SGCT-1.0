package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import model.CDA;
import model.Contribuinte;
import model.PCDA;
import model.Parcela;
import model.SITUACAO;
import model.TIPOIMPOSTO;
import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import persistance.MapeadorPCDA;

public class ControllerPCDA {
	
	private static ControllerPCDA instancia;
	
    public ControllerPCDA() {

  }

	public static ControllerPCDA getInstancia() {
		if (instancia == null) {
			instancia = new ControllerPCDA();
		}
		return instancia;
	}
	
	public double pegaValorTotal(ArrayList<CDA> CDASParcelar) {
		double valorTotal = 0.0;
		
		for(CDA parcelada : CDASParcelar) {
			valorTotal += parcelada.getValor();
		}
		return valorTotal;
	}
	
	public PCDA geraParcelamento(int nParcelas, Contribuinte titular, int identificacao, int tipoImposto,
			ArrayList<CDA> CDASParceladas, Date dataVencimento) {
		if (titular.getParcelamentos().size() < 10) { // condicao eh existir menos de 3 parcelamentos para possibilidade
														// de emissao de um novo
			try {

			double valor = pegaValorTotal(CDASParceladas);
			PCDA novo = new PCDA(nParcelas, titular, identificacao, valor, tipoImposto, CDASParceladas);
			MapeadorPCDA.getInstancia().put(novo);
			MapeadorContribuinte.getInstancia().get(titular.getIdentificacao()).getParcelamentos().add(novo);
			MapeadorPCDA.getInstancia().persist();
			// adiciona o parcelamento na lista de parcelamentos do contribuinte
			for (CDA parcelada : CDASParceladas) {
				try {
				System.out.println(titular.getCDAs().size());
				System.out.println(parcelada.getNCDA());
				parcelada.setSituacao(SITUACAO.PARCELADO); // seta todas as cdas parceladas para status parcelada
				System.out.println(titular.getCDAs().size());
				System.out.println(parcelada.getSituacaoCDA());
				System.out.println(parcelada.getTipoImposto());
				MapeadorCDA.getInstancia().persist();
				MapeadorContribuinte.getInstancia().persist();
				} catch(FileNotFoundException ex) {
					System.out.println(
							"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
				}	
			}
			for(CDA cda : titular.getCDAs()) {
				for(int i = 0; i < CDASParceladas.size(); i++) {
				if(cda.getNCDA() == CDASParceladas.get(i).getNCDA()) {
					titular.getCDAs().remove(cda);
				}
			}
			MapeadorContribuinte.getInstancia().persist();
			System.out.println(novo.getCDASParceladas().size());
			System.out.println(novo.getTitular().getNome());
			System.out.println(novo.getTitular().getParcelamentos().size());
			return novo;
			} }catch (FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}
			
		}
		return null; // ja existem mais de 3 parcelamentos para esse contribuinte
	}
	
    public void excluiPCDA(int PCDA) { //existe apenas para corrigir dados de forma interna, se necessario
        MapeadorPCDA.getInstancia().remove(PCDA);
    }
}
