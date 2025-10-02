package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PropriedadeRequestDTO(
        @Schema(description = "Nome da propriedade", example = "Hotel Copacabana")
        @NotBlank String nome,

        @Schema(description = "Descrição da propriedade", example = "Hotel 5 estrelas à beira-mar")
        @NotBlank String descricao,

        @Schema(description = "Tipo da propriedade", example = "HOTEL")
        @NotNull TipoPropriedade tipo,

        @Schema(description = "Endereço da propriedade")
        @Valid @NotNull EnderecoRequestDTO endereco
) {}