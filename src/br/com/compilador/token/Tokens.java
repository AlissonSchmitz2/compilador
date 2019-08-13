package br.com.compilador.token;

import java.util.HashMap;
import java.util.Map;

public class Tokens {

	public Map<Integer, String> tokens = new HashMap<Integer, String>();
	
	public Tokens() {
		tokens.put(1, "Program");
		tokens.put(2, "Label");
		tokens.put(3, "Const");
		tokens.put(4, "Var");
		tokens.put(5, "Procedure");
		tokens.put(6, "Begin");
		tokens.put(7, "End");
		tokens.put(8, "Integer");
		tokens.put(9, "Array");
		tokens.put(10, "Of");
		tokens.put(11, "Call");
		tokens.put(12, "Goto");
		tokens.put(13, "If");
		tokens.put(14, "Then");
		tokens.put(15, "Else");
		tokens.put(16, "While");
		tokens.put(17, "Do");
		tokens.put(18, "Repeat");
		tokens.put(19, "Until");
		tokens.put(20, "Readln");
		tokens.put(21, "Writeln");
		tokens.put(22, "Or");
		tokens.put(23, "And");
		tokens.put(24, "Not");
		tokens.put(25, "Identificador");
		tokens.put(26, "Inteiro");
		tokens.put(27, "For");
		tokens.put(28, "To");
		tokens.put(29, "Case");
		tokens.put(30, "+");
		tokens.put(31, "-");
		tokens.put(32, "*");
		tokens.put(33, "/");
		tokens.put(34, "[");
		tokens.put(35, "]");
		tokens.put(36, "(");
		tokens.put(37, ")");
		tokens.put(38, ":=");
		tokens.put(39, ":");
		tokens.put(40, "=");
		tokens.put(41, ">");
		tokens.put(42, ">=");
		tokens.put(43, "<");
		tokens.put(44, "<=");
		tokens.put(45, "<>");
		tokens.put(46, ",");
		tokens.put(47, ";");
		tokens.put(48, "literal");
		tokens.put(49, ".");
		tokens.put(50, "..");
		tokens.put(50, "$");
	}
	
}
