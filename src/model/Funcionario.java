package model;

import java.util.Date;

public class Funcionario extends Pessoa {

	private Long CPF;
	private Date dataAdmissao;
	private String email;
	private boolean ehAdm;

	public Funcionario() {
		super();
	}

	public Funcionario(String nome, Date DNF, String login, String senha, Long cPF,
			Date dataAdmissao, String email, boolean ehAdm) {
		super(nome, DNF, login, senha);
		CPF = cPF;
		this.dataAdmissao = dataAdmissao;
		this.email = email;
		this.ehAdm = ehAdm;
	}

	public Long getCPF() {
		return CPF;
	}

	public void setCPF(Long cPF) {
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
