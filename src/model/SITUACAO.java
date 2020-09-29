package model;

public enum SITUACAO {
	    EMABERTO(1), 
	    QUITADO(2), 
	    CANCELADO(3), 
	    PARCELADO(4);

	private int codSituacao;

	SITUACAO(int codSituacao) {
		this.codSituacao = codSituacao;
	}

	public int getCodImposto() {
		return codSituacao;
	}

	public void setCodImposto(int codSituacao) {
		this.codSituacao = codSituacao;
	}
}
