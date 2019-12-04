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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;

import br.com.compilador.analisadores.AnalisadorLexico;
import br.com.compilador.analisadores.AnalisadorSemantico;
import br.com.compilador.analisadores.TokensNaoTerminais;
import br.com.compilador.analisadores.TokensTerminais;
import br.com.compilador.enums.TabelaParsingMatriz;
import br.com.compilador.image.MasterImage;
import br.com.compilador.model.Pilha;
import br.com.compilador.model.PilhaErros;
import br.com.compilador.util.ColorirPalavras;
import br.com.compilador.util.ManipularArquivo;
import br.com.compilador.util.Msg;
import br.com.compilador.util.TextLineNumber;
import br.com.compilador.view.table.TableAnalisadorLexico;
import br.com.compilador.view.table.TableAnalisadorSematico;
import br.com.compilador.view.table.TableAnalisadorSintatico;

public class PrincipalForm extends JFrame {
	private static final long serialVersionUID = -4121820897834715812L;

	// Componentes
	private JPanel painelPrincipal, painelBotoes;
	private JButton btnNovo, btnAbrir, btnSalvar, btnExecutar, btnDebug, btnResumeProx, btnParar, btnSair;
	private JFileChooser fileChooser;
	private File arquivoFileChooser;

	// TextArea Compilador
	private JTextPane textAreaPrincipal;
	private JScrollPane scrollPaneTextCompilador;
	private TextLineNumber bordaCountLinhas; // Borda de Números

	// Tabelas
	private JPanel painelSemantico, painelSintatico, painelLexico;
	private TableAnalisadorSintatico tableAnalisadorSintatico;
	private TableAnalisadorLexico tableAnalisadorLexico;
	public static TableAnalisadorSematico tableAnalisadorSematico;
	private JScrollPane scrollTableAnalisadorSemantico, scrollTableAnalisadorLexico, scrollTableAnalisadorSintatico;

	// TextArea Console
	private JTabbedPane tabPaneConsole;
	private JTextArea textAreaConsole;

	// Analisadores
	private AnalisadorLexico analisadorLexico;
	private Stack<Pilha> simbolosLexicos;
	private AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico();

	// Auxiliares
	private String[] matrizProd;
	private String caminhoArquivo = "";
	private int codAux, auxLinha = 0;;
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
				textAreaPrincipal.setText(""); // Limpa o console
				textAreaConsole.setText(""); // Limpa o console
				limparTabelas();
				setTitle("Compilador LMS v1.0.0-betha");
				caminhoArquivo = "";
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
						textAreaPrincipal.setText("");
						textAreaConsole.setText(""); // Limpa o console
						limparTabelas();
						textAreaPrincipal.setText(texto.toString());
						setTitle("Compilador LMS v1.0.0-betha" + " - " + arquivoFileChooser.getAbsolutePath());
						caminhoArquivo = arquivoFileChooser.getAbsolutePath();
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

				if (!caminhoArquivo.isEmpty()) {
					gravarArquivo();
					return;
				}

