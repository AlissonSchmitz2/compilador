package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import br.com.compilador.main.PilhaErros;
import br.com.compilador.util.ManipularArquivo;
import br.com.compilador.util.Msg;
import br.com.compilador.util.TextLineNumber;
import br.com.compilador.view.table.TableAnalisadorLexico;

public class PrincipalForm extends JFrame {
	private static final long serialVersionUID = -4121820897834715812L;

	private JPanel painelPrincipal, painelBotoes;
	private JTextArea textAreaPrincipal, textAreaConsole;
	private JScrollPane scrollPaneTextCompilador, scrollTableAnalisadorLexico;
	private TextLineNumber bordaCountLinhas;
	private JButton btnSalvar, btnExecutar, btnDebug, btnSair;
	private TableAnalisadorLexico tableAnalisadorLexico;
	private JTabbedPane tabPaneConsole;
	private JFileChooser fileChooser;
	private File arquivoFileChooser;
	private AnalisadorLexico analisadorLexico;
	private Stack<Pilha> simbolos;
	private boolean ativo = false;
	private JButton btnNovo;
	private JButton btnAbrir;

	public PrincipalForm() {
		setTitle("Compilador LMS v1.0.0-betha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1091, 581);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		criarComponentes();
		acoesComponentes();
	}

	private void acoesComponentes() {
		
		btnNovo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limparArea();
				limparConsole();
				tableAnalisadorLexico.limparTabela();
				setTitle("Compilador LMS v1.0.0-betha");
			}
		});
		
		btnAbrir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ee) {
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Selecione o arquivo");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Apenas arquivos

				fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo Código(*.txt)", "txt"));

				if (fileChooser.showOpenDialog(PrincipalForm.this) == JFileChooser.APPROVE_OPTION) {
					arquivoFileChooser = fileChooser.getSelectedFile();
					try {
						new ManipularArquivo();
						StringBuilder texto = ManipularArquivo.lerArquivo(arquivoFileChooser.getAbsolutePath());
						limparArea();
						limparConsole();
						textAreaPrincipal.append(texto.toString());
						setTitle("Compilador LMS v1.0.0-betha" + " - " + arquivoFileChooser.getAbsolutePath());
					} catch (IOException e) {
						new Msg().mensagemErro("Problema ao ler arquivo selecionado!");
					}

				} else {
					new Msg().mensagemAviso("Seleção do arquivo cancelada!");
				}
				
			}
		});
		
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Selecione a pasta");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Teste teste(*.txt)", "txt");
				fileChooser.setFileFilter(filter);

				fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo Código(*.txt)", "txt"));

				if (fileChooser.showSaveDialog(PrincipalForm.this) == JFileChooser.APPROVE_OPTION) {
					arquivoFileChooser = fileChooser.getSelectedFile();
					String caminhoArquivo = arquivoFileChooser.getAbsolutePath();
					
					if(!caminhoArquivo.endsWith(".txt")) {
						caminhoArquivo += ".txt";
					}
					
					try {
						new ManipularArquivo();
						ManipularArquivo.gravarArquivo(caminhoArquivo, textAreaPrincipal.getText());
						
						new Msg().mensagemSucesso("Arquivo Salvo com Sucesso!");
					} catch (IOException e) {
						new Msg().mensagemErro("Problema ao gravar arquivo!");
					}

				} else {
					new Msg().mensagemAviso("Salvamento cancelado!");
				}
			}
		});

		btnExecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tableAnalisadorLexico.limparTabela();
				limparConsole();
				if(analisadorLexico == null) {
					 analisadorLexico = new AnalisadorLexico();
				}
				
				try {
					simbolos = analisadorLexico.analisar(textAreaPrincipal.getText());
					for (Pilha p : simbolos) {
						tableAnalisadorLexico.adicionarLinha(new Object[] { p.getCodigo(), p.getSimbolo(), p.getLinha() });
					}
					if(analisadorLexico.getErros() != null) {
						for(PilhaErros p : analisadorLexico.getErros()) {
							textAreaConsole.setText(p.getErro() + p.getLinha() + "\n");
						}
					}
					tableAnalisadorLexico.selecionaPrimeiraLinha();
					analisadorLexico = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnDebug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ativo) {
					ativo = false;
				} else {
					ativo = true;
				}
				
				tableAnalisadorLexico.setVisible(ativo);
				scrollTableAnalisadorLexico.setVisible(ativo);
				painelPrincipal.repaint();
				painelPrincipal.revalidate();
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

		// Painel dos botões
		painelBotoes = new JPanel();

		// Botões principais
		
		btnNovo = new JButton("Novo", MasterImage.novo);
		btnNovo.setFocusable(false);
		
		btnAbrir = new JButton("Abrir", MasterImage.abrir);
		btnAbrir.setFocusable(false);
		
		btnSalvar = new JButton("Salvar", MasterImage.salvar);
		btnSalvar.setFocusable(false);

		btnExecutar = new JButton("Executar", MasterImage.executar);
		btnExecutar.setFocusable(false);

		btnDebug = new JButton("Debug", MasterImage.debug);
		btnDebug.setFocusable(false);

		btnSair = new JButton("Sair", MasterImage.sair);
		btnSair.setFocusable(false);

		// TextArea principal do compilador
		textAreaPrincipal = new JTextArea();
		textAreaPrincipal.setFont(getDefaultFont()); // Fonte padrão

		scrollPaneTextCompilador = new JScrollPane(textAreaPrincipal);
		bordaCountLinhas = new TextLineNumber(textAreaPrincipal);
		scrollPaneTextCompilador.setRowHeaderView(bordaCountLinhas);

		// Tabela do Analisador Lexico
		tableAnalisadorLexico = new TableAnalisadorLexico();
		painelPrincipal.add(tableAnalisadorLexico);
		tableAnalisadorLexico.setVisible(false);
		scrollTableAnalisadorLexico = new JScrollPane(tableAnalisadorLexico);
		scrollTableAnalisadorLexico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollTableAnalisadorLexico.setVisible(false);

		// Aba Console e TextArea
		tabPaneConsole = new JTabbedPane(JTabbedPane.TOP);
		tabPaneConsole.setAutoscrolls(true);
		tabPaneConsole.setBackground(Color.WHITE);

		textAreaConsole = new JTextArea();
		textAreaConsole.setBackground(new Color(221, 221, 221));
		textAreaConsole.setFont(getDefaultFont());
		textAreaConsole.setEditable(false);
		tabPaneConsole.addTab("Console", MasterImage.monitor, textAreaConsole, "");
		tabPaneConsole.setEnabledAt(0, true);

		GroupLayout gl_painel = new GroupLayout(painelPrincipal);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_painel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 658, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_painel.createSequentialGroup()
							.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneTextCompilador, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
								.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE))
							.addGap(18)
							.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap()
					.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_painel.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneTextCompilador, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		GroupLayout gl_panel = new GroupLayout(painelBotoes);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAbrir, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExecutar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDebug, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnNovo, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(btnAbrir, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(btnExecutar, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(btnDebug, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(btnSalvar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		painelBotoes.setLayout(gl_panel);
		painelPrincipal.setLayout(gl_painel);
	}
	
	private void limparConsole() {
		textAreaConsole.setText("");
	}
	
	private void limparArea() {
		textAreaPrincipal.setText("");
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}
