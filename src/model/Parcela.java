package model;

import java.io.Serializable;
import java.util.Date;

public class Parcela implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int nParcela;
	private double valorParcela;
	private Date dataVencimento;
	private PagamentoParcela pagamento; //inicia como null
	private boolean quitado;
	
	public Parcela(int nParcela, double valorParcela, Date dataVencimento, boolean quitado) {
		super();
		this.nParcela = nParcela;
		this.valorParcela = valorParcela;
		this.quitado = quitado;
		this.dataVencimento = dataVencimento;
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
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public PagamentoParcela getPagamento() {
		return pagamento;
	}
	public void setPagamento(PagamentoParcela pagamento) {
		this.pagamento = pagamento;
	}
	public boolean isQuitado() {
		return quitado;
	}
	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}
	
}
