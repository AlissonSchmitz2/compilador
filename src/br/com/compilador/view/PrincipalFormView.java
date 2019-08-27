package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PrincipalFormView extends JFrame {
	private static final long serialVersionUID = -4121820897834715812L;

	private JPanel contentPane;
	public JTextArea textArea = new JTextArea();
	private JLayeredPane layeredPane;
	private JButton btnBuscar, btnExecutar;

	// tabela e modelo
	private TablePadrao table;
	private DefaultTableModel model;

	/**
	 * Create the frame.
	 */
	public PrincipalFormView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 581);
//		setExtendedState(MAXIMIZED_BOTH);
		setResizable(false);
		setLocationRelativeTo(null);
		criarComponentes();
	}
		
	private void criarComponentes() {	
		//Painel em toda a janela
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnBuscar = new JButton("Teste");
		btnBuscar.setBounds(5, 5, 150, 25);
		contentPane.add(btnBuscar);
		
		
		
		
		textArea.setFont(getDefaultFont());

		/**
		 * Create table;
		 */

		String colunas[] = { "Código", "Simbolo", "Linha" };

		model = new DefaultTableModel(null, colunas) {
			private static final long serialVersionUID = 3263923956276898672L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new TablePadrao();
		
		table.setModel(model);
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		table.getTableHeader().setEnabled(true);
		table.setBounds(800, 100, 235, 354);
		table.setVisible(true);
		contentPane.add(table);
		for(int i= 0; i<30; i++) {
		table.adicionarLinha(new Object[]{3,4, 5, 6});
		}

		// Scroll Pane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(800, 30, 235, 354);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollPane);

		/**
		 * Create o layer.
		 */
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 50, 789, 354);

		/**
		 * Cria o editor de texto.
		 */
		JScrollPane painelComBarraDeRolagem = new JScrollPane(textArea);
		TextLineNumber contadorLinhas = new TextLineNumber(textArea);
		painelComBarraDeRolagem.setRowHeaderView(contadorLinhas);
		painelComBarraDeRolagem.setBounds(0, 50, 789, 354);

		contentPane.add(painelComBarraDeRolagem);
		
		JTextArea area = new JTextArea();
		area.setBounds(0, 500, 200, 200);
		JScrollPane scroll = new JScrollPane(area);
		contentPane.add(scroll);

	}

	/**
	 * Create the gettextarea.
	 */
	public ArrayList<String> getTextArea() {
		ArrayList<String> lista = new ArrayList<>();
		String texto = textArea.getText();
		String[] aux;
		aux = texto.split("\n");
		for (int i = 0; i < aux.length; i++) {
			lista.add(aux[i]);
			System.out.println(lista.get(i));
		}
		return lista;
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}
