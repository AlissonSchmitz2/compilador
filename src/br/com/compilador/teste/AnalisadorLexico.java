package br.com.compilador.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\eduar\\Desktop\\compilador\\Exemplos\\Exemplo1.txt"));
		String linha;
		String palavras = "";
		char caracter, c, c2 = ' ';
		boolean comentario = false;

		while ((linha = br.readLine()) != null) {
			char linhaArray[] = linha.toCharArray();

			for (int i = 0; i < linhaArray.length; i++) {
				
				// inicialização de variaveis
				caracter = ' ';
				c = linhaArray[i];
				try {
					c2 = linhaArray[i+1];
				} catch (Exception e1) {
//					e1.printStackTrace();
				}

				// Tratamento de Comentários com uma ou mais de uma linha
				if (c == '(' && c2 == '*') {
					comentario = true;
					i ++;
				}
				if (comentario && c == '*' && c2 == ')') {
					i+=2;
					comentario = false;
					continue;
				}
				if(comentario) {
					continue;				
				}

					
					
					// Letras
				 else if (letra(linhaArray[i])) {
					palavras += linhaArray[i];
					caracter = linhaArray[i + 1];

					while (letra(caracter) || digito(caracter)) {
						i++;
						palavras += linhaArray[i];
						caracter = ' ';
						if (i + 1 < linhaArray.length)
							caracter = linhaArray[i + 1];
					}
					System.out.println(palavras);
					palavras = "";

				}
//				else if(linhaArray[i] == '<' || linhaArray[i] == '>') {
//					palavras += linhaArray[i];
//					caracter = linhaArray[i+1];
//					
//					while (letra(caracter) || digito(caracter)) {
//						i++;
//						palavras += linhaArray[i];
//						caracter = ' ';
//						if (i + 1 < linhaArray.length)
//							caracter = linhaArray[i + 1];
//					}
//					System.out.println(palavras);
//					palavras = "";
//			}
				else if (digito(linhaArray[i])) {
					palavras += linhaArray[i];
					caracter = linhaArray[i + 1];
					while (digito(caracter)) {
						i++;
						palavras += linhaArray[i];
						caracter = ' ';
						if (i + 1 < linhaArray.length)
							caracter = linhaArray[i + 1];

					}
					System.out.println(palavras);
					palavras = "";
				} else if (linhaArray[i] == ' ') {

				} else if (simbolos1Caracter(linhaArray[i])) {
					palavras += linhaArray[i];
					try {
						if (simbolos2Caracter(linhaArray[i], linhaArray[i + 1])) {
							palavras += linhaArray[i + 1];
							i++;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
//							e.printStackTrace();
					}

					System.out.println(palavras);
					palavras = "";

				}

			}

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
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}

	public static boolean simbolos2Caracter(char c, char cProx) {
		if ((c == ':' && cProx == '=') || (c == '>' && cProx == '=') || (c == '<' && cProx == '=')
				|| (c == '<' && cProx == '>') || (c == '.' && cProx == '.')) {
			return true;
		}

		return false;
	}

	public static boolean simbolos1Caracter(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '[' || c == ']' || c == '(' || c == ')' || c == ':'
				|| c == '=' || c == '>' || c == '<' || c == ',' || c == ';' || c == '.' || c == '$') {
			return true;
		}
		return false;
	}
}