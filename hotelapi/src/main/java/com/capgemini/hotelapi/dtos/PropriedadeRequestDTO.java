package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PropriedadeRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @NotNull(message = "O tipo é obrigatório")
        TipoPropriedade tipo,
        @NotNull(message = "O endereço é obrigatório")
        EnderecoRequestDTO endereco
) {}