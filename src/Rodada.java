
public class Rodada {

	private Carta manilha;
	private int nroJogada;
	private int valorRodada;
	private int quemJoga;
	private int qualJogadorGanhouPrimeiraJogada;
	private Placar placarRodada;
	private boolean amarrouJogada1;
	private boolean amarrouJogada2;
	private boolean amarrouJogada3;
	
	public Rodada(Carta manilha) {
		this.manilha = manilha;
		this.valorRodada = 1;
		this.nroJogada = 1;
		this.quemJoga = 0;
		this.qualJogadorGanhouPrimeiraJogada = -2;
		this.placarRodada = new Placar(2);
		this.amarrouJogada1 = false;
		this.amarrouJogada2 = false;
		this.amarrouJogada3 = false;
	}

	public Carta comparaCartaDupla(Carta carta1, Carta carta2) {
		
		if(carta1.getPeso() > carta2.getPeso()) return carta1;
		else if(carta1.getPeso() == carta2.getPeso()) {
			if(carta1.getNaipe() > carta2.getNaipe()) return carta1;
			else return carta2;
		}
		else return carta2;
		
	}
	
	public int qualJogadorGanhouOuAmarrouJogada(int quemComecouJogada, Carta[] cartasJogadas) {
		Carta maiorCartaDupla1, maiorCartaDupla2;
		
		maiorCartaDupla1 = comparaCartaDupla(cartasJogadas[0], cartasJogadas[2]);
		maiorCartaDupla2 = comparaCartaDupla(cartasJogadas[1], cartasJogadas[3]);
		
		if(maiorCartaDupla1.getPeso() == maiorCartaDupla2.getPeso()) {
			
			if(nroJogada == 1) {
				amarrouJogada1 = true;
				placarRodada.incrementarPontosDupla1(1);
				placarRodada.incrementarPontosDupla2(1);
			} else if(nroJogada == 2) {
				if(!amarrouJogada1) {
					if(qualJogadorGanhouPrimeiraJogada % 2 == 0) placarRodada.incrementarPontosDupla1(1);
					if(qualJogadorGanhouPrimeiraJogada % 2 == 1) placarRodada.incrementarPontosDupla2(1);
					amarrouJogada2 = true;
				} else amarrouJogada2 = true;
			} else if(nroJogada == 3) {
				if(qualJogadorGanhouPrimeiraJogada % 2 == 0) placarRodada.incrementarPontosDupla1(1);
				if(qualJogadorGanhouPrimeiraJogada % 2 == 1) placarRodada.incrementarPontosDupla2(1);
				amarrouJogada3 = true;
			}
			
			nroJogada++;
			
			if(quemComecouJogada == 0 || quemComecouJogada == 2) {
				if(maiorCartaDupla2.getPeso() == cartasJogadas[1].getPeso()){
					qualJogadorGanhouPrimeiraJogada = 1;
					return 1;
				} else {
					qualJogadorGanhouPrimeiraJogada = 3;
					return 3;
				}
			} else if(quemComecouJogada == 1 || quemComecouJogada == 3) {
				if(maiorCartaDupla1.getPeso() == cartasJogadas[0].getPeso()){
					qualJogadorGanhouPrimeiraJogada = 0;
					return 0;
				}else {
					qualJogadorGanhouPrimeiraJogada = 2;
					return 2;
				}
			}
			
		} else if(maiorCartaDupla1.getPeso() > maiorCartaDupla2.getPeso()) {
			if(nroJogada == 1) qualJogadorGanhouPrimeiraJogada = 1;
			placarRodada.incrementarPontosDupla1(1);
			nroJogada++;
			if(maiorCartaDupla1 == cartasJogadas[0]) return 0;
			else return 2;
		} else if(maiorCartaDupla1.getPeso() < maiorCartaDupla2.getPeso()) {
			if(nroJogada == 1) qualJogadorGanhouPrimeiraJogada = 2;
			placarRodada.incrementarPontosDupla2(1);
			nroJogada++;
			if(maiorCartaDupla2 == cartasJogadas[1]) return 1;
			else return 3;
		}
		
		return -1;
	}
	
	public int quemGanhouRodada() {
		
		if((amarrouJogada2 || amarrouJogada3) && !amarrouJogada1) return qualJogadorGanhouPrimeiraJogada;
		if(amarrouJogada1 && amarrouJogada2 && amarrouJogada3) return -1;
		
		return placarRodada.quemGanhou();
		
	}
	
	//Getters and Setters

	public Carta getManilha() {
		return manilha;
	}

	public void setManilha(Carta manilha) {
		this.manilha = manilha;
	}

	public int getValorRodada() {
		return valorRodada;
	}

	public void setValorRodada(int valorRodada) {
		this.valorRodada = valorRodada;
	}
	
	public boolean getAmarrouJogada1() {
		return amarrouJogada1;
	}

	public void setAmarrouJogada1(boolean amarrouJogada1) {
		this.amarrouJogada1 = amarrouJogada1;
	}

	public boolean getAmarrouJogada2() {
		return amarrouJogada2;
	}

	public void setAmarrouJogada2(boolean amarrouJogada2) {
		this.amarrouJogada2 = amarrouJogada2;
	}

	public boolean getAmarrouJogada3() {
		return amarrouJogada3;
	}

	public void setAmarrouJogada3(boolean amarrouJogada3) {
		this.amarrouJogada3 = amarrouJogada3;
	}

	public int getNroJogada() {
		return nroJogada;
	}

	public void setNroJogada(int nroJogada) {
		this.nroJogada = nroJogada;
	}

	public int getQuemJoga() {
		return quemJoga;
	}

	public void setQuemJoga(int quemJoga) {
		this.quemJoga = quemJoga;
	}

	public Placar getPlacarRodada() {
		return placarRodada;
	}

	public void setPlacarRodada(Placar placarRodada) {
		this.placarRodada = placarRodada;
	}

}
