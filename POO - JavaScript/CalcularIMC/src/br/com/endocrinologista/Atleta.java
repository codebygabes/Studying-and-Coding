package br.com.endocrinologista;

public class Atleta extends Pessoa {
	private String esportePraticado;
	
	public Atleta(String nome, double peso, double altura, String esportePraticado) {
		super(nome, peso, altura);
		this.esportePraticado = esportePraticado;	
	}
	
	@Override
	public double calcularIMC() {
		double IMCoriginal = super.calcularIMC();
		double IMCatleta = IMCoriginal * 0.95;	
		return IMCatleta;	
	}
	
	public void praticando() {
		System.out.println("Atleta está Praticando : "+esportePraticado);
	}
	
}
