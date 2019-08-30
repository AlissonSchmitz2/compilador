package br.com.compilador.util;

import javax.swing.JOptionPane;

public class Msg {

	public void mensagemAviso(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE, null);
	}
	
	public void mensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE, null);
	}
	
	public void mensagemSucesso(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE, null);
	}
}
