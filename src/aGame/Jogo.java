package aGame;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import bInterface.InterfaceMesa;
import cliente.ChatMessage;
import server.Atendente;
import server.Servidor;
/**
 * Classe Responsavel pelo objeto Jogo do Truco.
 */
public class Jogo {
	/**
	 * Variaveis utilizadas na classe Placar:
	 * Jogador[] jogadores -> vetor de jogadores do Truco
	 * Placar placarJogo -> placar do jogo
	 * int nroRodada -> numero da rodada
	 * int nroJogadores -> quantidade de jogadores do truco
	 * int quantosJogaram -> quantos jogaram em uma jogada
	 * int duplaQueGanhouARodada -> qual dupla ganhou a rodada
	 * int quemComecouJogada -> quem comecou a jogada
	 * Carta cartaViradaNoCentro -> carta virada no centro
	 * Carta manilha -> manilha da rodada
	 * Rodada rodada -> objeto Rodada do jogo do Truco
	 * boolean[] jogaram -> vetor de boolean para informar quem jogou
	 * Servidor servidor -> servidor de origem do atendente
	 */
	private Jogador[] jogadores;
	private Placar placarJogo;
	private int nroRodada, nroJogadores, i, quantosJogaram, duplaQueGanhouARodada, quemComecouJogada, quemGanhouUltimaRodada;
	private Baralho baralho;
	private Carta cartaViradaNoCentro, manilha;
	private Carta[] cartasJogadas;
	private Rodada rodada;
	boolean[] jogaram;
	private Servidor servidor;
	
	/**
	 * Metodo construtor da classe Jogo. Recebe o servidor de origem do atendente.
	 * @param Servidor servidor -> servidor de origem do atendente
	 */
	public Jogo(Servidor servidor) throws Exception {
		//inicializando variaveis...
		jogaram = new boolean[4];
		for(i = 0; i < 4; i++) jogaram[i] = false;
		
		//Comeco do jogo!
		System.out.println("GAME START");

		jogadores = new Jogador[4];
		for(i = 0; i < 4; i++) jogadores[i] = new Jogador("JOGADOR " + i, i);
		
		placarJogo = new Placar(12);
		nroRodada = 1;
		nroJogadores = 4;
		quantosJogaram = 0;
		quemComecouJogada = 0;
		quemGanhouUltimaRodada = 0;
		baralho = new Baralho();
		cartasJogadas = new Carta[nroJogadores];
		
		//iniciar telas dos clientes!
		this.servidor = servidor;
		iniciarTelas();
	}
	
	/**
	 * Funcao que inicia as telas de todos os clientes do servidor.
	 */
	public void iniciarTelas() throws Exception{
		String s = "GAME START";		
		
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		
		servidor.sendToAll(chatMessage, 6);
	}
	
	/**
	 * Funcao que faz com que o jogo va para a rodada seguinte (parte 1).
	 */
	public void proximaRodada() throws Exception{
		String s = "";		
		
		//Arrume o baralho do truco (retira 8, 9 e 10 e seta pesos certos) e embaralhe o baralho.
		baralho.baralho_trucoArrange();
		baralho.baralho_shuffle();
		
		System.out.println("\nRODADA " + nroRodada + "!");
		s += "\nRODADA " + nroRodada + "!";
		
		//Carta manilha = valor carta virada no centro da mesa + 1 % 13 (de as a K)
		cartaViradaNoCentro = baralho.baralho_retirarCarta();
		System.out.println("\nCARTA NO CENTRO: " + cartaViradaNoCentro.getValor());
		s += "\nCARTA NO CENTRO: " + cartaViradaNoCentro.getValor();
		
		manilha = new Carta(1, (cartaViradaNoCentro.getValor() + 1) % 13);
		//Seta casos especiais 7->11 (7 para Q) e (J para K)
		if(manilha.getValor() == 8) manilha.setValor(11);
		else if(manilha.getValor() == 0) manilha.setValor(13);
		
		System.out.println("MANILHA: " + manilha.getValor());
		s += "\nMANILHA: " + manilha.getValor();
		
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		servidor.sendToAll(chatMessage, 5);		
		
		//enviar carta no centro
		s = "CARTA VIRADA NO CENTRO";
		chatMessage = new ChatMessage("Server", s, 5, cartaViradaNoCentro); 
		servidor.sendToAll(chatMessage, 5);
	}
	
