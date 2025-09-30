package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva create(Reserva reserva) {
        // Regra de requisição: onde não pode check-out antes do check-in
        if (reserva.getCheckOut().isBefore(reserva.getCheckIn())) {
            throw new IllegalArgumentException("Primeiro você escolhe o check-in, depois o check-out");
        }

        //implementar o dto no package apos mergiar na develop (para nao criar duplicado)
        // Regra de requisição: calcular valor total automaticamente
        long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
        double valor = dias * quarto.getValorDiaria();
        reserva.setValorTotal(valor);

        return reservaRepository.save(reserva);
    }

    public Reserva update(Long id, Reserva reservaAtualizada) {
        return reservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setCheckIn(reservaAtualizada.getCheckIn());
                    reserva.setCheckOut(reservaAtualizada.getCheckOut());
                    reserva.setQuarto(reservaAtualizada.getQuarto());
                    reserva.setUser(reservaAtualizada.getUser());
                    reserva.setPropriedade(reservaAtualizada.getPropriedade());

                    // para recalcular o valor
                    long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
                    double valor = dias * quarto.getValorDiaria();
                    reserva.setValorTotal(valor);

                    return reservaRepository.save(reserva);
                }).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }
}
