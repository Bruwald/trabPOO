package cliente;

import java.io.Serializable;

import aGame.Carta;

public class ChatMessage implements Serializable{
	String nome;
	String mensagem;
	int nomeInt;
	Carta carta;
	Carta carta0, carta1, carta2;
	int valRodada;
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getNomeInt() {
		return nomeInt;
	}

	public void setNomeInt(int nomeInt) {
		this.nomeInt = nomeInt;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public Carta getCarta0() {
		return carta0;
	}

	public void setCarta0(Carta carta0) {
		this.carta0 = carta0;
	}

	public Carta getCarta1() {
		return carta1;
	}

	public void setCarta1(Carta carta1) {
		this.carta1 = carta1;
	}

	public Carta getCarta2() {
		return carta2;
	}

	public void setCarta2(Carta carta2) {
		this.carta2 = carta2;
	}

	public int getValRodada() {
		return valRodada;
	}

	public void setValRodada(int valRodada) {
		this.valRodada = valRodada;
	}	
	
	
		
}
