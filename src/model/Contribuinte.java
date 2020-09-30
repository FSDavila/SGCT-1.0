package model;

import java.io.Serializable;
import java.util.Date;

public class Contribuinte extends Pessoa{
	
	private String identificacao;
	private String login;
	private String senha;
	private String email;
	private Date dtCadastroChat;
	private boolean ehCNPJ;
	
	public Contribuinte () {
		super();
	}
	
	public Contribuinte(String nome, Date DNF, String identificacao, String login, String senha, String email, Date dtCadastroChat,
			boolean ehCNPJ) {
		super(nome, DNF, login, senha);
		this.identificacao = identificacao;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.dtCadastroChat = dtCadastroChat;
		this.ehCNPJ = ehCNPJ;
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
