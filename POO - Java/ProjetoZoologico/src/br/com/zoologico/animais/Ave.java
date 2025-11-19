package br.com.zoologico.animais;

public class Ave extends Animal {
		
	private double Asa;
	
	public Ave(String especie, String raca, double asa) {
		super(especie, raca);
		this.Asa = asa;
	}

	public void voar() {
		System.out.println("Voando . . .");
	}
	
	public double getAsa() {
		return Asa;
	}
	public void setAsa(double asa) {
		if (asa == 5.5) {
			this.Asa = asa;
		}else {                                             
			System.out.println("Deixe a que Está!");
		}
	}
}
