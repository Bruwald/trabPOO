package aGame;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe responsavel por gerenciar um baralho
 * @author 
 *
 */
public class Baralho {
	private LinkedList<Carta> baralho;
	private LinkedList<Carta> baralhoRemovidas; //cartas retiradas
	
	/**
	 * Metodo construtor do baralho. Constroi o baralho completo (52 cartas)
	 */
	public Baralho() { //cria baralho inteiro (sem coringa)
		super();
		// this.baralho = baralho;
		baralho = new LinkedList<Carta>();
		baralhoRemovidas = new LinkedList<Carta>();
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				baralho.add(new Carta(i, j));// criar todas as cartas do baralho
			}
		}
	}

//	/**
//	 * Pega o baralho
//	 * @return
//	 */
//	public LinkedList<Carta> getBaralho() {
//		return baralho;
//	}
//
//	/**
//	 * Seta o baralho
//	 * @param baralho
//	 */
//	public void setBaralho(LinkedList<Carta> baralho) {
//		this.baralho = baralho;
//	}

	/**
	 * Metodo que embaralha o baralho
	 */
	public void baralho_shuffle() { //embaralha
		Collections.shuffle(this.baralho);
	}
	
	/**
	 * Metodo que remove a primeira carta do baralho
	 * @return carta (Carta)
	 */
	public Carta baralho_retirarCarta(){ //remove a primeira carta
		Carta auxCarta = this.baralho.remove();
		this.baralhoRemovidas.add(auxCarta);
		
		return auxCarta;
	}
	
	/**
	 * Metodo que recoloca as cartas removidas no baralho
	 */
	public void baralho_restaurar(){ //re coloca cartas no monte
		this.baralho.addAll(this.baralhoRemovidas);
		this.baralhoRemovidas.clear();
	}
	
	/**
	 * Metodo que arruma o baralho para jogar truco (remove todos os 8, 9 e 10; ajusta os pesos das cartas na ordem 4->K->A->3) 
	 */
	public void baralho_trucoArrange(){ //ajusta o baralho para jogar truco
		for(Iterator<Carta> it = this.baralho.iterator(); it.hasNext();){ //usar iterador pois nao se sabe o indice
			Carta c = it.next();
			if(c.getValor() == 8 || c.getValor() == 9 || c.getValor() == 10){
				it.remove();
			}else{ //ajustar pesos iniciais das cartas: 4, 5, 6, 7, Q, J, K, A, 2, 3 (sujeito a mudanca para manilhas) -> de 0 a 9
				int valor = c.getValor();
				if(valor < 4) c.setPeso(valor + 6); //A, 2, 3
				else if(valor < 10) c.setPeso(valor - 4); //4, 5, 6, 7
				else c.setPeso(valor - 7); //Q, J, K
			}
		}
	}
	
	/**
	 * Metodo que restaura as cartas removidas para jogar truco no baralho (restaura todos os 8, 9 e 10) e os pesos das cartas para default (-1)
	 */
	public void baralho_trucoRestore(){ //restaura o baralho utilizado no truco
		for (int i = 1; i <= 4; i++) {
			baralho.add(new Carta(i, 8)); // re criar as cartas removidas para o truco
			baralho.add(new Carta(i, 9));
			baralho.add(new Carta(i, 10));
		}
		
		for(Carta c : baralho){ //restaurar pesos
			c.setPeso(-1);
		}
	}
	
	/**
	 * Metodo que imprime as cartas remanescentes no baralho
	 */
	public void baralho_imprimir(){ //imprime o baralho (para teste)
		for(Carta c : this.baralho){
			//System.out.println(c.getNaipe() + "  " + c.getValor());
			System.out.println(c);
		}
	}
	
	/**
	 * Metodo que imprime as cartas removidas/fora do baralho (cartas removidas para jogar truco nao contam)
	 */
	public void baralho_imprimirRemovidas(){ //imprime as cartas removidas do baralho (para teste)
		for(Carta c : this.baralhoRemovidas){
			//System.out.println(c.getNaipe() + "  " + c.getValor());
			System.out.println(c);
		}
	}
}
