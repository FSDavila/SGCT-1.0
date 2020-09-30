package model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

public class CDA implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer nCDA;
	private double valor;
	private TIPOIMPOSTO tipoImposto;
	private Date dataVencimento;
	private String descricao;
	private Contribuinte titular;
	private SITUACAO situacaoCDA;
	private ArrayList<CDA> dividas = new ArrayList<>();
	
	public CDA(int nCDA, double valor, int tipoImposto, Date dataVencimento, String descricao,
			Contribuinte titular, int situacaoCDA) {
		super();
		setSituacaoCDA(situacaoCDA);
		setTipoImposto(tipoImposto);
		this.nCDA = nCDA;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.descricao = descricao;
		this.titular = titular;
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
