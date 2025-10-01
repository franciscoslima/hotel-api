package com.capgemini.hotelapi.repository;

import com.capgemini.hotelapi.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByPropriedadeId(Long propriedadeId);

    @Query("SELECT q FROM Quarto q WHERE q.id NOT IN (SELECT r.quarto.id FROM Reserva r WHERE (:checkIn < r.checkOut AND :checkOut > r.checkIn))")
    List<Quarto> findQuartosDisponiveis(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);
}
