package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.ReservaStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaUpdateDTO(

        LocalDate checkIn,

        LocalDate checkOut,

        @Schema(description = "Status do quarto", example = "CONFIRMADA")
        ReservaStatus status
) {}
