package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import aGame.Jogo;
import cliente.ChatMessage;

/**
 * Classe responsavel por cuidar do servidor e gerenciar o jogo
 * @author thiago
 *
 */
public class Servidor implements Runnable{ // Para threads p/ novas conexoes
	private ServerSocket server; // para novas conexoes
	
	private List<Atendente> atendentes; //lista para armazenar atendentes abertos
	
	private boolean inicializado; // para saber se o servidor ja foi inicializado
	private boolean executando; // para saber se o servidor esta em execucao
	
	private Thread thread; // para controlar/representar thread auxiliar para recebimento de conexoes do servidor
	
	private boolean flagJogo;
	private Jogo jogo;
	private boolean flagAtendenteProximoaJogar;
	
	/**
	 * Metodo construtor para inicializar novo objeto servidor, recebe a porta de servico do servidor
	 * @param porta
	 * @throws Exception
	 */
	//caso ocorra algum problema durante a inicializacao, lancar excecao
	public Servidor(int porta) throws Exception{ //metodo construtor para inicializar novo objeto servidor, recebe a porta de servico do servidor
		atendentes = new ArrayList<Atendente>(); //inicializar lista de atendentes
		
		inicializado = false;
		executando = false;
		
		open(porta);
		
		flagJogo = true;
		flagAtendenteProximoaJogar = false;
	}
	
	/**
	 * Inicializar servidor, recebe a porta
	 * @param porta
	 * @throws Exception
	 */
	private void open(int porta) throws Exception{ //inicializar servidor
		//cria serverSocket	e marca servidor como inicializado
		server = new ServerSocket(porta);
		inicializado = true;
	}
	
	/**
	 * Fechar e liberar recursos utilizados pelo servidor
	 */
	private void close(){ //fecha/libera recursos utilizados pelo servidor
		for(Atendente atendente : atendentes){ //fechar todos os atendentes abertos/conexoes e seus recursos
			try{
				atendente.stop();
			}catch(Exception e){
				System.out.println(e);
			}
		}		
		
		try{
			server.close();
		}catch(Exception e){
			System.out.println(e); //"salvar em um log do servidor" 
		}
		
		//reiniciar valores
		server = null;
		
		inicializado = false;
		executando = false;
		
		thread = null;
	}
	
//	public void sendToAll(Atendente origem, String mensagem) throws Exception{ //reenvia a mensagem a todos os atendentes
//		Iterator<Atendente> i = atendentes.iterator();
//		
//		while(i.hasNext()) {
//			Atendente atendente = i.next();
//			
//			if(atendente != origem){ //sendo o atendente diferente do da origem da mensagem, envia mensagem para ser repassada ao cliente
//				try{
//					atendente.send(mensagem); //repassa mensagem aos clientes
//				}catch(Exception e){ //caso nao seja possivel repassar ao cliente
//					atendente.stop(); //parar o atendente
//					i.remove(); //e remove-lo da lista de atendentes
//				}
//			}
//		}
//	}
	
	/**
	 * Reenvia a mensagem recebida a todos os atendentes para repassarem a seus clientes
	 * @param chatMessage
	 * @param origm
	 * @throws Exception
	 */
	public void sendToAll(ChatMessage chatMessage, int origm) throws Exception{ //reenvia a mensagem a todos os atendentes
		Iterator<Atendente> i = atendentes.iterator();
		
		while(i.hasNext()) {
			Atendente atendente = i.next();
			
			if(atendente.getNum() != origm){ //sendo o atendente diferente do da origem da mensagem, envia mensagem para ser repassada ao cliente
				try{
//					atendente.send(mensagem); //repassa mensagem aos clientes
					atendente.send(chatMessage); //repassa mensagem aos clientes
				}catch(Exception e){ //caso nao seja possivel repassar ao cliente
					atendente.stop(); //parar o atendente
					i.remove(); //e remove-lo da lista de atendentes
				}
			}
		}
	}
	
	/**
	 * Reenvia a mensagem recebida a todos um atendente para repassar a seu cliente
	 * @param chatMessage
	 * @param origm
	 * @throws Exception
	 */
	public void sendToOne(ChatMessage chatMessage, int origm) throws Exception{ //reenvia a mensagem a um atendente
		Iterator<Atendente> i = atendentes.iterator();
		
		while(i.hasNext()) {
			Atendente atendente = i.next();
			
			if(atendente.getNum() == origm){ //sendo o atendente diferente do da origem da mensagem, envia mensagem para ser repassada ao cliente
				try{
//					atendente.send(mensagem); //repassa mensagem aos clientes
					atendente.send(chatMessage); //repassa mensagem aos clientes
				}catch(Exception e){ //caso nao seja possivel repassar ao cliente
					atendente.stop(); //parar o atendente
					i.remove(); //e remove-lo da lista de atendentes
				}
			}
		}
	}
	
