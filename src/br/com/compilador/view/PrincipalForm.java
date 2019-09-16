package br.com.compilador.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

import br.com.compilador.analisadores.AnalisadorLexico;
import br.com.compilador.analisadores.TabelaParsing;
import br.com.compilador.analisadores.TokensNaoTerminais;
import br.com.compilador.analisadores.TokensTerminais;
import br.com.compilador.image.MasterImage;
import br.com.compilador.model.Pilha;
import br.com.compilador.model.PilhaErros;
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
	private JTextArea textAreaPrincipal;
	private JScrollPane scrollPaneTextCompilador;
	private TextLineNumber bordaCountLinhas; // Borda de Números

	// Tabelas
	private TableAnalisadorSintatico tableAnalisadorSintatico;
	private TableAnalisadorLexico tableAnalisadorLexico;
	private JScrollPane scrollTableAnalisadorLexico, scrollTableAnalisadorSintatico;

	// TextArea Console
	private JTabbedPane tabPaneConsole;
	private JTextArea textAreaConsole;

	// Analisadores
	private AnalisadorLexico analisadorLexico;
	private TabelaParsing tabelaParsing;

	// Auxiliares
	private Stack<Pilha> simbolos;
	private TokensNaoTerminais tokensNaoTerminais = new TokensNaoTerminais();
	private TokensTerminais tokensTerminais = new TokensTerminais();
	private boolean debugAtivo = false;
	private int codAux;
	private ArrayList<String> listDerivacao;

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
				limparConsole();
				analisarLexico();
				analisarSintatico();
			}
		});

		btnDebug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				analisarLexico();
				analisarSintatico();

				tableAnalisadorLexico.setVisible(debugAtivo);
				scrollTableAnalisadorLexico.setVisible(debugAtivo);
				tableAnalisadorSintatico.setVisible(debugAtivo);
				scrollTableAnalisadorSintatico.setVisible(debugAtivo);
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

		// Tabela do Analisador Sintatico
		tableAnalisadorSintatico = new TableAnalisadorSintatico();
		painelPrincipal.add(tableAnalisadorSintatico);
		tableAnalisadorSintatico.setVisible(false);
		scrollTableAnalisadorSintatico = new JScrollPane(tableAnalisadorSintatico);
		scrollTableAnalisadorSintatico.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollTableAnalisadorSintatico.setVisible(false);

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
		gl_painel.setHorizontalGroup(gl_painel.createParallelGroup(Alignment.LEADING).addGroup(gl_painel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_painel.createParallelGroup(Alignment.LEADING).addGroup(gl_painel.createSequentialGroup()
						.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneTextCompilador, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
								.addComponent(tabPaneConsole, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE))
						.addGap(18)
						.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollTableAnalisadorSintatico, GroupLayout.PREFERRED_SIZE, 234,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 235,
										GroupLayout.PREFERRED_SIZE)))
						.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 470, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		gl_painel.setVerticalGroup(gl_painel.createParallelGroup(Alignment.LEADING).addGroup(gl_painel
				.createSequentialGroup().addContainerGap()
				.addComponent(painelBotoes, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING).addGroup(gl_painel.createSequentialGroup()
						.addComponent(scrollPaneTextCompilador, GroupLayout.PREFERRED_SIZE, 502,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tabPaneConsole, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painel.createSequentialGroup()
								.addComponent(scrollTableAnalisadorSintatico, GroupLayout.DEFAULT_SIZE, 303,
										Short.MAX_VALUE)
								.addGap(18).addComponent(scrollTableAnalisadorLexico, GroupLayout.PREFERRED_SIZE, 316,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		painelBotoes.setLayout(new GridLayout(0, 8, 0, 0));
		painelBotoes.add(btnNovo);
		painelBotoes.add(btnAbrir);
		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnExecutar);
		painelBotoes.add(btnDebug);
		painelBotoes.add(btnResumeProx);
		painelBotoes.add(btnParar);
		painelBotoes.add(btnSair);
		painelPrincipal.setLayout(gl_painel);
	}

	private void analisarLexico() {
		if (analisadorLexico == null) {
			analisadorLexico = new AnalisadorLexico();
		}

		try {
			simbolos = analisadorLexico.analisar(textAreaPrincipal.getText());
			for (Pilha p : simbolos) {
				tableAnalisadorLexico.adicionarLinha(new Object[] { p.getCodigo(), p.getSimbolo(), p.getLinha() });
			}
			if (analisadorLexico.getErros() != null) {
				for (PilhaErros p : analisadorLexico.getErros()) {
					textAreaConsole.setText(p.getErro() + p.getLinha() + "\n");
				}
			} else {
				textAreaConsole.append("Analisador Léxico Finalizado.");
			}
			tableAnalisadorLexico.selecionaPrimeiraLinha();
			analisadorLexico = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void analisarSintatico() {
		tableAnalisadorSintatico.limparTabela();
		tableAnalisadorSintatico.adicionarLinha(new Object[] { 52, "PROGRAMA" });
	}

	private void analisarSintaticoDebug() {
		if (tabelaParsing == null) {
			tabelaParsing = new TabelaParsing();
		}
		
		if(tokensTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			if(tableAnalisadorSintatico.getValorLinhaSelecionada(0) == tableAnalisadorLexico.getValorLinhaSelecionada(0)) {
				excluirLinhasIniciaisTabelas();
			}else {
				textAreaConsole.append("Erro");
			}
		}else if(tokensNaoTerminais.getSimbolo(tableAnalisadorSintatico.getValorLinhaSelecionada(0)) != null) {
			compararTabelas();
			if (listDerivacao != null) {
				tableAnalisadorSintatico.selecionaUltimaLinha();
				for (String aux : listDerivacao) {
//				for (int i = listDerivacao.size() - 1; i>-1; i--) {
					if (tokensNaoTerminais.getCodToken(aux) == 0) {
						codAux = tokensTerminais.getCodToken(aux);
					}else {
						codAux = tokensNaoTerminais.getCodToken(aux);
					}
					tableAnalisadorSintatico.adicionarLinha(new Object[] { codAux == 0 ? 25 : codAux, aux });
					
					if(aux.equals("PROGRAM")) {
						tableAnalisadorSintatico.selecionaPrimeiraLinha();
						tableAnalisadorSintatico.excluirLinhasSelecionadas();
						tableAnalisadorSintatico.selecionaPrimeiraLinha();
					}
				}
			}
			excluirLinhasIniciaisTabelas();
		}
		
		tabelaParsing = null;
		listDerivacao = null;
	}

	private void compararTabelas() {
		listDerivacao = new ArrayList<String>();
		listDerivacao = tabelaParsing.analisarValor(tableAnalisadorSintatico.getValorLinhaSelecionada(0),
				tableAnalisadorLexico.getValorLinhaSelecionada(0));
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
		tableAnalisadorLexico.limparTabela();
		tableAnalisadorSintatico.limparTabela();
	}

	private void limparArea() {
		textAreaPrincipal.setText("");
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
}