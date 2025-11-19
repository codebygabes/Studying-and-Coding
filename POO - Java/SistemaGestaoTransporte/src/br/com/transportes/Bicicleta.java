package br.com.transportes;

public class Bicicleta extends Transporte implements Manutenivel {
    private boolean eletrica;
    private boolean precisaManutencao;
    private int minutosDeUso;

    public Bicicleta(String modelo, int capacidade, boolean eletrica) {
        super(modelo, capacidade);
        this.eletrica = eletrica;
        this.precisaManutencao = false;
        this.minutosDeUso = 0;
    }

    @Override
    public double calcularTarifa(double minutos) {
    	double custoPorMinuto = eletrica ? 0.15 : 0.08;
        return minutos * custoPorMinuto;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo: Bicicleta");
        System.out.println("Elétrica: " + (eletrica ? "Sim" : "Não"));
        System.out.println("Status Manutenção: " + (precisaManutencao ? "Necessária" : "OK"));
    }

    @Override
    public boolean verificarManutencao() {
        precisaManutencao = minutosDeUso >= 500;
        return precisaManutencao;
    }

    @Override
    public void realizarManutencao() {
        if (precisaManutencao) {
            minutosDeUso = 0;
            precisaManutencao = false;
            System.out.println("Manutenção da bicicleta realizada com sucesso!");
        } else {
            System.out.println("Bicicleta não precisa de manutenção no momento.");
        }
    }

    public boolean isEletrica() {
        return eletrica;
    }

    public void setEletrica(boolean eletrica) {
        this.eletrica = eletrica;
    }

    public int getMinutosDeUso() {
        return minutosDeUso;
    }

    public void setMinutosDeUso(int minutosDeUso) {
        this.minutosDeUso = minutosDeUso;
        verificarManutencao();
    }
}
