package br.com.compilador.analisadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.com.compilador.enums.CategoriaSemantica;
import br.com.compilador.enums.TipoSemantico;
import br.com.compilador.model.PilhaErros;
import br.com.compilador.model.SimbolosSemanticos;
import br.com.compilador.view.PrincipalForm;

public class AnalisadorSemantico {

	private List<SimbolosSemanticos> listSimbolos;
	private Stack<PilhaErros> pilhaErros = new Stack<PilhaErros>();
	private SimbolosSemanticos simbolosSemanticos;
	private CategoriaSemantica categoriaSemantica;
	private int nivel = 1;
	private String identificador;
	private String ultimoIdentificador;

	public AnalisadorSemantico() {
		this.listSimbolos = new ArrayList<SimbolosSemanticos>();
	}

	public Stack<PilhaErros> getErros() {

		if (!pilhaErros.isEmpty()) {
			return pilhaErros;
		}

		return null;
	}

	public void analisar(int cod, String simbolo, int linha) {
		if (!pilhaErros.isEmpty()) {
			return;
		}

		simbolosSemanticos = new SimbolosSemanticos();
		simbolosSemanticos.setNome(simbolo);
		simbolosSemanticos.setNivel(nivel);
		simbolosSemanticos.setTipoSemantico(TipoSemantico.INTEIRO);
		simbolosSemanticos.setCategoriaSemantica(categoriaSemantica);

		switch (cod) {
		case 1: // PROGRAM
			categoriaSemantica = CategoriaSemantica.PROGRAMA;
			break;
		case 2: // LABEL
			categoriaSemantica = CategoriaSemantica.ROTULO;
			break;
		case 3: // CONST
			categoriaSemantica = CategoriaSemantica.CONSTANTE;
			break;
		case 4: // VAR
			categoriaSemantica = CategoriaSemantica.VARIAVEL;
			break;
		case 5: // PROCEDURE
			categoriaSemantica = CategoriaSemantica.PROCEDURE;
			// nivel++;
			break;
		case 6: // BEGIN
			categoriaSemantica = CategoriaSemantica.DESCONHECIDA;
			break;
		case 25: // IDENTIFICADOR
			ultimoIdentificador = simbolo;
			if (validaIdentificador(simbolosSemanticos, linha)) {
				inserirIdentificador(simbolosSemanticos);
			}
			break;
		case 38: // :=
			if (categoriaSemantica.equals(CategoriaSemantica.DESCONHECIDA)) {
				identificador = ultimoIdentificador;
			}
			break;
		}
	}

	private boolean validaIdentificador(SimbolosSemanticos simbolosSemanticos, int linha) {
		switch (categoriaSemantica) {
		case PROCEDURE:
			if (!buscar(simbolosSemanticos.getNome(), simbolosSemanticos.getNivel(), CategoriaSemantica.PROCEDURE)) {
				pilhaErros.add(new PilhaErros(
						"A procedure " + simbolosSemanticos.getNome() + " já foi declarada. Erro na linha ", linha));
				return false;
			}
			categoriaSemantica = CategoriaSemantica.PARAMETRO; // Se validou, o próx é parâmetro.
			nivel++;
			break;
		case PARAMETRO:
			if (!buscar(simbolosSemanticos.getNome(), simbolosSemanticos.getNivel(), CategoriaSemantica.PARAMETRO)) {
				pilhaErros.add(new PilhaErros("O parâmetro " + simbolosSemanticos.getNome()
						+ " já foi declarada na procedure. Erro na linha ", linha));
				return false;
			}
			break;
		case CONSTANTE:
		case VARIAVEL:
		case ROTULO:
			if (!buscar(simbolosSemanticos.getNome(), simbolosSemanticos.getNivel(), CategoriaSemantica.CONSTANTE)
					|| !buscar(simbolosSemanticos.getNome(), simbolosSemanticos.getNivel(), CategoriaSemantica.VARIAVEL)
					|| !buscar(simbolosSemanticos.getNome(), simbolosSemanticos.getNivel(),
							CategoriaSemantica.ROTULO)) {
				pilhaErros.add(new PilhaErros(
						"O identificador " + simbolosSemanticos.getNome() + " já foi declarado. Erro na linha ",
						linha));
				return false;
			}
			break;
		default:
			if (!validaDeclaracao(simbolosSemanticos)
					&& !simbolosSemanticos.getCategoriaSemantica().equals(CategoriaSemantica.PROGRAMA)) {
				pilhaErros.add(new PilhaErros(
						"O identificador " + simbolosSemanticos.getNome() + " não foi declarado. Erro na linha ",
						linha));
				return false;
			}
			if (identificador != null && !getTipoIdentificadorSemantico(identificador)
					.equals(getTipoIdentificadorSemantico(simbolosSemanticos.getNome()))) {
				pilhaErros.add(new PilhaErros(
						"A atribuição de valor " + simbolosSemanticos.getNome() + " não é compatível com "+ getTipoIdentificadorSemantico(identificador).toString() +". Erro na linha ",
						linha));
				return false;
			}
			if (identificador != null && !getTipoIdentificadorSemantico(simbolosSemanticos.getNome()).equals(TipoSemantico.INTEIRO)) {
				pilhaErros.add(new PilhaErros(
						"O argumento de valor " + simbolosSemanticos.getNome() + " não é compatível com a procedure "+ identificador +". Erro na linha ",
						linha));
				return false;
			}
		}

		return true;
	}

