package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.QuartoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuartoRequestDTO(

        @Schema(description = "Numeração do quarto", example = "")
        @NotBlank(message = "A numeração é obrigatória")
        @Min(value = 1, message = "A numeração deve ser maior ou igual a 1")
        int numeracao,

        @Schema(description = "Descrição do quarto", example = "Quarto Suite Master com vista para o mar")
        @NotBlank(message = "A numeração é obrigatória")
        @Size(max = 120)
        String descricao,

        @Schema(description = "Status do quarto", example = "DISPONIVEL")
        @NotNull(message = "O status é obrigatório")
        QuartoStatus status,

        @Schema(description = "ID da propriedade associada ao quarto", example = "1")
        @NotNull(message = "O ID da propriedade é obrigatório")
        Long propriedadeId,

        @Schema(description = "Valor da diária do quarto", example = "250.00")
        @Min(value = 0, message = "O valor da diária deve ser maior ou igual a zero")
        @NotNull Double valorDiaria
) {}
