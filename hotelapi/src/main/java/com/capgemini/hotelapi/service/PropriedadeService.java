package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;
import com.capgemini.hotelapi.model.Propriedade;

import java.util.List;

public interface PropriedadeService {

    List<PropriedadeResponseDTO> getAllPropriedades();

    PropriedadeResponseDTO getPropriedadeById(Long id);

    PropriedadeResponseDTO createPropriedade(PropriedadeRequestDTO dto);

    PropriedadeResponseDTO updatePropriedade(Long id, PropriedadeRequestDTO dto);

    void deletePropriedade(Long id);

    PropriedadeResponseDTO fromEntity(Propriedade propriedade);

    Propriedade toEntity(PropriedadeRequestDTO dto);

    Propriedade fromResponseDTO(PropriedadeResponseDTO propriedade);
}
