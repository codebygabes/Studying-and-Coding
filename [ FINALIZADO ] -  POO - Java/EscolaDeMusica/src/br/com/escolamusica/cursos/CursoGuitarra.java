package br.com.escolamusica.cursos;

public class CursoGuitarra extends Curso {
    public CursoGuitarra(String nome, int duracao, int numAlunos) {
        super(nome, duracao, numAlunos);
    }

    @Override
    public void avaliar() {
        System.out.println("Avaliação: tocar música de guitarra no final do curso.");
    }
}
