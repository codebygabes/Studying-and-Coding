package br.ceub.ProjetoFinal.dto;

public record ClientUpdate(
        String nome,
        String email,
        String cpf,
        String telefone,
        String endereco
) {
}
