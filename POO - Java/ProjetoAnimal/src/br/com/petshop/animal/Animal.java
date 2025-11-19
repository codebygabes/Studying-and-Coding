package br.com.petshop.animal;

public class Animal {

	// Atributos - Caracteristicas
	private String animal;
	private String raca;
	private String sexo;
	private int idade;
	
	public Animal(String animal, String raca, String sexo, int idade) {
		super();
		this.animal = animal;
		this.raca = raca;
		this.sexo = sexo;
		this.idade = idade;
	}
	
	public void exibirAcoes() {
		System.out.println(animal+", esta andando!");
		System.out.println(animal+", esta comendo!");
		System.out.println("---- ---- ---- ----");
	}
	
	public void exibirInformacoesAnimal() {
		System.out.println("Raça : "+raca);
		System.out.println("Sexo : "+sexo);
		System.out.println("Idade : "+idade+ "(mês)");
		System.out.println("---- ---- ---- ----");
	}
}
