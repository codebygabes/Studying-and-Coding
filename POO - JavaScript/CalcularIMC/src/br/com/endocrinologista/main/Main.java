package br.com.endocrinologista.main;

import br.com.endocrinologista.Atleta;
import br.com.endocrinologista.Pessoa;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pessoa PessoaComum = new Pessoa("Gabriel", 55.3, 1.75);
		System.out.println("DADOS DA PESSOA COMUM !");
		System.out.printf("O IMC de %s é : %.2f\n", PessoaComum.getNome(), PessoaComum.calcularIMC());
					
		System.out.println("---- ----- ----- -----");
		
		Atleta PessoaAtleta = new Atleta("João", 55.3, 1.75, "Volêi");	
		System.out.println("DADOS DA PESSOA ATLETA !");
		PessoaAtleta.praticando();
		System.out.printf("O IMC de %s é : %.2f\n", PessoaAtleta.getNome(), PessoaAtleta.calcularIMC());	
		
	}

}
