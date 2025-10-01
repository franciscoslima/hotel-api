package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.QuartoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuartoRequestDTO(
        @Schema(description = "Número do quarto", example = "101")
        int numeracao,

        @Schema(description = "Descrição do quarto", example = "Quarto Suíte com vista para o mar")
        @NotBlank(message = "Descrição é obrigatório")
        @Size(max = 120)
        String descricao,

        @Schema(description = "Status do quarto", example = "DISPONIVEL")
        @NotNull(message = "Status é obrigatório")
        QuartoStatus status,

        @Schema(description = "Propriedade associada ao quarto")
        @NotNull(message = "Propriedade é obrigatório")
        long propriedadeId,

        @Schema(description = "Valor da diária do quarto")
        @NotNull(message = "Valor da diária é obrigatório")
        Double valorDiaria
) {}
