package aGame;
import java.io.IOException;
import java.util.List;

import cliente.ChatMessage;
import server.Atendente;
import server.Servidor;

/**
 * Classe Responsavel pelo objeto Rodada simbolizando uma rodada no jogo do Truco.
 */
public class Rodada {
	
	/**
	 * Variaveis utilizadas na classe Rodada:
	 * Carta manilha -> manilha da rodada especifica.
	 * int nroJogada -> numero da jogada da rodada (vai de 1 ate 3, ja como utiliza-se melhor de 3)
	 * int valorRodada -> valor da rodada de acordo com o estado da aposta (3 -> truco, 6, 9, 12)
	 * int quemJoga -> simboliza um "ponteiro" para qual jogador esta jogando na rodada (de 0 a 3)
	 * int quemTrucou -> variavel que guarda quem trucou na rodada (de 0 a 3), armazena-se -1 inicialmente (ninguem trucou)
	 * int quemPediu6 -> variavel que guarda quem pediu 6 (de 0 a 3), armazena-se -1 inicialmente (ninguem pediu 6)
	 * int quemPediu9 -> variavel que guarda quem pediu 9 (de 0 a 3), armazena-se -1 inicialmente (ninguem pediu 9)
	 * int quemPediu12 -> variavel que guarda quem pediu 12 (de 0 a 3), armazena-se -1 inicialmente (ninguem pediu 12)
	 * int qualJogadorGanhouPrimeiraJogada -> variavel importante que guarda quem ganhou a primeira jogada para verificar criterios de amarros nas
	 * 										  jogadas seguintes (se amarrou a segunda ou a terceira, ganha quem ganhou a primeira jogada).
	 * Placar placarRodada -> um placar que armazena os pontos da dupla 1 e da dupla 2 da melhor de 3 da rodada.
	 * boolean amarrouJogada1 -> true se amarrou a jogada 1 ou false se nao amarrou.
	 * boolean amarrouJogada2 -> true se amarrou a jogada 2 ou false se nao amarrou.
	 * boolean amarrouJogada3 -> true se amarrou a jogada 3 ou false se nao amarrou.
	 * boolean mudouJogador -> verifica se mudou o jogador na rodada. Se mudou, o jogador seguinte joga.
	 * boolean acabouRodada -> verifica se acabou a rodada (caso algum jogador nao aceita o estado de aposta proposto por uma outra dupla).
	 * Jogador[] jogadores -> jogadores da rodada (cada qual com sua mao especifica).
	 */
	private Carta manilha;
	private int nroJogada;
	private int valorRodada;
	private int quemJoga;
	private int quemTrucou;
	private int quemPediu6;
	private int quemPediu9;
	private int quemPediu12;
	private int qualJogadorGanhouPrimeiraJogada;
	private Placar placarRodada;
	private boolean amarrouJogada1;
	private boolean amarrouJogada2;
	private boolean amarrouJogada3;
	private boolean mudouJogador;
	private boolean acabouRodada;
	private Jogador[] jogadores;
	
	/**
	 * Metodo construtor da classe Rodada. Recebe os jogadores e a manilha da rodada.
	 * @param Jogador[] jogadores -> representa os jogadores da rodada.
	 * @param Carta manilha -> representa a carta manilha da rodada.
	 */
	public Rodada(Jogador[] jogadores, Carta manilha) {
		//Inicializacao das variaveis...
		this.manilha = manilha;
		this.valorRodada = 1;
		this.nroJogada = 1;
		this.quemJoga = 0;
		this.quemTrucou = -1;
		this.quemPediu6 = -1;
		this.quemPediu9 = -1;
		this.quemPediu12 = -1;
		this.qualJogadorGanhouPrimeiraJogada = -2;
		this.placarRodada = new Placar(2);
		this.amarrouJogada1 = false;
		this.amarrouJogada2 = false;
		this.amarrouJogada3 = false;
		this.mudouJogador = false;
		this.acabouRodada = false;
		this.jogadores = jogadores;
	}
	
