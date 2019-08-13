package br.com.compilador.teste;

import br.com.compilador.token.Tokens;

public class Principal {

	public static void main(String[] args) {
		Tokens tokens = new Tokens();
		int i = tokens.getCodToken(")");
		System.out.println(i);
	}

}
