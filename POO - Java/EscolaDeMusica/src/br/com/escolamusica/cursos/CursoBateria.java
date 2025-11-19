package br.com.escolamusica.cursos;

public class CursoBateria extends Curso {
    public CursoBateria(String nome, int duracao, int numAlunos) {
        super(nome, duracao, numAlunos);
    }

    @Override
    public void avaliar() {
        System.out.println("Avaliação: solo de bateria.");
    }
}