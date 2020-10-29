package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PCDA {
	private int identificacao;
	private Contribuinte titular;
	private double valor;
	private TIPOIMPOSTO tipoImposto;
	private boolean quitado;
	private Parcela[] parcelas;
	private ArrayList<CDA> CDASParceladas;

	public PCDA(int nParcelas, Contribuinte titular, int identificacao, double valor, int tipoImposto, boolean quitado, ArrayList<CDA> CDASParceladas,
			Date dataVencimento) {
		super();
		this.titular = titular;
		this.identificacao = identificacao;
		this.valor = valor;
		setTipoImposto(tipoImposto);
		this.quitado = quitado;
		this.parcelas = new Parcela[nParcelas];
		this.CDASParceladas = CDASParceladas;

		// abaixo acontece o processo para criacao das parcelas
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataVencimento);

		double valorParcela = valor / nParcelas;
		for (int i = 0; i < nParcelas; i++) { // gera as parcelas no array

			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			
			if(dia > 30) { // nao pode emitir com data pro dia 31, entao automaticamente eh ajustado isso aqui
				dia = 30;
			}
			
			int mes = calendar.get(Calendar.MONTH);
			int ano = calendar.get(Calendar.YEAR);

			if (mes + i <= 12) { // se nao virar o ano
				calendar.set(ano, mes + i, dia); // seta a dt de vencimento pra iteracao atual
			} else {
				calendar.set(ano + 1, mes - 11, dia); // caso vire o ano, soma +1 ano e subtrai 11 meses (janeiro)
			}

			if (mes == 2 && dia > 28) {
				dia = 28; // pega a questao de fevereiro que tem menos de 30 dias, ajusta a data pro
							// ultimo dia de fev
				calendar.set(ano, mes + i, dia);
			}

			Date dataVencimentoParcelaAtual = calendar.getTime();

			parcelas[i] = new Parcela(i, valorParcela, dataVencimentoParcelaAtual, false);

			if (mes == 2 && dia == 28) {

				Calendar original = Calendar.getInstance();
				original.setTime(dataVencimento);
				int diaOriginal = calendar.get(Calendar.DAY_OF_MONTH); // p corrigir dia pras proximas iter. no caso de
																		// ter feveireiro na iteracao atual
				calendar.set(ano, mes, diaOriginal);

			}
		}
	}

	public int getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(int identificacao) {
		this.identificacao = identificacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public TIPOIMPOSTO getTipoImposto() {
		return tipoImposto;
	}

	public void setTipoImposto(int tipoImposto) {
		switch(tipoImposto) {
		case 1:
			this.tipoImposto = TIPOIMPOSTO.ICMS;
			break;
		case 2:
			this.tipoImposto = TIPOIMPOSTO.ICMSSIMPLES;
			break;	
		case 3:
			this.tipoImposto = TIPOIMPOSTO.ITCMD;
			break;	
		case 4:
			this.tipoImposto = TIPOIMPOSTO.FATMA;
			break;	
		case 5:
			this.tipoImposto = TIPOIMPOSTO.PMAMBIENTAL;
			break;	
		case 6:
			this.tipoImposto = TIPOIMPOSTO.TCE;
			break;	
		}
	}

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public Parcela[] getParcelas() {
		return parcelas;
	}

	public void setParcelas(Parcela[] parcelas) {
		this.parcelas = parcelas;
	}

	public ArrayList<CDA> getCDASParceladas() {
		return CDASParceladas;
	}

	public void setCDASParceladas(ArrayList<CDA> cDASParceladas) {
		CDASParceladas = cDASParceladas;
	}

}
