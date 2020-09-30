package model;

import java.io.Serializable;
import java.util.Date;

public class Funcionario extends Pessoa{
	
	private String CPF;
	private Date dataAdmissao;
	private String email;
	private boolean ehAdm;

	public Funcionario() {
		super();
	}

	public Funcionario(String nome, Date DNF, String cpf, Date dataAdmissao, String email, boolean ehAdm, String login, String senha) {
		super(nome, DNF, login, senha);
		this.CPF = cpf;
		this.dataAdmissao = dataAdmissao;
		this.email = email;
		this.ehAdm = ehAdm;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEhAdm() {
		return ehAdm;
	}

	public void setEhAdm(boolean ehAdm) {
		this.ehAdm = ehAdm;
	}

}