	/**
	 * Funcao que compara as cartas de uma dupla e retorna a MAIOR carta desta (para evitar amarros de jogadores da mesma dupla).
	 * @param Carta carta1 -> carta 1 que vai ser comparada.
	 * @param Carta carta2 -> carta 2 que vai ser comparada.
	 * @return maior carta das duas passadas por parametro para a funcao.
	 */
	public Carta comparaCartaDupla(Carta carta1, Carta carta2) {
		
		//Verifica se a carta 1 contem peso maior que a carta 2. Se o tiver, retorne-a.
		if(carta1.getPeso() > carta2.getPeso()) return carta1;
		//Se os pesos das cartas forem iguais, verifique a maior carta pelo maior naipe entre elas.
		else if(carta1.getPeso() == carta2.getPeso()) {
			if(carta1.getNaipe() > carta2.getNaipe()) return carta1;
			else return carta2;
		}
		else return carta2;
		
	}
	
	/**
	 * Funcao que retorna qual jogador ganhou ou amarrou a jogada especifica.
	 * @param int quemComecouJogada -> retorna o numero do jogador que comecou a jogada (de 0 a 3).
	 * @param Carta[] cartasJogadas -> vetor armazenando quais cartas de cada jogador foram jogadas na jogada.
	 * 								   o indice do vetor indica o jogador que jogou a carta. Ex: cartasJogadas[2] -> carta jogada pelo Jogador 2.
	 * @return qual jogador ganhou ou amarrou a jogada especifica.
	 */
	public int qualJogadorGanhouOuAmarrouJogada(int quemComecouJogada, Carta[] cartasJogadas) {
		Carta maiorCartaDupla1, maiorCartaDupla2;
		
		//Verifica as maiores cartas de cada dupla, armazenando-as na variavei maiorCartaDupla 1 e maiorCartaDupla2.
		maiorCartaDupla1 = comparaCartaDupla(cartasJogadas[0], cartasJogadas[2]);
		maiorCartaDupla2 = comparaCartaDupla(cartasJogadas[1], cartasJogadas[3]);
		
		//Se o peso das duas cartas forem iguais, entre na condicao do if.
		if(maiorCartaDupla1.getPeso() == maiorCartaDupla2.getPeso()) {
			//Se a numero da jogada for 1, a jogada 1 foi amarrada, portanto o jogo sera decidido na proxima jogada.
			if(nroJogada == 1) {
				amarrouJogada1 = true;
				placarRodada.incrementarPontosDupla1(1);
				placarRodada.incrementarPontosDupla2(1);
			//Se o numero da jogada for 2, a jogada 2 foi amarrada, portanto o jogo sera decidido por quem ganhou a jogada 1.
			//Se a jogada 1 tambem foi amarrada, a rodada sera decididida por quem ganhar a ultima jogada.
			} else if(nroJogada == 2) {
				if(!amarrouJogada1) {
					if(qualJogadorGanhouPrimeiraJogada % 2 == 0) placarRodada.incrementarPontosDupla1(1);
					if(qualJogadorGanhouPrimeiraJogada % 2 == 1) placarRodada.incrementarPontosDupla2(1);
					amarrouJogada2 = true;
				} else amarrouJogada2 = true;
			//Se o numero da jogada for 3, verifique quem ganhou o jogo por quem ganhou a jogada 1.
			} else if(nroJogada == 3) {
				if(qualJogadorGanhouPrimeiraJogada % 2 == 0) placarRodada.incrementarPontosDupla1(1);
				if(qualJogadorGanhouPrimeiraJogada % 2 == 1) placarRodada.incrementarPontosDupla2(1);
				amarrouJogada3 = true;
			}
			
			//Incremente o numero da jogada.
			nroJogada++;
			
			//Verifique quem comecou a jogada. Se for a dupla 1, entre neste if e sete qual jogador AMARROU a primeira jogada.
			if(quemComecouJogada == 0 || quemComecouJogada == 2) {
				if(maiorCartaDupla2.getPeso() == cartasJogadas[1].getPeso()){
					qualJogadorGanhouPrimeiraJogada = 1;
					return 1;
				} else {
					qualJogadorGanhouPrimeiraJogada = 3;
					return 3;
				}
			//Verifique quem comecou a jogada. Se for a dupla 2, entre neste if e sete qual jogador AMARROU a primeira jogada.
			} else if(quemComecouJogada == 1 || quemComecouJogada == 3) {
				if(maiorCartaDupla1.getPeso() == cartasJogadas[0].getPeso()){
					qualJogadorGanhouPrimeiraJogada = 0;
					return 0;
				} else {
					qualJogadorGanhouPrimeiraJogada = 2;
					return 2;
				}
			}
		//Se o peso da carta 1 for maior, sete quem ganhou a jogada como a dupla 1.
		} else if(maiorCartaDupla1.getPeso() > maiorCartaDupla2.getPeso()) {
			if(nroJogada == 1) qualJogadorGanhouPrimeiraJogada = 1; //dupla 1.
			//Incremente os pontos da dupla 1 do placar e a jogada.
			placarRodada.incrementarPontosDupla1(1);
			nroJogada++;
			if(maiorCartaDupla1 == cartasJogadas[0]){
				qualJogadorGanhouPrimeiraJogada = 0;
				return 0;
			}
			else {
				qualJogadorGanhouPrimeiraJogada = 2;
				return 2;
			}
		} else if(maiorCartaDupla1.getPeso() < maiorCartaDupla2.getPeso()) {
			if(nroJogada == 1) qualJogadorGanhouPrimeiraJogada = 2; //dupla 2.
			//Incremente os pontos da dupla 1 do placar e a jogada.
			placarRodada.incrementarPontosDupla2(1);
			nroJogada++;
			if(maiorCartaDupla2 == cartasJogadas[1]) {
				qualJogadorGanhouPrimeiraJogada = 1;
				return 1;
			}
			else {
				qualJogadorGanhouPrimeiraJogada = 3;
				return 3;
			}
		}
		
		return -1;
	}
	
