package model;

import java.util.Date;

public class Contribuinte extends Pessoa{
	private int identificacao;
	private String login;
	private String senha;
	private String email;
	private Date dtCadastroChat;
	private boolean ehCNPJ;
	
	public Contribuinte () {
		super();
	}
	
	public Contribuinte(String nome, Date DNF, int identificacao, String login, String senha, String email, Date dtCadastroChat,
			boolean ehCNPJ) {
		super(nome, DNF);
		this.identificacao = identificacao;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.dtCadastroChat = dtCadastroChat;
		this.ehCNPJ = ehCNPJ;
	}

	public int getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(int identificacao) {
		this.identificacao = identificacao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
