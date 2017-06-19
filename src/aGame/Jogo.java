package aGame;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import bInterface.InterfaceMesa;
import cliente.ChatMessage;
import server.Atendente;
import server.Servidor;

public class Jogo {
	Jogador[] jogadores;
	Placar placarJogo;
	int nroRodada, nroJogadores, i, quantosJogaram, duplaQueGanhouARodada, quemComecouJogada, quemGanhouUltimaRodada;
	Baralho baralho;
	Carta cartaViradaNoCentro, manilha;
	Carta[] cartasJogadas;
	Rodada rodada;
	boolean[] jogaram;
	
	private Servidor servidor; //servidor de origem do atendente
	
	public Jogo(Servidor servidor) throws Exception {
		jogaram = new boolean[4];
		for(i = 0; i < 4; i++) jogaram[i] = false;
		
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
		
		this.servidor = servidor;
		iniciarTelas();
	}
	
	public void iniciarTelas() throws Exception{
		String s = "GAME START\n";		
		
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		
		servidor.sendToAll(chatMessage, 6);
	}
	
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
		
		s = "CARTA VIRADA NO CENTRO"; //enviar carta no centro
		chatMessage = new ChatMessage("Server", s, 5, cartaViradaNoCentro); 
		servidor.sendToAll(chatMessage, 5);
		
	}
	
	public void proximaRodada2(int jogadorNum) throws Exception{
		//Cada jogador recebe cartas na mao no inicio de cada rodada e seta o peso correspondente destas de acordo com a manilha da rodada.
		//for(i = 0; i < nroJogadores; i++) {
			jogadores[jogadorNum].receberCartasDaMao(baralho);
			jogadores[jogadorNum].getMao().setPesoCartas(manilha);
		//}
		
//		//Imprime a mao dos jogadores...
//		for(i = 0; i < nroJogadores; i++) {
//			System.out.printf("\nMao do " + jogadores[i].getNome() + ": ");
//			jogadores[i].getMao().printMao();
//		}
			
		String s = "\nMao do " + jogadores[jogadorNum].getNome() + ": ";
		s += jogadores[jogadorNum].getMao().printMao();
		
		ChatMessage chatMessage = new ChatMessage("Server", s, jogadorNum); 
		
		servidor.sendToOne(chatMessage, jogadorNum);		
		
		s = "MINHA MAO"; //enviar mao
		chatMessage = new ChatMessage("Server", s, jogadorNum, 
				jogadores[jogadorNum].getMao().getCartasNaMao()[0], 
				jogadores[jogadorNum].getMao().getCartasNaMao()[1], 
				jogadores[jogadorNum].getMao().getCartasNaMao()[2]); 
		servidor.sendToOne(chatMessage, jogadorNum);
	}
	
	public void newRodada(){
		//Inicia uma nova rodada de acordo com os jogadores e a manilha da rodada
		rodada = new Rodada(jogadores, manilha);
	}
	
	public void proximaJogada(){
		//Se a rodada for a primeira, pegue quem comeca a jogada como o jogador 0. Apos a rodada 1, quem comeca a rodada = 
		//quem ganhou a rodada anterior.
		if(nroRodada == 1) quemComecouJogada = rodada.getQuemJoga();
		else rodada.setQuemJoga(quemGanhouUltimaRodada);
		
	}
	
	public void proximoaJogar(List<Atendente> atendentes, Servidor servidor) throws Exception{
		//Imprima a vez do jogador de quem joga a jogada
		System.out.println("\n\nVEZ DO " + jogadores[rodada.getQuemJoga()].getNome() + "\n");
		String s = "\n\nVEZ DO " + jogadores[rodada.getQuemJoga()].getNome() + "\n";
		
		ChatMessage chatMessage = new ChatMessage("Server", s, rodada.getQuemJoga()); 
		
		servidor.sendToOne(chatMessage, rodada.getQuemJoga());
		
//		s = "MINHA VEZ"; //minha vez
//		chatMessage = new ChatMessage("Server", s, rodada.getQuemJoga(), rodada.getValorRodada());
//		servidor.sendToOne(chatMessage, rodada.getQuemJoga());
		
		
		//Pega a carta jogada pelo jogador e armazena esta no vetor de cartas jogadas.
		cartasJogadas[rodada.getQuemJoga()] = jogadores[rodada.getQuemJoga()].getMao().jogarCarta(rodada, atendentes, servidor);
		//--------------------------------------------------------------------------------------------------
		
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
		
		while(jogaram[rodada.getQuemJoga()] == true && quantosJogaram != 4) {
			rodada.setQuemJoga((rodada.getQuemJoga() + 1) % 4);
		}
	
	}
	
	public void encerrarJogada() throws Exception{
		rodada.setNroJogada(rodada.getNroJogada() + 1);
	    for(i = 0; i < 4; i++) jogaram[i] = false;		
		
	    ChatMessage chatMessage;
	    String s;
	    
		//Se nao acabou a rodada, imprima como foi a rodada (para meios de verificacao de corretude).
		if(rodada.getAcabouRodada() == false) {
//			String s = "";
			System.out.println("\nQuem comecou a jogada: JOGADOR " + quemComecouJogada);
			System.out.printf("Cartas jogadas: ");
			for(i = 0; i < nroJogadores; i++) System.out.printf("%d ", cartasJogadas[i].getValor());
			
			rodada.setQuemJoga(rodada.qualJogadorGanhouOuAmarrouJogada(quemComecouJogada, cartasJogadas));
			System.out.println("\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2());
			System.out.println("Quem ganhou/amarrou a jogada: " + rodada.getQuemJoga());

			s = "\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2();
			chatMessage = new ChatMessage("Server", s, 5); 
			
			servidor.sendToAll(chatMessage, 5);		
		}
		
		//Reset em quantos jogaram como zero (0).
		quantosJogaram = 0;
		
		
		s = "ENCERRAR JOGADA E LIMPAR MESA"; //enviar carta no centro
		chatMessage = new ChatMessage("Server", s, 5, cartaViradaNoCentro); 
		servidor.sendToAll(chatMessage, 5);
	}
	
	public void encerrarRodada(Servidor servidor) throws Exception{
		//Retorne quem ganhou a rodada e imprima quem ganhou.
		duplaQueGanhouARodada = rodada.quemGanhouRodada();
		System.out.println("A Dupla " + duplaQueGanhouARodada + " ganhou a rodada!\n==========================================");
		
		String s = "A Dupla " + duplaQueGanhouARodada + " ganhou a rodada!\n==========================================\n";
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		
		servidor.sendToAll(chatMessage, 5);	
		
		//Incremente o ponto do placar do jogo de acordo com quem ganhou a rodada.
		if(duplaQueGanhouARodada == 1) placarJogo.incrementarPontosDupla1(rodada.getValorRodada());
		else if(duplaQueGanhouARodada == 2) placarJogo.incrementarPontosDupla2(rodada.getValorRodada());
		
		//Imprima o placar do jogo.
		System.out.println("\nPLACAR DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
		s = "\nPLACAR DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2() + "\n";
		chatMessage = new ChatMessage("Server", s, 5); 
		
		servidor.sendToAll(chatMessage, 5);	
			
		
		//Sete quem ganhou a ultima rodada como quem joga nesta rodada (quem ganhou).
		quemGanhouUltimaRodada = rodada.getQuemJoga();
		
		//Incremente o numero da rodada e restaure o baralho de acordo com as cartas removidas deste.
		nroRodada++;
		baralho.baralho_restaurar();
	}
	
	public void encerrarJogo() throws Exception{
		//JOGO ACABOU!!! Imprima o placar final do jogo.
		System.out.println("\nPLACAR FINAL DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
		System.out.println("\nGAME END");
		String s = "\nPLACAR FINAL DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2() + "\n";
		s += "\nGAME END\n";
		ChatMessage chatMessage = new ChatMessage("Server", s, 5); 
		
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

	public Jogador[] getJogadores() {
		return jogadores;
	}

	public Placar getPlacarJogo() {
		return placarJogo;
	}

	public int getNroRodada() {
		return nroRodada;
	}

	public int getNroJogadores() {
		return nroJogadores;
	}

	public int getI() {
		return i;
	}

	public int getQuantosJogaram() {
		return quantosJogaram;
	}

	public int getDuplaQueGanhouARodada() {
		return duplaQueGanhouARodada;
	}

	public int getQuemComecouJogada() {
		return quemComecouJogada;
	}

	public int getQuemGanhouUltimaRodada() {
		return quemGanhouUltimaRodada;
	}

	public Baralho getBaralho() {
		return baralho;
	}

	public Carta getCartaViradaNoCentro() {
		return cartaViradaNoCentro;
	}

	public Carta getManilha() {
		return manilha;
	}

	public Carta[] getCartasJogadas() {
		return cartasJogadas;
	}

	public Rodada getRodada() {
		return rodada;
	}

	
	
}
