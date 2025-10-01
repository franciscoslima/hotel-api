package com.capgemini.hotelapi.service;


import java.util.List;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.model.Quarto;

public interface QuartoService {
    QuartoResponseDTO create(QuartoRequestDTO dto);
    List<QuartoResponseDTO> getAll();
    QuartoResponseDTO findById(Long id);
    QuartoResponseDTO update(Long id, QuartoRequestDTO dto);
    void delete(Long id);
}