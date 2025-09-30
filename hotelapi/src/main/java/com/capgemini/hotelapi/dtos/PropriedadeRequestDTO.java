package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;

public record PropriedadeRequestDTO(
        String nome,
        String descricao,
        TipoPropriedade tipo,
        EnderecoRequestDTO endereco
        // List<QuartoRequestDto> quartos
) {}