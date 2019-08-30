package br.com.compilador.main;

public class Pilha {
	private int codigo, linha;
	private String simbolo;
	
	public Pilha(int codigo, int linha, String simbolo){
		this.codigo = codigo;
		this.simbolo = simbolo;
		this.linha = linha;
	}
	
	public void setLinha(int linha) {
		this.linha = linha;
	}
	
	public int getLinha() {
		return linha;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

}
