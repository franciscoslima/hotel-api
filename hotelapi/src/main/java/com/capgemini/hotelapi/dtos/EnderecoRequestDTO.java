package com.capgemini.hotelapi.dtos;

public record EnderecoRequestDTO(
        String rua,
        String bairro,
        String cidade,
        String estado
) {}