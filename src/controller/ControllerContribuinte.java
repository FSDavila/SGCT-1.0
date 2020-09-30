package controller;

import persistance.MapeadorCDA;
import persistance.MapeadorContribuinte;
import model.CDA;
import model.Contribuinte;
import model.Imposto;
import model.SITUACAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public Contribuinte getContribuinteById(String id) {
    	if (MapeadorContribuinte.getInstancia().get(id) != null) {
    		return MapeadorContribuinte.getInstancia().get(id);
    	}
    	return null;
    }
    
    public Contribuinte getContribuinteByLogin(String login) {
		for(Contribuinte contribuinte : MapeadorContribuinte.getInstancia().getList()) {
			if(contribuinte.getLogin().equals(login)) {
				return contribuinte;
			}
		}
		return null;
    }

    public Contribuinte cadastraContribuinte(String nome, Date DNF, String identificacao, String login, String senha, String email,
			boolean ehCNPJ) {
			Date dataAtual = new Date(); //pega data de cadastro
            Contribuinte novo = new Contribuinte(nome, DNF, identificacao, login, senha, email, dataAtual, ehCNPJ);
            MapeadorContribuinte.getInstancia().put(novo);
            return novo;
    }

    public void excluiContribuinte(String identificacao) { //existe apenas para corrigir dados de forma interna, se necessario
    	MapeadorContribuinte.getInstancia().remove(identificacao);
    }
    
    public void alteraDados(String identificacao, String nome, Date DNF, String login, String senha, String email, Date dtCadastroChat,
			boolean ehCNPJ) { 
    	Contribuinte alterar = MapeadorContribuinte.getInstancia().get(identificacao);
    	alterar.setDNF(DNF);
    	alterar.setNome(nome);
    	alterar.setLogin(login);
    	alterar.setSenha(senha);
    	alterar.setEmail(email);
    	alterar.setDtCadastroChat(dtCadastroChat);
    	alterar.setEhCNPJ(ehCNPJ);
    }

    public ArrayList<Contribuinte> listaContribuintes() {
        return MapeadorContribuinte.getInstancia().getList();
    }

}
