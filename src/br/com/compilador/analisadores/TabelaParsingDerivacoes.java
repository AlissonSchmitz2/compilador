package br.com.compilador.analisadores;

public enum TabelaParsingDerivacoes {

	DERIVACOES_1(getArrayString("PROGRAM|IDENTIFICADOR|;|BLOCO|.")),
	DERIVACOES_2(getArrayString("DCLROT|DCLCONST|DCLVAR|DCLPROC|CORPO"));
//    REGRA1(new Integer[]{1, 25, 47, 54, 49}),
	
    private final String[] derivacoes;
	
    private TabelaParsingDerivacoes(String[] derivacoes) {
        this.derivacoes = derivacoes;
    }
    
    public String[] getDerivacoes() {
        return derivacoes;
    }
    
	public static String[] getArrayString(String string) {		
		return string.replace("|", "!").split("!");
	}
}
