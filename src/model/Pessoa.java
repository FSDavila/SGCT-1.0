package model;

import java.util.Date;

public abstract class Pessoa {
	private String nome;
	private Date DNF; // data de nascimento ou data de fundacao
	private String login;
	private String senha;

	public Pessoa(String nome, Date DNF, String login, String senha) {
		this.nome = nome;
		this.DNF = DNF;
		this.login = login;
		this.senha = senha;
	}

	public Pessoa() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDNF() {
		return DNF;
	}

	public void setDNF(Date dNF) {
		DNF = dNF;
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
}
