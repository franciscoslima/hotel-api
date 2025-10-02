package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.QuartoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuartoRequestDTO(

        @Schema(description = "Numeração do quarto", example = "101")
        @NotNull(message = "Numeração é obrigatória") int numeracao,

        @Schema(description = "Descrição do Quarto", example = "Quarto com vista para o mar")
        @NotBlank(message = "Descrição é obrigatória") String descricao,

        @Schema(description = "Valor da diária", example = "500")
        @NotNull(message = "Valor da diária é obrigatória") Double valorDiaria,

        @Schema(description = "Propriedade do quarto", example = "1")
        @NotNull(message = "Propriedade é obrigatória") Long propriedadeId,

        @Schema(description = "Status do quarto", example = "OCUPADO")
        QuartoStatus status
) {}