	/**
	 * Funcao que vai para a proxima rodada do jogo do Truco (parte 2).
	 * @param Baralho baralho -> baralho do jogo do Truco
	 * @return Carta[] cartasNaMao -> vetor de cartas apos retira-las do baralho
	 */
	public void proximaRodada2(int jogadorNum) throws Exception{
		//Cada jogador recebe cartas na mao no inicio de cada rodada e seta o peso correspondente destas de acordo com a manilha da rodada.
		//for(i = 0; i < nroJogadores; i++) {
			jogadores[jogadorNum].receberCartasDaMao(baralho);
			jogadores[jogadorNum].getMao().setPesoCartas(manilha);
		//}
			
		String s = "\nMao do " + jogadores[jogadorNum].getNome() + ": ";
		s += jogadores[jogadorNum].getMao().printMao();
		
		ChatMessage chatMessage = new ChatMessage("Server", s, jogadorNum); 
		servidor.sendToOne(chatMessage, jogadorNum);		
		
		//Envia a mao do jogador para ser utilizada na interface mao deste
		s = "MINHA MAO"; 
		chatMessage = new ChatMessage("Server", s, jogadorNum, 
				jogadores[jogadorNum].getMao().getCartasNaMao()[0], 
				jogadores[jogadorNum].getMao().getCartasNaMao()[1], 
				jogadores[jogadorNum].getMao().getCartasNaMao()[2]); 
		servidor.sendToOne(chatMessage, jogadorNum);
	}
	
	/**
	 * Funcao que instancia uma nova rodada do jogo do Truco.
	 */
	public void newRodada(){
		//Inicia uma nova rodada de acordo com os jogadores e a manilha da rodada
		rodada = new Rodada(jogadores, manilha);
	}
	
	/**
	 * Funcao que vai para a proxima jogada de uma rodada do Truco.
	 */
	public void proximaJogada(){
		//Se a rodada for a primeira, pegue quem comeca a jogada como o jogador 0. Apos a rodada 1, quem comeca a rodada = 
		//quem ganhou a rodada anterior.
		if(nroRodada == 1) quemComecouJogada = rodada.getQuemJoga();
		else rodada.setQuemJoga(quemGanhouUltimaRodada);
		
	}
	
	/**
	 * Funcao que administra uma jogada de um jogador e verifica o proximo jogador a jogar da rodada.
	 * @param List<Atendente> atendentes -> lista de atendentes que se comunicam com os clientes do servidor
	 * @param Servidor servidor -> servidor do jogo
	 */
	public void proximoaJogar(List<Atendente> atendentes, Servidor servidor) throws Exception{
		//Imprima a vez do jogador de quem joga a jogada
		System.out.println("\n\nVEZ DO " + jogadores[rodada.getQuemJoga()].getNome() + "\n");
		String s = "\n\nVEZ DO " + jogadores[rodada.getQuemJoga()].getNome() + "\n";
		
		ChatMessage chatMessage = new ChatMessage("Server", s, rodada.getQuemJoga()); 	
		servidor.sendToOne(chatMessage, rodada.getQuemJoga());
		
		//Pega a carta jogada pelo jogador e armazena esta no vetor de cartas jogadas.
		cartasJogadas[rodada.getQuemJoga()] = jogadores[rodada.getQuemJoga()].getMao().jogarCarta(rodada, atendentes, servidor);
		
		//Se nao mudou de jogador a rodada, incremente quantos jogaram.
		if(rodada.getMudouJogador() == false) {
			quantosJogaram++;
			jogaram[rodada.getQuemJoga()] = true;
		}
		
		//Se a carta jogada for diferente de null, entre na condicao if.
		if(cartasJogadas[rodada.getQuemJoga()] != null) {
			//Se a carta jogada contiver naipe 50 a rodada acabou e quantos jogaram eh setado como 4.
			if(cartasJogadas[rodada.getQuemJoga()].getNaipe() == 50) {
				rodada.setAcabouRodada(true);
				quantosJogaram = 4;
			}
		}
		//Se mudou de jogador na rodada, sete se mudou para false para reiniciar o boolean.
		if(rodada.getMudouJogador() == true) rodada.setMudouJogador(false);
		//Sete quem joga como o jogador seguinte.
		rodada.setQuemJoga((rodada.getQuemJoga() + 1) % 4);
		
		//verificacao se todos os jogadores ja jogaram na jogada de uma rodada
		while(jogaram[rodada.getQuemJoga()] == true && quantosJogaram != 4) {
			rodada.setQuemJoga((rodada.getQuemJoga() + 1) % 4);
		}
	}
	
