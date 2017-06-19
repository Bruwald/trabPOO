import java.io.IOException;

/**
 * Classe Responsavel pelo objeto Mao de cada jogador do Truco.
 */
public class Mao {
	
	/**
	 * Variaveis utilizadas na classe Mao:
	 * Carta[] cartasNaMao -> vetor de objeto carta representando a mao do jogador
	 * int nCartas -> representa a quantidade de cartas que a mao pode receber (no caso do Truco sao 3)
	 */
	private Carta[] cartasNaMao;
	private int nCartas;
	
	/**
	 * Metodo construtor da classe Mao. Recebe o baralho para instanciar o vetor de cartas.
	 * @param Baralho baralho -> baralho do jogo do Truco
	 */
	public Mao(Baralho baralho) {
		this.nCartas = 3;
		this.cartasNaMao = new Carta[nCartas];
		this.cartasNaMao = retirarCartasDoBaralho(baralho);
	}
	
	/**
	 * Funcao que retira cartas do baralho e seta o vetor de cartas da mao do jogador.
	 * @param Baralho baralho -> baralho do jogo do Truco
	 * @return Carta[] cartasNaMao -> vetor de cartas apos retira-las do baralho
	 */
	public Carta[] retirarCartasDoBaralho(Baralho baralho) {
		Carta[] cartasNaMao = new Carta[nCartas];
		
		//Pegando as cartas do baralho e armazenando-as no vetor de cartasNaMao
		for(int i = 0; i < nCartas; i++) cartasNaMao[i] = baralho.baralho_retirarCarta();
		
		return cartasNaMao;
	}
	
	/**
	 * Funcao que seta o peso de cada carta segundo a manilha da rodada.
	 * @param Carta manilha -> manilha da rodada do Truco.
	 */
	public void setPesoCartas(Carta manilha) {
		
		for(Carta carta: cartasNaMao) {
			//Se alguma carta da mao do jogador for a manilha, de um set respectivo de acordo com seu naipe.
			if(carta.getValor() == manilha.getValor()) {
				//Ouros -> peso 14
				if(carta.getNaipe() == 1) carta.setPeso(14);
				//Espadilhas -> peso 15
				else if(carta.getNaipe() == 2) carta.setPeso(15);
				//Copas -> peso 16
				else if(carta.getNaipe() == 3) carta.setPeso(16);
				//Paus -> peso 17 (zap do jogo)
				else if(carta.getNaipe() == 4) carta.setPeso(17);
			}
		}
		
	}
	
