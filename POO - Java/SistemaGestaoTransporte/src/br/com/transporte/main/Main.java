package br.com.transporte.main;

import br.com.transportes.Bicicleta;
import br.com.transportes.Manutenivel;
import br.com.transportes.Metro;
import br.com.transportes.Onibus;
import br.com.transportes.Transporte;

public class Main {

	public static void main(String[] args) {
        Onibus onibus = new Onibus("Mercedes O-500", 50, true);
        Metro metro = new Metro("Siemens M1", 800, 6);
        Bicicleta bicicleta = new Bicicleta("Caloi Explorer", 1, false);
        Bicicleta bikeEletrica = new Bicicleta("Smart Bike Pro", 1, true);

        System.out.println("=== CÁLCULO DE TARIFAS ===");
        mostrarTarifa(onibus, 10.0, "Ônibus");
        mostrarTarifa(metro, 15.0, "Metrô");
        mostrarTarifa(bicicleta, 30.0, "Bicicleta Comum");
        mostrarTarifa(bikeEletrica, 45.0, "Bicicleta Elétrica");

        System.out.println("\n=== INFORMAÇÕES DOS TRANSPORTES ===");
        onibus.exibirInformacoes();
        System.out.println();
        metro.exibirInformacoes();
        System.out.println();
        bicicleta.exibirInformacoes();
        System.out.println();
        bikeEletrica.exibirInformacoes();
        

        System.out.println("\n=== SISTEMA DE MANUTENÇÃO ===");
        Manutencao(onibus, "Ônibus");
        Manutencao(metro, "Metrô");
        Manutencao(bicicleta, "Bicicleta");
        Manutencao(bikeEletrica, "Bicicleta Eletrica");
    }

    private static void mostrarTarifa(Transporte transporte, double distancia, String tipo) {
        double tarifa = transporte.calcularTarifa(distancia);
        if (transporte instanceof Bicicleta) {
            System.out.printf("Tarifa da %s por %.0f minutos: R$ %.2f%n", tipo, distancia, tarifa);
        } else {
            System.out.printf("Tarifa do %s para %.1f km: R$ %.2f%n", tipo, distancia, tarifa);
        }
    }

    private static void Manutencao(Manutenivel veiculo, String tipo) {
        System.out.println("\n--- " + tipo + " ---");
        System.out.println("Precisa de manutenção? " + veiculo.verificarManutencao());
        
        if (veiculo instanceof Onibus) {
            ((Onibus) veiculo).setQuilometragem(12000);
        } else if (veiculo instanceof Metro) {
            ((Metro) veiculo).setDiasDesdeUltimaManutencao(35);
        } else if (veiculo instanceof Bicicleta) {
            ((Bicicleta) veiculo).setMinutosDeUso(600);
        }
        
        System.out.println("Após uso - Precisa de manutenção? " + veiculo.verificarManutencao());
        veiculo.realizarManutencao();
        System.out.println("Após manutenção - Precisa de manutenção? " + veiculo.verificarManutencao());
    }
}