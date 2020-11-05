package model;

import java.util.Calendar;
import java.util.Date;

public class PagamentoIntegral implements Comparable<PagamentoIntegral> {
	private int nCDA;
	private double valorPgto;
	private Date dataPagamento;

	private long idPagamento; // eh criado com a seguinte formula: NCDA + 0 + dataPgto (formato DDMM) -
								// primary key

	public PagamentoIntegral(int nCDA, double valorPgto, Date dataPagamento) {
		super();
		this.nCDA = nCDA;
		this.valorPgto = valorPgto;
		this.dataPagamento = dataPagamento;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataPagamento);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);

		String idPagStr = Integer.toString(nCDA) + "0" + Integer.toString(dia) + Integer.toString(mes);

		this.idPagamento = Integer.parseInt(idPagStr);
	}

	public int getnCDA() {
		return nCDA;
	}

	public void setnCDA(int nCDA) {
		this.nCDA = nCDA;
	}

	public double getValorPgto() {
		return valorPgto;
	}

	public void setValorPgto(double valorPgto) {
		this.valorPgto = valorPgto;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(long idPagamento) {
		this.idPagamento = idPagamento;
	}

	@Override
	public int compareTo(PagamentoIntegral pagI) {
		if (this.getDataPagamento().before(pagI.getDataPagamento())) {
			return -1;
		} else if (this.getDataPagamento().after(pagI.getDataPagamento())) {
			return +1;
		} else {
			return 0;
		}
	}
}
