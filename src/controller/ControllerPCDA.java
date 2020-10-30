package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import model.CDA;
import model.Contribuinte;
import model.PCDA;
import model.Parcela;
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
		if (titular.getParcelamentos().size() < 3) { // condicao eh existir menos de 3 parcelamentos para possibilidade
														// de emissao de um novo
			double valor = pegaValorTotal(CDASParceladas);
			for (CDA parcelada : CDASParceladas) {
				parcelada.setSituacaoCDA(4); // seta todas as cdas parceladas para status parcelada
			}
			PCDA novo = new PCDA(nParcelas, titular, identificacao, valor, tipoImposto, false, CDASParceladas,
					dataVencimento);
			MapeadorPCDA.getInstancia().put(novo);
			titular.getParcelamentos().add(novo); // adiciona o parcelamento na lista de parcelamentos do contribuinte
			try {
				MapeadorCDA.getInstancia().persist();
			} catch (FileNotFoundException ex) {
				System.out.println(
						"Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
			}
			return novo;
		}
		return null; // ja existem mais de 3 parcelamentos para esse contribuinte
	}
	
    public void excluiPCDA(int PCDA) { //existe apenas para corrigir dados de forma interna, se necessario
        MapeadorPCDA.getInstancia().remove(PCDA);
    }
}
