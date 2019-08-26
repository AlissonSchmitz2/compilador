package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.com.compilador.image.MasterImage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DropMode;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.DebugGraphics;

public class PrincipalFormView extends JFrame {
	private static final long serialVersionUID = 5193930094467396247L;

	private NumeredBorder numeredBorder = new NumeredBorder();
	
	public PrincipalFormView() {
		initWindow();
		criarComponentes();
//		add(numeredBorder.getPanel());
	}
	
	private void initWindow() {
		setTitle("Compilador v1.0.0-betha");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		setBounds(new Rectangle(0, 0, 1280, 713));
//		setFocusableWindowState(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	private void criarComponentes() {
		
		JToolBar toolBar = new JToolBar();
		
		JPanel panel = new JPanel();
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(getDefaultFont());
		textArea.setLineWrap(true);
		textArea.setBorder(new NumeredBorder());
//		textArea.addFocusListener(l);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JButton button = new JButton(MasterImage.pasta_22x22);
		button.setFocusable(false);
		button.setFocusPainted(false);
		
		JScrollPane scroll = new JScrollPane(textArea);
		panel.add(scroll);
		toolBar.add(button);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(725, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(225, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}
