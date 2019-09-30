package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;

import br.com.compilador.analisadores.AnalisadorLexico;
import br.com.compilador.analisadores.TabelaParsingMatriz;
import br.com.compilador.analisadores.TokensNaoTerminais;
import br.com.compilador.analisadores.TokensTerminais;
import br.com.compilador.image.MasterImage;
import br.com.compilador.model.Pilha;
import br.com.compilador.model.PilhaErros;
import br.com.compilador.util.ColorirPalavras;
import br.com.compilador.util.ManipularArquivo;
import br.com.compilador.util.Msg;
import br.com.compilador.util.TextLineNumber;
import br.com.compilador.view.table.TableAnalisadorLexico;
import br.com.compilador.view.table.TableAnalisadorSintatico;

public class PrincipalForm extends JFrame {
	private static final long serialVersionUID = -4121820897834715812L;

	// Componentes
	private JPanel painelPrincipal, painelBotoes;
	private JButton btnNovo, btnAbrir, btnSalvar, btnExecutar, btnDebug, btnResumeProx, btnParar, btnSair;

	// Componentes btnAbrir
	private JFileChooser fileChooser;
	private File arquivoFileChooser;

	// TextArea Compilador
	private JTextPane textAreaPrincipal;
	private JScrollPane scrollPaneTextCompilador;
	private TextLineNumber bordaCountLinhas; // Borda de Números

	// Tabelas
	private JPanel painelSintatico, painelLexico;
	private TableAnalisadorSintatico tableAnalisadorSintatico;
	private TableAnalisadorLexico tableAnalisadorLexico;
	private JScrollPane scrollTableAnalisadorLexico, scrollTableAnalisadorSintatico;

	// TextArea Console
	private JTabbedPane tabPaneConsole;
	private JTextArea textAreaConsole;

	// Analisadores
	private AnalisadorLexico analisadorLexico;
	private Stack<Pilha> simbolosLexicos;

	// Auxiliares
	private String[] matrizProd;
	private int codAux;
	private TokensNaoTerminais tokensNaoTerminais = new TokensNaoTerminais();
	private TokensTerminais tokensTerminais = new TokensTerminais();
	private boolean debugAtivo = false, erroLexico = false;
	private JScrollPane scrollPane;
	private JPanel panel;
	private ColorirPalavras colorirPalavras = new ColorirPalavras();

