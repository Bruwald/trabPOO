package aGame;
/**
 * Classe Responsavel pelo objeto Placar da rodada e do jogo do Truco.
 */
public class Placar {
	
	/**
	 * Variaveis utilizadas na classe Placar:
	 * int pontosDupla1 -> pontos da dupla 1 (jogadores 0 e 2)
	 * int pontosDupla2 -> pontos da dupla 2 (jogadores 1 e 3)
	 * int maxPontos -> qual a quantidade maxima de pontos ate o jogo ou rodada acabar
	 */
	private int pontosDupla1;
	private int pontosDupla2;
	private int maxPontos;
	
	/**
	 * Metodo construtor da classe Placar. Recebe o valor maximo de pontos do placar.
	 * @param int maxPontos -> valor maximo de pontos do placar.
	 */
	public Placar(int maxPontos) {
		this.pontosDupla1 = 0;
		this.pontosDupla2 = 0;
		this.maxPontos = maxPontos;
	}
	
	/**
	 * Funcao que verifica se alguem ganhou o jogo ou rodada (como criterio de termino dos loops na funcao main).
	 * @return boolean true or false -> true se alguem ganhou o jogo e false se ninguem ganhou ainda.
	 */
	public boolean alguemGanhou() {
		if(pontosDupla1 >= maxPontos || pontosDupla2 >= maxPontos) return true;
		else return false;
	}
	
	/**
	 * Funcao que verifica qual dupla ganhou o jogo ou rodada e a retorna.
	 * @return int quemGanhou -> qual dupla ganhou o jogo ou rodada e a retorna.
	 */
	public int quemGanhou() {
		if(pontosDupla1 >= maxPontos) return 1;
		else if(pontosDupla2 >= maxPontos) return 2;
		else return -1;
	}
	
	/**
	 * Funcao que pega a quantidade de pontos da dupla 1.
	 * @return quantidade de pontos da dupla 1.
	 */
	public int getPontosDupla1() {
		return pontosDupla1;
	}
	
	/**
	 * Funcao que incrementa os pontos do placar da dupla 1. Recebe a quantidade de pontos para incrementar.
	 * @param int pontosDupla1 -> qtde de pontos para incrementar da dupla 1 no objeto Placar.
	 */
	public void incrementarPontosDupla1(int pontosDupla1) {
		this.pontosDupla1 += pontosDupla1;
	}
	
	/**
	 * Funcao que pega a quantidade de pontos da dupla 2.
	 * @return quantidade de pontos da dupla 2.
	 */
	public int getPontosDupla2() {
		return pontosDupla2;
	}
	
	/**
	 * Funcao que incrementa os pontos do placar da dupla 2. Recebe a quantidade de pontos para incrementar.
	 * @param int pontosDupla2 -> qtde de pontos para incrementar da dupla 2 no objeto Placar.
	 */
	public void incrementarPontosDupla2(int pontosDupla2) {
		this.pontosDupla2 += pontosDupla2;
	}
	
	/**
	 * Funcao que pega a quantidade maxima de pontos do placar.
	 * @return quantidade maxima de pontos do placar.
	 */
	public int getMaxPontos() {
		return maxPontos;
	}
	
	/**
	 * Funcao que seta a quantidade maxima de pontos do placar.
	 */
	public void setMaxPontos(int maxPontos) {
		this.maxPontos = maxPontos;
	}

}
