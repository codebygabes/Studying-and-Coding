package br.ceub.ProjetoFinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais para obtenção do JWT")
public record LoginRequest(
        @Schema(description = "E-mail cadastrado", example = "usuario@exemplo.com") String email,
        @Schema(description = "Senha em texto claro (somente na requisição)", example = "senhaSegura123") String password
) {
}
