
public class Jogador {
	
	private String nome;
	private int nroJogador;
	private Mao mao;
	
	public Jogador(String nome, int nroJogador) {
		this.nome = nome;
		this.nroJogador = nroJogador;
	}
	
	public void receberCartasDaMao(Baralho baralho) {
		this.mao = new Mao(baralho);
	}
	
	//Getters and Setters
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNroJogador() {
		return nroJogador;
	}

	public void setNroJogador(int nroJogador) {
		this.nroJogador = nroJogador;
	}

	public Mao getMao() {
		return mao;
	}

	public void setMao(Mao mao) {
		this.mao = mao;
	}
	
	

}
