package br.com.compilador.teste;

import java.util.HashMap;
import java.util.Map;

import br.com.compilador.analisadores.TokensTerminais;

public class teste {

	public static void main(String[] args) {
		
		TokensTerminais terminais = new TokensTerminais();
		HashMap<String, Integer> mapTokens = terminais.getMap();
		String palavra = "";
		
		for (Map.Entry<String, Integer> candidatoEntry : mapTokens.entrySet()) { 
			if(candidatoEntry.getValue() < 30 && !candidatoEntry.getKey().equals("INTEIRO") && !candidatoEntry.getKey().equals("IDENTIFICADOR")) {
				palavra += candidatoEntry.getKey() + "|";
			}
		}
		
		System.out.println(palavra);

	}
}
