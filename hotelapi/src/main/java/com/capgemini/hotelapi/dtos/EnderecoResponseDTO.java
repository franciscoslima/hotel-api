package com.capgemini.hotelapi.dtos;

public record EnderecoResponseDTO(
        String rua,
        String bairro,
        String cidade,
        String estado
) {}