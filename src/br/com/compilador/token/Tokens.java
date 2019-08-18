package br.com.compilador.token;

import java.util.HashMap;
import java.util.Map;

public class Tokens {

	public static HashMap<String, Integer> mapTokens = new HashMap<String, Integer>();

	public Tokens() {
		criarTokens();
	}

	public void criarTokens() {
		mapTokens.put("Program", 1);
		mapTokens.put("Label", 2);
		mapTokens.put("Const", 3);
		mapTokens.put("Var", 4);
		mapTokens.put("Procedure", 5);
		mapTokens.put("Begin", 6);
		mapTokens.put("End", 7);
		mapTokens.put("Integer", 8);
		mapTokens.put("Array", 9);
		mapTokens.put("Of", 10);
		mapTokens.put("Call", 11);
		mapTokens.put("Goto", 12);
		mapTokens.put("If", 13);
		mapTokens.put("Then", 14);
		mapTokens.put("Else", 15);
		mapTokens.put("While", 16);
		mapTokens.put("Do", 17);
		mapTokens.put("Repeat", 18);
		mapTokens.put("Until", 19);
		mapTokens.put("Readln", 20);
		mapTokens.put("Writeln", 21);
		mapTokens.put("Or", 22);
		mapTokens.put("And", 23);
		mapTokens.put("Not", 24);
		mapTokens.put("Identificador", 25);
		mapTokens.put("Inteiro", 26);
		mapTokens.put("For", 27);
		mapTokens.put("To", 28);
		mapTokens.put("Case", 29);
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
		mapTokens.put("literal", 48);
		mapTokens.put(".", 49);
		mapTokens.put("..", 50);
		mapTokens.put("$", 51);
	}

	public int getCodToken(String simbolo) {

		if (mapTokens.containsKey(simbolo)) {
			return mapTokens.get(simbolo);
		}

		return 0;
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
