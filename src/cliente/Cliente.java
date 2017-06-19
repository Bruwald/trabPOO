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

import aGame.Carta;
import bInterface.InterfaceMao;
import bInterface.InterfaceMesa;


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
	
	public Cliente(String endereco, int porta, String nome) throws Exception{ //construtor //manda inicializar conexao e obter InputStream e OutputStream
		inicializado = false;
		executando = false;
		
		this.nome = nome;
		
		open(endereco, porta);
	}
	
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
	
	private void start(){ //tentar iniciar thread auxilliar
		if(!inicializado || executando){
			return;
		}
		
		executando = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() throws Exception{ //encerrar thread auxiliar adequadamente (e seus recursos)
		executando = false;
		
		if(thread != null){
			thread.join(); //esperar ate a thread encerrar
		}
	}
	
	public boolean isExecutando(){
		return executando;
	}
	
//	public void send(String mensagem){ //metodo que permite o envio de mensagens para o servidor
//		out.println(mensagem);
//	}
	
	public void send(ChatMessage chatMessage) throws IOException{ //metodo que permite o envio de mensagens para o servidor
		out.writeObject(chatMessage);
		out.flush(); //garantir que o envio seja feito corretamente / limpar o cache / ler ate o fim do buffer
	}
	
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
					System.out.println("\n" + chatMessage.getNome() + " : " + chatMessage.getMensagem());
				
					verificarStringSinal(chatMessage);					
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
	
	public void verificarStringSinal(ChatMessage chatMessage){
		if(chatMessage.getMensagem().equals("GAME START\n")){
			frameMesa = new InterfaceMesa(this);
			frameMesa.setVisible(true);
			
//			frame = new InterfaceMao(new Carta[]{new Carta(1, 2), new Carta(1, 10), new Carta(1, 5)});
//			frame.setVisible(true);
//			frame.toFront();
			frameMao = null;
		}else if(chatMessage.getMensagem().equals("CARTA VIRADA NO CENTRO")){
			frameMesa.pintarCarta(chatMessage.getCarta(), frameMesa.getLblCartaCentro());
			if(frameMao != null) frameMao.dispose();
			frameMao = null;
		}else if(chatMessage.getMensagem().equals("MINHA MAO")){
			frameMao = new InterfaceMao(new Carta[]{chatMessage.carta0, chatMessage.carta1, chatMessage.carta2}, this);
			frameMao.getBtnJogar().setEnabled(false);
			
			frameMao.getBtnSim().setVisible(false);
			frameMao.getBtnNao().setVisible(false);
			frameMao.getBtnAumentar().setVisible(false);
//			frameMao.getLblTrucar().setVisible(false);			
			frameMao.getLblTrucar().setText("");
			
			frameMao.setVisible(true);
			frameMao.toFront();			
		}else if(chatMessage.getMensagem().equals("MINHA VEZ")){
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
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 6:
				frameMao.getLblTrucar().setText("9?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 9:
				frameMao.getLblTrucar().setText("12?");
				frameMao.getBtnSim().setVisible(true);
				frameMao.getBtnNao().setVisible(true);
				frameMao.getBtnAumentar().setVisible(true);
				break;
			case 12:
				frameMao.getLblTrucar().setText("");
				frameMao.getBtnSim().setVisible(false);
				frameMao.getBtnNao().setVisible(false);
				frameMao.getBtnAumentar().setVisible(false);
				break;
			}
			
//			frameMao.getBtnJogar().setEnabled(true);
		}else if(chatMessage.getMensagem().equals("MINHA VEZ JOGAR CARTA")){
			frameMao.getBtnJogar().setEnabled(true);
		}else if(chatMessage.getMensagem().equals("MINHA VEZ JOGAR CARTA ABERTA FECHADA")){
//			frameMao.getBtnJogar().setEnabled(true);

			frameMao.getLblTrucar().setText("Aberta?");
			frameMao.getBtnSim().setVisible(true);
			frameMao.getBtnNao().setVisible(true);
		}
	}
	
	public static void main(String[] args) throws Exception{ //auto-"tratar" excecoes / nao tratar
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in); //para ler da entrada do teclado
		
		System.out.println("Iniciando cliente ...");
		
		System.out.println("Iniciando conexao com o servidor ...");
		
		System.out.print("Digite o nome: ");
		String nome = scanner.nextLine(); //pegar nome do cliente
		
		Cliente cliente = new Cliente("localhost", 2525, nome); //ip e porta do servidor
		
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

