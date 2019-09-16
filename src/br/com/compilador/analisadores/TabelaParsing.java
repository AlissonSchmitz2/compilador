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
				addLista("PROGRAM|IDENTIFICADOR|;|BLOCO|.");
				break;
			}
		case 53:
			if (isBetween(codTerminal, 2, 6)) {
				addLista("DCLROT|DCLCONST|DCLVAR|DCLPROC|CORPO");
				break;
			}
		case 54:
			if (codTerminal == 2) {
				addLista("LABEL|LID|;");
				break;
			} else if (isBetween(codTerminal, 3, 6)) {
				listDerivacao = null;
				break;
			}
		case 55:
			if (codTerminal == 25) {
				addLista("IDENTIFICADOR|REPIDENT");
				break;
			}
		case 56:
			if (codTerminal == 39 || codTerminal == 47) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 46) {
				addLista(",|IDENTIFICADOR|REPIDENT");
				break;
			}
		case 57:
			if (codTerminal == 3) {
				addLista("CONST|IDENTIFICADOR|=|INTEIRO|;|LDCONST");
				break;
			} else if (isBetween(codTerminal, 4, 6)) {
				listDerivacao = null;
				break;
			}
		case 58:
			if (isBetween(codTerminal, 4, 6)) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 25) {
				addLista("IDENTIFICADOR|=|INTEIRO|;|LDCONST");
				break;
			}
		case 59:
			if (codTerminal == 4) {
				addLista("VAR|LID|:|TIPO|;|LDVAR");
				break;
			} else if (codTerminal == 5 || codTerminal == 6) {
				listDerivacao = null;
				break;
			}
		case 60:
			if (codTerminal == 5 || codTerminal == 6) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 25) {
				addLista("LID|:|TIPO|;|LDVAR");
				break;
			}
		case 61:
			if (codTerminal == 8) {
				addLista("INTEGER");
				break;
			} else if (codTerminal == 9) {
				addLista("ARRAY|[|INTEIRO|..|INTEIRO|]|OF|INTEGER");
				break;
			}
		case 62:
			if (codTerminal == 5) {
				addLista("PROCEDURE|IDENTIFICADOR|DEFPAR|;|BLOCO|;|DCLPROC");
				break;
			} else if (codTerminal == 6) {
				listDerivacao = null;
				break;
			}
		case 63:
			if (codTerminal == 36) {
				addLista("(|LID|:|INTEGER|)");
				break;
			} else if (codTerminal == 39) {
				listDerivacao = null;
				break;
			}
		case 64:
			if (codTerminal == 6) {
				addLista("BEGIN|COMANDO|REPCOMANDO|END");
				break;
			}
		case 65:
			if (codTerminal == 7) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 47) {
				addLista(";|COMANDO|REPCOMANDO");
				break;
			}
		case 66:
			switch (codTerminal) {
			case 7:
			case 15:
			case 19:
			case 47:
				listDerivacao = null;
				break;
			case 6:
				addLista("CORPO");
				break;
			case 11:
				addLista("CALL|IDENTIFICADOR|PARAMETROS");
				break;
			case 12:
				addLista("GOTO|IDENTIFICADOR");
				break;
			case 13:
				addLista("IF|EXPRESSAO|THEN|COMANDO|ELSEPARTE");
				break;
			case 16:
				addLista("WHILE|EXPRESSAO|DO|COMANDO");
				break;
			case 18:
				addLista("REPEAT|COMANDO|UNTIL|EXPRESSAO");
				break;
			case 20:
				addLista("READLN|(|VARIAVEL|REPVARIAVEL|)");
				break;
			case 21:
				addLista("WRITELN|(|ITEMSAIDA|REPITEM|)");
				break;
			case 25:
				addLista("IDENTIFICADOR|RCOMID");
				break;
			case 27:
				addLista("FOR|IDENTIFICADOR|:=|EXPRESSAO|TO|EXPRESSAO|DO|COMANDO");
				break;
			case 29:
				addLista("CASE|EXPRESSAO|OF|CONDCASE|END");
				break;
			}
		case 67:
			if (codTerminal == 34) {
				addLista("RVAR|:=|EXPRESSAO");
				break;
			} else if (codTerminal == 38) {
				addLista("RVAR|:=|EXPRESSAO");
				break;
			} else if (codTerminal == 39) {
				addLista(":|COMANDO");
				break;
			}
		case 68:
			if (codTerminal == 34) {
				addLista("[|EXPRESSAO|]");
				break;
			} else if (codTerminal == 38) {
				listDerivacao = null;
				break;
			}
		case 69:
			if (codTerminal == 7 || codTerminal == 15 || codTerminal == 19 || codTerminal == 47) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 36) {
				addLista("(|EXPRESSAO|REPPAR|)");
				break;
			}
		case 70:
			if (codTerminal == 37) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 46) {
				addLista(",|EXPRESSAO|REPPAR");
				break;
			}
		case 71:
			if (codTerminal == 7 || codTerminal == 19 || codTerminal == 47) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 15) {
				addLista("ELSE|COMANDO");
				break;
			}
		case 72:
			if (codTerminal == 25) {
				addLista("IDENTIFICADOR|VARIAVEL1");
				break;
			}
		case 73:
			switch (codTerminal) {
			case 7:
			case 10:
			case 14:
			case 15:
			case 17:
			case 19:
			case 22:
			case 23:
			case 28:
			case 30:
			case 31:
			case 32:
			case 33:
			case 35:
			case 37:
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
			case 46:
			case 47:
				listDerivacao = null;
				break;
			case 34:
				addLista("[|EXPRESSAO|]");
				break;
			}
		case 74:
			if (codTerminal == 37) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 46) {
				addLista(",|VARIAVEL|REPVARIAVEL");
				break;
			}
		case 75:
			switch (codTerminal) {
			case 24:
			case 25:
			case 26:
			case 30:
			case 31:
			case 36:
				addLista("EXPRESSAO");
				break;
			case 48:
				addLista("LITERAL");
				break;
			}
		case 76:
			if (codTerminal == 37) {
				listDerivacao = null;
				break;
			} else if (codTerminal == 46) {
				addLista(",|ITEMSAIDA|REPITEM");
				break;
			}
		case 77:
			switch (codTerminal) {
			case 24:
			case 25:
			case 26:
			case 30:
			case 31:
			case 36:
				addLista("EXPSIMP|REPEXPSIMP");
				break;
			}
		case 78:
			switch (codTerminal) {
			case 7:
			case 10:
			case 14:
			case 15:
			case 17:
			case 19:
			case 28:
			case 35:
			case 37:
			case 46:
			case 47:
				listDerivacao = null;
				break;
			case 40:
				addLista("=|EXPSIMP");
				break;
			case 41:
				addLista(">|EXPSIMP");
				break;
			case 42:
				addLista(">=|EXPSIMP");
				break;
			case 43:
				addLista("<|EXPSIMP");
				break;
			case 44:
				addLista("<=|EXPSIMP");
				break;
			case 45:
				addLista("<>|EXPSIMP");
				break;
			}
		default:
			new Msg().mensagemErro("Nenhuma combinação entontrada");
		}

		return listDerivacao;
	}

	public static boolean isBetween(int num, int abaixo, int acima) {
		return abaixo <= num && num <= acima;
	}

	public void addLista(String regra) {
		String[] aCampos = regra.replace("|", "!").split("!");

		for (int i = 0; i < aCampos.length; i++) {
			listDerivacao.add(aCampos[i]);
		}
	}

}
