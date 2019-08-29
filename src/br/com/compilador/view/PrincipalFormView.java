package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.compilador.image.MasterImage;
import br.com.compilador.main.AnalisadorLexico;
import br.com.compilador.main.Pilha;
import br.com.compilador.manipularArquivo.ManipularArquivo;
import br.com.compilador.view.table.TableAnalisadorLexico;

public class PrincipalFormView extends JFrame {
	private static final long serialVersionUID = -4121820897834715812L;

	private JPanel painelPrincipal, painelBotoes;
	private JTextArea textAreaPrincipal, textAreaConsole;
	private JScrollPane scrollPaneTextCompilador, scrollTableAnalisadorLexico;
	private TextLineNumber bordaCountLinhas;
	private JButton btnAbrir, btnExecutar, btnDebug, btnSair;
	private TableAnalisadorLexico table;
	private JTabbedPane tabPaneConsole;
	private JFileChooser fileChooser;
	private File arquivoFileChooser;
	private AnalisadorLexico analisadorLexico = new AnalisadorLexico();
	private Stack<Pilha> simbolos = new Stack<Pilha>();

	public PrincipalFormView() {
		setTitle("Compilador LMS v1.0.0-betha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1091, 581);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		criarComponentes();
		acoesComponentes();
	}

	private void acoesComponentes() {
		btnAbrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser = new JFileChooser();
		        fileChooser.setDialogTitle("Selecione o arquivo para converter");
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //Apenas arquivos
		        
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Teste teste(*.txt)", "txt");
		        fileChooser.setFileFilter(filter);
		        
		        int retorno = fileChooser.showOpenDialog(PrincipalFormView.this);
		        
		        if(retorno == JFileChooser.APPROVE_OPTION){
		            arquivoFileChooser = fileChooser.getSelectedFile();
		            try {
						new ManipularArquivo();
						StringBuilder texto = ManipularArquivo.lerArquivo(arquivoFileChooser.getAbsolutePath());
						textAreaPrincipal.setText("");
						textAreaPrincipal.append(texto.toString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		            
		        }else{
		            System.out.println("CANCELOU A SELE√á√ÉO DO ARQUIVO");
		        }
			}
		});
		
		btnExecutar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.limparTabela();
				try {
					simbolos = analisadorLexico.analisar(textAreaPrincipal.getText());
					
					
					for(Pilha p : simbolos) {
						table.adicionarLinha(new Object[] { p.getCodigo(), p.getSimbolo(), p.getLinha() });
					}
					table.selecionaPrimeiraLinha();
					simbolos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnSair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	private void criarComponentes() {
		// Painel em toda a janela
		painelPrincipal = new JPanel();
		setContentPane(painelPrincipal);

		// Painel dos botıes
		painelBotoes = new JPanel();

		// Botıes principais
		btnAbrir = new JButton("Abrir", MasterImage.pasta_22x22);
		btnAbrir.setFocusable(false);

		btnExecutar = new JButton("Executar", MasterImage.executar);
		btnExecutar.setFocusable(false);

		btnDebug = new JButton("Debug", null);
		btnDebug.setFocusable(false);

		btnSair = new JButton("Sair", MasterImage.sair);
		btnSair.setFocusable(false);

		// TextArea principal do compilador
		textAreaPrincipal = new JTextArea();
		textAreaPrincipal.setFont(getDefaultFont()); // Fonte padr„o

		scrollPaneTextCompilador = new JScrollPane(textAreaPrincipal);
		bordaCountLinhas = new TextLineNumber(textAreaPrincipal);
		scrollPaneTextCompilador.setRowHeaderView(bordaCountLinhas);

		// Tabela do Analisador Lexico
		table = new TableAnalisadorLexico();
		painelPrincipal.add(table);
		scrollTableAnalisadorLexico = new JScrollPane(table);
		scrollTableAnalisadorLexico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Aba Console e TextArea
		tabPaneConsole = new JTabbedPane(JTabbedPane.TOP);
		tabPaneConsole.setAutoscrolls(true);
		tabPaneConsole.setBackground(Color.WHITE);

		textAreaConsole = new JTextArea();
		textAreaConsole.setBackground(new Color(221, 221, 221));
		textAreaConsole.setFont(getDefaultFont());
		tabPaneConsole.addTab("Console", MasterImage.monitor, textAreaConsole, "");
		tabPaneConsole.setEnabledAt(0, true);

		GroupLayout gl_painel = new GroupLayout(painelPrincipal);
		gl_painel.setHorizontalGroup(gl_painel.createParallelGroup(Alignment.TRAILING).addGroup(gl_painel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_painel.createSequentialGroup()
								.addComponent(scrollPaneTextCompilador, GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 235,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(Alignment.TRAILING,
								gl_painel.createSequentialGroup()
										.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
										.addGap(263))
						.addGroup(gl_painel.createSequentialGroup()
								.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(630, Short.MAX_VALUE)))));
		gl_painel.setVerticalGroup(gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup().addContainerGap()
						.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_painel.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 354,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneTextCompilador, GroupLayout.PREFERRED_SIZE, 354,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
						.addContainerGap()));

		GroupLayout gl_panel = new GroupLayout(painelBotoes);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addComponent(btnAbrir)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExecutar)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnDebug, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(31, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnAbrir)
								.addComponent(btnExecutar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDebug, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		painelBotoes.setLayout(gl_panel);
		painelPrincipal.setLayout(gl_painel);
	}

	public ArrayList<String> getTextArea() {
		ArrayList<String> lista = new ArrayList<>();
		String texto = textAreaPrincipal.getText();
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
