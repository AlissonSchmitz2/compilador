package br.com.compilador.model;

import br.com.compilador.enums.CategoriaSemantica;
import br.com.compilador.enums.TipoSemantico;

public class SimbolosSemanticos {

	private String nome;
	private CategoriaSemantica categoriaSemantica;
	private TipoSemantico tipoSemantico;
	private int nivel;
	
	public SimbolosSemanticos() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CategoriaSemantica getCategoriaSemantica() {
		return categoriaSemantica;
	}

	public void setCategoriaSemantica(CategoriaSemantica categoriaSemantica) {
		this.categoriaSemantica = categoriaSemantica;
	}

	public TipoSemantico getTipoSemantico() {
		return tipoSemantico;
	}

	public void setTipoSemantico(TipoSemantico tipoSemantico) {
		this.tipoSemantico = tipoSemantico;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
}
