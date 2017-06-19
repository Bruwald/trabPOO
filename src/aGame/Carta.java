package aGame;

import java.io.Serializable;

/**
 * Classe Responsavel pelo objeto Carta de um baralho
 * @author 
 *
 */
public class Carta implements Serializable{
	private int naipe;
	private int valor; //dama valete rei / 10 11 12 (truco: 4 5 6 7 dama valete rei A 2 3 Manilha
	private int peso; //peso utilizado para avaliar carta, que vai variar dependendo do jogo
	
	/**
	 * MÈtodo construtor da classe carta. Recebe o naipe e o valor da carta
	 * @param naipe (1 - ouros, 2 - espadilha 3 - copas, 4 - paus)
	 * @param valor (1 - As, 2_10 - 2_10, 11 - dama, 12 - valete, 13 - rei)
	 */
	public Carta(int naipe, int valor) {
		super();
		this.naipe = naipe;
		this.valor = valor;
		this.peso = -1; //peso default inicial / sem peso
	}

	/**
	 * Pega o naipe da carta
	 * @return
	 */
	public int getNaipe() {
		return naipe;
	}

	/**
	 * Seta um naipe na carta
	 * @param naipe
	 */
	public void setNaipe(int naipe) {
		this.naipe = naipe;
	}

	/**
	 * Pega o valor da carta
	 * @return
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Seta um valor na carta
	 * @param valor
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}	
	
	/**
	 * Pega o peso da carta
	 * @return
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * Seta um peso na carta
	 * @param peso
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

//	@Override
//	public boolean equals(Object obj) {
//		Carta other = (Carta) obj;
//		if (naipe != other.naipe)
//			return false;
//		if (valor != other.valor)
//			return false;
//		return true;
//	}

	/**
	 * Imprime a carta (Naipe + " " + Valor)
	 */
	@Override
	public String toString(){
		String s = "";
		s = this.getNaipe() + "  " + this.getValor() + " . " + this.getPeso();
		
		return s;
	}
}
