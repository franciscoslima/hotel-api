package com.capgemini.hotelapi.service;


import java.util.List;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;

public interface QuartoService {

    List<QuartoResponseDTO> getAll();

    QuartoResponseDTO findById(Long id);

    QuartoResponseDTO create(QuartoRequestDTO quarto);

    QuartoResponseDTO update(QuartoRequestDTO quarto, Long id);

    void delete(Long id);

    QuartoResponseDTO reservarQuarto(Long id);

    QuartoResponseDTO checkin(Long id);

    QuartoResponseDTO checkout(Long id);

    QuartoResponseDTO manutencao(Long id);

    QuartoResponseDTO cancelarResereva(Long id);
}