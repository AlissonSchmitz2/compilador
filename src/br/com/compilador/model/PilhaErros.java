package br.com.compilador.model;

public class PilhaErros {
	private String erro;
	private int linha;

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public PilhaErros(String erro, int linha) {
		super();
		this.erro = erro;
		this.linha = linha;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

}