package cliente;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import aGame.Carta;
import bInterface.InterfaceMao;
import bInterface.InterfaceMesa;

/**
 * Classe responsavel pela thread e main do cliente
 * @author 
 *
 */
//obs: quando o servidor eh encerrado antes do cliente ele encerra os clientes abertos
//     assim que o cliente tentar enviar uma mensagem ele eh tambem para
public class Cliente implements Runnable{ 
	private Socket socket;
	
//	private BufferedReader in;
//	private PrintStream out;
	
	private ObjectOutputStream out; //criar e declarar output antes, evitar erros
	/*
	 * Caso contrário, new ObjectInputStream() bloqueia a tentativa de ler o cabeçalho do fluxo 
	 * que ainda não foi escrito pelos pares new ObjectOutputStream(), porque ele também está 
	 * bloqueado new ObjectInputStream().
	 */
	private ObjectInputStream in;
	
	private boolean inicializado;
	private boolean executando;
	
	private Thread thread;
	
	private String nome;
	private InterfaceMesa frameMesa;
	private InterfaceMao frameMao;
	
	/**
	 * contrutor que recebe uma string endereco, um inteiro porta, e uma string nome
	 *  e manda inicializar conexao e obter InputStream e OutputStream
	 * @param endereco
	 * @param porta
	 * @param nome
	 * @throws Exception
	 */
	public Cliente(String endereco, int porta, String nome) throws Exception{ //construtor //manda inicializar conexao e obter InputStream e OutputStream
		inicializado = false;
		executando = false;
		
		this.nome = nome;
		
		open(endereco, porta);
	}
	
	/**
	 * Funcao que recebe uma string endereco e um int porta e 
	 * estabelece conexao e obtem InputStream e OutputStream da conexao
	 * @param endereco
	 * @param porta
	 * @throws Exception
	 */
	private void open(String endereco, int porta) throws Exception{ //estabelece conexao e obtem InputStream e OutputStream da conexao
		try{
			socket = new Socket(endereco, porta);
			
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintStream(socket.getOutputStream());
			
			out = new ObjectOutputStream(socket.getOutputStream()); //criar variaveis responsaveis pelo envio e recebimento de objetos
			in = new ObjectInputStream(socket.getInputStream());
//			out = new ObjectOutputStream(socket.getOutputStream());
			
			inicializado = true;
		}catch(Exception e){ //
			System.out.println(e);
			close(); //fechar recursos
			throw e; //relancar excecao
		}
	}
	
