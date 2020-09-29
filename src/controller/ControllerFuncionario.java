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

	public Funcionario getFuncionarioByCpf(int cpf) {
		String CPF = Integer.toString(cpf);

		if (cpf != 0 && CPF.length() == 11) {
			for (Funcionario funcionario : MapeadorFuncionario.getInstancia().getList()) {
				if (funcionario.getCPF() == cpf) {
					return funcionario;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null ,"Cpf Inválido");
		}
		return null;
	}

	public Funcionario cadastrarFuncionario(String nome, Date DNF, int cpf, Date dataAdmissao, String email,
			boolean ehAdmin) {
		Funcionario funcionario = new Funcionario(nome, DNF, cpf, dataAdmissao, email, ehAdmin);
		MapeadorFuncionario.getInstancia().put(funcionario);
		return funcionario;
	}

	public void atualizarFuncionario(String nome, Date DNF, int cpf, Date dataAdmissao, String email, boolean ehAdmin) {
		Funcionario funcionario = MapeadorFuncionario.getInstancia().get(cpf);
		funcionario.setNome(nome);
		funcionario.setEmail(email);
		funcionario.setDNF(DNF);
		funcionario.setEhAdm(ehAdmin);
     }

}
