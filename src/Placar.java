
public class Placar {
	
	private int pontosDupla1;
	private int pontosDupla2;
	private int maxPontos;

	public Placar(int maxPontos) {
		this.pontosDupla1 = 0;
		this.pontosDupla2 = 0;
		this.maxPontos = maxPontos;
	}
	
	public boolean alguemGanhou() {
		if(pontosDupla1 >= maxPontos || pontosDupla2 >= maxPontos) return true;
		else return false;
	}
	
	public int quemGanhou() {
		if(pontosDupla1 >= maxPontos) return 1;
		else if(pontosDupla2 >= maxPontos) return 2;
		else return -1;
	}
	
	//Getters and Setters

	public int getPontosDupla1() {
		return pontosDupla1;
	}

	public void incrementarPontosDupla1(int pontosDupla1) {
		this.pontosDupla1 += pontosDupla1;
	}

	public int getPontosDupla2() {
		return pontosDupla2;
	}

	public void incrementarPontosDupla2(int pontosDupla2) {
		this.pontosDupla2 += pontosDupla2;
	}

	public int getMaxPontos() {
		return maxPontos;
	}

	public void setMaxPontos(int maxPontos) {
		this.maxPontos = maxPontos;
	}

}
