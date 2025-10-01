package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PropriedadeRequestDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull TipoPropriedade tipo,
        @Valid @NotNull EnderecoRequestDTO endereco
) {}