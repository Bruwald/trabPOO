import java.util.Scanner;

public class Mao {
	
	private Carta[] mao;

	public Mao(Carta[] mao) {
		this.mao = mao;
	}
	
	public void setPesoCartas(Carta manilha) {
		
		for(Carta carta: mao) {
			if(carta.getValor() == manilha.getValor()) {
				if(carta.getNaipe() == 1) carta.setPeso(14);
				else if(carta.getNaipe() == 2) carta.setPeso(15);
				else if(carta.getNaipe() == 3) carta.setPeso(16);
				else if(carta.getNaipe() == 4) carta.setPeso(17);
			}
		}
		
	}
	
	public Carta tirarCartaMao() {
		int cartaRetirada; //vai de 1 a 3
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Carta a ser retirada da mao (de 1 a 3):");
		    cartaRetirada = sc.nextInt();
			
		    if(mao[cartaRetirada - 1].getValor() == -1) System.out.println("A carta ja foi retirada!\n");
		    else {
		    	Carta carta = mao[cartaRetirada - 1];
		    	mao[cartaRetirada - 1] = new Carta(-1,-1,-1);
		    	return carta;
		    }
		}
	}
	
	//Getters and Setters

	public Carta[] getMao() {
		return mao;
	}

	public void setMao(Carta[] mao) {
		this.mao = mao;
	}
	
}
