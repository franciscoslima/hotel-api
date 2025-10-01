package com.capgemini.hotelapi.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaRequestDTO(

        @NotNull(message = "O ID do usuário não pode ser nulo") Long userId,
        @NotNull(message = "O ID do quarto não pode ser nulo") Long quartoId,
        @NotNull(message = "A data de check-in é obrigatória") LocalDate checkIn,
        @NotNull(message = "A data de check-out é obrigatória") LocalDate checkOut) {
}
