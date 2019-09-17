package br.com.compilador.analisadores;

import java.util.HashMap;
import java.util.Map;

public class TokensNaoTerminais {
	public static HashMap<String, Integer> mapTokens = new HashMap<String, Integer>();

	public TokensNaoTerminais() {
		criarTokens();
	}

	public void criarTokens() {
		mapTokens.put("PROGRAMA", 52);
		mapTokens.put("BLOCO", 53);
		mapTokens.put("DCLROT", 54);
		mapTokens.put("LID", 55);
		mapTokens.put("REPIDENT", 56);
		mapTokens.put("DCLCONST", 57);
		mapTokens.put("LDCONST", 58);
		mapTokens.put("DCLVAR", 59);
		mapTokens.put("LDVAR", 60);
		mapTokens.put("TIPO", 61);
		mapTokens.put("DCLPROC", 62);
		mapTokens.put("DEFPAR", 63);
		mapTokens.put("CORPO", 64);
		mapTokens.put("REPCOMANDO", 65);
		mapTokens.put("COMANDO", 66);
		mapTokens.put("RCOMID", 67);
		mapTokens.put("RVAR", 68);
		mapTokens.put("PARAMETROS", 69);
		mapTokens.put("REPPAR", 70);
		mapTokens.put("ELSEPARTE", 71);
		mapTokens.put("VARIAVEL", 72);
		mapTokens.put("VARIAVEL1", 73);
		mapTokens.put("REPVARIAVEL", 74);
		mapTokens.put("ITEMSAIDA", 75);
		mapTokens.put("REPITEM", 76);
		mapTokens.put("EXPRESSAO", 77);
		mapTokens.put("REPEXPSIMP", 78);
		mapTokens.put("EXPSIMP", 79);
		mapTokens.put("REPEXP", 80);
		mapTokens.put("TERMO", 81);
		mapTokens.put("REPTERMO", 82);
		mapTokens.put("FATOR", 83);
		mapTokens.put("CONDCASE", 84);
		mapTokens.put("CONTCASE", 85);
		mapTokens.put("RPINTEIRO", 86);
		mapTokens.put("SEMEFEITO", 87);
	}

	public int getCodToken(String simbolo) {
		if (mapTokens.containsKey(simbolo.toUpperCase())) {
			return mapTokens.get(simbolo.toUpperCase());
		}

		return 0;
	}

	public String getSimbolo(int cod) {
		if (mapTokens.containsValue(cod)) {
			for (Map.Entry<String, Integer> candidatoEntry : mapTokens.entrySet()) {
				if (candidatoEntry.getValue().equals(cod)) {
					return candidatoEntry.getKey();
				}
			}
		}

		return null;
	}
}
