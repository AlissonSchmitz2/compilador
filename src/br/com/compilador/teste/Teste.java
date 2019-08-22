package br.com.compilador.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Teste {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\\\Users\\\\aliss\\\\Documents\\\\Projetos Eclipse\\\\compilador\\\\Exemplos\\\\Exemplo1.txt"));

		String linha;
		String teste = "";
		char c;
		while ((linha = br.readLine()) != null) {
			char linhaArray[] = linha.toCharArray();
			for(int i = 0; i < linhaArray.length; i++ ) {
				c = ' ';
<<<<<<< HEAD

			if(linhaArray[i] == '(' && linhaArray[i+1] == '*') {
					
					if(linhaArray.length > 3) {
						i+=2;
						while(linhaArray[i] != '*' && linhaArray[i+1] != ')') {
							i++;
						}
					}else{
						linha = br.readLine();
					}
					
				}else if(letra(linhaArray[i])) {
=======
				
				
				if(letra(linhaArray[i])) {
>>>>>>> 42c337337dae7fcf42e7cfb2dfc509dd029b020a
					teste = teste + linhaArray[i];
					c = linhaArray[i+1];
					while(letra(c) || digito(c)) {
						i++;
						teste = teste + linhaArray[i];
						c = ' ';
						if(i+1 < linhaArray.length)
							c = linhaArray[i+1];
						
					}
					System.out.println(teste);
					teste = "";
					
				}else if(digito(linhaArray[i])) {
					teste = teste + linhaArray[i];
					c = linhaArray[i+1];
					while(digito(c)) {
						i++;
						teste = teste + linhaArray[i];
						c = ' ';
						if(i+1 < linhaArray.length)
							c = linhaArray[i+1];
						
					}
					System.out.println(teste);
					teste = "";	
				}
				else if(linhaArray[i] == ' '){
					
	
				}
				else if(linhaArray[i] == ';'){
					teste = teste + linhaArray[i];
					System.out.println(teste);
					teste = "";
					
				}else if(linhaArray[i] == '<' || linhaArray[i] == '>') {
					teste = teste + linhaArray[i];
					c = linhaArray[i+1];
					while(letra(c) || digito(c)) {
						i++;
						teste = teste + linhaArray[i];
						c = ' ';
						if(i+1 < linhaArray.length)
							c = linhaArray[i+1];
						
					}
					System.out.println(teste);
					teste = "";
					
				}
				
			
<<<<<<< HEAD
			}
=======

>>>>>>> 42c337337dae7fcf42e7cfb2dfc509dd029b020a
			
		}

		br.close();
	}

	public static boolean letra(char c) {
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') {
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