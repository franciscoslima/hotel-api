package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;

public record PropriedadeResponseDTO(
        long id,
        String nome,
        String descricao,
        TipoPropriedade tipo,
        EnderecoResponseDTO endereco
        // List<QuartoRequestDto> quartos
) {}