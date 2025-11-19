package br.com.transportes;

public abstract class Transporte {
    private String modelo;
    private int capacidade;

    public Transporte(String modelo, int capacidade) {
        setModelo(modelo);
        setCapacidade(capacidade);
    }

    public abstract double calcularTarifa(double distancia);

    public void exibirInformacoes() {
        System.out.println("Modelo: " + modelo);
        System.out.println("Capacidade: " + capacidade + " pessoas");
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
