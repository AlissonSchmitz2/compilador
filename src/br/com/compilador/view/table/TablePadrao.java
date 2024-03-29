package br.com.compilador.view.table;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.compilador.util.Msg;

public class TablePadrao extends JTable {
	private static final long serialVersionUID = -1460232083708245880L;

	public TablePadrao() {
        super();
        this.setDefaultRenderer(Object.class, new MyCellRenderer().getMyCellRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowHeight(18);
    }
    
	//Remove todas as linhas da tabela.
    public void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setNumRows(0);
    }
    
    /**
     * Adicionar uma linha na tabela.
     * @param dados Informa��es para serem adicionadas na linha.
     */
    public void adicionarLinha(Object[] dados) {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.addRow(dados);
        selecionaUltimaLinha();
    }
    
    /**
     * Inseri uma linha acima da linha atualmente seleiconada na tabela.
     * @param dados Informa��es para serem adicionadas na linha.
     */
    public void inserirLinha(Object[] dados) {
        if(getSelectedRow() != -1) {
            DefaultTableModel model = (DefaultTableModel) this.getModel();
            model.insertRow(getSelectedRow(), dados);
            selectAndScroll(getSelectedRow()-1);
        } else {
            adicionarLinha(dados);
        }
    }
    
    /**
     * Seleciona a �ltima linha da tabela.
     */
    public void selecionaUltimaLinha() {
        selectAndScroll(getRowCount()-1);
    }
    
    /**
     * Seleciona a �ltima linha da tabela.
     */
    public void selecionaPrimeiraLinha() {
        selectAndScroll(0);
    }
    
    /**
     * Seleciona na tabela as linhas passadas por par�metro.
     * @param linha List de linhas para serem selecionadas.
     */
    public void selecionarLinhas(List<Integer> linha) {
        DefaultListSelectionModel model = new DefaultListSelectionModel();
        for(Integer i : linha) {
            model.addSelectionInterval(i, i);
        }
        this.setSelectionModel(model);
    }
    
    public void selecionarTodasLinhas() {
        DefaultListSelectionModel model = new DefaultListSelectionModel();
        model.addSelectionInterval(0, getRowCount()-1);
        this.setSelectionModel(model);
    }
    
    /**
     * Rola a barra de rolagem at� a linha passada por par�metro.
     * @param rowIndex n�mero da linha at� onde a barra de rolagem deve ir.
     */
    public void scrollToVisible(int rowIndex) {  
        if (!(this.getParent() instanceof JViewport))  
            return;
        
        setViewPortPosition((JViewport) this.getParent(), this.getCellRect(  
                rowIndex, 0, true));
    }
    
    /**
     * Seleciona uma linha da tabela, e ap�s rola barra de rolagem at� essa linha.
     * @param rowIndex Linha para selecionar.
     */
    public void selectAndScroll(int rowIndex) {
        if(rowIndex >= 0 && rowIndex < this.getRowCount()) {
            this.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);  
            scrollToVisible(rowIndex);
        } else {
            clearSelection();
        }
    }
    
    private static void setViewPortPosition(JViewport viewport, Rectangle position) {  
        // The location of the viewport relative to the object  
        Point pt = viewport.getViewPosition();  
  
        // Translate the cell location so that it is relative  
        // to the view, assuming the northwest corner of the  
        // view is (0,0)  
        position.setLocation(position.x - pt.x, position.y - pt.y);  
  
        // Scroll the area into view  
        viewport.scrollRectToVisible(position);  
    }
    
    /**
     * Exclui as linhas selecionadas de uma JTable.
     * @param tab
     */
    public void excluirLinhasSelecionadas() {
        int linhaSel[] = getLinhasSelecionadas();
        if(linhaSel.length > 0) {
            DefaultTableModel model = (DefaultTableModel) getModel();
            for(int i = 0; i < linhaSel.length; i++) {
                model.removeRow(linhaSel[i] - i);
            }
            setModel(model);
            if(getRowCount() > 0) {
                setRowSelectionInterval(getRowCount() - 1, getRowCount() - 1);
                this.selecionaPrimeiraLinha();
        		this.selecionaPrimeiraLinha();
            }
        } else {
            new Msg().mensagemAviso("Nenhuma linha selecionada.");
        }
    }
    
    /**
     * Retorna a linha selecionada na tabela.
     */
    public int getLinhaSelecionada() {
        return getSelectedRow();
    }
    
    /**
     * @return Retorna um vetor contendo todas as linhas seleciondas na tabela.
     */
    public int[] getLinhasSelecionadas() {
        return getSelectedRows();
    }
    
	public int getValorLinhaSelecionada(int coluna) {	
		if(getSelectedRow() != -1) {
			return (int)getValueAt(getLinhaSelecionada(), coluna);
		}
		
		return 0;
	}
	
	public int getValorLinhaAbaixoDaSelecionada(int coluna) {	
		if(getSelectedRow() != -1) {
			return (int)getValueAt(getLinhaSelecionada() + 1, coluna);
		}
		
		return 0;
	}
}

