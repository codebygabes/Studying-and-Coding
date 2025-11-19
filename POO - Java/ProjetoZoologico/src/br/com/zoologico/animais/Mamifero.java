package br.com.zoologico.animais;

public class Mamifero extends Animal {

	private String pelo;
	
	public Mamifero(String especie, String raca, String pelo) {
		super(especie, raca);
		this.pelo = pelo;
	}
	
	public void amamentar() {
		System.out.println(getEspecie()+ ", Amamentando . . .");
	}
	
}
