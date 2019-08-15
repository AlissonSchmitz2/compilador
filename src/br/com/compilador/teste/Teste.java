package br.com.compilador.teste;

public class Teste {
	public static int contIni;
	public static int contFin;

	public static void main(String[] args) {
		char[] a = { 'm', 'n', 'x' };
		String b = String.copyValueOf(a);
		System.out.println(b);
		String c = "aabbC4";
		String d = TrabalharIdentificador(c);
		

	}

	public static String TrabalharIdentificador(String texto) {
		char texto2[] = texto.toCharArray();
		StringBuilder str = new StringBuilder();

		contIni = 0;
		contFin = 0;
		if (Character.isLetter(texto2[0])) {
			for (int i = 0; i <= texto2.length; i++) {
				while (Character.isLetterOrDigit(texto2[i])) {
					contFin++;
					str.append(texto2[i]);
					
				}
			}
			return str.toString();
			
		} else {
			return null;
		}

	}

}
