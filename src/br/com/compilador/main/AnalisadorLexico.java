package br.com.compilador.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import br.com.compilador.token.Tokens;


public class AnalisadorLexico {

	private Tokens tokens = new Tokens();
	private String linha;
	private String palavras = "";
	private char c2 = ' ', c= ' ';
	private boolean comentario = false, literal = false;
	private int numLinha = 0;
	private Pilha p;
	private Stack<Pilha> simbolos = new Stack<Pilha>();
	
	public AnalisadorLexico() {
	}
	
	public Stack<Pilha> analisar(String texto) throws IOException  {
		simbolos.removeAllElements();
		numLinha = 0;
		Reader inputString = new StringReader(texto);
		BufferedReader br = new BufferedReader(inputString);
		
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

				if (!literal && linhaArray[i] == '\'') {
					literal = true;
					palavras += linhaArray[i];
				}else
				if(literal && linhaArray[i] != '\'') {
					palavras += linhaArray[i];
					
				}else
				if (literal && linhaArray[i] == '\'') {
					palavras += linhaArray[i];
					i++;
					System.out.println(palavras);
					p = new Pilha(48, numLinha, palavras);
					simbolos.add(p);
					palavras = "";
					literal = false;
				}
				
				
					
					// Letras
				 else if (!literal && letra(linhaArray[i])) {
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
					
					p = new Pilha(tokens.getCodToken(palavras), numLinha, palavras);
					simbolos.add(p);
					palavras = "";

				} else if (!literal && (c == '-' || digito(linhaArray[i]))) {
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
					p = new Pilha(26, numLinha, palavras);
					simbolos.add(p);
					palavras = "";
					
					//caractere vazio
				} else if (!literal && linhaArray[i] == ' ') {
					
					//simbolos
				} else if ( !literal && (simbolos1Caracter(linhaArray[i]))) {
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
					p = new Pilha(tokens.getCodToken(palavras), numLinha, palavras);
					simbolos.add(p);
					palavras = "";

				}

			}

		}

		br.close();
		return simbolos;
	}
//	public static void main(String[] args) throws IOException {
		
//	}

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