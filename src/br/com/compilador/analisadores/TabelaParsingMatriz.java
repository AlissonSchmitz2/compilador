package br.com.compilador.analisadores;

import br.com.compilador.analisadores.TabelaParsingDerivacoes;;

public enum TabelaParsingMatriz {

	MATRIZ_53_1(53, 1, TabelaParsingDerivacoes.DERIVACOES_1.getDerivacoes()),
	MATRIZ_53_2(53, 2, TabelaParsingDerivacoes.DERIVACOES_2.getDerivacoes()),
//	MATRIZ_53_3(53,3);
	
    private int terminal, naoTerminal;
    private String[] regra;
    
    public String[] getDerivacao(int naoTerminal, int terminal){
    	if (this.naoTerminal == naoTerminal && this.terminal == terminal) {
            return regra;
        }
    	
    	return null;
    }
    
    private TabelaParsingMatriz(int naoTerminal, int terminal, String[] regra) {
        this.naoTerminal = naoTerminal;
        this.terminal = terminal;
        this.regra = regra;
    }
}
