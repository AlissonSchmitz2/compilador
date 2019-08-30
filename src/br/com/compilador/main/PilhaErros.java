package br.com.compilador.main;

public class PilhaErros {
	private int linha;
	private String simbolo, erro;

	public PilhaErros(int linha, String simbolo, String erro) {
		this.simbolo = simbolo;
		this.linha = linha;
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getLinha() {
		return linha;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
}
