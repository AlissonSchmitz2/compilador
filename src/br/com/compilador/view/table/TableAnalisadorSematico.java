package br.com.compilador.view.table;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TableAnalisadorSematico extends TablePadrao {
	private static final long serialVersionUID = -8575782420229430010L;

	private String colunas[] = { "Nome", "Categoria", "Tipo", "Nível" };
	private DefaultTableModel model;
	
	public TableAnalisadorSematico() {
		model = new DefaultTableModel(null, colunas) {
			private static final long serialVersionUID = 3263923956276898672L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		setModel(model);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setVisible(true);
	}
	
}
