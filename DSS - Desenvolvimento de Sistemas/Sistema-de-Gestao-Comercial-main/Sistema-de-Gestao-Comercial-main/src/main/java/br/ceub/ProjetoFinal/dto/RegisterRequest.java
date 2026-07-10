package br.ceub.ProjetoFinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de um novo usuário")
public record RegisterRequest(
        @Schema(description = "Nome completo do usuário", example = "Fulano de Tal") String nome,
        @Schema(description = "E-mail para login", example = "usuario@exemplo.com") String email,
        @Schema(description = "Senha do usuário", example = "senhaSegura123") String password
) {
}