	/**
	 * Iniciar thread auxiliar do servidor
	 */
	public void start(){ //Iniciar thread auxiliar do servidor
		if(!inicializado || executando){ //caso nao tenha sido iniciado ou esteja executando
			return;
		}
		
		executando = true;
		thread = new Thread(this); //executa metodo run da classe x = "this" (iniciar thread)
		thread.start(); //iniciar thread
		
	}
	
	/**
	 * Metodo responsavel por "encerrar" thread, e "encerrar" recursos utilizados
	 * @throws Exception
	 */
	public void stop() throws Exception{ //"encerrar" thread, e "encerrar" recursos utilizados
		executando = false;
		
		if(thread != null){ //caso o servidor/atendente ja tenha parado
			thread.join(); //bloqueia a thread atual ate que a auxiliar acabe/ metodo run retorne
		}
	}

	/**
	 * Override do metodo run ja existente da interface runnable (thread)
	 * inicia quando a thread iniciar e roda enquanto a thread estiver executando
	 */
	@Override
	public void run() { //da interface Runnable (run executado pela thread auxiliar)
		System.out.println("Aguardando conexao.");

		while(executando){
			try{
				server.setSoTimeout(2500); //2500 ms //para reavaliar condicao do laco //se em 2500ms nao chegar outra conexao, desbloquear a thread
			
				Socket socket = server.accept(); //conectar com o cliente
				
				System.out.println("Conexao estabelecida.");

				//Informa o servidor de origem tbm
				//System.out.println(atendentes.size());
				Atendente atendente = new Atendente(this, socket, atendentes.size()); //informa o socket do cliente conectado
				atendente.start(); //objetos da classe sao threads, que executam em separado para liberar a thread auxiliar do servidor para novas conexoes
			
				
				atendentes.add(atendente); //guardar uma lista dos atendetes para liberar quando encerra o servidor
				if(atendentes.size() == 4 && flagJogo){ //quando o numero de atendentes abertos for 4
					//iniciar jogo e "bloquear servidor"
					jogo = new Jogo(this); //passar servidor
					
					while(!jogo.getPlacarJogo().alguemGanhou()){
						jogo.proximaRodada();
						
						for(int i = 0; i < jogo.getNroJogadores(); i++) {
							jogo.proximaRodada2(i);
						}
						
						jogo.newRodada();
						jogo.proximaJogada();
						while(!jogo.getRodada().getPlacarRodada().alguemGanhou()){
//							jogo.proximaJogada();
							//Enquanto todos nao jogaram
							while(jogo.getQuantosJogaram() < 4) { //------------------------------------------------------
								flagAtendenteProximoaJogar = true;
							//	proximoaJogar();
								jogo.proximoaJogar(atendentes, this);
								
							}
							flagAtendenteProximoaJogar = false;
							
							jogo.encerrarJogada();
						
						}
						
						jogo.encerrarRodada(this);
					}
					
					jogo.encerrarJogo();
					flagJogo = false;
					break;
				}
			}catch(SocketTimeoutException e){
				//
				
			}catch(Exception e){ //qualquer outra excecao encerra o laco
				System.out.println(e);
				e.printStackTrace();
				break;
			}
		}
		
		close(); //liberar recursos antes de finalizar
		
	}
	
	/**
	 * Main do Servidor, responsavel pela inicializacao do programa no servidor
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{ //auto-"tratar" excecoes / nao tratar
		// TODO Auto-generated method stub
		
		//Servidor e Cliente TCP/IP ("basico") 
		System.out.println("Iniciando servidor.");
		
		//ServerSocket server = new ServerSocket(2525); //porta (onde "ficara" o servidor) [default ip -> local]
		Servidor servidor = new Servidor(2525); //Criar instancia do servidor e iniciar 
		servidor.start(); //apos o start a thread principal continua rodando junto com a auxiliar do servidor
		
		//Bloquear thread principal ate que pressione ENTER, evitando que a aplicacao termine, mantendo o servidor em execucao
		System.out.println("Pressione ENTER para encerrar o servidor.");
		new Scanner(System.in).nextLine();
		
		System.out.println("Encerrando Servidor.");
//		server.close(); //fechar serverSocket
		servidor.stop(); //encerrar servidor
	}

	public boolean isFlagAtendenteProximoaJogar() {
		return flagAtendenteProximoaJogar;
	}
	
}


//bibliografia/ajuda/baseadoEm: Canal Youtube https://www.youtube.com/channel/UCHX1Nkx02uVBKwhjlroKTyQ


	