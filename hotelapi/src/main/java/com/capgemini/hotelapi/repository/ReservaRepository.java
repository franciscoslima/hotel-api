package com.capgemini.hotelapi.repository;

import com.capgemini.hotelapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
}
