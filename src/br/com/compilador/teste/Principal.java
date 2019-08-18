package br.com.compilador.teste;

import java.io.IOException;

import br.com.compilador.manipularArquivo.ManipularArquivo;
import br.com.compilador.token.Tokens;

public class Principal {

	public static void main(String[] args) {
//		Stack<Integer> pilha = new Stack<>();
//		
//		- Vari�veis importantes :
//			- CODIGO � armazenar� um c�digo num�rico relativo ao token encontrado
//			(conforme tabela fornecida acima).
//			- CAR � armazenar� ao pr�ximo car�ter a ser analisado.
//			- VALOR � armazenar� o valor na base 10 de constantes inteiras
//			encontradas (token = inteiro).
//			- BUFFER_IDENT � armazenar� os identificadores encontrados (token =
//			identificador).
//			- BUFFER_LITERAL � armazenar� a cadeia relativa a literais (token= literal)
		
		
		
//			- Rotinas importantes : ************
//			- PEGACAR � pega o pr�ximo car�ter no programa fonte e o coloca na
//			vari�vel CAR
//			- BUSCA_PALAVRA_RESERVADA � verifica se token = identificador est�
//			na tabela de palavras reservadas. Caso n�o esteja trata-se realmente do
//			token = identificador. Se estiver trata-se de palavra reservada cujo c�digo
//			est� na pr�pria tabela. 
		
		Tokens tokens = new Tokens();
		
		System.out.println(tokens.getCodToken(";"));
		System.out.println(tokens.getSimbolo(51));
		
		try {
			StringBuilder builder = ManipularArquivo.lerArquivo("C:\\Users\\aliss\\Documents\\Projetos Eclipse\\compilador\\Exemplos\\teste.txt");
			System.out.print(builder.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