	/**
	 * Funcao que eh chamada para encerrar uma jogada de uma rodada do Truco.
	 */
	public void encerrarJogada() throws Exception{
		
		//Incremente o numero da jogada! 
		//rodada.setNroJogada(rodada.getNroJogada() + 1);
	    for(i = 0; i < 4; i++) jogaram[i] = false;		
		
	    ChatMessage chatMessage;
	    String s;
	    
		//Se nao acabou a rodada, imprima como foi a rodada (para meios de verificacao de corretude).
		if(rodada.getAcabouRodada() == false) {
			System.out.println("\nQuem comecou a jogada: JOGADOR " + quemComecouJogada);
			System.out.printf("Cartas jogadas: ");
			for(i = 0; i < nroJogadores; i++) System.out.printf("%d ", cartasJogadas[i].getValor());
			
			//Imprime como foi a jogada (quem ganhou ou amarrou) e imprime o placar atual da rodada!
			rodada.setQuemJoga(rodada.qualJogadorGanhouOuAmarrouJogada(quemComecouJogada, cartasJogadas));
			System.out.println("\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2());
			System.out.println("Quem ganhou/amarrou a jogada: " + rodada.getQuemJoga());
			
			//Para atualizar o placar atual da rodada para todos os clientes do servidor
			s = "\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2();
			chatMessage = new ChatMessage("Server", s, 5); 
			servidor.sendToAll(chatMessage, 5);		
			
			//Para enviar o placar da rodada para o servidor
			s = "PLACAR RODADA";
			chatMessage = new ChatMessage("Server", s, 5, rodada.getPlacarRodada().getPontosDupla1(), rodada.getPlacarRodada().getPontosDupla2()); 
			servidor.sendToAll(chatMessage, 5);
		}
		
		//Reset em quantos jogaram como zero (0).
		quantosJogaram = 0;
		
		//Para encerrar uma jogada e limpar a mesa para uma nova jogada seguinte
		s = "ENCERRAR JOGADA E LIMPAR MESA"; //limpar a mesa e mostrar quem ganhou jogada
		chatMessage = new ChatMessage("Server", s, 5, rodada.getQuemJoga()); 
		servidor.sendToAll(chatMessage, 5); //enviar para todos
	}
	
	/**
	 * Funcao que encerra uma rodada do jogo do Truco.
	 * @param Servidor servidor -> servidor do jogo do truco
	 */
	public void encerrarRodada(Servidor servidor) throws Exception{
		//Retorne quem ganhou a rodada e imprima quem ganhou.
		duplaQueGanhouARodada = (rodada.quemGanhouRodada()%2) + 1;
		
		System.out.println("A Dupla " + duplaQueGanhouARodada + " ganhou a rodada!\n==========================================");
		
		//Para enviar a todos os clientes do servidor que dupla ganhou a rodada
		String s = "A Dupla " + duplaQueGanhouARodada + " ganhou a rodada!\n==========================================\n";
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		servidor.sendToAll(chatMessage, 5);	
		
		//Para encerrar uma rodada em todas as maquinas dos clientes do servidor
		s = "ENCERRAR RODADA"; 
		chatMessage = new ChatMessage("Server", s, 5, duplaQueGanhouARodada); 
		servidor.sendToAll(chatMessage, 5);
		
		//Incremente o ponto do placar do jogo de acordo com quem ganhou a rodada.
		if(duplaQueGanhouARodada == 1) placarJogo.incrementarPontosDupla1(rodada.getValorRodada());
		else if(duplaQueGanhouARodada == 2) placarJogo.incrementarPontosDupla2(rodada.getValorRodada());
		
		//Imprima o placar do jogo.
		System.out.println("\nPLACAR DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
		s = "\nPLACAR DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2() + "\n";
		chatMessage = new ChatMessage("Server", s, 5); 
		servidor.sendToAll(chatMessage, 5);	
		
		//Para enviar o placar do jogo a todos os jogadores do truco.
		s = "PLACAR JOGO";
		chatMessage = new ChatMessage("Server", s, 5, placarJogo.getPontosDupla1(), placarJogo.getPontosDupla2()); 
		servidor.sendToAll(chatMessage, 5);		
		
		//Sete quem ganhou a ultima rodada como quem joga nesta rodada (quem ganhou).
		quemGanhouUltimaRodada = rodada.getQuemJoga();
		
		//Incremente o numero da rodada e restaure o baralho de acordo com as cartas removidas deste.
		nroRodada++;
		
		//Restaure o baralho com as cartas removidas deste.
		baralho.baralho_restaurar();
	}
	
	/**
	 * Funcao que encerra o jogo do Truco, imprimindo o placar final do jogo a todos os clientes.
	 */
	public void encerrarJogo() throws Exception{
		//JOGO ACABOU!!! Imprima o placar final do jogo para todo mundo.
		System.out.println("\nPLACAR FINAL DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
		System.out.println("\nGAME END");
		
		//Para imprimir o placar final para todo mundo
		String s = "\nPLACAR FINAL DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2() + "\n";
		s += "\nGAME END\n";
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		servidor.sendToAll(chatMessage, 5);	
		
		//Para encerrar o jogo para todos os clientes do servidor.
		s = "ENCERRAR JOGO"; 
		chatMessage = new ChatMessage("Server", s, 5, placarJogo.getPontosDupla1(), placarJogo.getPontosDupla2()); 
		servidor.sendToAll(chatMessage, 5);
	}
	
//	public void iniciar() throws Exception{		
//		
//		//Enquanto ninguem ganhou o jogo, entre no loop...
//		while(!placarJogo.alguemGanhou()){//------------------------------------------------------
//			
//			proximaRodada();
//			
//			for(i = 0; i < nroJogadores; i++) {
//				proximaRodada2(i);
//			}
//			
//			newRodada();
//			
//			//Enquanto ninguem ganhou a rodada, entre no loop...
//			while(!rodada.getPlacarRodada().alguemGanhou()){//------------------------------------------------------
//				
//				proximaJogada();
//				
//				//Enquanto todos nao jogaram
//				while(quantosJogaram < 4) { //------------------------------------------------------
//					
//				//	proximoaJogar();
//					
//				}
//				
//				encerrarJogada();
//			}
//			
//			//encerrarRodada();
//		}
//		
//		encerrarJogo();
//
//	}
	
	/**
	 * Funcao que pega o vetor de jogadores do truco.
	 * @return jogadores do truco.
	 */
	public Jogador[] getJogadores() {
		return jogadores;
	}
	
	/**
	 * Funcao que pega o placar do jogo.
	 * @return placar do jogo do truco.
	 */
	public Placar getPlacarJogo() {
		return placarJogo;
	}
	
	/**
	 * Funcao que pega o numero da rodada.
	 * @return numero da rodada.
	 */
	public int getNroRodada() {
		return nroRodada;
	}
	
	/**
	 * Funcao que pega o nro de jogadores do truco.
	 * @return numero de jogadores do truco.
	 */
	public int getNroJogadores() {
		return nroJogadores;
	}
	
	/**
	 * Funcao que pega o i.
	 * @return pega o auxiliar inteiro i do jogo do truco.
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * Funcao que pega o valor de quantas pessoas jogaram na jogada.
	 * @return quantas pessoas jogaram na jogada.
	 */
	public int getQuantosJogaram() {
		return quantosJogaram;
	}
	
	/**
	 * Funcao que pega a dupla que ganhou a rodada.
	 * @return a dupla que ganhou a rodada.
	 */
	public int getDuplaQueGanhouARodada() {
		return duplaQueGanhouARodada;
	}
	
	/**
	 * Funcao que pega quem comecou a jogada.
	 * @return quem comecou a jogada.
	 */
	public int getQuemComecouJogada() {
		return quemComecouJogada;
	}
	
	/**
	 * Funcao que pega quem ganhou a ultima rodada.
	 * @return quem ganhou a ultima rodada.
	 */
	public int getQuemGanhouUltimaRodada() {
		return quemGanhouUltimaRodada;
	}
	
	/**
	 * Funcao que pega o baralho de cartas.
	 * @return baralho de cartas.
	 */
	public Baralho getBaralho() {
		return baralho;
	}

	/**
	 * Funcao que pega a carta virada no centro da mesa.
	 * @return carta virada no centro da mesa.
	 */
	public Carta getCartaViradaNoCentro() {
		return cartaViradaNoCentro;
	}
	
	/**
	 * Funcao que pega a manilha da rodada.
	 * @return manilha da rodada.
	 */
	public Carta getManilha() {
		return manilha;
	}
	
	/**
	 * Funcao que pega o vetor de cartas jogadas de uma jogada especifica.
	 * @return vetor de cartas jogadas em uma jogada especifica.
	 */
	public Carta[] getCartasJogadas() {
		return cartasJogadas;
	}
	
	/**
	 * Funcao que pega uma rodada do truco.
	 * @return retorna uma rodada do truco.
	 */
	public Rodada getRodada() {
		return rodada;
	}
}
