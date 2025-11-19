package br.com.escolamusica.main;

import br.com.escolamusica.cursos.Curso;
import br.com.escolamusica.cursos.CursoFactory;

public class Main {
    public static void main(String[] args) {
        try {
            // Criando cursos diversos
            Curso curso1 = CursoFactory.criarCurso("Guitarra", "Guitarra Básico", 12, 20);
            Curso curso2 = CursoFactory.criarCurso("Piano", "Piano Adulto", 18, 12);
            Curso curso3 = CursoFactory.criarCurso("Bateria", "Bateria Rock", 10, 15);

            // Trabalhando com eles (polimorfismo)
            curso1.iniciarCurso();
            curso1.avaliar();
            curso1.encerrarCurso();

            System.out.println();

            curso2.iniciarCurso();
            curso2.avaliar();
            curso2.encerrarCurso();

            System.out.println();

            curso3.iniciarCurso();
            curso3.avaliar();
            curso3.encerrarCurso();

            System.out.println();

            // Exemplo de tratamento de erro
            Curso cursoErro = CursoFactory.criarCurso("violino", "Violino Introdutório", 16, 9);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}