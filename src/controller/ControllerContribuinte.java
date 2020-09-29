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

public class ControllerContribuinte {

    private static ControllerContribuinte instancia; //singleton

    public ControllerContribuinte() {
    }

    public static ControllerContribuinte getInstancia() {
        if (instancia == null) {
            instancia = new ControllerContribuinte();
        }
        return instancia;
    }

    public CDA getCDAByNCDA(int nCDA) {
        CDA procurado = null;
        boolean encontrado = false;
        if (nCDA > 0) {
            for (CDA registro : MapeadorCDA.getInstancia().getList()) {
            	CDA atual = registro;

                if (atual.getNCDA() == nCDA) {
                    procurado = atual;
                    encontrado = true;
                }
                if (encontrado) {
                    return procurado;
                } else {
                    return null;
                }

            }
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
