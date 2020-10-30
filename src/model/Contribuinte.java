package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Contribuinte extends Pessoa{
	
	private String identificacao;
	private String email;
	private Date dtCadastroChat;
	private boolean ehCNPJ;
	private ArrayList<CDA> CDAs = new ArrayList<>();
	private ArrayList<PCDA> parcelamentos = new ArrayList<>();
	
	public ArrayList<CDA> getCDAs() {
		return CDAs;
	}

	public void setCDAs(ArrayList<CDA> cDAs) {
		CDAs = cDAs;
	}

	public Contribuinte () {
		super();
	}
	
	public Contribuinte(String nome, Date DNF, String identificacao, String login, String senha, String email, Date dtCadastroChat,
			boolean ehCNPJ) {
		super(nome, DNF, login, senha);
		this.identificacao = identificacao;
		this.email = email;
		this.dtCadastroChat = dtCadastroChat;
		this.ehCNPJ = ehCNPJ;
	}

	public ArrayList<PCDA> getParcelamentos() {
		return parcelamentos;
	}

	public void setParcelamentos(ArrayList<PCDA> parcelamentos) {
		this.parcelamentos = parcelamentos;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtCadastroChat() {
		return dtCadastroChat;
	}

	public void setDtCadastroChat(Date dtCadastroChat) {
		this.dtCadastroChat = dtCadastroChat;
	}

	public boolean isEhCNPJ() {
		return ehCNPJ;
	}

	public void setEhCNPJ(boolean ehCNPJ) {
		this.ehCNPJ = ehCNPJ;
	}
	
}
