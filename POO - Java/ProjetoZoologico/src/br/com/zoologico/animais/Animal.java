package br.com.zoologico.animais;

public class Animal {

	private String especie;
	private String raca;
	
	public Animal(String especie, String raca) {
		super();
		this.especie = especie;
		this.raca = raca;
	}
	
	public void comer() {
		System.out.println("Comendo . . . ");
	}
	public void dormir() {
		System.out.println("Dormindo . . .");
	}

	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		if (especie == "Cachorro" && especie == "Pinguim") {
			this.especie = especie;
		}else {                                             
			System.out.println("Deixe a que Está!");
		}
	}

	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	
}
