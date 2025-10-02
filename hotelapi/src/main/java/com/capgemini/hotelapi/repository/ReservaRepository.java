package com.capgemini.hotelapi.repository;

import com.capgemini.hotelapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUserId(Long userId);
    List<Reserva> findByQuartoId(Long quartoId);
}
