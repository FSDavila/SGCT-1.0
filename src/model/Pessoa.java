package model;

import java.util.Date;

public abstract class Pessoa {
	private String nome;
	private Date DNF; //data de nascimento ou data de fundacao
	private String login;
	private String senha;
	
	public Pessoa(String nome, Date DNF, String login, String senha) {
		this.nome = nome;
		this.DNF = DNF;
		this.login = login;
		this.senha = senha;
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

	public Pessoa() {
		
	}
}
