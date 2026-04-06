package br.spring.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="livros")
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "usuarioId", length = 100, nullable = false)
    private int usuarioId;
	
	@Column(name = "livroId", length = 100, nullable = false)
    private int livroId;
    
    @Column(name = "dataEmprestimo", nullable = false)
    private LocalDate dataEmprestimo;
    
    @Column(name = "dataDevolucaoPrevista", nullable = false)
    private LocalDate dataDevolucaoPrevista;
    
    @Column(name = "dataDevoluçaoReal", nullable = false)
    private LocalDate dataDevoluçaoReal;
    
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    public Emprestimo() {
    }

    public Emprestimo(int id, int usuarioId, int livroId, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevoluçaoReal() {
        return dataDevoluçaoReal;
    }

    public void setDataDevoluçaoReal(LocalDate dataDevoluçaoReal) {
        this.dataDevoluçaoReal = dataDevoluçaoReal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", livroId=" + livroId +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevoluçaoReal=" + dataDevoluçaoReal +
                ", ativo=" + ativo +
                '}';
    }
}
