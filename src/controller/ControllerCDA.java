package controller;

import persistance.MapeadorCDA;
import model.CDA;
import model.Contribuinte;
import model.Funcionario;
import model.Imposto;
import model.SITUACAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ControllerCDA {

    private static ControllerCDA instancia; //singleton

    public ControllerCDA() {
//        if (this.funcionarios == null) {
//            Funcionario admin = new Funcionario(1,"Admin",1999,12,31,99999999,3);//apenas para inicializar
//            this.funcionarios = new ArrayList<>();
//            this.funcionarios.add(admin);
//        }

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

    public int cadastraCDA(int nCDA, double valor, Imposto tipoImposto, Date dataVencimento, String descricao,
			Contribuinte titular, SITUACAO situacaoCDA) {
            CDA nova = new CDA(nCDA, valor, tipoImposto, dataVencimento, descricao, titular, situacaoCDA);
            MapeadorCDA.getInstancia().put(nova);
            return 1;
    }

    public void excluiCDA(int nCDA) { //existe apenas para corrigir dados de forma interna, se necessario
        MapeadorCDA.getInstancia().remove(nCDA);
    }

    public void alteraSituacao(int nCDA, int situacao) {
    	CDA procurada = getCDAByNCDA(nCDA);
        procurada.setSituacaoCDA(situacao);
    }

    public ArrayList<CDA> listaFuncionarios() {
        return MapeadorCDA.getInstancia().getList();
    }

}
