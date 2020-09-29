package model;

import java.util.ArrayList;
import java.util.Date;

public class CDA {
	
	private Integer nCDA;
	private double valor;
	private Imposto tipoImposto;
	private Date dataVencimento;
	private String descricao;
	private Contribuinte titular;
	private SITUACAO situacaoCDA;
	private ArrayList<CDA> dividas = new ArrayList<>();
	
	public CDA(int nCDA, double valor, Imposto tipoImposto, Date dataVencimento, String descricao,
			Contribuinte titular, SITUACAO situacaoCDA) {
		super();
		this.nCDA = nCDA;
		this.valor = valor;
		this.tipoImposto = tipoImposto;
		this.dataVencimento = dataVencimento;
		this.descricao = descricao;
		this.titular = titular;
		this.situacaoCDA = situacaoCDA;
	}
	
	public int getNCDA() {
		return nCDA;
	}
	public void setNCDA(int nCDA) {
		this.nCDA = nCDA;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Imposto getTipoImposto() {
		return tipoImposto;
	}
	public void setTipoImposto(Imposto tipoImposto) {
		this.tipoImposto = tipoImposto;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Contribuinte getTitular() {
		return titular;
	}
	public void setTitular(Contribuinte titular) {
		this.titular = titular;
	}
	public SITUACAO getSituacaoCDA() {
		return situacaoCDA;
	}
	public void setSituacaoCDA(int situacao) {
		switch(situacao) {
			case 1:
				this.situacaoCDA = SITUACAO.EMABERTO;
				break;
			case 2:
				this.situacaoCDA = SITUACAO.QUITADO;
				break;	
			case 3:
				this.situacaoCDA = SITUACAO.CANCELADO;
				break;	
			case 4:
				this.situacaoCDA = SITUACAO.PARCELADO;
				break;		
		}

	}

	public ArrayList<CDA> getDividas() {
		return dividas;
	}

	public void setDividas(ArrayList<CDA> dividas) {
		this.dividas = dividas;
	}
	
	
}
