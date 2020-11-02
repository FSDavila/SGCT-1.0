package model;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class PagamentoParcela implements Comparable<PagamentoParcela>{
	private int identificacaoPCDA; // para o sistema poder identificar em qual parcelamento / parcela anexar o pagamento quando alimentando com planilhas externas
	private int nParcela;
	private double valorParcela;
	private Date dataPagamento;
	private long idPagamento; // eh criado com a seguinte formula: NPCDA + 0 + nParcela + dataPgto(formato DDMM)
	
	public PagamentoParcela(int identificacaoPCDA, int nParcela, double valorParcela, Date dataPagamento) {
		super();
		this.identificacaoPCDA = identificacaoPCDA;
		this.nParcela = nParcela;
		this.valorParcela = valorParcela;
		this.dataPagamento = dataPagamento;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataPagamento);
		
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);
		
		String idPagStr = Integer.toString(identificacaoPCDA) + "0" + Integer.toString(nParcela) + Integer.toString(dia) + Integer.toString(mes);
			
		this.idPagamento = Integer.parseInt(idPagStr);
	}
	
	public int getIdentificacaoPCDA() {
		return identificacaoPCDA;
	}

	public void setIdentificacaoPCDA(int identificacaoPCDA) {
		this.identificacaoPCDA = identificacaoPCDA;
	}

	public int getnParcela() {
		return nParcela;
	}
	public void setnParcela(int nParcela) {
		this.nParcela = nParcela;
	}
	public double getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
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
	public int compareTo(PagamentoParcela pagP) {
		if (this.getIdentificacaoPCDA() < pagP.getIdentificacaoPCDA()) {
			return -1;
		} else if (this.getIdentificacaoPCDA() > pagP.getIdentificacaoPCDA()) {
			return +1;
		}
		return 0;
	}

}