	public PrincipalForm() {
		setTitle("Compilador LMS v1.0.0-betha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1350);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setIconImage(MasterImage.iconePrincipal.getImage());

		criarComponentes();
		acoesComponentes();
	}

	private void acoesComponentes() {

		btnNovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limparArea();
				limparConsole();
				limparTabelas();
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
						limparTabelas();
						textAreaPrincipal.setText(texto.toString());
						setTitle("Compilador LMS v1.0.0-betha" + " - " + arquivoFileChooser.getAbsolutePath());
					} catch (IOException e) {
						new Msg().mensagemErro("Problema ao ler arquivo selecionado!");
					}
					adicionarLinhaPadraoSintatico();
					tableAnalisadorLexico.limparTabela();
					analisarLexico();
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

					if (!caminhoArquivo.endsWith(".txt")) {
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
				textAreaConsole.setForeground(Color.black);
				limparConsole();
				tableAnalisadorLexico.limparTabela();
				analisarLexico();
				tableAnalisadorSintatico.limparTabela();
				analisarSintatico();
			}
		});

		btnDebug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textAreaConsole.setForeground(Color.black);
				if (debugAtivo) {
					debugAtivo = false;
					btnDebug.setIcon(MasterImage.debugOff);
					btnDebug.setToolTipText("Debug OFF");
					btnResumeProx.setEnabled(false);
					btnParar.setEnabled(false);
				} else {
					debugAtivo = true;
					btnDebug.setIcon(MasterImage.debugOn);
					btnDebug.setToolTipText("Debug ON");
					btnResumeProx.setEnabled(true);
					btnParar.setEnabled(true);
				}

				limparConsole();
				limparTabelas();
				analisarLexico();
				tableAnalisadorSintatico.adicionarLinha(new Object[] { 52, "PROGRAMA" });

				tableAnalisadorLexico.setVisible(debugAtivo);
				scrollTableAnalisadorLexico.setVisible(debugAtivo);
				painelSintatico.setVisible(debugAtivo);
				tableAnalisadorSintatico.setVisible(debugAtivo);
				scrollTableAnalisadorSintatico.setVisible(debugAtivo);
				painelLexico.setVisible(debugAtivo);
				painelBotoes.repaint();
				painelBotoes.revalidate();
			}
		});

		btnResumeProx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				analisarSintaticoDebug();
			}
		});

		btnParar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limparConsole();
				limparTabelas();
				textAreaConsole.setText("Execução parada.");
				textAreaConsole.setForeground(Color.red);
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

		// TextArea principal do compilador
		textAreaPrincipal = new JTextPane(getDoc());
		textAreaPrincipal.setFont(getDefaultFont()); // Fonte padrão

		scrollPaneTextCompilador = new JScrollPane(textAreaPrincipal);
		bordaCountLinhas = new TextLineNumber(textAreaPrincipal);
		scrollPaneTextCompilador.setRowHeaderView(bordaCountLinhas);

		// Tabela do Analisador Lexico
		painelSintatico = new JPanel();
		painelSintatico.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Analisador Sint\u00E1tico", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		painelSintatico.setVisible(false);
		
		// Aba Console e TextArea
		tabPaneConsole = new JTabbedPane(JTabbedPane.TOP);
		tabPaneConsole.setBackground(Color.WHITE);
		
		painelLexico = new JPanel();
		painelLexico.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Analisador L\u00E9xico", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		painelLexico.setVisible(false);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Menu", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		
		GroupLayout gl_painel = new GroupLayout(painelPrincipal);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painel.createSequentialGroup()
							.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneTextCompilador, GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE)
								.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE))
							.addGap(18))
						.addGroup(gl_painel.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addComponent(painelSintatico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelLexico, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(53)
							.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_painel.createSequentialGroup()
									.addGap(23)
									.addComponent(scrollPaneTextCompilador, GroupLayout.PREFERRED_SIZE, 479, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
									.addComponent(tabPaneConsole, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_painel.createSequentialGroup()
									.addComponent(painelSintatico, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(painelLexico, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_painel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
				// Painel dos botões
				painelBotoes = new JPanel();
				
						// Botões principais
						btnNovo = new JButton(MasterImage.novo);
						btnNovo.setToolTipText("Novo");
						btnNovo.setFocusable(false);
						
								btnAbrir = new JButton(MasterImage.abrir);
								btnAbrir.setToolTipText("Abrir");
								btnAbrir.setFocusable(false);
								
										btnSalvar = new JButton(MasterImage.salvar);
										btnSalvar.setToolTipText("Salvar");
										btnSalvar.setFocusable(false);
										
												btnExecutar = new JButton(MasterImage.executar);
												btnExecutar.setToolTipText("Executar");
												btnExecutar.setFocusable(false);
												
														btnDebug = new JButton(MasterImage.debugOff);
														btnDebug.setToolTipText("Debug OFF");
														btnDebug.setFocusable(false);
														
																btnResumeProx = new JButton(MasterImage.resumeProx);
																btnResumeProx.setToolTipText("Resume/Próximo");
																btnResumeProx.setFocusable(false);
																btnResumeProx.setEnabled(false);
																
																		btnParar = new JButton(MasterImage.parar);
																		btnParar.setToolTipText("Parar");
																		btnParar.setFocusable(false);
																		btnParar.setEnabled(false);
																		
																				btnSair = new JButton(MasterImage.sair);
																				btnSair.setToolTipText("Sair");
																				btnSair.setFocusable(false);
																				painelBotoes.setLayout(new GridLayout(0, 8, 0, 0));
																				painelBotoes.add(btnNovo);
																				painelBotoes.add(btnAbrir);
																				painelBotoes.add(btnSalvar);
																				painelBotoes.add(btnExecutar);
																				painelBotoes.add(btnDebug);
																				painelBotoes.add(btnResumeProx);
																				painelBotoes.add(btnParar);
																				painelBotoes.add(btnSair);
																				GroupLayout gl_panelmenu2 = new GroupLayout(panel);
																				gl_panelmenu2.setHorizontalGroup(
																						gl_panelmenu2.createParallelGroup(Alignment.LEADING)
																						.addGroup(gl_panelmenu2.createSequentialGroup()
																							.addContainerGap()
																							.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																							.addContainerGap(85, Short.MAX_VALUE))
																				);
																				gl_panelmenu2.setVerticalGroup(
																						gl_panelmenu2.createParallelGroup(Alignment.LEADING)
																						.addGroup(gl_panelmenu2.createSequentialGroup()
																							.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																				);
																				panel.setLayout(gl_panelmenu2);
		
		scrollPane = new JScrollPane();
		tabPaneConsole.addTab("Console", null, scrollPane, null);
		textAreaConsole = new JTextArea();
		scrollPane.setViewportView(textAreaConsole);
		textAreaConsole.setBackground(new Color(221, 221, 221));
		textAreaConsole.setFont(getDefaultFont());
		textAreaConsole.setEditable(false);
		
				tableAnalisadorLexico = new TableAnalisadorLexico();
				painelPrincipal.add(tableAnalisadorLexico);
				tableAnalisadorLexico.setVisible(false);
				scrollTableAnalisadorLexico = new JScrollPane(tableAnalisadorLexico);
				scrollTableAnalisadorLexico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollTableAnalisadorLexico.setVisible(false);
		GroupLayout gl_panelmenu = new GroupLayout(painelLexico);
		gl_panelmenu.setHorizontalGroup(
			gl_panelmenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelmenu.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_panelmenu.setVerticalGroup(
			gl_panelmenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelmenu.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		painelLexico.setLayout(gl_panelmenu);
		
				// Tabela do Analisador Sintatico
				tableAnalisadorSintatico = new TableAnalisadorSintatico();
				painelPrincipal.add(tableAnalisadorSintatico);
				tableAnalisadorSintatico.setVisible(false);
				scrollTableAnalisadorSintatico = new JScrollPane(tableAnalisadorSintatico);
				scrollTableAnalisadorSintatico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				GroupLayout gl_panel2 = new GroupLayout(painelSintatico);
				gl_panel2.setHorizontalGroup(
						gl_panel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel2.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollTableAnalisadorSintatico, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
							.addContainerGap())
				);
				gl_panel2.setVerticalGroup(
						gl_panel2.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel2.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollTableAnalisadorSintatico, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
							.addContainerGap())
				);
				painelSintatico.setLayout(gl_panel2);
				scrollTableAnalisadorSintatico.setVisible(false);
		painelPrincipal.setLayout(gl_painel);
	}

	private void analisarLexico() {
		
		if(tableAnalisadorLexico.getRowCount() != 0) {
			return;
		}
		
		if (analisadorLexico == null) {
			analisadorLexico = new AnalisadorLexico();
		}

		try {
			simbolosLexicos = analisadorLexico.analisar(textAreaPrincipal.getText());
			for (Pilha p : simbolosLexicos) {
				tableAnalisadorLexico.adicionarLinha(new Object[] { p.getCodigo(), p.getSimbolo(), p.getLinha() });
			}
			if (analisadorLexico.getErros() != null) {
				for (PilhaErros p : analisadorLexico.getErros()) {
					textAreaConsole.setText(p.getErro() + p.getLinha() + "\n");
				}
			} else {
				if(tableAnalisadorLexico.getRowCount() != 0) {
					textAreaConsole.append("Análise Léxica Finalizada.\n");
					erroLexico = false;
				}
			}
			tableAnalisadorLexico.selecionaPrimeiraLinha();
			analisadorLexico = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void analisarSintatico() {
		adicionarLinhaPadraoSintatico();
		
		while (tableAnalisadorLexico.getRowCount() != 0 && erroLexico == false) {
			analisarSintaticoDebug();
		}

	}

//	private void analisarSintatico() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					while (!Thread.currentThread().isInterrupted()) {
//						
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							throw e;
//						}
//						
//				analisarSintaticoDebug();
//
//					if(tableAnalisadorLexico.getRowCount() == 0 || erroLexico == true) {
//						textAreaConsole.append("Análise Sintática Finalizada.");
//						break;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	},"Teste.arquivosPastaPrincipal").start();
//	}

	private void analisarSintaticoDebug() {
		
		if(tableAnalisadorLexico.getRowCount() == 0) {
			return;
		}
		
		if (tokensTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			if (tableAnalisadorSintatico.getValorLinhaSelecionada(0) == tableAnalisadorLexico
					.getValorLinhaSelecionada(0)) {
				excluirLinhasIniciaisTabelas();
			} else {
				erroLexico = true;
				textAreaConsole.append("Erro na linha " + tableAnalisadorLexico.getValorLinhaSelecionada(2) + "\n");
			}
		} else if (tokensNaoTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			matrizProd = getDerivacoes(tableAnalisadorSintatico.getValorLinhaSelecionada(0),
					tableAnalisadorLexico.getValorLinhaSelecionada(0));
			if (matrizProd != null) {
				textAreaConsole.append("[" + tableAnalisadorSintatico.getValorLinhaSelecionada(0) + ","
						+ tableAnalisadorLexico.getValorLinhaSelecionada(0) + "] Derivou em ");
						for(String s : matrizProd ) {
							textAreaConsole.append(s + "|");
						}
						textAreaConsole.append("\n");
				if (matrizProd[0] == "NULL") { // .equals
					tableAnalisadorSintatico.excluirLinhasSelecionadas();
					tableAnalisadorSintatico.selecionaPrimeiraLinha();
				} else {
					tableAnalisadorSintatico.excluirLinhasSelecionadas();
					tableAnalisadorSintatico.selecionaPrimeiraLinha();

					for (int i = matrizProd.length - 1; i > -1; i--) {
						if (tokensNaoTerminais.getCodToken(matrizProd[i]) == 0) {
							codAux = tokensTerminais.getCodToken(matrizProd[i]);
						} else {
							codAux = tokensNaoTerminais.getCodToken(matrizProd[i]);
						}

						tableAnalisadorSintatico
								.inserirLinha(new Object[] { codAux == 0 ? 25 : codAux, matrizProd[i] });
					}
				}
				tableAnalisadorSintatico.selecionaPrimeiraLinha();
			} else {
				erroLexico = true;
				textAreaConsole.append("Erro na linha " + tableAnalisadorLexico.getValorLinhaSelecionada(2) + "\n");
				textAreaConsole.append("A derivação [" + tableAnalisadorSintatico.getValorLinhaSelecionada(0) + ","
						+ tableAnalisadorLexico.getValorLinhaSelecionada(0) + "] \'"+
						tokensNaoTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0))+ ","+
						tokensTerminais.getSimbolo(tableAnalisadorLexico.getValorLinhaSelecionada(0))+
						"\' Não foi encontrada na tabela de parsing.");
			}
		}
		
		if(tableAnalisadorLexico.getRowCount() == 0 && tableAnalisadorSintatico.getRowCount() == 0) {
			textAreaConsole.append("Análise Sintática Finalizada.");
		}

		matrizProd = null;
	}

	public String[] getDerivacoes(int naoTerminal, int terminal) {
		String[] derivacao = null;
		for (TabelaParsingMatriz matrizAnalise : TabelaParsingMatriz.values()) {
			derivacao = matrizAnalise.getDerivacao(naoTerminal, terminal);

			if (derivacao != null) {
				break;
			}
		}

		return derivacao;
	}

	private void excluirLinhasIniciaisTabelas() {
		tableAnalisadorLexico.selecionaPrimeiraLinha();
		tableAnalisadorSintatico.selecionaPrimeiraLinha();
		tableAnalisadorLexico.excluirLinhasSelecionadas();
		tableAnalisadorSintatico.excluirLinhasSelecionadas();
		tableAnalisadorLexico.selecionaPrimeiraLinha();
		tableAnalisadorSintatico.selecionaPrimeiraLinha();
	}

	private void limparConsole() {
		textAreaConsole.setText("");
	}

	private void limparTabelas() {
		tableAnalisadorLexico.limparTabela();
		tableAnalisadorSintatico.limparTabela();
	}

	private void limparArea() {
		textAreaPrincipal.setText("");
	}
	
	private StyledDocument getDoc() {
		HashMap<String, Integer> mapTokens = tokensTerminais.getMap();
		String palavras="";
		for (Map.Entry<String, Integer> candidatoEntry : mapTokens.entrySet()) { 
			if(candidatoEntry.getValue() < 30 && !candidatoEntry.getKey().equals("INTEIRO") && !candidatoEntry.getKey().equals("IDENTIFICADOR")) {
				palavras += candidatoEntry.getKey() + "|";
			}
		}
		palavras = palavras.substring(0, palavras.length()-1);
		
		return colorirPalavras.colorir(Color.BLUE, palavras);
	}
	
	private void adicionarLinhaPadraoSintatico() {
		if(tableAnalisadorSintatico.getRowCount() == 0) {
			tableAnalisadorSintatico.adicionarLinha(new Object[] { 52, "PROGRAMA" });
		}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}