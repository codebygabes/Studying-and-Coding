package br.com.zoologico.main;

import br.com.zoologico.animais.Ave;
import br.com.zoologico.animais.Mamifero;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Mamifero objCachorro = new Mamifero("Cachorro", "Pinscher", "Longo");
		objCachorro.comer();
		objCachorro.dormir();
		objCachorro.setEspecie("Cachorro");
		
		System.out.println("---- ----- ----- -----");
		
		Ave objPinguim = new Ave("Pinguim", "Little Blue", 1.5);
		objPinguim.voar();
		objPinguim.comer();
		objPinguim.setEspecie("Pinguim");
		objPinguim.setAsa(10.0);

	}
	

}
