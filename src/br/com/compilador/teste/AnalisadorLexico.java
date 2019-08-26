package br.com.compilador.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class AnalisadorLexico {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\eduar\\Desktop\\compilador\\Exemplos\\Exemplo1.txt"));
		String linha;
		String palavras = "";
		char c2 = ' ', c= ' ';
		boolean comentario = false;
		int numLinha = -1;
		Pilha p;
		Stack<Pilha> simbolos = new Stack<Pilha>();

		while ((linha = br.readLine()) != null) {
			numLinha++;
			char linhaArray[] = linha.toCharArray();

			for (int i = 0; i < linhaArray.length; i++) {
				
				// inicialização de variaveis
				
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

				//tratamento do livreral 'meu nome é julia' 

					//fazer aqui
				
					
					// Letras
				 else if (letra(linhaArray[i])) {
					palavras += linhaArray[i];
					c2 = linhaArray[i + 1];

					while (letra(c2) || digito(c2)) {
						i++;
						palavras += linhaArray[i];
						c2 = ' ';
						if (i + 1 < linhaArray.length)
							c2 = linhaArray[i + 1];
					}
					System.out.println(palavras);
					p = new Pilha(26, numLinha, palavras);
					simbolos.add(p);
					palavras = "";

				} else if (c == '-' || digito(linhaArray[i])) {
					palavras += linhaArray[i];
					c2 = linhaArray[i + 1];
					while (digito(c2)) {
						i++;
						palavras += linhaArray[i];
						c2 = ' ';
						if (i + 1 < linhaArray.length)
							c2 = linhaArray[i + 1];

					}
					System.out.println(palavras);
					palavras = "";
					
					//caractere vazio
				} else if (linhaArray[i] == ' ') {
					
					//simbolos
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