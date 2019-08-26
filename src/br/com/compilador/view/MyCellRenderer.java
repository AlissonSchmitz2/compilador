package br.com.compilador.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class MyCellRenderer {

	public TableCellRenderer renderer;
	
	public MyCellRenderer() {
		renderer = new EvenOddRenderer();
	}
	
	public class EvenOddRenderer implements TableCellRenderer {
		public DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);

			((JLabel) renderer).setOpaque(true);
			Color background;
			if (isSelected) {
				background = new Color(65, 105, 225);
			} else {
				if (row % 2 == 0) {
					background = new Color(220, 220, 220);
				} else {
					background = Color.WHITE;
				}
			}

			renderer.setBackground(background);
			return renderer;
		}
	}
	
	public TableCellRenderer getMyCellRenderer() {
		return renderer;
	}
	
}
