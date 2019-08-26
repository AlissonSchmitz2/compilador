package br.com.compilador.teste;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import br.com.compilador.token.Tokens;
import br.com.compilador.view.PrincipalFormView;

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
		
		
		
		
		// Muda o Layout da janela para o padr�o do Windows
				try {
					boolean hasWindowLookAndFeel = false;

					for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						// Windows
						if ("Windows".equals(info.getName())) {
							hasWindowLookAndFeel = true;
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}

					// Alternativa para outros sistemas operacionais
					if (!hasWindowLookAndFeel) {
						UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

						setUIFont(new FontUIResource(Font.SANS_SERIF, Font.PLAIN, 11));
					}
				} catch (ClassNotFoundException ex) {
					java.util.logging.Logger.getLogger(PrincipalFormView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (InstantiationException ex) {
					java.util.logging.Logger.getLogger(PrincipalFormView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					java.util.logging.Logger.getLogger(PrincipalFormView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				} catch (javax.swing.UnsupportedLookAndFeelException ex) {
					java.util.logging.Logger.getLogger(PrincipalFormView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
				}

				java.awt.EventQueue.invokeLater(new Runnable() {

					public void run() {

						new PrincipalFormView().setVisible(true);
						// Para testes
						// new Window().setVisible(true);
					}
				});
			}

			public static void setUIFont(javax.swing.plaf.FontUIResource f) {
				Enumeration<Object> keys = UIManager.getDefaults().keys();
				while (keys.hasMoreElements()) {
					Object key = keys.nextElement();
					Object value = UIManager.get(key);
					if (value instanceof FontUIResource)
						UIManager.put(key, f);
				}
			}
	

}
