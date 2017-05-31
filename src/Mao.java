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
	
	public Carta jogarCarta() throws IOException {
		int cartaRetirada; //vai de 1 a 3
		
		while(true) {
			System.out.printf("CARTAS NA MAO: ");
			
			printMao();
			
			System.out.println("\nJOGAR CARTA (de 0 a 2):");
		    cartaRetirada = EntradaTeclado.leInt();
			
		    Carta carta = cartasNaMao[cartaRetirada];
		    
		    return carta;
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
