package com.capgemini.hotelapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
        @NotBlank(message = "Rua é obrigatória") String rua,
        @NotBlank(message = "Bairro é obrigatório") String bairro,
        @NotBlank(message = "Cidade é obrigatória") String cidade,
        @NotBlank(message = "Estado é obrigatório") String estado
) {}