package aGame;
import java.io.IOException;

public class Main {

//	public static void main(String[] args) throws IOException {
//		Jogador[] jogadores = new Jogador[4];
//		Placar placarJogo = new Placar(12);
//		int nroRodada = 1, nroJogadores = 4, i, quantosJogaram = 0, duplaQueGanhouARodada, quemComecouJogada = 0, quemGanhouUltimaRodada = 0;
//		Baralho baralho = new Baralho();
//		Carta cartaViradaNoCentro, manilha;
//		Carta[] cartasJogadas = new Carta[nroJogadores];
//		
//		for(i = 0; i < 4; i++) jogadores[i] = new Jogador("JOGADOR " + i, i);
//		
//		System.out.println("GAME START");
//		
//		//Enquanto ninguem ganhou o jogo, entre no loop...
//		while(!placarJogo.alguemGanhou()){
//			
//			//Arrume o baralho do truco (retira 8, 9 e 10 e seta pesos certos) e embaralhe o baralho.
//			baralho.baralho_trucoArrange();
//			baralho.baralho_shuffle();
//			
//			System.out.println("\nRODADA " + nroRodada + "!");
//			
//			//Carta manilha = valor carta virada no centro da mesa + 1 % 13 (de as a K)
//			cartaViradaNoCentro = baralho.baralho_retirarCarta();
//			System.out.println("\nCARTA NO CENTRO: " + cartaViradaNoCentro.getValor());
//			
//			manilha = new Carta(1, (cartaViradaNoCentro.getValor() + 1) % 13);
//			//Seta casos especiais 7->11 (7 para Q) e (J para K)
//			if(manilha.getValor() == 8) manilha.setValor(11);
//			else if(manilha.getValor() == 0) manilha.setValor(13);
//			
//			System.out.println("MANILHA: " + manilha.getValor());
//			
//			//Cada jogador recebe cartas na mao no inicio de cada rodada e seta o peso correspondente destas de acordo com a manilha da rodada.
//			for(i = 0; i < nroJogadores; i++) {
//				jogadores[i].receberCartasDaMao(baralho);
//				jogadores[i].getMao().setPesoCartas(manilha);
//			}
//			
//			//Imprime a mao dos jogadores...
//			for(i = 0; i < nroJogadores; i++) {
//				System.out.printf("\nMao do " + jogadores[i].getNome() + ": ");
//				jogadores[i].getMao().printMao();
//			}
//			
//			//Inicia uma nova rodada de acordo com os jogadores e a manilha da rodada
//			Rodada rodada = new Rodada(jogadores, manilha);
//			
//			//Enquanto ninguem ganhou a rodada, entre no loop...
//			while(!rodada.getPlacarRodada().alguemGanhou()){
//				
//				//Se a rodada for a primeira, pegue quem comeca a jogada como o jogador 0. Apos a rodada 1, quem comeca a rodada = 
//				//quem ganhou a rodada anterior.
//				if(nroRodada == 1) quemComecouJogada = rodada.getQuemJoga();
//				else rodada.setQuemJoga(quemGanhouUltimaRodada);
//				
//				//Enquanto todos nao jogaram
//				while(quantosJogaram < 4) {
//					//Imprima a vez do jogador de quem joga a jogada
//					System.out.println("\n\nVEZ DO " + jogadores[rodada.getQuemJoga()].getNome() + "\n");
//					//Pega a carta jogada pelo jogador e armazena esta no vetor de cartas jogadas.
//					cartasJogadas[rodada.getQuemJoga()] = jogadores[rodada.getQuemJoga()].getMao().jogarCarta(rodada);
//					//Se nao mudou de jogador a rodada, incremente quantos jogaram.
//					if(rodada.getMudouJogador() == false) quantosJogaram++;
//					//Se a carta jogada for diferente de null, entre na condicao if.
//					if(cartasJogadas[rodada.getQuemJoga()] != null) {
//						//Se a carta jogada contiver naipe 50 a rodada acabou e quantos jogaram eh setado como 4.
//						if(cartasJogadas[rodada.getQuemJoga()].getNaipe() == 50) {
//							rodada.setAcabouRodada(true);
//							quantosJogaram = 4;
//						}
//					}
//					//Se mudou de jogador na rodada, sete se mudou para false para reiniciar o boolean.
//					if(rodada.getMudouJogador() == true) rodada.setMudouJogador(false);
//					//Sete quem joga como o jogador seguinte.
//					rodada.setQuemJoga((rodada.getQuemJoga() + 1) % 4);
//				}
//				
//				//Se nao acabou a rodada, imprima como foi a rodada (para meios de verificacao de corretude).
//				if(rodada.getAcabouRodada() == false) {
//					System.out.println("\nQuem comecou a jogada: JOGADOR " + quemComecouJogada);
//					System.out.printf("Cartas jogadas: ");
//					for(i = 0; i < nroJogadores; i++) System.out.printf("%d ", cartasJogadas[i].getValor());
//					
//					rodada.setQuemJoga(rodada.qualJogadorGanhouOuAmarrouJogada(quemComecouJogada, cartasJogadas));
//					System.out.println("\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2());
//					System.out.println("Quem ganhou/amarrou a jogada: " + rodada.getQuemJoga());
//					
//				}
//				
//				//Reset em quantos jogaram como zero (0).
//				quantosJogaram = 0;
//			}
//			
//			//Retorne quem ganhou a rodada e imprima quem ganhou.
//			duplaQueGanhouARodada = rodada.quemGanhouRodada();
//			System.out.println("A Dupla " + duplaQueGanhouARodada + " ganhou a rodada!\n==========================================");
//			
//			//Incremente o ponto do placar do jogo de acordo com quem ganhou a rodada.
//			if(duplaQueGanhouARodada == 1) placarJogo.incrementarPontosDupla1(rodada.getValorRodada());
//			else if(duplaQueGanhouARodada == 2) placarJogo.incrementarPontosDupla2(rodada.getValorRodada());
//			
//			//Imprima o placar do jogo.
//			System.out.println("\nPLACAR DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
//			
//			//Sete quem ganhou a ultima rodada como quem joga nesta rodada (quem ganhou).
//			quemGanhouUltimaRodada = rodada.getQuemJoga();
//			
//			//Incremente o numero da rodada e restaure o baralho de acordo com as cartas removidas deste.
//			nroRodada++;
//			baralho.baralho_restaurar();
//		}
//		
//		//JOGO ACABOU!!! Imprima o placar final do jogo.
//		System.out.println("\nPLACAR FINAL DO JOGO: " + placarJogo.getPontosDupla1() + " x " + placarJogo.getPontosDupla2());
//		System.out.println("\nGAME END");
//	}

}