	/**
	 * Funcao que retorna qual dupla ganhou a rodada, verificando se as jogadas amarraram.
	 * @return qual dupla ganhou a rodada.
	 */
	public int quemGanhouRodada() {
		
		//Se a jogada 2 ou 3 amarrou mas a jogada 1 nao amarrou, retorne quem ganhou a primeira jogada.
		if((amarrouJogada2 || amarrouJogada3) && !amarrouJogada1) return qualJogadorGanhouPrimeiraJogada;
		//Se amarrou as 3 jogadas, retorne -1 (ninguem ganhou -> chance infima de acontecer)
		if(amarrouJogada1 && amarrouJogada2 && amarrouJogada3) return -1;
		//Se nao amarrou nenhuma jogada, chame a funcao que verifica quem ganhou da classe placar e retorne-a.
		return placarRodada.quemGanhou()-1;
		
	}
	
	/**
	 * Funcao que retorna a resposta do jogador caso este aceita um pedido de truco, de 6, de 9 ou de 12.
	 * @return resposta do jogador (0 se nao aceita, 1 se aceita, 2 se aumenta o estado da aposta).
	 * @throws Exception 
	 */
	public int jogadorAceitaEstadoDaAposta(Jogador jogador, List<Atendente> atendentes, Servidor servidor) throws Exception {
		int resposta = 0;
		String s;
		ChatMessage chatMessage;
		
/**/	s = "MINHA VEZ ACEITAR"; //minha vez trucar/aumentar
		chatMessage = new ChatMessage("Server", s, jogador.getNroJogador(), valorRodada); 			
		servidor.sendToOne(chatMessage, jogador.getNroJogador());
		
		//De acordo com o valor da rodada, imprima a mensagem correspondente e retorne a resposta do jogador.
		switch(getValorRodada()) {
			case 3:
				System.out.printf("\nAceita o truco " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 6) : ");
				s = "\nAceita o truco " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 6) : ";
				
				chatMessage = new ChatMessage("Server", s, jogador.getNroJogador()); 			
				servidor.sendToOne(chatMessage, jogador.getNroJogador());
				
				resposta = responderSimNao(jogador, atendentes);
				
				//resposta = EntradaTeclado.leInt();
				break;
			case 6:
				System.out.printf("\nAceita o pedido de 6 " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 9) : ");
				s = "\nAceita o pedido de 6 " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 9) : ";
				
