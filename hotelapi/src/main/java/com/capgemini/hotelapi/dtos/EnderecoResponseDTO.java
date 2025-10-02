package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.Endereco;

public record EnderecoResponseDTO(
        String rua,
        String bairro,
        String cidade,
        String estado
) {
    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }
}