package com.capgemini.hotelapi.service;


import java.util.List;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.model.Quarto;

public interface QuartoService {

    List<QuartoResponseDTO> getAll();

    QuartoResponseDTO findById(Long id);

    QuartoResponseDTO create(QuartoRequestDTO quarto);

    QuartoResponseDTO update(QuartoRequestDTO quarto, Long id);

    void delete(Long id);

    QuartoResponseDTO fromEntity(Quarto quarto);

    Quarto toEntity(QuartoRequestDTO dto);
}