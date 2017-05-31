import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Jogador[] jogadores = new Jogador[4];
		Placar placarJogo = new Placar(12);
		int nroRodada = 1, nroJogadores = 4, i, quantosJogaram = 0, duplaQueGanhouARodada, quemComecouJogada = 0, quemGanhouUltimaRodada = 0;
		Baralho baralho = new Baralho();
		Carta cartaViradaNoCentro, manilha;
		Carta[] cartasJogadas = new Carta[nroJogadores];
		
		for(i = 0; i < 4; i++) jogadores[i] = new Jogador("Jogador " + i, i);
		
		System.out.println("Comeco do jogo!\n");
		
		while(!placarJogo.alguemGanhou()){
			
			baralho.baralho_trucoArrange();
			baralho.baralho_shuffle();
			
			System.out.println("\nRodada " + nroRodada + "!");
			
			cartaViradaNoCentro = baralho.baralho_retirarCarta();
			System.out.println("\nCarta virada no centro: " + cartaViradaNoCentro.getValor());
			
			manilha = new Carta(1, (cartaViradaNoCentro.getValor() + 1) % 13);
			System.out.println("Manilha da rodada: " + manilha.getValor());
			
			Rodada rodada = new Rodada(manilha);
			
			for(i = 0; i < nroJogadores; i++) {
				jogadores[i].receberCartasDaMao(baralho);
				jogadores[i].getMao().setPesoCartas(manilha);
			}
			
			for(i = 0; i < nroJogadores; i++) {
				System.out.printf("\nMao do " + jogadores[i].getNome() + ": ");
				jogadores[i].getMao().printMao();
			}
			System.out.println("");
			
			while(!rodada.getPlacarRodada().alguemGanhou()){
				if(nroRodada == 1) quemComecouJogada = rodada.getQuemJoga();
				else rodada.setQuemJoga(quemGanhouUltimaRodada);;
			
				while(quantosJogaram != 4) {
					System.out.println("\nEsta na sua vez: " + jogadores[rodada.getQuemJoga()].getNome() + "!\n");
					cartasJogadas[rodada.getQuemJoga()] = jogadores[rodada.getQuemJoga()].getMao().jogarCarta();
					rodada.setQuemJoga((rodada.getQuemJoga() + 1) % 4);
					quantosJogaram++;
				}
				
				System.out.println("\nQuem comecou a jogada foi o jogador: " + quemComecouJogada);
				System.out.println("Vetor de cartas jogadas na Jogada " + rodada.getNroJogada() + ": ");
				for(i = 0; i < nroJogadores; i++) System.out.printf("%d ", cartasJogadas[i].getValor());
				
				rodada.setQuemJoga(rodada.qualJogadorGanhouOuAmarrouJogada(quemComecouJogada, cartasJogadas));
				System.out.println("\nPlacar atual: " + rodada.getPlacarRodada().getPontosDupla1() + " x " + rodada.getPlacarRodada().getPontosDupla2());
				System.out.println("\nQuem ganhou ou amarrou a jogada foi o jogador: " + rodada.getQuemJoga());
				System.out.println("Portanto, este torna!\n");
				
				quantosJogaram = 0;
				
			}
			
			duplaQueGanhouARodada = rodada.getPlacarRodada().quemGanhou();
			System.out.println("A Dupla " + duplaQueGanhouARodada + " ganhou a rodada! Parabens!");
			quemGanhouUltimaRodada = rodada.getQuemJoga();
			
			if(duplaQueGanhouARodada == 1) placarJogo.incrementarPontosDupla1(rodada.getValorRodada());
			else if(duplaQueGanhouARodada == 2) placarJogo.incrementarPontosDupla2(rodada.getValorRodada());
			
			nroRodada++;
			baralho.baralho_restaurar();
		}
	}

}
