package br.com.compilador.teste;

import br.com.compilador.analisadores.TabelaParsingMatriz;

public class teste {

	public static void main(String[] args) {

		String[] regras = null;
        for (TabelaParsingMatriz matrizAnalise : TabelaParsingMatriz.values()) {
            regras = matrizAnalise.getDerivacao(53, 2);

            if (regras != null) {
            	for(String a : regras) {
            		System.out.println(a);
            	}
            }
        }
		
	}

}
