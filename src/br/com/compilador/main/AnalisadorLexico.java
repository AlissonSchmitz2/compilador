package br.com.compilador.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import br.com.compilador.hashmaps.Tokens;

public class AnalisadorLexico {
	private Tokens tokens = new Tokens();
	private String linha, palavras = "";
	private char charProx = ' ', charIni = ' ';
	private boolean comentario = false, literal = false, freio = false;
	private int numLinha = 0, numComentario = 0, numLiteral = 0;
	private Pilha p;
	private Stack<Pilha> simbolos = new Stack<Pilha>();
	private Stack<PilhaErros> pilhaErros = new Stack<PilhaErros>();
	private Reader inputString;
	private BufferedReader br;

	public AnalisadorLexico() {
	}

	public Stack<PilhaErros> getErros() {

		if (!pilhaErros.isEmpty()) {
			return pilhaErros;
		}

		return null;
	}

	public Stack<Pilha> analisar(String texto) throws IOException {
		inputString = new StringReader(texto);
		br = new BufferedReader(inputString);
		pilhaErros.removeAllElements();
		simbolos.removeAllElements();
		numLinha = 0;
		palavras = "";

		while ((linha = br.readLine()) != null) {
			
			// finalizar while quando sai do for
			if (freio) {
				freio = false;
				System.out.println("Parou");
				break;
			}
			
			// inicio variaveis usadas
			numLinha++;
			
//			System.out.println(numLinha);
			
			char linhaArray[] = linha.toCharArray();

			// erro literal com mais de uma linha
			if (literal) {
				literal = false;
				pilhaErros.add(new PilhaErros("Erro de não fechamento de literal na linha ", numLiteral));
				break;
			}


			// for que passa por cada elemento
			for (int i = 0; i < linhaArray.length; i++) {
				
				charIni = linhaArray[i];
				try {
					charProx = linhaArray[i + 1];
				} catch (Exception e1) {
//					e1.printStackTrace();
				}

				// tratamento de acento
				if (!literal && !comentario && acento(charIni)) {
					pilhaErros.add(new PilhaErros("Erro de caractere inválido na linha ", numLinha));
					freio = true;
					break;
				}

				// Tratamento de Comentários com uma ou mais de uma linha
				if (charIni == '(' && charProx == '*' && !comentario) {
					numComentario = numLinha;
					comentario = true;
					i++;
				}
				if (comentario && charIni == '*' && charProx == ')') {
					i += 2;
					comentario = false;
					continue;
				}
				if (comentario) {
					continue;
				}

				if (!literal && linhaArray[i] == '\'') {
					numLiteral = numLinha;
					literal = true;
					palavras += linhaArray[i];
				} else if (literal && linhaArray[i] != '\'') {
					palavras += linhaArray[i];

				} else if (literal && linhaArray[i] == '\'') {
					palavras += linhaArray[i];
					adicionarPilhaPrincipal(48);
					literal = false;
				}

				// Letras
				else if (!literal && letra(linhaArray[i])) {
					palavras += linhaArray[i];

					try {
						charProx = linhaArray[i + 1];
					} catch (Exception e1) {
//						e1.printStackTrace();
					}

					while (letra(charProx) || digito(charProx)) {
						i++;
						try {
							palavras += linhaArray[i];
						} catch (Exception e) {
						}

						charProx = ' ';
						if (i + 1 < linhaArray.length)
							charProx = linhaArray[i + 1];
					}

					adicionarPilhaPrincipal(tokens.getCodToken(palavras.toUpperCase()));
					
					// tratamento de - teste
				} else if (!literal && (charIni == '-' && linhaArray[i+1] == ' ')) {
					palavras += linhaArray[i];
					
					
					adicionarPilhaPrincipal(tokens.getCodToken(palavras.toUpperCase()));
					palavras = "";
				
					// Tratamento de números
				} else if (!literal && (charIni == '-' || digito(linhaArray[i]))) {
					palavras += linhaArray[i];
					try {
						charProx = linhaArray[i + 1];
					} catch (ArrayIndexOutOfBoundsException e) {
						// TODO: handle exception
					}
					while (digito(charProx)) {
						i++;
						try {
							palavras += linhaArray[i];
						} catch (ArrayIndexOutOfBoundsException e) {
							// TODO: handle exception
						}
						charProx = ' ';
						if (i + 1 < linhaArray.length) {
							charProx = linhaArray[i + 1];
						}
					}
					
					if (Double.parseDouble(palavras) > -32768 && Double.parseDouble(palavras) < 32768) {
						adicionarPilhaPrincipal(26);
					} else {
						pilhaErros.add(new PilhaErros("Erro de número fora de escala na linha: ", numLinha));
						freio = true;
						break;
					}

					palavras = "";
				} else if (!literal && (simbolos1Caracter(linhaArray[i]))) {
					palavras += linhaArray[i];
					try {
						if (simbolos2Caracter(linhaArray[i], linhaArray[i + 1])) {
							palavras += linhaArray[i + 1];
							i++;
						}
						//Validação realizada aqui pois é encontrado o ponto nesse if
						if(digito(linhaArray[i - 1])) {
							if(verificaPontoFlutuante(linhaArray[i], linhaArray[i + 1])) {
								pilhaErros.add(new PilhaErros("Erro de Ponto flutuante não aceito na liguagem na linha: ", numLinha));
								freio = true;
								break;
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						// e.printStackTrace();
					}
					
					
					
					adicionarPilhaPrincipal(tokens.getCodToken(palavras.toUpperCase()));
				}
			}
		}
		if (literal) {
			pilhaErros.add(new PilhaErros("Erro de não fechamento de literal na linha ", numLiteral));
		}
		if (comentario) {
			pilhaErros.add(new PilhaErros("Erro de não fechamento de comentário na linha ", numComentario));
		}

		br.close();
		return simbolos;
	}

	private void adicionarPilhaPrincipal(int token) {
		p = new Pilha(token, numLinha, palavras);
		simbolos.add(p);
		palavras = "";
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

	public static boolean verificaPontoFlutuante(char c, char cProx) {
		if (c == '.' && digito(cProx)) {
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

	public static boolean acento(char c) {
		for (int i = 192; i < 256; i++) {
			if ((int) c == i)
				return true;
		}

		return false;
	}
}