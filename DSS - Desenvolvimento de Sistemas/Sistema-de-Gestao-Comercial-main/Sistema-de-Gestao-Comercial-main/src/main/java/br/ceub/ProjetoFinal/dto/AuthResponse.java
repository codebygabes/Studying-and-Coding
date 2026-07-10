package br.ceub.ProjetoFinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token JWT para uso no cabeçalho Authorization: Bearer <token>")
public record AuthResponse(
        @Schema(description = "JWT assinado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String token,
        @Schema(description = "Tipo do esquema HTTP", example = "Bearer") String tokenType
) {
    public AuthResponse(String token) {
        this(token, "Bearer");
    }
}
