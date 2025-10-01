package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.QuartoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuartoRequestDTO(
        @NotNull int numeracao,
        @NotBlank @Size(max = 120) String descricao,
        @NotNull Long propriedadeId,
        @NotNull Double valorDiaria,
        @NotNull QuartoStatus status
) {}
