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
		
		for(int i = 0; i < CDASParcelar.size(); i++) {
			valorTotal += CDASParcelar.get(i).getValor();
		}
		return valorTotal;
	}
	
	public PCDA geraParcelamento(int nParcelas, Contribuinte titular, int identificacao, int tipoImposto,
	ArrayList<CDA> CDASParceladas, Date dataVencimento){
		double valor = pegaValorTotal(CDASParceladas);
		PCDA novo = new PCDA(nParcelas, titular, identificacao, valor, tipoImposto, false, CDASParceladas, dataVencimento);
		MapeadorPCDA.getInstancia().put(novo);
        titular.getParcelamentos().add(novo);
		try {
			MapeadorCDA.getInstancia().persist();
		} catch (FileNotFoundException ex) {
			System.out.println("Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
		}
		return novo;
	}
	
    public void excluiPCDA(int PCDA) { //existe apenas para corrigir dados de forma interna, se necessario
        MapeadorPCDA.getInstancia().remove(PCDA);
    }
}
