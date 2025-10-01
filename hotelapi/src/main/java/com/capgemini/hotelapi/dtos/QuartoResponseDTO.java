package com.capgemini.hotelapi.dtos;

import java.util.List;

public record QuartoResponseDTO(
        Long id,
        int numeracao,
        String descricao,
        String status,
        Long propriedadeId,
        List<Long> reservaIds,
        Double valorDiaria
) {}
