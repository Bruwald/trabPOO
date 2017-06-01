import java.io.IOException;

public class Mao {
	
	private Carta[] cartasNaMao;
	private int nCartas;

	public Mao(Baralho baralho) {
		this.nCartas = 3;
		this.cartasNaMao = new Carta[nCartas];
		this.cartasNaMao = retirarCartasDoBaralho(baralho);
	}
	
	//Para testar passando uma mao ja feita
	public Mao(Carta[] cartasNaMao) {
		this.cartasNaMao = cartasNaMao;
		this.nCartas = 3;
	}
	
	public Carta[] retirarCartasDoBaralho(Baralho baralho) {
		Carta[] cartasNaMao = new Carta[nCartas];
		
		for(int i = 0; i < nCartas; i++) cartasNaMao[i] = baralho.baralho_retirarCarta();
		
		return cartasNaMao;
	}
	
	public void setPesoCartas(Carta manilha) {
		
		for(Carta carta: cartasNaMao) {
			if(carta.getValor() == manilha.getValor()) {
				if(carta.getNaipe() == 1) carta.setPeso(14);
				else if(carta.getNaipe() == 2) carta.setPeso(15);
				else if(carta.getNaipe() == 3) carta.setPeso(16);
				else if(carta.getNaipe() == 4) carta.setPeso(17);
			}
		}
		
	}
	
	public Carta jogarCarta(Rodada rodada) throws IOException {
		int cartaRetirada, cartaFechada, resposta, quemJoga;
		
		while(true) {
			System.out.printf("CARTAS NA MAO: ");
			printMao();
			
			switch(rodada.getValorRodada()) {
				case 1:
					System.out.printf("\nDESEJA TRUCAR? (0 - NAO / 1 - SIM)");
					resposta = EntradaTeclado.leInt();
			    	if(resposta == 1) {
			    		rodada.setQuemTrucou(rodada.getQuemJoga());
			    		rodada.setValorRodada(3);
			    		resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4]);
			    		if(resposta == 0) {
			    			rodada.setValorRodada(1);
			    			quemJoga = rodada.getQuemJoga();
			    			if(quemJoga % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    			else rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    			return new Carta(50,50);
			    		} else if(resposta == 1) {
			    			System.out.println("O jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " aceitou seu truco!");
			    			
			    			System.out.printf("\nJOGAR CARTA (de 0 a 2): ");
			    		    cartaRetirada = EntradaTeclado.leInt();
			    		    
			    		    System.out.printf("JOGAR CARTA (0 - ABERTA / 1 - FECHADA): ");
			    		    cartaFechada = EntradaTeclado.leInt();
			    		    
			    		    if(cartaFechada == 1) cartasNaMao[cartaRetirada].setPeso(-1);
			    		    
			    		    Carta carta = cartasNaMao[cartaRetirada];
			    		    
			    		    return carta;
			    		} else if(resposta == 2) {
			    			rodada.setValorRodada(6);
			    			System.out.println("\nO jogador " + rodada.getJogadores()[(rodada.getQuemJoga() + 1) % 4].getNome() + " pediu 6!");
			    			resposta = rodada.jogadorAceitaEstadoDaAposta(rodada.getJogadores()[(rodada.getQuemJoga())]);
			    			if(resposta == 0) {
			    				rodada.setValorRodada(3);
			    				if(rodada.getQuemJoga() % 2 == 0) rodada.getPlacarRodada().incrementarPontosDupla2(2);
			    				else rodada.getPlacarRodada().incrementarPontosDupla1(2);
			    				return new Carta(50,50);
			    			} else if(resposta == 1) {
			    				rodada.setMudouJogador(true);
			    				return null;
			    			} else if(resposta == 2) {
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
					System.out.printf("\nDESEJA PEDIR 6? (0 - NAO / 1 - SIM)");
					resposta = EntradaTeclado.leInt();
					if(resposta == 1) {
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
					System.out.printf("\nDESEJA PEDIR 9? (0 - NAO / 1 - SIM)");
					resposta = EntradaTeclado.leInt();
					if(resposta == 1) {
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
					System.out.printf("\nDESEJA PEDIR 12? (0 - NAO / 1 - SIM)");
					resposta = EntradaTeclado.leInt();
					if(resposta == 1) {
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
	
	public void printMao() {
		for(int i = 0; i < nCartas; i++) System.out.printf("%d ", cartasNaMao[i].getValor());
	}
	
	//Getters and Setters

	public Carta[] getCartasNaMao() {
		return cartasNaMao;
	}

	public void setCartasNaMao(Carta[] cartasNaMao) {
		this.cartasNaMao = cartasNaMao;
	}
	
}
