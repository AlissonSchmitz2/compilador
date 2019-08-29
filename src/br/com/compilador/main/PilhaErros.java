package br.com.compilador.main;

public class PilhaErros {
	
//	int codigo;
	int linha;
	String simbolo;
	String erro;
	


	PilhaErros( int linha, String simbolo, String erro){
//		this.codigo = codigo;
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
	
//	public int getCodigo() {
//		return codigo;
//	}
//	
//	public void setCodigo(int codigo) {
//		this.codigo = codigo;
//	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

}
