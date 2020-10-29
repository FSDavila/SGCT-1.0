package controller;

import persistance.MapeadorCDA;
import model.CDA;
import model.Contribuinte;
import model.Funcionario;
import model.Imposto;
import model.SITUACAO;
import model.TIPOIMPOSTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ControllerCDA {

    private static ControllerCDA instancia; //singleton

    public ControllerCDA() {

    }

    public static ControllerCDA getInstancia() {
        if (instancia == null) {
            instancia = new ControllerCDA();
        }
        return instancia;
    }

    public CDA getCDAByNCDA(int nCDA) {
    	if (MapeadorCDA.getInstancia().get(nCDA) != null) {
    		return MapeadorCDA.getInstancia().get(nCDA);
    	}
    	return null;
    }

    public boolean ehInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }
    
	public CDA cadastraCDA(int nCDA, double valor, int tipoImposto, Date dataVencimento, String descricao,
			Contribuinte titular, int situacaoCDA) {
		CDA nova = new CDA(nCDA, valor, tipoImposto, dataVencimento, descricao, titular, situacaoCDA);
		MapeadorCDA.getInstancia().put(nova);
		titular.getCDAs().add(nova); // adiciona a CDA no arraylist de dividas do devedor
		try {
			MapeadorCDA.getInstancia().persist();
		} catch (FileNotFoundException ex) {
			System.out.println("Houve um problema ao inicializar o arquivo de serializacao. Favor cadastrar novamente");
		}
		return nova;
	}

    public double defineValorITCMD(TIPOIMPOSTO imposto, double valor) {
    	if(imposto.equals(TIPOIMPOSTO.ITCMD)){
    		if(valor < 100000.0){
    			return valor * 0.07; //7% de imposto pois esta abaixo de 100000
    		}
    		else if(valor > 100000.0 && valor < 1000000.0){
    			return valor * 0.11; //11% de imposto pois esta acima de 100k e abaixo de 1kk
    		}
    		else if(valor > 1000000.0){
    			return valor * 0.14; //14% de imposto pois esta acima de 1kk
    		}
    	}
		return 0;
    }

    public void excluiCDA(int nCDA) { //existe apenas para corrigir dados de forma interna, se necessario
        MapeadorCDA.getInstancia().remove(nCDA);
    }

    public void alteraSituacao(int nCDA, int situacao) {
    	CDA procurada = getCDAByNCDA(nCDA);
        procurada.setSituacaoCDA(situacao);
    }

    public ArrayList<CDA> listaCDAS() {
        return MapeadorCDA.getInstancia().getList();
    }

}
