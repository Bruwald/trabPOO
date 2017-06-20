package cliente;

import java.io.Serializable;

import aGame.Carta;

/**
 * Classe responsavel por ser o objeto que sera passado do servidor para os clientes (serializavel)
 * @author 
 *
 */
public class ChatMessage implements Serializable{
	String nome;
	String mensagem;
	int nomeInt;
	Carta carta;
	Carta carta0, carta1, carta2;
	int valRodada;
	int placarG1, placarG2;
	
	/**
	 * Construtor 1: recebe um nome, uma mensagem e um nome em formato de int (ex: Jose = Jogador 0 ==> nInt = 0)
	 * @param nome
	 * @param mensagem
	 * @param nomeInt
	 */
	public ChatMessage(String nome, String mensagem, int nomeInt) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
		this.carta = null;
		this.carta0 = null;
		this.carta1 = null;
		this.carta2 = null;
	}
	
	/**
	 * Construtor 2: recebe um nome, uma mensagem, um nomeInt e uma Carta para enviar ao cliente
	 * @param nome
	 * @param mensagem
	 * @param nomeInt
	 * @param carta
	 */
	public ChatMessage(String nome, String mensagem, int nomeInt, Carta carta) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
		this.carta = carta;
		this.carta0 = null;
		this.carta1 = null;
		this.carta2 = null;
	}
	
	/**
	 * Construtor 3: recebe um nome, uma mensagem, um nomeInt e uma Mao (3 cartas) para enviar ao cliente
	 * @param nome
	 * @param mensagem
	 * @param nomeInt
	 * @param carta0
	 * @param carta1
	 * @param carta2
	 */
	public ChatMessage(String nome, String mensagem, int nomeInt, Carta carta0, Carta carta1, Carta carta2) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
		this.carta = null;
		this.carta0 = carta0;
		this.carta1 = carta1;
		this.carta2 = carta2;
	}
	
	/**
	 * Construtor 4: recebe um nome, uma mensagem, um nomeInt e o valor da rodada (ou apenas um segudo int) para enviar ao cliente
	 * @param nome
	 * @param mensagem
	 * @param nomeInt
	 * @param valRodada
	 */
	public ChatMessage(String nome, String mensagem, int nomeInt, int valRodada) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
		this.carta = null;
		this.carta0 = null;
		this.carta1 = null;
		this.carta2 = null;
		this.valRodada = valRodada;
	}
	
	/**
	 * Construtor 2: recebe um nome, uma mensagem, um nomeInt e os/as placares/pontuacoes para enviar ao cliente
	 * @param nome
	 * @param mensagem
	 * @param nomeInt
	 * @param placarG1
	 * @param placarG2
	 */
	public ChatMessage(String nome, String mensagem, int nomeInt, int placarG1, int placarG2) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
		this.carta = null;
		this.carta0 = null;
		this.carta1 = null;
		this.carta2 = null;
		this.placarG1 = placarG1;
		this.placarG2 = placarG2;
	}
	
	/**
	 * Get String nome
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Seta o nome
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Get String mensagem
	 * @return
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Seta a mensagem
	 * @param mensagem
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * Get nomeInt (inteiro)
	 * @return
	 */
	public int getNomeInt() {
		return nomeInt;
	}

	/**
	 * Seta o nomeInt
	 * @param nomeInt
	 */
	public void setNomeInt(int nomeInt) {
		this.nomeInt = nomeInt;
	}

	/**
	 * Get Carta carta
	 * @return
	 */
	public Carta getCarta() {
		return carta;
	}

	/**
	 * Seta o valor de uma carta
	 * @param carta
	 */
	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	/**
	 * Get Carta 0 da Mao
	 * @return
	 */
	public Carta getCarta0() {
		return carta0;
	}

	/**
	 * Seta o valor da carta 0 da mao
	 * @param carta0
	 */
	public void setCarta0(Carta carta0) {
		this.carta0 = carta0;
	}
	
	/**
	 * Get Carta 1 da Mao
	 * @return
	 */
	public Carta getCarta1() {
		return carta1;
	}

	/**
	 * Seta o valor da carta 1 da mao
	 * @param carta1
	 */
	public void setCarta1(Carta carta1) {
		this.carta1 = carta1;
	}

	/**
	 * Get carta 2 da Mao
	 * @return
	 */
	public Carta getCarta2() {
		return carta2;
	}

	/**
	 * Seta o valor da carta 2 da mao
	 * @param carta2
	 */
	public void setCarta2(Carta carta2) {
		this.carta2 = carta2;
	}

	/**
	 * Pega um inteiro (valRodada)
	 * @return
	 */
	public int getValRodada() {
		return valRodada;
	}

	/**
	 * Seta um inteiro (valRodada)
	 * @param valRodada
	 */
	public void setValRodada(int valRodada) {
		this.valRodada = valRodada;
	}

	/**
	 * Pega placar do grupo 1
	 * @return
	 */
	public int getPlacarG1() {
		return placarG1;
	}

	/**
	 * Seta placar do grupo 1
	 * @param placarG1
	 */
	public void setPlacarG1(int placarG1) {
		this.placarG1 = placarG1;
	}

	/**
	 * Pega placar do grupo 2
	 * @return
	 */
	public int getPlacarG2() {
		return placarG2;
	}

	/**
	 * Seta placar do grupo 2
	 * @param placarG2
	 */
	public void setPlacarG2(int placarG2) {
		this.placarG2 = placarG2;
	}	
	
	
		
}
