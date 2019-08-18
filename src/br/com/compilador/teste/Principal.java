package br.com.compilador.teste;

import java.io.IOException;

import br.com.compilador.manipularArquivo.ManipularArquivo;
import br.com.compilador.token.Tokens;

public class Principal {

	public static void main(String[] args) {
//		Stack<Integer> pilha = new Stack<>();
//		
//		- Variáveis importantes :
//			- CODIGO – armazenará um código numérico relativo ao token encontrado
//			(conforme tabela fornecida acima).
//			- CAR – armazenará ao próximo caráter a ser analisado.
//			- VALOR – armazenará o valor na base 10 de constantes inteiras
//			encontradas (token = inteiro).
//			- BUFFER_IDENT – armazenará os identificadores encontrados (token =
//			identificador).
//			- BUFFER_LITERAL – armazenará a cadeia relativa a literais (token= literal)
		
		
		
//			- Rotinas importantes : ************
//			- PEGACAR – pega o próximo caráter no programa fonte e o coloca na
//			variável CAR
//			- BUSCA_PALAVRA_RESERVADA – verifica se token = identificador está
//			na tabela de palavras reservadas. Caso não esteja trata-se realmente do
//			token = identificador. Se estiver trata-se de palavra reservada cujo código
//			está na própria tabela. 
		
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