				chatMessage = new ChatMessage("Server", s, jogador.getNroJogador()); 			
				servidor.sendToOne(chatMessage, jogador.getNroJogador());
				
				resposta = responderSimNao(jogador, atendentes);
				
				//resposta = EntradaTeclado.leInt();
				break;
			case 9:
				System.out.printf("\nAceita o pedido de 9 " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 12) : ");
				s = "\nAceita o pedido de 9 " + jogador.getNome() + "? (0 - NAO, 1 - SIM, 2 - PEDIR 12) : ";
				
				chatMessage = new ChatMessage("Server", s, jogador.getNroJogador());
				servidor.sendToOne(chatMessage, jogador.getNroJogador());
				
				resposta = responderSimNao(jogador, atendentes);
				
				//resposta = EntradaTeclado.leInt();
				break;
			case 12:
				System.out.printf("\nAceita o pedido de 12 " + jogador.getNome() + "? (0 - NAO, 1 - SIM) : ");
				s = "\nAceita o pedido de 12 " + jogador.getNome() + "? (0 - NAO, 1 - SIM) : ";
				
				chatMessage = new ChatMessage("Server", s, jogador.getNroJogador());
				servidor.sendToOne(chatMessage, jogador.getNroJogador());
				
				resposta = responderSimNao(jogador, atendentes);
				
