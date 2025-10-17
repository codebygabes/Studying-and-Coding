package br.com.transportes;

public class Metro extends Transporte implements Manutenivel {
    private int numeroDeVagoes;
    private boolean precisaManutencao;
    private int diasDesdeUltimaManutencao;

    public Metro(String modelo, int capacidade, int numeroDeVagoes) {
        super(modelo, capacidade);
        setNumeroDeVagoes(numeroDeVagoes);
        this.precisaManutencao = false;
        this.diasDesdeUltimaManutencao = 0;
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 5.80;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo: Metrô");
        System.out.println("Número de Vagões: " + numeroDeVagoes);
        System.out.println("Status Manutenção: " + (precisaManutencao ? "Necessária" : "OK"));
    }

    @Override
    public boolean verificarManutencao() {
        precisaManutencao = diasDesdeUltimaManutencao >= 30;
        return precisaManutencao;
    }

    @Override
    public void realizarManutencao() {
        if (precisaManutencao) {
            diasDesdeUltimaManutencao = 0;
            precisaManutencao = false;
            System.out.println("Manutenção do metrô realizada com sucesso!");
        } else {
            System.out.println("Metrô não precisa de manutenção no momento.");
        }
    }

    public int getNumeroDeVagoes() {
        return numeroDeVagoes;
    }

    public void setNumeroDeVagoes(int numeroDeVagoes) {
        if (numeroDeVagoes < 1) {
            throw new IllegalArgumentException("Número de vagões deve ser maior ou igual a 1");
        }
        this.numeroDeVagoes = numeroDeVagoes;
    }

    public int getDiasDesdeUltimaManutencao() {
        return diasDesdeUltimaManutencao;
    }

    public void setDiasDesdeUltimaManutencao(int diasDesdeUltimaManutencao) {
        this.diasDesdeUltimaManutencao = diasDesdeUltimaManutencao;
        verificarManutencao();
    }
}