	/**
	 * Funcao responsavel por fechar/desalocar os recursos utilizados/alocados
	 */
	private void close(){ //fechar/desalocar recursos utilizados/alocados
		if(in != null){
			try{
				in.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		if(out != null){
			try{
				out.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		if(socket != null){
			try{
				socket.close();
			}catch(Exception e){
				System.out.println(e);
			}	
		}
		
		//resetar valores
		in = null;
		out = null;
		
		socket = null;
		
		inicializado = false;
		executando = false;

		thread = null;
	}
	
	/**
	 * Funcao responsavel por tentar iniciar thread auxilliar
	 */
	private void start(){ //tentar iniciar thread auxilliar
		if(!inicializado || executando){
			return;
		}
		
		executando = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Funcao responsavel por encerrar thread auxiliar adequadamente (e seus recursos) chamando a close()
	 *  e dando thread.join() (aguardar thread encerrar)
	 * @throws Exception
	 */
	private void stop() throws Exception{ //encerrar thread auxiliar adequadamente (e seus recursos)
		executando = false;
		
		if(thread != null){
			thread.join(); //esperar ate a thread encerrar
		}
	}
	
	/**
	 * Retorna se a thread ainda esta executando
	 * @return
	 */
	public boolean isExecutando(){
		return executando;
	}
	
//	public void send(String mensagem){ //metodo que permite o envio de mensagens para o servidor
//		out.println(mensagem);
//	}
	
	/**
	 * Metodo que recebe uma mensagem e permite o envio de mensagens para o servidor
	 * @param chatMessage
	 * @throws IOException
	 */
	public void send(ChatMessage chatMessage) throws IOException{ //metodo que permite o envio de mensagens para o servidor
		out.writeObject(chatMessage);
		out.flush(); //garantir que o envio seja feito corretamente / limpar o cache / ler ate o fim do buffer
	}
	
	/**
	 * Metodo que recebe um inteiro e permite o envio de mensagens para o servidor
	 * @param msg
	 * @throws IOException
	 */
	public void sendInt(int msg) throws IOException{ //metodo que permite o envio de mensagens para o servidor
		out.writeObject(msg);
		out.flush(); //garantir que o envio seja feito corretamente / limpar o cache / ler ate o fim do buffer
	}
	
	/*
	 * InterfaceMesa frame = new InterfaceMesa();
		frame.setVisible(true);
		
		frame.pintarCarta(new Carta(1, 2), frame.getLblCartaCentro());
		
		new Scanner(System.in).nextLine();
		
		frame.pintarCarta(new Carta(2, 5), frame.getLblCartaCentro());
		frame.pintarCarta(new Carta(3, 6), frame.getLblJogador0());
		frame.pintarCarta(new Carta(1, 7), frame.getLblJogador1());
		frame.pintarCarta(new Carta(1, 10), frame.getLblJogador2());
		frame.pintarCarta(new Carta(4, 13), frame.getLblJogador3());
//		
	 */
	
	/**
	 * Override do metodo run ja existente da interface runnable (thread)
	 * inicia quando a thread iniciar e roda enquanto a thread estiver executando
	 */
	@Override
	public void run() {
		while(executando){
			try{
				socket.setSoTimeout(2500); //timeout (2,5 s) para operacao de leitura e leitura da mensagem enviada pelo servidor
				
//				String mensagem = in.readLine();
				Object obj = in.readObject();
				
				ChatMessage chatMessage = null;
				if(obj instanceof ChatMessage){
					chatMessage = (ChatMessage) obj;
				}
				
				if(chatMessage == null){ //caso o servidor ja tenha sido encerrado/cai a mensagem retornada sera null, encerrando o cliente tbm
					break;
				}
				
				//mensagem recebida para o usuario
				if(obj instanceof ChatMessage){
					//System.out.println("Mensagem enviada pelo servidor " + chatMessage.getNome() + " : " + chatMessage.getMensagem());
//					System.out.println("\n" + chatMessage.getNome() + " : " + chatMessage.getMensagem());
				
					boolean flag = verificarStringSinal(chatMessage);
					
					if(flag){
						System.out.println("\n" + chatMessage.getNome() + " : " + chatMessage.getMensagem());
					}
				}
			}catch(SocketTimeoutException e){
				//ignorar, apenas para circular a thread enquanto espera mensagem
			}catch(EOFException e){ // caso nao encontre, parar // == chatMessage = null // perda de conexao ou coisa do genero
				break;
			}catch(Exception e){ //finalizar/encerrar para qualquer outra excecao ("fatal")
				System.out.println(e);
				
				//e.printStackTrace();
			}
		}
		
		close(); //encerrar/desalocar cliente recursos
	}
	
	/**
	 * Metodo responsavel por receber uma mensagem como sinal de controle e indicar o que deve
	 * ser feito para o cliente
	 * @param chatMessage
	 * @return
	 * @throws Exception
	 */
	public boolean verificarStringSinal(ChatMessage chatMessage) throws Exception{
		if(chatMessage.getMensagem().equals("GAME START")){
			
			frameMesa = new InterfaceMesa(this, nome);
			
			frameMesa.setVisible(true);
			
			enviarMsgInterface(new ChatMessage("Server", "Game Start!", 5));
			
//			frame = new InterfaceMao(new Carta[]{new Carta(1, 2), new Carta(1, 10), new Carta(1, 5)});
//			frame.setVisible(true);
//			frame.toFront();
			frameMao = null;
		}else if(chatMessage.getMensagem().equals("CARTA VIRADA NO CENTRO")){
			frameMesa.pintarCarta(chatMessage.getCarta(), frameMesa.getLblCartaCentro());
			frameMesa.pintarCarta(new Carta(0, 0), frameMesa.getLblBaralho());	
			if(frameMao != null) frameMao.dispose();
			frameMao = null;
		}else if(chatMessage.getMensagem().equals("MINHA MAO")){
			frameMao = new InterfaceMao(new Carta[]{chatMessage.carta0, chatMessage.carta1, chatMessage.carta2}, this, nome);
			
			frameMesa.pintarCarta(null, frameMesa.getLblJogador0()); //zerar mesa
			frameMesa.pintarCarta(null, frameMesa.getLblJogador1());
			frameMesa.pintarCarta(null, frameMesa.getLblJogador2());
			frameMesa.pintarCarta(null, frameMesa.getLblJogador3());			
			
			frameMao.getBtnJogar().setEnabled(false);
			
			frameMao.getBtnSim().setVisible(false);
			frameMao.getBtnNao().setVisible(false);
			frameMao.getBtnAumentar().setVisible(false);
//			frameMao.getLblTrucar().setVisible(false);			
			frameMao.getLblTrucar().setText("");
			
			frameMao.setVisible(true);
			frameMao.toFront();			
		}else if(chatMessage.getMensagem().equals("MINHA VEZ")){
			enviarMsgInterface(new ChatMessage("Server", nome + " ==> Sua Vez!", 5));
			
			switch(chatMessage.getValRodada()){
			case 1:
				frameMao.getLblTrucar().setText("Trucar?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);				
				break;
			case 3:
				frameMao.getLblTrucar().setText("6?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				break;
			case 6:
				frameMao.getLblTrucar().setText("9?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				break;
			case 9:
				frameMao.getLblTrucar().setText("12?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				break;
			default:
				frameMao.getLblTrucar().setText("");
				frameMao.getBtnSim().setVisible(false);
				frameMao.getBtnNao().setVisible(false);
				frameMao.getBtnAumentar().setVisible(false);
				break;
			}
			
//			frameMao.getBtnJogar().setEnabled(true);
		}else if(chatMessage.getMensagem().equals("MINHA VEZ ACEITAR")){
			enviarMsgInterface(new ChatMessage("Server", nome + " ==> Sua Vez!", 5));
			
			switch(chatMessage.getValRodada()){
			case 1:
				frameMao.getLblTrucar().setText("Trucar?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);				
				break;
			case 3:
				frameMao.getLblTrucar().setText("<html>Aceita Truco?</html>");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				
				frameMao.getBtnAumentar().setText("6");
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 6:
				frameMao.getLblTrucar().setText("Aceita 6?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				
				frameMao.getBtnAumentar().setText("9");
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 9:
				frameMao.getLblTrucar().setText("Aceita 9?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				
				frameMao.getBtnAumentar().setText("12");
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 12:
				frameMao.getLblTrucar().setText("Aceita 12?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				frameMao.getBtnAumentar().setVisible(false);
				break;
			}
			
//			frameMao.getBtnJogar().setEnabled(true);
		}else if(chatMessage.getMensagem().equals("MINHA VEZ JOGAR CARTA")){
			enviarMsgInterface(new ChatMessage("Server", nome + " ==> Sua Vez!", 5));
			frameMao.getBtnJogar().setEnabled(true);
		}else if(chatMessage.getMensagem().equals("MINHA VEZ JOGAR CARTA ABERTA FECHADA")){
//			frameMao.getBtnJogar().setEnabled(true);

			frameMao.getLblTrucar().setText("Fechada?");
			frameMao.getBtnSim().setVisible(true);
			frameMao.getBtnNao().setVisible(true);
		}else if(chatMessage.getMensagem().equals("PINTAR MESA")){
			JLabel jlblAux = frameMesa.getLblJogador0(); //default
			
			switch(chatMessage.getNomeInt()){
			case 0:
				jlblAux = frameMesa.getLblJogador0();
				break;
			case 1:
				jlblAux = frameMesa.getLblJogador1();
				break;
			case 2:
				jlblAux = frameMesa.getLblJogador2();
				break;
			case 3:
				jlblAux = frameMesa.getLblJogador3();
				break;
			}
			
			if(chatMessage.getCarta().getPeso() == -1){
				frameMesa.pintarCarta(new Carta(0, 0), jlblAux);
			}else{
				frameMesa.pintarCarta(chatMessage.getCarta(), jlblAux);
			}
		}else if(chatMessage.getMensagem().equals("ENCERRAR JOGADA E LIMPAR MESA")){
			int grup = (chatMessage.getValRodada()%2)+1;
			enviarMsgInterface(new ChatMessage("Server", "Grupo " + grup + " Ganhou a Jogada!"+ "\nAguarde...", 5));
			
			thread.sleep(5000); //esperar 5seg
			frameMesa.pintarCarta(null, frameMesa.getLblJogador0()); //zerar mesa
			frameMesa.pintarCarta(null, frameMesa.getLblJogador1());
			frameMesa.pintarCarta(null, frameMesa.getLblJogador2());
			frameMesa.pintarCarta(null, frameMesa.getLblJogador3());
		}else if(chatMessage.getMensagem().equals("PLACAR RODADA")){
			frameMesa.getLblRodadaG1().setText("" + chatMessage.getPlacarG1());
			frameMesa.getLblRodadaG2().setText("" + chatMessage.getPlacarG2());
		}else if(chatMessage.getMensagem().equals("PLACAR JOGO")){
			frameMesa.getLblRodadaG1().setText("00");
			frameMesa.getLblRodadaG2().setText("00");
			
			String s = chatMessage.getPlacarG1() > 9 ?	//seta numero arrumado: 00, 01... 10 12 12...
					("" + chatMessage.getPlacarG1()) : ("0" + chatMessage.getPlacarG1());
			frameMesa.getLblPlacarG1().setText(s);
			
			s = chatMessage.getPlacarG2() > 9 ?	//seta numero arrumado: 00, 01... 10 12 12...
					("" + chatMessage.getPlacarG2()) : ("0" + chatMessage.getPlacarG2());
			frameMesa.getLblPlacarG2().setText(s);
					
		}else if(chatMessage.getMensagem().equals("ENCERRAR RODADA")){
			int grup = chatMessage.getValRodada();
			enviarMsgInterface(new ChatMessage("Server", "Grupo " + grup + " Ganhou a Rodada!"+ "\nAguarde...", 5));
		}else if(chatMessage.getMensagem().equals("ENCERRAR JOGO")){
			int grup1 = chatMessage.getPlacarG1();
			int grup2 = chatMessage.getPlacarG2();
			
			enviarMsgInterface(new ChatMessage("Server", "End Game\n  Placar Final: \nGrupo 1 (" + grup1 + ") X (" + grup2 + ") Grupo 2" + "\nSair...", 5));
			
			frameMesa.setDefaultCloseOperation(frameMesa.EXIT_ON_CLOSE);
			frameMao.setDefaultCloseOperation(frameMao.EXIT_ON_CLOSE);
		}else{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Metodo responsavel por printar em um JTextPane as mensagens recebidas do servidor
	 * quando necessario e se possivel
	 * @param chatMessage
	 * @throws Exception
	 */
	public void enviarMsgInterface(ChatMessage chatMessage) throws Exception{
		if(frameMesa != null){
//			if(chatMessage.isItsMe()){
//				frame.getDoc().insertString(frame.getDoc().getLength(), "\n[Me]: " + chatMessage.getMensagem(), frame.getLeftBlue());
//				frame.getDoc().setParagraphAttributes(frame.getDoc().getLength(), 1, frame.getLeftBlue(), false);	
//			}else{
				frameMesa.getDoc().insertString(frameMesa.getDoc().getLength(), "\n[" + chatMessage.getNome() + "]: " + chatMessage.getMensagem(), frameMesa.getLeftBlue());
				frameMesa.getDoc().setParagraphAttributes(frameMesa.getDoc().getLength(), 1, frameMesa.getLeftBlue(), false);
//			}
		}else{
			throw new Exception("Cliente 'nao operando'"); //frame fechado, encerrar
		}
	}
	
	/**
	 * Main do cliente, responsavel pela inicializacao do programa no cliente
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{ //auto-"tratar" excecoes / nao tratar
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in); //para ler da entrada do teclado
		
		System.out.println("Iniciando cliente ...");
		
		System.out.println("Iniciando conexao com o servidor ...");
		
		String nome = JOptionPane.showInputDialog("Qual é o seu nome?");
	    if (nome == null) {	    	
	    	System.exit(0);
	    }else if(nome.equals("")){
	    	JOptionPane.showMessageDialog(null, "Nome Invalido");
	    	scanner.close();
	    	System.exit(0);
	    }
		
//		System.out.print("Digite o nome: ");
//		String nome = scanner.nextLine(); //pegar nome do cliente
		
	    String ip = JOptionPane.showInputDialog("Qual é o ip do servidor?");
	    if (ip == null) {	    	
	    	System.exit(0);
	    }else if(ip.equals("")){
	    	JOptionPane.showMessageDialog(null, "IP Invalido");
	    	scanner.close();
	    	System.exit(0);
	    }
//	    String ip = "localhost";
	    
		Cliente cliente = new Cliente(ip, 2525, nome); //ip e porta do servidor
		
		System.out.println("Conexao estabelecida com sucesso ...");
		
		cliente.start(); //o cliente passa a receber as msgs do servidor de forma assincrona
		
		ChatMessage chatMessage;// = new ChatMessage(nome, "");
		
		while(true){			
			//System.out.print("Digite uma mensagem: ");
			int msg = scanner.nextInt();
			//String mensagem = scanner.nextLine();
			
			//chatMessage.setMensagem(mensagem);
			//chatMessage = new ChatMessage(nome, mensagem, 5);
			
			if(!cliente.isExecutando()){ //caso o cliente nao esteja mais executando, finalizar (aplicacao)
				break; //caso nao esteja executando, = conexao encerrada
			}
			
			//envia mensagem para o servidor
			//cliente.send(mensagem);
//			cliente.send(chatMessage);
			cliente.sendInt(msg);
			
//			if("FIM".equals(mensagem)){ //arrumar EOF error
////				cliente.send(null);
//				break;
//			}
			if(msg == -1){ //arrumar EOF error
//				cliente.send(null);
				break;
			}
		}
		
		System.out.println("Encerrando cliente ...");
		
		cliente.stop(); //encerra thread auxiliar e fecha recursos alocados pelo cliente
		
		scanner.close();
	}

}


//bibliografia/ajuda/baseadoEm: Canal Youtube https://www.youtube.com/channel/UCHX1Nkx02uVBKwhjlroKTyQ