				if (fileChooser.showSaveDialog(PrincipalForm.this) == JFileChooser.APPROVE_OPTION) {
					arquivoFileChooser = fileChooser.getSelectedFile();

					caminhoArquivo = arquivoFileChooser.getAbsolutePath();

					if (!caminhoArquivo.endsWith(".txt")) {
						caminhoArquivo += ".txt";
					}

					gravarArquivo();
					setTitle("Compilador LMS v1.0.0-betha" + " - " + arquivoFileChooser.getAbsolutePath());
				} else {
					new Msg().mensagemAviso("Salvamento cancelado!");
				}
			}
		});

		btnExecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textAreaConsole.setForeground(Color.black);
				textAreaConsole.setText(""); // Limpa o console
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

				textAreaConsole.setText(""); // Limpa o console
				limparTabelas();
				analisarLexico();
				tableAnalisadorSintatico.adicionarLinha(new Object[] { 52, "PROGRAMA" });

				painelSintatico.setVisible(debugAtivo);
				painelLexico.setVisible(debugAtivo);
				painelSemantico.setVisible(debugAtivo);
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
				textAreaConsole.setText(""); // Limpa o console
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
		painelSintatico.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Analisador Sint\u00E1tico",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		painelSintatico.setVisible(false);

		// Aba Console e TextArea
		tabPaneConsole = new JTabbedPane(JTabbedPane.TOP);
		tabPaneConsole.setBackground(Color.WHITE);

		painelLexico = new JPanel();
		painelLexico.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null),
				"Analisador L\u00E9xico", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		painelLexico.setVisible(false);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Menu",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		painelSemantico = new JPanel();
		painelSemantico.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null),
				"Analisador Sem\u00E2ntico", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		painelSemantico.setVisible(false);

		GroupLayout gl_painel = new GroupLayout(painelPrincipal);
		gl_painel.setHorizontalGroup(gl_painel.createParallelGroup(Alignment.LEADING).addGroup(gl_painel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painel.createSequentialGroup()
								.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
										.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 1372, Short.MAX_VALUE)
										.addComponent(scrollPaneTextCompilador, Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 1225, Short.MAX_VALUE))
								.addGap(18)
								.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(painelLexico, 0, 0, Short.MAX_VALUE)
										.addComponent(painelSintatico, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(painelSemantico, GroupLayout.PREFERRED_SIZE, 433,
												GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		gl_painel.setVerticalGroup(gl_painel.createParallelGroup(Alignment.LEADING).addGroup(gl_painel
				.createSequentialGroup().addContainerGap()
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(6)
				.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_painel.createSequentialGroup()
								.addComponent(scrollPaneTextCompilador, GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
								.addGap(18).addComponent(tabPaneConsole, GroupLayout.PREFERRED_SIZE, 118,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painel.createSequentialGroup()
								.addComponent(painelSemantico, GroupLayout.PREFERRED_SIZE, 287,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(painelSintatico, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
								.addGap(18).addComponent(painelLexico, GroupLayout.PREFERRED_SIZE, 305,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));

		tableAnalisadorSematico = new TableAnalisadorSematico();
		painelPrincipal.add(tableAnalisadorSematico);
		scrollTableAnalisadorSemantico = new JScrollPane(tableAnalisadorSematico);
		scrollTableAnalisadorSemantico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		GroupLayout gl_painelSemantico = new GroupLayout(painelSemantico);
		gl_painelSemantico.setHorizontalGroup(gl_painelSemantico.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSemantico.createSequentialGroup().addContainerGap()
						.addComponent(scrollTableAnalisadorSemantico, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
						.addContainerGap()));
		gl_painelSemantico.setVerticalGroup(gl_painelSemantico.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSemantico.createSequentialGroup().addGap(5)
						.addComponent(scrollTableAnalisadorSemantico, GroupLayout.PREFERRED_SIZE, 251,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		painelSemantico.setLayout(gl_painelSemantico);

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
		gl_panelmenu2.setHorizontalGroup(gl_panelmenu2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelmenu2
						.createSequentialGroup().addContainerGap().addComponent(painelBotoes,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(85, Short.MAX_VALUE)));
		gl_panelmenu2.setVerticalGroup(gl_panelmenu2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelmenu2.createSequentialGroup()
						.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
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
		scrollTableAnalisadorLexico = new JScrollPane(tableAnalisadorLexico);
		scrollTableAnalisadorLexico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GroupLayout gl_panelmenu = new GroupLayout(painelLexico);
		gl_panelmenu.setHorizontalGroup(gl_panelmenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelmenu.createSequentialGroup().addContainerGap()
						.addComponent(scrollTableAnalisadorLexico, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelmenu.setVerticalGroup(gl_panelmenu.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_panelmenu
						.createSequentialGroup().addContainerGap().addComponent(scrollTableAnalisadorLexico,
								GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(42, Short.MAX_VALUE)));
		painelLexico.setLayout(gl_panelmenu);

		// Tabela do Analisador Sintatico
		tableAnalisadorSintatico = new TableAnalisadorSintatico();
		painelPrincipal.add(tableAnalisadorSintatico);
		scrollTableAnalisadorSintatico = new JScrollPane(tableAnalisadorSintatico);
		scrollTableAnalisadorSintatico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		GroupLayout gl_panel2 = new GroupLayout(painelSintatico);
		gl_panel2.setHorizontalGroup(gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel2.createSequentialGroup().addContainerGap()
						.addComponent(scrollTableAnalisadorSintatico, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel2.setVerticalGroup(gl_panel2.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel2.createSequentialGroup().addContainerGap()
						.addComponent(scrollTableAnalisadorSintatico, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addContainerGap()));
		painelSintatico.setLayout(gl_panel2);
		painelPrincipal.setLayout(gl_painel);
	}

	private void analisarLexico() {

		if (tableAnalisadorLexico.getRowCount() != 0) {
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
				mostrarErros(analisadorLexico.getErros());
			} else {
				if (tableAnalisadorLexico.getRowCount() != 0) {
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
	
	private void mostrarErros(Stack<PilhaErros> pilhaErros) {
		for (PilhaErros p : pilhaErros) {
			textAreaConsole.append(p.getErro() + p.getLinha() + "\nErro Semântico.");
		}
	}

	private void analisarSintatico() {
		adicionarLinhaPadraoSintatico();

		while (tableAnalisadorLexico.getRowCount() != 0 && erroLexico == false) {
			analisarSintaticoDebug();
		}
		//limparTabelas();
		analisadorSemantico = null;
	}

	private void analisarSintaticoDebug() {
		if (tableAnalisadorLexico.getRowCount() == 0) {
			return;
		}

		if (tokensTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			if (tableAnalisadorSintatico.getValorLinhaSelecionada(0) == tableAnalisadorLexico
					.getValorLinhaSelecionada(0)) {
				auxLinha = tableAnalisadorLexico.getValorLinhaSelecionada(2);
				
				if(analisadorSemantico == null) {
					analisadorSemantico = new AnalisadorSemantico();
				}
				
				analisadorSemantico.analisar(
						tableAnalisadorLexico.getValorLinhaSelecionada(0),
						tableAnalisadorLexico.getValueAt(tableAnalisadorLexico.getLinhaSelecionada(), 1).toString(),
						tableAnalisadorLexico.getValorLinhaSelecionada(2)
						);
				
				if (analisadorSemantico.getErros() != null) {
					mostrarErros(analisadorSemantico.getErros());
					erroLexico = true;
				}
								
				excluirLinhasIniciaisTabelas();
			} else {
				erroLexico = true;
				auxLinha = auxLinha != tableAnalisadorLexico.getValorLinhaSelecionada(2) ? auxLinha
						: tableAnalisadorLexico.getValorLinhaSelecionada(2);
				textAreaConsole.append("Erro na linha " + auxLinha + ", encontrado " + "\" "
						+ tokensTerminais.getSimbolo(tableAnalisadorLexico.getValorLinhaSelecionada(0)) + " \""
						+ " e esperado " + "\" "
						+ tokensTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) + " \" \n");
			}
		} else if (tokensNaoTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			matrizProd = getDerivacoes(tableAnalisadorSintatico.getValorLinhaSelecionada(0),
					tableAnalisadorLexico.getValorLinhaSelecionada(0));
			if (matrizProd != null) {
				if (debugAtivo) {
					textAreaConsole.append("[" + tableAnalisadorSintatico.getValorLinhaSelecionada(0) + ","
							+ tableAnalisadorLexico.getValorLinhaSelecionada(0) + "] Derivou em ");
					for (String s : matrizProd) {
						textAreaConsole.append(s + "|");
					}
					textAreaConsole.append("\n");
				}
				if (matrizProd[0].equals("NULL")) {
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
				textAreaConsole.append("Erro na linha " + tableAnalisadorLexico.getValorLinhaSelecionada(2));
				textAreaConsole.append(" A derivação [" + tableAnalisadorSintatico.getValorLinhaSelecionada(0) + ","
						+ tableAnalisadorLexico.getValorLinhaSelecionada(0) + "] \'"
						+ tokensNaoTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) + ","
						+ tokensTerminais.getSimbolo(tableAnalisadorLexico.getValorLinhaSelecionada(0))
						+ "\' Não foi encontrada na tabela de parsing. \n"
						+ "Erro Sintático.");
			}
		}

		if (tableAnalisadorLexico.getRowCount() == 0 && tableAnalisadorSintatico.getRowCount() == 0) {
			textAreaConsole.append("Análise Sintática Finalizada.\n");
			textAreaConsole.append("Análise Semântica Finalizada.");
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
	}

	private void limparTabelas() {
		tableAnalisadorLexico.limparTabela();
		tableAnalisadorSintatico.limparTabela();
		tableAnalisadorSematico.limparTabela();
	}

	private void gravarArquivo() {
		try {
			new ManipularArquivo();
			ManipularArquivo.gravarArquivo(caminhoArquivo, textAreaPrincipal.getText());

			new Msg().mensagemSucesso("Arquivo Salvo com Sucesso!");
		} catch (IOException e) {
			new Msg().mensagemErro("Problema ao gravar arquivo!");
		}
	}

	private StyledDocument getDoc() {
		HashMap<String, Integer> mapTokens = tokensTerminais.getMap();
		String palavras = "";
		for (Map.Entry<String, Integer> candidatoEntry : mapTokens.entrySet()) {
			if (candidatoEntry.getValue() < 30 && !candidatoEntry.getKey().equals("INTEIRO")
					&& !candidatoEntry.getKey().equals("IDENTIFICADOR")) {
				palavras += candidatoEntry.getKey() + "|";
			}
		}
		palavras = palavras.substring(0, palavras.length() - 1);

		return colorirPalavras.colorir(Color.BLUE, palavras);
	}

	private void adicionarLinhaPadraoSintatico() {
		if (tableAnalisadorSintatico.getRowCount() == 0) {
			tableAnalisadorSintatico.adicionarLinha(new Object[] { 52, "PROGRAMA" });
		}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}