package aGame;
/**
 * Classe Responsavel pelo objeto Jogador do jogo do Truco.
 */
public class Jogador {
	
	/**
	 * Variaveis utilizadas na classe Jogador:
	 * String nome -> nome do jogador
	 * int nroJogador -> numero do jogador (de 0 a 3)
	 * Mao mao -> objeto Mao representando a mao do jogador (contendo 3 cartas)
	 */
	private String nome;
	private int nroJogador;
	private Mao mao;
	
	/**
	 * Metodo construtor da classe Jogador. Recebe o nome e o numero do jogador.
	 * @param String nome -> nome do jogador
	 * @param int nroJogador -> numero do jogador (de 0 a 3)
	 */
	public Jogador(String nome, int nroJogador) {
		this.nome = nome;
		this.nroJogador = nroJogador;
	}
	
	/**
	 * Funcao que seta a mao do jogador pegando tres cartas do baralho.
	 * @param Baralho baralho -> baralho do jogo Truco
	 */
	public void receberCartasDaMao(Baralho baralho) {
		this.mao = new Mao(baralho);
	}
	
	/**
	 * Pega o nome do jogador.
	 * @return nome do jogador
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Seta o nome do jogador.
	 * @param String nome -> nome do jogador
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Pega o numero do jogador.
	 * @return int nroJogador -> numero do jogador (de 0 a 3)
	 */
	public int getNroJogador() {
		return nroJogador;
	}
	
	/**
	 * Seta o numero do jogador.
	 * @param int nroJogador -> numero do jogador (de 0 a 3)
	 */
	public void setNroJogador(int nroJogador) {
		this.nroJogador = nroJogador;
	}

	/**
	 * Pega a mao do jogador.
	 * @return Mao mao -> objeto Mao do jogador, o qual armazena 3 cartas
	 */
	public Mao getMao() {
		return mao;
	}
	
	/**
	 * Seta a mao do jogador.
	 * @return
	 */
	public void setMao(Mao mao) {
		this.mao = mao;
	}

}
