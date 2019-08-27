package br.com.compilador.token;

import java.util.HashMap;
import java.util.Map;

public class Tokens {

	public static HashMap<String, Integer> mapTokens = new HashMap<String, Integer>();

	public Tokens() {
		criarTokens();
	}

	public void criarTokens() {
		mapTokens.put("PROGRAM", 1);
		mapTokens.put("LABEL", 2);
		mapTokens.put("CONST", 3);
		mapTokens.put("VAR", 4);
		mapTokens.put("PROCEDURE", 5);
		mapTokens.put("BEGIN", 6);
		mapTokens.put("END", 7);
		mapTokens.put("INTEGER", 8);
		mapTokens.put("ARRAY", 9);
		mapTokens.put("OF", 10);
		mapTokens.put("CALL", 11);
		mapTokens.put("GOTO", 12);
		mapTokens.put("IF", 13);
		mapTokens.put("THEN", 14);
		mapTokens.put("ELSE", 15);
		mapTokens.put("WHILE", 16);
		mapTokens.put("DO", 17);
		mapTokens.put("REPEAT", 18);
		mapTokens.put("UNTIL", 19);
		mapTokens.put("READLN", 20);
		mapTokens.put("WRITELN", 21);
		mapTokens.put("OR", 22);
		mapTokens.put("AND", 23);
		mapTokens.put("NOT", 24);
//		mapTokens.put("IDENTIFICADOR", 25);
//		mapTokens.put("INTEIRO", 26);
		mapTokens.put("FOR", 27);
		mapTokens.put("TO", 28);
		mapTokens.put("CASE", 29);
		mapTokens.put("+", 30);
		mapTokens.put("-", 31);
		mapTokens.put("*", 32);
		mapTokens.put("/", 33);
		mapTokens.put("[", 34);
		mapTokens.put("]", 35);
		mapTokens.put("(", 36);
		mapTokens.put(")", 37);
		mapTokens.put(":=", 38);
		mapTokens.put(":", 39);
		mapTokens.put("=", 40);
		mapTokens.put(">", 41);
		mapTokens.put(">=", 42);
		mapTokens.put("<", 43);
		mapTokens.put("<=", 44);
		mapTokens.put("<>", 45);
		mapTokens.put(",", 46);
		mapTokens.put(";", 47);
//		mapTokens.put("LITERAL", 48);
		mapTokens.put(".", 49);
		mapTokens.put("..", 50);
		mapTokens.put("$", 51);
	}

	public int getCodToken(String simbolo) {
		String simboloAux = simbolo;
		simboloAux.toUpperCase();

		if (mapTokens.containsKey(simboloAux)) {
			return mapTokens.get(simbolo);
		}

		return 25;
	}

	public String getSimbolo(int cod) {
		if (mapTokens.containsValue(cod)) {
			for (Map.Entry<String, Integer> candidatoEntry : mapTokens.entrySet()) {
				if(candidatoEntry.getValue().equals(cod)) {
					return candidatoEntry.getKey();
				}
			}
		}

		return null;
	}

}
