package br.com.transportes;

public class Onibus extends Transporte implements Manutenivel {
    private boolean possuiArCondicionado;
    private boolean precisaManutencao;
    private int quilometragem;

    public Onibus(String modelo, int capacidade, boolean possuiArCondicionado) {
        super(modelo, capacidade);
        this.possuiArCondicionado = possuiArCondicionado;
        this.precisaManutencao = false;
        this.quilometragem = 0;
    }

    @Override
    public double calcularTarifa(double distancia) {
        double tarifaBase = 4.50;
        double acrescimoPorKm = 0.25;
        return tarifaBase + (distancia * acrescimoPorKm);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo: Ônibus");
        System.out.println("Ar Condicionado: " + (possuiArCondicionado ? "Sim" : "Não"));
        System.out.println("Status Manutenção: " + (precisaManutencao ? "Necessária" : "OK"));
    }

    @Override
    public boolean verificarManutencao() {
        precisaManutencao = quilometragem >= 10000;
        return precisaManutencao;
    }

    @Override
    public void realizarManutencao() {
        if (precisaManutencao) {
            quilometragem = 0;
            precisaManutencao = false;
            System.out.println("Manutenção do ônibus realizada com sucesso!");
        } else {
            System.out.println("Ônibus não precisa de manutenção no momento.");
        }
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
        verificarManutencao();
    }
}
