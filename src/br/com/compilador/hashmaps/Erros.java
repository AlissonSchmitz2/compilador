package br.com.compilador.hashmaps;

import java.util.HashMap;
import java.util.Map;

public class Erros {
	public static HashMap<String, Integer> mapErros = new HashMap<String, Integer>();

	public Erros() {
		criarTokens();
	}

	public void criarTokens() {
		mapErros.put("Erro de caractere invalido na linha", 1);
		mapErros.put("Erro de não fehamento de comentário na linha", 2);
		mapErros.put("Erro de não fehamento de literal na linha", 3);
		mapErros.put("Erro de número fora de escala na linha", 3);
		
	}

	public String getSimbolo(int cod) {
		if (mapErros.containsValue(cod)) {
			for (Map.Entry<String, Integer> candidatoEntry : mapErros.entrySet()) {
				if (candidatoEntry.getValue().equals(cod)) {
					return candidatoEntry.getKey();
				}
			}
		}

		return null;
	}

}
