package br.com.compilador.manipularArquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ManipularArquivo {

	public static void gravarArquivo(String path, String str) throws FileNotFoundException, IOException {
		FileOutputStream arq = null;
		PrintStream ps = null;

		try {
			// cria o arquivo
			File f = new File(path);
			arq = new FileOutputStream(f);

			// escreve no arquivo
			try {
				ps = new PrintStream(arq);
				ps.println(str);
			} finally {
				if (ps != null) {
					ps.close();
				}
			}

		} finally {
			if (arq != null) {
				arq.close();
			}
		}

	}

	public static StringBuilder lerArquivo(String path) throws FileNotFoundException, IOException {
		StringBuilder result = new StringBuilder();
		FileInputStream arq = null;

		try {
			File f = new File(path);
			arq = new FileInputStream(f);

			int caracterLido = arq.read();

			while (caracterLido != -1) {
				result.append((char) caracterLido);
				caracterLido = arq.read();
			}
		} finally {
			if (arq != null) {
				arq.close();
			}
		}
		return result;
	}

}
