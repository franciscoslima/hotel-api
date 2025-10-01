package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PropriedadeRequestDTO(

        @Schema(description = "Nome da propriedade", example = "Hotel Copacabana Palace")
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @Schema(description = "Descrição da propriedade", example = "Hotel de luxo à beira-mar com piscina e spa.")
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @Schema(description = "Tipo da propriedade", example = "HOTEL")
        @NotNull(message = "O tipo é obrigatório")
        TipoPropriedade tipo,

        @Schema(description = "Endereço da propriedade", example = "Rua da Paz, 321, Centro, Recife, PE")
        @NotNull(message = "O endereço é obrigatório")
        EnderecoRequestDTO endereco
) {}