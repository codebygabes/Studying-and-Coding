package br.com.escolamusica.cursos;

import br.com.escolamusica.interfaces.IGestaoCurso;

public abstract class Curso implements IGestaoCurso {
        private String nome;
        private int duracao;
        private int numAlunos;

        public Curso(String nome, int duracao, int numAlunos) {
            this.nome = nome;
            this.duracao = duracao;
            this.numAlunos = numAlunos;
        }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public int getDuracao() { return duracao; }
        public void setDuracao(int duracao) { this.duracao = duracao; }

        public int getNumAlunos() { return numAlunos; }
        public void setNumAlunos(int numAlunos) { this.numAlunos = numAlunos; }

        @Override
        public void iniciarCurso() {
            System.out.println("Curso '" + nome + "' iniciado.");
        }

        @Override
        public void encerrarCurso() {
            System.out.println("Curso '" + nome + "' encerrado.");
        }

        // Método abstrato polimórfico
        public abstract void avaliar();
    }
