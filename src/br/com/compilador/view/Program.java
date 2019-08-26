package br.com.compilador.view;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class Program  extends JFrame{

public String EnderecoArquivo,EnderecoTemporario,bufferIn;
public String data ;
public int resultadoJanelas;
public ArrayList<String> Numero = new ArrayList<>();
public ArrayList<String> Identificador = new ArrayList<>();
public ArrayList<String> Operador = new ArrayList<>();
public ArrayList<String> Separador = new ArrayList<>();
public ArrayList<String> Palavra = new ArrayList<>();


public Program() {
	initComponents();
}
private javax.swing.JList jList1;
private javax.swing.JMenu jMenu2;
private javax.swing.JMenu jMenu3;
private javax.swing.JMenu jMenu4;
private javax.swing.JMenuBar jMenuBar1;
private javax.swing.JMenuItem jMenuItem1;
private javax.swing.JMenuItem jMenuItem2;
private javax.swing.JMenuItem jMenuItem3;
private javax.swing.JMenuItem jMenuItem4;
private javax.swing.JMenuItem jMenuItem5;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JScrollPane jScrollPane2;
private javax.swing.JTable jTable1;
private java.awt.TextArea textArea1;



private void initComponents() {

	jScrollPane1 = new javax.swing.JScrollPane();
	jTable1 = new javax.swing.JTable();
	textArea1 = new java.awt.TextArea();
	jScrollPane2 = new javax.swing.JScrollPane();
	jList1 = new javax.swing.JList();
	jMenuBar1 = new javax.swing.JMenuBar();
	jMenu2 = new javax.swing.JMenu();
	jMenuItem1 = new javax.swing.JMenuItem();
	jMenuItem2 = new javax.swing.JMenuItem();
	jMenuItem3 = new javax.swing.JMenuItem();
	jMenu3 = new javax.swing.JMenu();
	jMenuItem5 = new javax.swing.JMenuItem();
	jMenu4 = new javax.swing.JMenu();
	jMenuItem4 = new javax.swing.JMenuItem();

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	jTable1.setModel(new javax.swing.table.DefaultTableModel(
		new Object [][] {},
		new String [] {
			"Palavras", "Identificador", "Separador", "Operador", "Numero"
		}
	));


	jScrollPane1.setViewportView(jTable1);

	textArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

	jScrollPane2.setViewportView(jList1);

	jMenu2.setText("Arquivo");

	jMenuItem1.setText("Abrir arquivo");
	jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			jMenuItem1ActionPerformed(evt);
		}
	});
	jMenu2.add(jMenuItem1);

	jMenuItem2.setText("Salvar arquivo");
	jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			jMenuItem2ActionPerformed(evt);
		}
	});
	jMenu2.add(jMenuItem2);

	jMenuItem3.setText("Sair");
	jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			jMenuItem3ActionPerformed(evt);
		}
	});
	jMenu2.add(jMenuItem3);

	jMenuBar1.add(jMenu2);

	jMenu3.setText("Ferramentas");

	jMenuItem5.setText("Analisar");
	jMenu3.add(jMenuItem5);
	jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			jMenuItem5ActionPerformed(evt);
		}
	});
	jMenuBar1.add(jMenu3);

	jMenu4.setText("Sobre");

	jMenuItem4.setText("Exemplo de linguagem");
	jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			jMenuItem4ActionPerformed(evt);
		}
	});
	jMenu4.add(jMenuItem4);

	jMenuBar1.add(jMenu4);

	setJMenuBar(jMenuBar1);

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
		.addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addComponent(jScrollPane2)
	);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
	);

	pack();
}// </editor-fold>					  


 /**
Metodo que evalua nuestro caracter si existe y nos retorna
verdadero para los separadores
y
falso para los operadores
*/
public static boolean Caracter(char c){
	if(c=='(') return true;
	else if(c==')')return true;
	else if(c=='<')return false;
	else if(c=='>')return false;
	else return false;
}

/**
retornamos nuestro caracter de operador
*/
public static char Operador(char c){
	char car=' ';
	if(c=='<')car='<';
	else if(c=='>')car='>';
	return car;
}

/**
retornamos nuestro caracter de separador
*/
public static char Separador(char c){
	char car=' ';
	if(c=='(') car='(';
	else if(c==')')car=')';
	return car;
}



