import java.util.Vector;

public class Rodada {

	private Carta manilha;
	private int nroJogada;
	private int valorRodada;
	private Placar placarRodada;
	private Vector<Jogador> jogadores;
	private boolean amarrouJogada1;
	private boolean amarrouJogada2;
	private boolean amarrouJogada3;
	
	public Rodada(Carta manilha, Vector<Jogador> jogadores) {
		this.manilha = manilha;
		this.jogadores = jogadores;
		this.valorRodada = 1;
		this.nroJogada = 1;
		this.placarRodada = new Placar(2);
		this.amarrouJogada1 = false;
		this.amarrouJogada2 = false;
		this.amarrouJogada3 = false;
	}
	
	public Carta comparaCartaDupla(Carta carta1, Carta carta2) {
		
		if(carta1.getPeso() > carta2.getPeso()) return carta1;
		else return carta2;

	}
	
	public boolean amarrou() {
		
	}
	
	public Carta verificaMaiorCartaJogada() {
		
	}
	
	public int quemGanhouJogada() {
		Carta maiorCartaDupla1, maiorCartaDupla2;
		
		
	}
	
	public int quemGanhouRodada() {
		
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
	
	

}
