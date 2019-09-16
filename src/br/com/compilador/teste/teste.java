package br.com.compilador.teste;

import java.util.ArrayList;

import br.com.compilador.analisadores.TabelaParsing;

public class teste {

	public static void main(String[] args) {
//		ArrayList<String> listDerivacao = new ArrayList<String>();
//
//		String a = "BEGIN.COMANDO";
//		String b[] = a.split(".");
//		
//		System.out.println(b[0]);
//		LABEL|LID|;
		
//		String sLinha = "FOR";
////		sLinha = sLinha.replace("|", "!");
//		String[] aCampos = sLinha.replace("|", "!").split("!");
//		for(int i = 0;i < aCampos.length;i++){
//			System.out.println("Campo "+i+": "+aCampos[i]);
//		}
		TabelaParsing parsing = new TabelaParsing();
		
		parsing.analisarValor(74, 38);
		
		
//		
//		for(String t : a.split(".")) {
//			listDerivacao.add(t);
//			System.out.println(t);
//		}
//		
//		for(int i = 0; i< listDerivacao.size(); i++) {
//			System.out.println(listDerivacao.get(i));
//		}
//		System.out.println(listDerivacao.size());
		
		
	}

}
