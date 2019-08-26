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
		
		
		
		
		// Muda o Layout da janela para o padrão do Windows
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
