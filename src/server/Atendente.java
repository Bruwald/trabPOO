package server;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import cliente.ChatMessage;

/**
 * Classe responsavel pela thread e main do atendente que eh responsavel por
 * cuidar de um cliente
 * @author 
 *
 */
public class Atendente implements Runnable{
	private Servidor servidor; //servidor de origem do atendente
	private Socket socket; //semelhante ao servidor
	
//	private BufferedReader in;
//	private PrintStream out;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean inicializado;
	private boolean executando;
	
	private Thread thread;
	
	private int num;
	
	/**
	 * contrutor que recebe o servidor e o socket do cliente e o numero do atendente (em ordem de chegada)
	 *  e inicializa alguns atributos
	 * @param servidor
	 * @param socket
	 * @param num
	 * @throws Exception
	 */
	public Atendente(Servidor servidor, Socket socket, int num) throws Exception{ //construtor //Receber socket e inicializar alguns atributos
		this.servidor = servidor;
		this.socket = socket;
		
		this.inicializado = false;
		this.executando = false;
		
		open(); //similar ao do servidor
		
		this.num = num;
	}
	
	/**
	 * Funcao responsavel por obter InputStream e OutputStream da conexao e
	 * inicializa-la	
	 * @throws Exception
	 */
	private void open() throws Exception{ //inicializar
		try{ //verificar se foi possivel abrir
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintStream(socket.getOutputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
//			out = new ObjectOutputStream(socket.getOutputStream());
			inicializado = true;
		}catch(Exception e){
			close(); //fechar recursos abertos
			throw e; //relançar excecao
		}
	}
	
	/**
	 * Funcao responsavel por fechar/desalocar os recursos utilizados/alocados
	 */
	private void close(){ //fechar recursos / desalocar
		if(in != null){ //verificar se foi corretamente aberto
			try{ //fechar InputStream
				in.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		if(out != null){ //verificar se foi corretamente aberto
			try{ //fechar OutputStream
				out.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		if(socket != null){
			try{ //fechar Socket
				socket.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		//reinicializar valores
		in = null;
		out = null;
		socket = null;
		
		inicializado = false;
		executando = false;
		
		thread = null;
	}
	
//	public void send(String mensagem){ //repassar mensagem ao cliente
//		out.println(mensagem);
//	}
	
	/**
	 * Funcao responsavel por repassar mensagem ao cliente
	 * @param chatMessage
	 * @throws IOException
	 */
	public void send(ChatMessage chatMessage) throws IOException{ //repassar mensagem ao cliente
		out.writeObject(chatMessage);
		out.flush();
	}
	
	/**
	 * Funcao responsavel por criar e iniciar a thread auxiliar e muda para executando. 
	 * Retorna caso ao atendente nao tenha sido inicializado ou esteja em execucao
	 */
	public void start(){ //cria e inicia a thread auxiliar e muda para executando. Retorna caso ao atendente nao tenha sido inicializado ou esteja em execucao
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
	public void stop() throws Exception{ //muda para nao executando, manda encerrar thread auxiliar e espera finalizar
		executando = false;
		
		if(thread != null){ //caso o cliente/atendente ja tenha parado
			thread.join();
		}
	}
	
	/**
	 *  Override do metodo run ja existente da interface runnable (thread)
	 * inicia quando a thread iniciar e roda enquanto a thread estiver executando
	 * Obs: Como neste caso o servidor esta gerenciando o jogo, apenas mantem a conexao com o cliente
	 * (no caso de um chat serviria melhor para sua funcao)
	 */
	@Override
	public void run() { //executado por uma thread auxiliar //semelhante ao do servidor
		while(executando){ //ecoar msg do cliente //atender cliente //enquanto executando
			try{ //tratar excecoes
//				socket.setSoTimeout(2500); //timeOut para evitar que InputStream fique preso para sempre
//				
////				String mensagem = in.readLine(); //Ler mensagem
//				Object obj = in.readObject();
//				
//				ChatMessage chatMessage = null;
//				if(obj instanceof ChatMessage){
//					chatMessage = (ChatMessage) obj;
//				}
//				
//				if(chatMessage == null){ //caso a mensagem recebida seja nula, encerrar conexao (porque provavelmente a conexao com o cliente foi perdida)
//					break;
//				}
//			
//				if(servidor.isFlagAtendenteProximoaJogar() && obj instanceof ChatMessage){
//					System.out.println("Mensagem recebida do cliente ["
//							+ socket.getInetAddress().getHostName()
//							+ ":"
//							+ socket.getPort() //para diferenciar nos testes, incluir porta de conexao do cliente
//							+ "]: " + chatMessage.getMensagem()); //Escrever no servidor
//				
//					if("FIM".equals(chatMessage.getMensagem())){ //finalizar o atendiento quando cliente enviar mensagem "FIM"
////						out.writeObject(null);
////						out.flush();
//						break;
//					}
//					
//	//				out.println(mensagem); //devolver pro cliente //ecoar msg
//					
//					servidor.sendToAll(chatMessage, this.num); //reenvia mensagem a todos, recebe o atendente de origem e a mensagem
//				}
//			}catch(SocketTimeoutException e){
//				//
//			}catch(EOFException e){ //fim de arquivo / conexao / parar
//				break;
			}catch(Exception e){ // qualquer outra excecao
				System.out.println(e);
				break; //forcar encerramento do cliente
			}
		}
		
		System.out.println("Encerrando conexao.");
		
		close(); //liberar recursos do atendente
	}

	/**
	 * Pega numero do Atendente/Cliente
	 * @return
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Pega o ObjectOutputStream
	 * @return
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * Pega o ObjectInputStream
	 * @return
	 */
	public ObjectInputStream getIn() {
		return in;
	}
	
}


//bibliografia/ajuda/baseadoEm: Canal Youtube https://www.youtube.com/channel/UCHX1Nkx02uVBKwhjlroKTyQ


