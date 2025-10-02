package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.ReservaStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        Long userId,
        Long quartoId,
        LocalDate checkIn,
        LocalDate checkOut,
        ReservaStatus status,
        Double valorTotal,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}
