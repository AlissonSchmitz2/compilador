package br.com.compilador.enums;

public enum TipoSemantico {
	LITERAL("literal"),
	INTEIRO("inteiro");
	
	private String nome;

	private TipoSemantico(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}
}
