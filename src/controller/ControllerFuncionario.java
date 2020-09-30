package controller;

import java.util.Date;

import javax.swing.JOptionPane;

import model.Funcionario;
import persistance.MapeadorFuncionario;

public class ControllerFuncionario {

	private static ControllerFuncionario instancia;

	public static ControllerFuncionario getInstancia() {
		if (instancia == null) {
			instancia = new ControllerFuncionario();
		}
		return instancia;
	}

	public boolean cpfValido(Long cpf) {
		String CPF = Long.toString(cpf);
		if (cpf != 0 && CPF.length() == 11) {
			return true;
		}
		return false;
	}
	
	public boolean validaCamposCadastro(String nome, Long cpf, String email, String login, String senha) {
		if (nome.trim().equals("") || cpfValido(cpf) || email.trim().equals("") || senha.trim().equals("")) {
			return false;
		}
		return true;	
	}

	public Funcionario getFuncionarioByCpf(Long cpf) {
		if (cpfValido(cpf)) {
			for (Funcionario funcionario : MapeadorFuncionario.getInstancia().getList()) {
				if (funcionario.getCPF() == cpf) {
					return funcionario;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cpf Inválido");
		}
		return null;
	}

	public Funcionario getFuncionarioByLogin(String login, String senha) {
		for (Funcionario funcionario : MapeadorFuncionario.getInstancia().getList()) {
			if (funcionario.getLogin().equals(login) && funcionario.getSenha().equals(senha)) {
				return funcionario;
			}
		}
		return null;

	}

	public Funcionario cadastrarFuncionario(String nome, Date DNF, Long cpf, Date dataAdmissao, String email,
			boolean ehAdmin, String login, String senha) {
		Funcionario funcionario = new Funcionario(nome, DNF, login, senha, cpf, dataAdmissao, email, ehAdmin);
		MapeadorFuncionario.getInstancia().put(funcionario);
		return funcionario;
	}

	public void atualizarFuncionario(String nome, Date DNF, Long cpf, Date dataAdmissao, String email, boolean ehAdmin) {
		Funcionario funcionario = MapeadorFuncionario.getInstancia().get(cpf);
		funcionario.setNome(nome);
		funcionario.setEmail(email);
		funcionario.setDNF(DNF);
		funcionario.setEhAdm(ehAdmin);
	}

}
