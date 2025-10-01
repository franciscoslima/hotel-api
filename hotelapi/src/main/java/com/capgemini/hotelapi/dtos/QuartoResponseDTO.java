package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.QuartoStatus;

public record QuartoResponseDTO(
        Long id,
        int numeracao,
        String descricao,
        Double valorDiaria,
        QuartoStatus status,
        String propriedade
) {
}
