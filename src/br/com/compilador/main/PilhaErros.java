package br.com.compilador.main;

public class PilhaErros {
	private int codigo, linha;

	public PilhaErros(int codigo, int linha) {
		super();
		this.codigo = codigo;
		this.linha = linha;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

}