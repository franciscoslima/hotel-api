package com.capgemini.hotelapi.service;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaUpdateDTO(
        @NotNull
        LocalDate checkIn,
        @NotNull
        LocalDate checkOut

        //Long quartoId se quiser passar o Long (que seria o numero da estadia)
) {}
