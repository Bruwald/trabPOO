package cliente;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	String nome;
	String mensagem;
	int nomeInt;
	
	public ChatMessage(String nome, String mensagem, int nomeInt) {
		//super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.nomeInt = nomeInt;
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
	
}
