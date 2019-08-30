package br.com.compilador.main;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import br.com.compilador.view.PrincipalForm;

public class Principal {

	public static void main(String[] args) {
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
			java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PrincipalForm().setVisible(true);
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