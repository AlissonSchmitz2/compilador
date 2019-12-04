package br.com.compilador.enums;

public enum CategoriaSemantica {
	VARIAVEL("VAR"),
	CONSTANTE("CONST"),
	PROCEDURE("PROCEDURE"),
	ROTULO("LABEL"),
	PARAMETRO("PARAMETRO"),
	PROGRAMA("PROGRAMA"),
	DESCONHECIDA("DESCONHECIDA");
	
	private String nome;

	private CategoriaSemantica(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}
}
