package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.TipoPropriedade;

import java.util.List;

public record PropriedadeResponseDTO(
        long id,
        String nome,
        String descricao,
        TipoPropriedade tipo,
        EnderecoResponseDTO endereco,
        List<QuartoResponseDTO> quartos
) {}