package br.com.compilador.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.AbstractBorder;

public class NumeredBorder extends AbstractBorder {
	private static final long serialVersionUID = 3291512428217848986L;

	private int lineHeight = 16;
	private int characterHeight = 8;
	private int characterWidth = 7;
	private Color myColor;
	private JViewport viewport;
	private static JTextArea area = new JTextArea();

	public NumeredBorder() {
		this.myColor = new Color(255, 0, 0);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (this.viewport == null) {
			searchViewport(c);
		}

		Point point;
//		Dimension d = new Dimension();
		if (this.viewport != null) {
			point = this.viewport.getViewPosition();
		} else {
			point = new Point();
		}

		Color oldColor = g.getColor();
		g.setColor(this.myColor);

		double r = (double) height / (double) this.lineHeight;
		int rows = (int) (r + 0.5);
		String str = String.valueOf(rows);
		int maxLenght = str.length();

		int py;
		int i = 0;
		if (point.y > 0) {
			i = point.y / this.lineHeight;
		}

		int lenght;
		int px;
		for (; i < rows; i++) {
			str = String.valueOf(i + 1);
			lenght = str.length();
			lenght = maxLenght - lenght;

			py = this.lineHeight * i + 14;
			px = this.characterWidth * lenght + 2;
			px += point.x;

			g.drawString(str, px, py);
		}
		
		int left = this.calculateLeft(height) + 7;
		left += point.x;
		g.drawLine(left, 0, left, height);
//		g.drawLine(point.x, 0, left * 2, height * 2); // 0 = linha *16

		g.setColor(oldColor);
	}

	public Insets getBorderInsets(Component c) {
		int left = this.calculateLeft(c.getHeight()) + 10;
		return new Insets(1, left, 1, 1);
	}

	public Insets getBorderInsets(Component c, Insets insets) {
		insets.top = 1;
		insets.left = this.calculateLeft(c.getHeight()) + 10;
		insets.bottom = 1;
		insets.right = 1;
		return insets;
	}

	protected int calculateLeft(int height) {
		double r = (double) height / (double) this.lineHeight;
		int rows = (int) (r + 0.5);
		String str = String.valueOf(rows);
		int lenght = str.length();

		return this.characterHeight * lenght;
	}

	protected void searchViewport(Component c) {
		Container parent = c.getParent();
		if (parent instanceof JViewport) {
			this.viewport = (JViewport) parent;
		}
	}

//	public JPanel getPanel() {
//		JPanel panel = new JPanel(new BorderLayout());
//
//		
//		area.setBorder(new NumeredBorder());
//		area.setLineWrap(true); // Quebra de linha automática
//		area.requestFocusInWindow();
//		area.setWrapStyleWord(true);
//		
//		//Pintar linha da escrita
////		area.setCaretColor(new Color(0, 255, 255));
//		
//		System.out.println(area.getLineCount());
//		
//
//		JScrollPane scroll = new JScrollPane(area);
//		panel.add(scroll);
//		return panel;
//
//	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(getPanel());
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(400, 300);
//		frame.setVisible(true);
//	}
}