public static boolean palavraReservada(String cad){
	//AQUI FICA A TABELA DE PALAVRAS RESERVADAS
	if(cad.equalsIgnoreCase("programa")) return true;
	else if(cad.equalsIgnoreCase("se")) return true;
	else if(cad.equalsIgnoreCase("fim")) return true;
	else if(cad.equalsIgnoreCase("variaveis")) return true;
	else if(cad.equalsIgnoreCase("repita")) return true;
	else if(cad.equalsIgnoreCase("fimse")) return true;
	else if(cad.equalsIgnoreCase("para")) return true;
	else if(cad.equalsIgnoreCase("ate")) return true;
	else if(cad.equalsIgnoreCase("faz")) return true;
	else if(cad.equalsIgnoreCase("enquanto")) return true;
	else if(cad.equalsIgnoreCase("entao"))return true;
	else if(cad.equalsIgnoreCase("leia"))return true;
	else if(cad.equalsIgnoreCase("escreva")) return true;
	else if(cad.equalsIgnoreCase("procedimento")) return true;
	else if(cad.equalsIgnoreCase("funcao")) return true;
	else if(cad.equalsIgnoreCase("inicio"))return true;
	else return false;
}
@SuppressWarnings({ })
private void Analise() throws IOException{

		EnderecoTemporario =  System.getProperty("java.io.tmpdir")+"temp.txt";
   	 File arquivo = new File(EnderecoTemporario);  
   	 FileWriter fw = new FileWriter(arquivo);  
   	 BufferedWriter bw = new BufferedWriter(fw);	  
   	 bw.write(textArea1.getText());  
   	 bw.flush();  
   	 bw.close();  

  		 try{
  			 DataInputStream in=new DataInputStream(new FileInputStream(EnderecoTemporario));//leemos nuestro archivo de entrada
  			 try{
  				 while((bufferIn=in.readLine())!=null){//mientras no lleguemos al fin del archivo...
  					 int i=0;
  					 String cad=bufferIn.trim();
  					 //eliminamos los espacios en blanco al incio o al final (pero no a la mitad)
  					 while(i<cad.length()){//recorremos la línea
  						 char t=cad.charAt(i);//vamos leyendo caracter por caracter
  						 if(Character.isDigit(t)){//comprobamos si es un digito
  							 String ora="";
  							 ora+=t;
  							 int j=i+1;
  							 while(Character.isDigit(cad.charAt(j))){
  							 //mientras el siguiente elemento sea un numero
  								 ora+=cad.charAt(j);//concatenamos
  								 j++;
  								 if(j==cad.length())break;//rompemos si llegamos al final de la línea
  							 }
  							 i=j;//movemos a nuestra variable i en la cadena
  							 System.out.println("Número-->"+ora);
  							 Numero.add(ora);
  							 continue;//pasamos al siguiente elemento
  						 }//end if si es Digito
  						 else if(Character.isLetter(t)){//comprobamos si es una letra
  							 String ora="";
  							 ora+=t;
  							 int j=i+1;
  							 while(Character.isLetterOrDigit(cad.charAt(j))){
  							 //mientras el siguiente elemento sea una letra o un digito
  							 //ya que las variables pueden ser con numeros
  								 ora+=cad.charAt(j);
  								 j++;
  								 if(j==cad.length())break;
  							 }
  							 i=j;
  							 if(palavraReservada(ora)){//comprobamos si es una palabra reservada
  								 System.out.println("Palabra reservada="+ora);
  								 Palavra.add(ora);
  							 }
  							 else{//caso contrario es un identificador o variable
  								 System.out.println("Identificador-->"+ora);
  								 Identificador.add(ora);
  							 }
  							 continue;
  						 }//end if si es variable
  						 else if(!Character.isLetterOrDigit(t)){
  						 //si no es letra ni digito entonces...
  							 if(Caracter(t)){//¿es separador?
  								 System.out.println("Separador-->"+Separador(t));
  								 String c = ""+Separador(t);
  								 Separador.add(c);
  							 }else{//¿o es un operador?
  								 System.out.println("Operador-->"+Operador(t));
  								 String c = ""+Operador(t);
  								 Operador.add(c);
  							 }  
  							 i++;
  							 continue;
  						 }//end if si es diferente de letra y digito
  					 }
  				 }//end while
  			 }catch(IOException e){}
  		 }catch(FileNotFoundException e){}

}




  	 private void AbrirArquivo(){
  		 try{
  			  FileReader fileReader = new FileReader(EnderecoArquivo);
  			  BufferedReader reader = new BufferedReader(fileReader);
  			  while((data = reader.readLine()) != null){
  				 textArea1.append(data+"\n");
  			 }
  			 fileReader.close();
  			 reader.close();
  		 }
  		 catch(Exception erro){
  			 JOptionPane.showMessageDialog(null, erro.getMessage());
  		 }
  	 }

  	 private void SalvarArquivo(){


  		 try{
  			 FileWriter escrever;
  			 escrever = new FileWriter(new File(EnderecoArquivo));  
  			 escrever.write(textArea1.getText());  
  			 escrever.close();  
  		 }catch(Exception erro){
  			 JOptionPane.showMessageDialog(null, erro.getMessage());
  		 }

  	 }


  	 private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {										  

  		JFileChooser openFile = new JFileChooser();
  		openFile.showOpenDialog(openFile);
  		resultadoJanelas = JFileChooser.OPEN_DIALOG;

  		if(JFileChooser.APPROVE_OPTION == resultadoJanelas){
  			  EnderecoArquivo = openFile.getSelectedFile().toString();
  			  AbrirArquivo();
  		}


  	 }										  

  	 private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {										  

  		JFileChooser saveFile = new JFileChooser();
  		saveFile.showSaveDialog(saveFile);
  		resultadoJanelas = JFileChooser.OPEN_DIALOG;
  		 if(JFileChooser.APPROVE_OPTION == resultadoJanelas){
  			  EnderecoArquivo = saveFile.getSelectedFile().toString();
  			  SalvarArquivo();
  			  JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
  		}


  	 }										  

  	 private void Fechar(){
  		   String message = "Tem certeza que deseja sair do analisador?";
  		   String title = "Confirmação";
  		   int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
  		   if (reply == JOptionPane.YES_OPTION){
  			 System.exit(0);
  		 }

  	 }

  	 private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {										  
  		 Fechar();
  	 }										  

  	 private void MenssagemPercaDados(){
  		  String message = "Você vai perder os dados na caixa de texto, deseja prosseguir?";
  		   String title = "Confirmação";
  		   int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
  		   if (reply == JOptionPane.YES_OPTION){
  			textArea1.setText("");
  			textArea1.setText("programa NomePrograma;"
  				 + "\n"+"variaveis "
  				 + "\n"+"x,i,z:inteiro"
  				 + "\n"+"inicio"
  				 + "\n"+"escreva('Digite o numero: ');"
  				 + "\n"+"leia(x);"
  				 + "\n"+"escreva('Digite o numero: ')"
  				 + "\n"+"leia(i);"
  				 + "\n"+"escreva('Resultado: ');"
  				 + "\n"+"fim.");
  		 }
  	 }


  	 private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {										  

  		 MenssagemPercaDados();

  	 }										  
  	 private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {										  

  		try{
		   Analise();
		   DefaultTableModel var = (DefaultTableModel) jTable1.getModel();
		   for(int i=0;i<Palavra.size();i++){
			   var.addRow(new String[]{Palavra.get(i)});
		   }
		   for(int i=0;i<Identificador.size();i++){
			   var.addRow(new String[]{null,Identificador.get(i)});
		   }
		   for(int i=0;i<Separador.size();i++){
			   var.addRow(new String[]{null,null,Separador.get(i)});
		   }
		   for(int i=0;i<Operador.size();i++){
			   var.addRow(new String[]{null,null,null,Operador.get(i)});
		   }
		   for(int i=0;i<Numero.size();i++){
			   var.addRow(new String[]{null,null,null,null,Numero.get(i)});
		   }

		  // var.addRow(new String[]{palavra,identificador,"oi",Numero,"oi","oi,","oii"});

  		}
  		catch(Exception e){
		   JOptionPane.showMessageDialog(null, "Houve um erro: "+e.getMessage());
  		}
  	 }  

public static void main(String[] args) {

	  new Program().setVisible(true);

}

}