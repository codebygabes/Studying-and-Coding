package br.com.escolamusica.cursos;

public class CursoFactory {
    public static Curso criarCurso(String tipo, String nome, int duracao, int numAlunos) {
        return switch (tipo.toLowerCase()) {
            case "guitarra" -> new CursoGuitarra(nome, duracao, numAlunos);
            case "piano" -> new CursoPiano(nome, duracao, numAlunos);
            case "bateria" -> new CursoBateria(nome, duracao, numAlunos);
            default -> throw new IllegalArgumentException("Tipo de curso inválido: " + tipo);
        };
    }
}
