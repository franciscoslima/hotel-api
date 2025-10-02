package com.capgemini.hotelapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequestDTO(
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email
) {}
