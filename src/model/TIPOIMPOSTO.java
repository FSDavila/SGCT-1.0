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
	
	public String getTipoAsString() {
		switch(this.codImposto) {
		case 1: 
			return "ICMS";
		case 2:
			return "ICMSSIMPLES";
		case 3:
			return "ITCMD";
		case 4:
			return "FATMA";
		case 5:
			return "PMAMBIENTAL";
		case 6:
			return "TCE";
		}
		return null;
	}

	public void setCodImposto(int codImposto) {
		this.codImposto = codImposto;
	}
}
