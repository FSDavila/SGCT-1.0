package model;

public enum TIPOIMPOSTO {
	    ICMS(1), 
	    ICMSSIMPLES(2), 
	    ITCMD(3), 
	    FATMA(4), 
	    PMAMBIENTAL(5), 
	    TCE(6);

	private int codImposto;

	TIPOIMPOSTO(int codImposto) {
		this.codImposto = codImposto;
	}

	public int getCodImposto() {
		return codImposto;
	}

	public void setCodImposto(int codImposto) {
		this.codImposto = codImposto;
	}
}
