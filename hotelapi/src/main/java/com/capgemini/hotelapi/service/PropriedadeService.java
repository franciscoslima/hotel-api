package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;

import java.util.List;

public interface PropriedadeService {
    PropriedadeResponseDTO createPropriedade(PropriedadeRequestDTO dto);
    List<PropriedadeResponseDTO> getAllPropriedades();
    PropriedadeResponseDTO getPropriedadeById(Long id);
    PropriedadeResponseDTO updatePropriedade(Long id, PropriedadeRequestDTO dto);
    void deletePropriedade(Long id);
}