	/**
	 * Funcao que condiciona a jogada de alguma carta de um jogador do Truco.
	 * Esta funcao verifica se o jogador quer jogar a carta aberta ou fechada. Se a carta for jogada fechada, seu peso se anula.
	 * Tambem verifica-se se o jogador quer aumentar o estado da aposta da rodada, de acordo com o valor da rodada especifica.
	 * @param Rodada rodada -> rodada de um jogo de Truco.
	 * @return Carta carta -> carta jogada do vetor de cartas da mao do jogador.
	 */
	public Carta jogarCarta(Rodada rodada) throws IOException {
		int cartaRetirada, cartaFechada, resposta, quemJoga;
		
		while(true) {
			System.out.printf("CARTAS NA MAO: ");
			printMao();
			
			//Verifica o valor da rodada e vai para o caso especifico, realizando aumento de apostas especificas.
			switch(rodada.getValorRodada()) {
				case 1:
					//Verifica se o jogador deseja trucar a rodada. 
					System.out.printf("\nDESEJA TRUCAR? (0 - NAO / 1 - SIM)");
					resposta = EntradaTeclado.leInt();
					//Se a resposta for sim, sete quem Trucou como quem joga na rodada e sete o valor da rodada como 3.
			    	if(resposta == 1) {
			    		rodada.setQuemTrucou(rodada.getQuemJoga());
			    		rodada.setValorRodada(3);
			    		//Verifica resposta do jogador seguinte da rodada.
			    		resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    		//Se a resposta do jogador seguinte for 0, entao a jogada acabou valendo 1 ponto somente.
			    		if(resposta == 0) {
			    			rodada.setValorRodada(1);
			    			//Verifique quem joga e de acordo com quem joga incremente os pontos da dupla especifica (acabou a rodada).
			    			quemJoga = rodada.getQuemJoga();
			    			if(quemJoga % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    			else rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    			//Esta carta simboliza que a rodada acabou! (Para verificacao posterior na main do jogo).
			    			return new Carta(50,50);
			    		//Se a resposta do jogador seguinte for 1, entao o jogador aceitou a aposta e quem joga na rodada
			    		//joga alguma carta da sua mao.
			    		} else if(resposta == 1) {
			    			System.out.println("O jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu truco!");
			    			
			    			//Escolha de qual carta o jogador deseja jogar.
			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
			    		    cartaRetirada = EntradaTeclado.leInt();
			    		    
			    		    //Escolha do jogador de jogar a carta aberta ou fechada.
			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
			    		    cartaFechada = EntradaTeclado.leInt();
			    		    
			    		    //Se o jogador jogar a carta fechada, sete o peso desta como -1.
			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
			    		    
			    		    //Pegue a carta que o jogador deseja jogar e a retorne.
			    		    Carta carta = cartasNaMao[cartaRetirada];
			    		    
			    		    return carta;
			    		//Se a resposta do jogador seguinte for (PEDIR 6) sete o valor da jogada como 6 e verifique a resposta
			    		//do jogador que pediu truco pela primeira vez.
			    		} else if(resposta == 2) {
			    			//------------------------------------------------- MUDEI AQUI --------------------------------
			    			rodada.setQuemPediu6((rodada.getQuemJoga() + 1) % 4);
			    			//-------------------------------------------------  --------------------------------
			    			rodada.setValorRodada(6);
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " pediu 6!");
			    			resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga())]);
			    			//Se a resposta do jogador que pediu truco for 0, a rodada acabou valendo somente 3 pontos.
			    			if(resposta == 0) {
			    				rodada.setValorRodada(3);
			    				//Verifique quem ganhou a jogada de acordo com quem PEDIU 6.
			    				if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    				else rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    				//Esta carta simboliza que a rodada acabou.
			    				return new Carta(50,50);
			    			//Se a resposta do jogador que pediu truco for 1, mudou a vez do jogador na rodada, isto e,
			    			//o jogador seguinte, que pediu 6, deve jogar ao inves do jogador que pediu truco.
			    			//Portanto, sete a variavel (mudouJogador) como true e retorne null (para verificacao posterior na main).
			    			} else if(resposta == 1) {
			    				rodada.setMudouJogador(true);
			    				return null;
			    			//Se a resposta do jogador que pediu truco for 2 (PEDIR 9), verifique a resposta do jogador seguinte novamente, e por ai vai...
			    			} else if(resposta == 2) {
			    				//------------------------------------------------- MUDEI AQUI --------------------------------
				    			rodada.setQuemPediu9(rodada.getQuemJoga());
				    			//-------------------------------------------------  --------------------------------
			    				rodada.setValorRodada(9);
			    				resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    				if(resposta == 0) {
			    					rodada.setValorRodada(6);
					    			if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
					    			else rodada.getPlacarRodada().incrementarPontosDupla2(2);
					    			return new Carta(50,50);
			    				} else if (resposta == 1) {
			    					System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu pedido de 9!");
					    			
					    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
					    		    cartaRetirada = EntradaTeclado.leInt();
					    		    
					    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
					    		    cartaFechada = EntradaTeclado.leInt();
					    		    
					    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
					    		    
					    		    Carta carta = cartasNaMao[cartaRetirada];
					    		    
					    		    return carta;
			    				} else if (resposta == 2) {
			    					//------------------------------------------------- MUDEI AQUI --------------------------------
					    			rodada.setQuemPediu12((rodada.getQuemJoga() + 1) % 4);
					    			//-------------------------------------------------  --------------------------------
			    					rodada.setValorRodada(12);
			    					System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " pediu 12!");
			    					resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga())]);
			    					if(resposta == 0) {
			    						rodada.setValorRodada(9);
			    						if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla2(2);
					    				else rodada.getPlacarRodada().incrementarPontosDupla1(2);
					    				return new Carta(50,50);
			    					} else if(resposta == 1) {
			    						rodada.setMudouJogador(true);
					    				return null;
			    					}
			    				}
			    			}
			    		}
			    	} else if(resposta == 0) {
			    		System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
		    		    cartaRetirada = EntradaTeclado.leInt();
		    		    
		    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
		    		    cartaFechada = EntradaTeclado.leInt();
		    		    
		    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
		    		    
		    		    Carta carta = cartasNaMao[cartaRetirada];
		    		    
		    		    return carta;
			    	}
					break;
				case 3:
					//MUDEI AQUI
					if(rodada.getQuemTrucou() != -1 && ((rodada.getQuemJoga() == rodada.getQuemTrucou()) || ((rodada.getQuemTrucou() + rodada.getQuemJoga()) == 2) || ((rodada.getQuemTrucou() + rodada.getQuemJoga()) == 4))) {
						resposta = 0;
					} else {
						System.out.printf("\nDESEJA PEDIR 6? (0 - NAO / 1 - SIM)");
						resposta = EntradaTeclado.leInt();
					}
					if(resposta == 1) {
						//------------------------------------------------- MUDEI AQUI --------------------------------
		    			rodada.setQuemPediu6(rodada.getQuemJoga());
		    			//-------------------------------------------------  --------------------------------
						rodada.setValorRodada(6);
			    		resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    		if(resposta == 0) {
			    			rodada.setValorRodada(3);
			    			if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    			else rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    			return new Carta(50,50);
			    		} else if(resposta == 1) {
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu pedido de 6!");
			    			
			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
			    		    cartaRetirada = EntradaTeclado.leInt();
			    		    
			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
			    		    cartaFechada = EntradaTeclado.leInt();
			    		    
			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
			    		    
			    		    Carta carta = cartasNaMao[cartaRetirada];
			    		    
			    		    return carta;
			    		} else if(resposta == 2) {
			    			//------------------------------------------------- MUDEI AQUI --------------------------------
			    			rodada.setQuemPediu9((rodada.getQuemJoga() + 1) % 4);
			    			//-------------------------------------------------  --------------------------------
			    			rodada.setValorRodada(9);
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " pediu 9!");
	    					resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga())]);
	    					if(resposta == 0) {
	    						rodada.setValorRodada(6);
	    						if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    				else rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    				return new Carta(50,50);
	    					} else if(resposta == 1) {
	    						rodada.setMudouJogador(true);
			    				return null;
	    					} else if(resposta == 2) {
	    						//------------------------------------------------- MUDEI AQUI --------------------------------
				    			rodada.setQuemPediu12(rodada.getQuemJoga());
				    			//-------------------------------------------------  --------------------------------
	    						rodada.setValorRodada(12);
	    						resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
	    						if(resposta == 0) {
	    							rodada.setValorRodada(9);
	    							if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
				    				else rodada.getPlacarRodada().incrementarPontosDupla2(2);
				    				return new Carta(50,50);
	    						} else if(resposta == 1) {
	    							System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu pedido de 12!");
	    			    			
	    			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
	    			    		    cartaRetirada = EntradaTeclado.leInt();
	    			    		    
	    			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
	    			    		    cartaFechada = EntradaTeclado.leInt();
	    			    		    
	    			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
	    			    		    
	    			    		    Carta carta = cartasNaMao[cartaRetirada];
	    			    		    
	    			    		    return carta;
	    						}
	    					}
			    		}
						
					} else if(resposta == 0) {
						System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
		    		    cartaRetirada = EntradaTeclado.leInt();
		    		    
		    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
		    		    cartaFechada = EntradaTeclado.leInt();
		    		    
		    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
		    		    
		    		    Carta carta = cartasNaMao[cartaRetirada];
		    		    
		    		    return carta;
					} 
					break;
				case 6:
					//MUDEI AQUI
					if(rodada.getQuemPediu6() != -1 && ((rodada.getQuemJoga() == rodada.getQuemPediu6()) || ((rodada.getQuemPediu6() + rodada.getQuemJoga()) == 2) || ((rodada.getQuemPediu6() + rodada.getQuemJoga()) == 4))) {
						resposta = 0;
					} else {
						System.out.printf("\nDESEJA PEDIR 9? (0 - NAO / 1 - SIM)");
						resposta = EntradaTeclado.leInt();
					}
					if(resposta == 1) {
						//------------------------------------------------- MUDEI AQUI --------------------------------
		    			rodada.setQuemPediu9(rodada.getQuemJoga());
		    			//-------------------------------------------------  --------------------------------
						rodada.setValorRodada(9);
			    		resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    		if(resposta == 0) {
			    			rodada.setValorRodada(6);
							if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
		    				else rodada.getPlacarRodada().incrementarPontosDupla2(2);
		    				return new Carta(50,50);
			    		} else if(resposta == 1) {
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu pedido de 9!");
			    			
			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
			    		    cartaRetirada = EntradaTeclado.leInt();
			    		    
			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
			    		    cartaFechada = EntradaTeclado.leInt();
			    		    
			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
			    		    
			    		    Carta carta = cartasNaMao[cartaRetirada];
			    		    
			    		    return carta;
			    		} else if(resposta == 2) {
			    			//------------------------------------------------- MUDEI AQUI --------------------------------
			    			rodada.setQuemPediu12((rodada.getQuemJoga() + 1) % 4);
			    			//-------------------------------------------------  --------------------------------
			    			rodada.setValorRodada(12);
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " pediu 12!");
	    					resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga())]);
	    					if(resposta == 0) {
	    						rodada.setValorRodada(9);
	    						if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    				else rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    				return new Carta(50,50);
	    					} else if(resposta == 1) {
	    						rodada.setMudouJogador(true);
			    				return null;
	    					}
			    		}
					} else if(resposta == 0) {
						System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
		    		    cartaRetirada = EntradaTeclado.leInt();
		    		    
		    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
		    		    cartaFechada = EntradaTeclado.leInt();
		    		    
		    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
		    		    
		    		    Carta carta = cartasNaMao[cartaRetirada];
		    		    
		    		    return carta;
					}
					break;
				case 9:
					//MUDEI AQUI
					if(rodada.getQuemPediu9() != -1 && ((rodada.getQuemJoga() == rodada.getQuemPediu9()) || ((rodada.getQuemPediu9() + rodada.getQuemJoga()) == 2) || ((rodada.getQuemPediu9() + rodada.getQuemJoga()) == 4))) {
						resposta = 0;
					} else {
						System.out.printf("\nDESEJA PEDIR 12? (0 - NAO / 1 - SIM)");
						resposta = EntradaTeclado.leInt();
					}
					if(resposta == 1) {
						//------------------------------------------------- MUDEI AQUI --------------------------------
		    			rodada.setQuemPediu12(rodada.getQuemJoga());
		    			//-------------------------------------------------  --------------------------------
						rodada.setValorRodada(12);
			    		resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    		if(resposta == 0) {
			    			rodada.setValorRodada(9);
			    			if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
		    				else rodada.getPlacarRodada().incrementarPontosDupla2(2);
		    				return new Carta(50,50);
			    		} else if(resposta == 1) {
			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
			    		    cartaRetirada = EntradaTeclado.leInt();
			    		    
			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
			    		    cartaFechada = EntradaTeclado.leInt();
			    		    
			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
			    		    
			    		    Carta carta = cartasNaMao[cartaRetirada];
			    		    
			    		    return carta;
			    		}
					} else if(resposta == 0) {
						System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
		    		    cartaRetirada = EntradaTeclado.leInt();
		    		    
		    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
		    		    cartaFechada = EntradaTeclado.leInt();
		    		    
		    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
		    		    
		    		    Carta carta = cartasNaMao[cartaRetirada];
		    		    
		    		    return carta;
					}
					break;
				case 12:
					System.out.println("\nA rodada esta valendo 12!");
					
					System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
	    		    cartaRetirada = EntradaTeclado.leInt();
	    		    
	    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
	    		    cartaFechada = EntradaTeclado.leInt();
	    		    
	    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
	    		    
	    		    Carta carta = cartasNaMao[cartaRetirada];
	    		    
	    		    return carta;
			}
		}
	}
	
	/**
	 * Funcao que imprime a mao do jogador.
	 */
	public void printMao() {
		for(int i = 0; i < nCartas; i++) System.out.printf("%d ", cartasNaMao[i].getValor());
	}
	
	/**
	 * Funcao que pega o vetor de cartas da mao do jogador.
	 * @return Carta[] cartasNaMao -> vetor de cartas da mao do Jogador.
	 */
	public Carta[] getCartasNaMao() {
		return cartasNaMao;
	}
	
	/**
	 * Funcao que seta as cartas na mao do jogador.
	 */
	public void setCartasNaMao(Carta[] cartasNaMao) {
		this.cartasNaMao = cartasNaMao;
	}
	
}
