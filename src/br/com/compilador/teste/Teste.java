package br.com.compilador.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Teste {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\eduar\\Desktop\\teste.txt"));
		String linha;
		String teste = "";
		while ((linha = br.readLine()) != null) {
			char linhaArray[] = linha.toCharArray();
			for(int i = 0; i <= linhaArray.length; i++ ) {
				
				
				
				if(letra(linhaArray[i])) {
					teste = teste + linhaArray[i];
					i++;
					while(letra(linhaArray[i]) || digito(linhaArray[i])) {
						teste = teste + linhaArray[i];
						if(i<linhaArray.length) {
						i++;
						}
					}
					
				}else {
					System.out.println("terminou");
				}
				
			}

			System.out.println(linha);
		}

		br.close();
	}

	public static boolean letra(char c) {
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
			return true;
		}
		return false;
	}

	public static boolean digito(char c) {
		boolean idDigito = false;
		if (c >= '0' && c <= '9')
			idDigito = true;
		return idDigito;
	}

//	public static void main(String[] args) {
//		char[] a = { 'm', 'n', 'x' };
//		String b = String.copyValueOf(a);
//		System.out.println(b);
//		String c = "aabbC4";
//		String d = TrabalharIdentificador(c);
//		
//
//	}

//	public static String TrabalharIdentificador(String texto) {
//		char texto2[] = texto.toCharArray();
//		StringBuilder str = new StringBuilder();
//
//		contIni = 0;
//		contFin = 0;
//		if (Character.isLetter(texto2[0])) {
//			for (int i = 0; i <= texto2.length; i++) {
//				while (Character.isLetterOrDigit(texto2[i])) {
//					contFin++;
//					str.append(texto2[i]);
//					
//				}
//			}
//			return str.toString();
//			
//		} else {
//			return null;
//		}
//
//	}

}
