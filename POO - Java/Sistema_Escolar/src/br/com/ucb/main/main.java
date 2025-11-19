package br.com.ucb.main;

import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Qual seu nome?");
		String nome = scanner.nextLine();
		
		System.out.println("Qual a sua nota?");
		int nota = scanner.nextInt();
		
		if (nota >= 7)
		{
			System.out.println(nome+", Voce passou!");
		} else {
			System.out.println(nome+", Infelizmente, voce reprovou!");
		}
		
		scanner.close();

	}

}
