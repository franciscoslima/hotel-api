package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.ReservaRequestDTO;
import com.capgemini.hotelapi.dtos.ReservaResponseDTO;
import com.capgemini.hotelapi.dtos.ReservaUpdateDTO;

import java.util.List;

public interface ReservaService {

        ReservaResponseDTO create(ReservaRequestDTO dto);
        ReservaResponseDTO confirmar(Long id);
        ReservaResponseDTO cancelar(Long id);
        ReservaResponseDTO finalizar(Long id);
        ReservaResponseDTO buscarPorId(Long id);
        List<ReservaResponseDTO> listarTodas();
        ReservaResponseDTO update(Long id, ReservaUpdateDTO dto);

}
