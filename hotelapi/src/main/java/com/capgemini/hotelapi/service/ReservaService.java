package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public interface ReservaService {

        ReservaResponseDTO create(ReservaRequestDTO dto);
        ReservaResponseDTO confirmar(Long id);
        ReservaResponseDTO cancelar(Long id);
        ReservaResponseDTO finalizar(Long id);
        ReservaResponseDTO buscarPorId(Long id);
        List<ReservaResponseDTO> listarTodas();
        ReservaResponseDTO update(Long id, ReservaUpdateDTO dto);

}