	private boolean validaDeclaracao(SimbolosSemanticos simbolosSemanticos) {
		for (SimbolosSemanticos listSimbolo : listSimbolos) {
			if (simbolosSemanticos.getNome().equalsIgnoreCase(listSimbolo.getNome())) {
				return true;
			}
		}

		return false;
	}

	private boolean buscar(String nome, int nivel, CategoriaSemantica categoriaSemantica) {
		for (SimbolosSemanticos simbolo : listSimbolos) {
			
			if(simbolo.getCategoriaSemantica().equals(CategoriaSemantica.PROCEDURE)) {
				if (simbolo.getNome().equalsIgnoreCase(nome) && simbolo.getCategoriaSemantica().equals(categoriaSemantica)) {
					return false;
				}
			}else {
				if (simbolo.getNome().equalsIgnoreCase(nome) && simbolo.getNivel() == nivel
						&& simbolo.getCategoriaSemantica().equals(categoriaSemantica)) {
					return false;
				}
			}
		}

		return true;
	}

	private TipoSemantico getTipoIdentificadorSemantico(String simbolo) {
		for (SimbolosSemanticos simbolos : listSimbolos) {
			if (simbolos.getNome().equalsIgnoreCase(simbolo)) {
				return simbolos.getTipoSemantico();
			}
		}

		return null;
	}

	private void inserirIdentificador(SimbolosSemanticos simbolosSemanticos) {
		if (!simbolosSemanticos.getCategoriaSemantica().equals(CategoriaSemantica.PROGRAMA)
				&& !simbolosSemanticos.getCategoriaSemantica().equals(CategoriaSemantica.DESCONHECIDA)) {
			if (categoriaSemantica.equals(CategoriaSemantica.ROTULO)) {
				simbolosSemanticos.setTipoSemantico(TipoSemantico.LITERAL);
			}
			listSimbolos.add(simbolosSemanticos);
			PrincipalForm.tableAnalisadorSematico.adicionarLinha(
					new Object[] { simbolosSemanticos.getNome(), simbolosSemanticos.getCategoriaSemantica(),
							simbolosSemanticos.getTipoSemantico(), simbolosSemanticos.getNivel() });
		}
	}

	public List<SimbolosSemanticos> getListaSemantica() {
		return listSimbolos;
	}

	public CategoriaSemantica getCategoria(String simbolo) {
		if (simbolo.equalsIgnoreCase(CategoriaSemantica.CONSTANTE.getNome())) {
			return CategoriaSemantica.CONSTANTE;
		} else if (simbolo.equalsIgnoreCase(CategoriaSemantica.PROCEDURE.getNome())) {
			return CategoriaSemantica.PROCEDURE;
		} else if (simbolo.equalsIgnoreCase(CategoriaSemantica.ROTULO.getNome())) {
			return CategoriaSemantica.ROTULO;
		} else if (simbolo.equalsIgnoreCase(CategoriaSemantica.VARIAVEL.getNome())) {
			return CategoriaSemantica.VARIAVEL;
		}

		return null;
	}
}