				//resposta = EntradaTeclado.leInt();
				break;
		}
		
		return resposta;
	}
	
	public int responderSimNao(Jogador jogador, List<Atendente> atendentes) throws Exception{
		int resposta = (int) atendentes.get(jogador.getNroJogador()).getIn().readObject();
		
		return resposta;
	}

	/**
	 * Funcao que pega a manilha da Rodada e a retorna.
	 * @return manilha da rodada.
	 */
	public Carta getManilha() {
		return manilha;
	}

	/**
	 * Funcao que seta a manilha da Rodada.
	 * @param Carta manilha -> manilha da rodada.
	 */
	public void setManilha(Carta manilha) {
		this.manilha = manilha;
	}
	
	/**
	 * Funcao que retorna o valor da rodada de acordo com truco, pedidos de 6, de 9 e de 12.
	 * @return valor da rodada.
	 */
	public int getValorRodada() {
		return valorRodada;
	}
	
	/**
	 * Funcao que seta o valor da rodada de acordo com truco, epdidos de 6, de 9 e de 12.
	 * @param valor da rodada.
	 */
	public void setValorRodada(int valorRodada) {
		this.valorRodada = valorRodada;
	}
	
	/**
	 * Funcao que retorna true ou false de acordo com o criterio de se a jogada 1 amarrou.
	 * @return true or false -> amarrou ou nao a jogada 1, respectivamente.
	 */
	public boolean getAmarrouJogada1() {
		return amarrouJogada1;
	}
	
	/**
	 * Funcao que seta true ou false se a jogada 1 amarrou.
	 * @param boolean amarrouJogada1 -> true (amarrou a jogada 1) ou false (nao amarrou a jogada 1).
	 */
	public void setAmarrouJogada1(boolean amarrouJogada1) {
		this.amarrouJogada1 = amarrouJogada1;
	}
	
	/**
	 * Funcao que retorna true ou false de acordo com o criterio de se a jogada 2 amarrou.
	 * @return true or false -> amarrou ou nao a jogada 2, respectivamente.
	 */
	public boolean getAmarrouJogada2() {
		return amarrouJogada2;
	}
	
	/**
	 * Funcao que seta true ou false se a jogada 2 amarrou.
	 * @param boolean amarrouJogada2 -> true (amarrou a jogada 2) ou false (nao amarrou a jogada 2).
	 */
	public void setAmarrouJogada2(boolean amarrouJogada2) {
		this.amarrouJogada2 = amarrouJogada2;
	}
	
	/**
	 * Funcao que retorna true ou false de acordo com o criterio de se a jogada 3 amarrou.
	 * @return true or false -> amarrou ou nao a jogada 3, respectivamente.
	 */
	public boolean getAmarrouJogada3() {
		return amarrouJogada3;
	}
	
	/**
	 * Funcao que seta true ou false se a jogada 3 amarrou.
	 * @param boolean amarrouJogada3 -> true (amarrou a jogada 3) ou false (nao amarrou a jogada 3).
	 */
	public void setAmarrouJogada3(boolean amarrouJogada3) {
		this.amarrouJogada3 = amarrouJogada3;
	}
	
	/**
	 * Funcao que pega o numero da jogada (de 1 a 3).
	 * @param boolean amarrouJogada2 -> true (amarrou a jogada 2) ou false (nao amarrou a jogada 2).
	 */
	public int getNroJogada() {
		return nroJogada;
	}
	
	/**
	 * Funcao que seta o numero da jogada.
	 * @param numero da rodada.
	 */
	public void setNroJogada(int nroJogada) {
		this.nroJogada = nroJogada;
	}
	
	/**
	 * Funcao que pega quem joga na jogada (de 0 a 3).
	 * @return quem joga na jogada.
	 */
	public int getQuemJoga() {
		return quemJoga;
	}
	
	/**
	 * Funcao que seta quem joga na jogada (de 0 a 3).
	 * @param int quemJoga -> quem joga na jogada (de 0 a 3).
	 */
	public void setQuemJoga(int quemJoga) {
		this.quemJoga = quemJoga;
	}
	
	/**
	 * Funcao que pega o placar da Rodada.
	 * @return Placar placarRodada -> placar da Rodada.
	 */
	public Placar getPlacarRodada() {
		return placarRodada;
	}
	
	/**
	 * Funcao que seta o placar da Rodada.
	 * @param Placar placarRodada -> placar da Rodada.
	 */
	public void setPlacarRodada(Placar placarRodada) {
		this.placarRodada = placarRodada;
	}

	/**
	 * Funcao que pega quem trucou na rodada (de 0 a 3)
	 * @return quemTrucou -> jogador que trucou na rodada (de 0 a 3).
	 */
	public int getQuemTrucou() {
		return quemTrucou;
	}
	
	/**
	 * Funcao que seta quem trucou na rodada (de 0 a 3).
	 * @param int quemTrucou -> quem trucou na rodada.
	 */
	public void setQuemTrucou(int quemTrucou) {
		this.quemTrucou = quemTrucou;
	}
	
	/**
	 * Funcao que pega o vetor de jogadores na rodada.
	 * @return vetor de jogadores da rodada.
	 */
	public Jogador[] getJogadores() {
		return jogadores;
	}
	
	/**
	 * Funcao que seta o vetor de jogadores na rodada.
	 * @param Jogador[] jogadores -> vetor de jogadores da rodada.
	 */
	public void setJogadores(Jogador[] jogadores) {
		this.jogadores = jogadores;
	}
	
	/**
	 * Funcao que pega se mudou de jogador na rodada.
	 * @return se mudou de jogador na rodada.
	 */
	public boolean getMudouJogador() {
		return mudouJogador;
	}
	
	/**
	 * Funcao que seta se mudou de jogador na rodada.
	 * @param boolean mudouJogador -> true se mudou o jogador na rodada e false caso contrario.
	 */
	public void setMudouJogador(boolean mudouJogador) {
		this.mudouJogador = mudouJogador;
	}
	
	/**
	 * Funcao que pega se a rodada acabou.
	 * @return se a rodada acabou.
	 */
	public boolean getAcabouRodada() {
		return acabouRodada;
	}
	
	/**
	 * Funcao que seta se acabou a rodada.
	 * @param boolean acabouRodada -> true se acabou a rodada e false caso contrario.
	 */
	public void setAcabouRodada(boolean acabouRodada) {
		this.acabouRodada = acabouRodada;
	}

	public int getQuemPediu6() {
		return quemPediu6;
	}

	public void setQuemPediu6(int quemPediu6) {
		this.quemPediu6 = quemPediu6;
	}

	public int getQuemPediu9() {
		return quemPediu9;
	}

	public void setQuemPediu9(int quemPediu9) {
		this.quemPediu9 = quemPediu9;
	}

	public int getQuemPediu12() {
		return quemPediu12;
	}

	public void setQuemPediu12(int quemPediu12) {
		this.quemPediu12 = quemPediu12;
	}	
	

}
