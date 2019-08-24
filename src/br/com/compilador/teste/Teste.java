package br.com.compilador.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Teste {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"C:\\\\Users\\\\aliss\\\\Documents\\\\Projetos Eclipse\\\\compilador\\\\Exemplos\\\\Exemplo1.txt"));
		String linha;
		String palavras = "";
		char caracter;

		while ((linha = br.readLine()) != null) {
			char linhaArray[] = linha.toCharArray();

			for (int i = 0; i < linhaArray.length; i++) {
				caracter = ' ';

				// Comentários do código
				if (linhaArray[i] == '(' && linhaArray[i + 1] == '*') {
					if (linhaArray.length > 3) {
						i += 2;
						while (linhaArray[i] != '*' && linhaArray[i + 1] != ')') {
							i++;
						}
						i++;
					} else {
						linha = br.readLine();
					}

					// Letras
				} else if (letra(linhaArray[i])) {
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