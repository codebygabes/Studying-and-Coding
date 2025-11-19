package br.com.escolamusica.cursos;

public class CursoPiano extends Curso {
    public CursoPiano(String nome, int duracao, int numAlunos) {
        super(nome, duracao, numAlunos);
    }

    @Override
    public void avaliar() {
        System.out.println("Avaliação: executar peça de piano.");
    }
}