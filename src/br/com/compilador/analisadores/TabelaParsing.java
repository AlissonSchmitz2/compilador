package br.com.compilador.analisadores;

import java.util.ArrayList;

import br.com.compilador.util.Msg;

public class TabelaParsing {
	private ArrayList<String> listDerivacao = new ArrayList<String>();

	public TabelaParsing() {
	}

	public ArrayList<String> analisarValor(int codNaoTerminal, int codTerminal) {
		switch (codNaoTerminal) {
		case 52:
			if (codTerminal == 1) {
				listDerivacao.add("PROGRAM");
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add(";");
				listDerivacao.add("BLOCO");
				listDerivacao.add(".");
			}
			break;
		case 53:
			if (isBetween(codTerminal, 2, 6)) {
				listDerivacao.add("DCLROT");
				listDerivacao.add("DCLCONST");
				listDerivacao.add("DCLVAR");
				listDerivacao.add("DCLPROC");
				listDerivacao.add("CORPO");
			}
			break;
		case 54:
			if (codTerminal == 2) {
				listDerivacao.add("LABEL");
				listDerivacao.add("LID");
				listDerivacao.add(";");
			} else if (isBetween(codTerminal, 3, 6)) {
				listDerivacao = null;
			}
			break;
		case 55:
			if (codTerminal == 25) {
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add("REPIDENT");
			}
			break;
		case 56:
			if (codTerminal == 39 || codTerminal == 47) {
				listDerivacao = null;
			} else if (codTerminal == 46) {
				listDerivacao.add(",");
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add("REPIDENT");
			}
			break;
		case 57:
			if (codTerminal == 3) {
				listDerivacao.add("CONST");
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add("=");
				listDerivacao.add("INTEIRO");
				listDerivacao.add(";");
				listDerivacao.add("LDCONST");
			} else if (isBetween(codTerminal, 4, 6)) {
				listDerivacao = null;
			}
			break;
		case 58:
			if (isBetween(codTerminal, 4, 6)) {
				listDerivacao = null;
			} else if (codTerminal == 25) {
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add("=");
				listDerivacao.add("INTEIRO");
				listDerivacao.add(";");
				listDerivacao.add("LDCONST");
			}
			break;
		case 59:
			if (codTerminal == 4) {
				listDerivacao.add("VAR");
				listDerivacao.add("LID");
				listDerivacao.add(":");
				listDerivacao.add("TIPO");
				listDerivacao.add(";");
				listDerivacao.add("LDVAR");
			} else if (codTerminal == 5 || codTerminal == 6) {
				listDerivacao = null;
			}
			break;
		case 60:
			if (codTerminal == 5 || codTerminal == 6) {
				listDerivacao = null;
			}else if(codTerminal == 25) {
				listDerivacao.add("LID");
				listDerivacao.add(":");
				listDerivacao.add("TIPO");
				listDerivacao.add(";");
				listDerivacao.add("LDVAR");
			}
			break;
		case 61:
			if (codTerminal == 8) {
				listDerivacao.add("INTEGER");
			}else if(codTerminal == 9) {
				listDerivacao.add("ARRAY");
				listDerivacao.add("[");
				listDerivacao.add("INTEIRO");
				listDerivacao.add("..");
				listDerivacao.add("INTEIRO");
				listDerivacao.add("]");
				listDerivacao.add("OF");
				listDerivacao.add("INTEGER");
			}
			break;
		case 62:
			if (codTerminal == 5) {
				listDerivacao.add("PROCEDURE");
				listDerivacao.add("IDENTIFICADOR");
				listDerivacao.add("DEFPAR");
				listDerivacao.add(";");
				listDerivacao.add("BLOCO");
				listDerivacao.add(";");
				listDerivacao.add("DCLPROC");
			}else if(codTerminal == 6) {
				listDerivacao = null;
			}
			break;
		case 63:
			if(codTerminal == 36) {
				listDerivacao.add("(");
				listDerivacao.add("LID");
				listDerivacao.add(":");
				listDerivacao.add("INTEGER");
				listDerivacao.add(")");
			}
			break;
		default:
			new Msg().mensagemErro("Nenhuma combinação entontrada");
		}

		return listDerivacao;
	}

	public static boolean isBetween(int num, int abaixo, int acima) {
		return abaixo <= num && num <= acima;
	}

}
