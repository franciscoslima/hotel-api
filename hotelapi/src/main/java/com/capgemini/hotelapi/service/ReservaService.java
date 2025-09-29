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

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva criarReserva(Reserva reserva) {
        // Regra de requisição: onde não pode check-out antes do check-in
        if (reserva.getCheckOut().isBefore(reserva.getCheckIn())) {
            throw new IllegalArgumentException("Primeiro você escolhe o check-in, depois o check-out");
        }

        // Regra de requisição: calcular valor total automaticamente (exemplo: 200 por dia)
        long dias = ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut());
        double valor = dias * 200.0;
        reserva.setValorTotal(valor);

        return reservaRepository.save(reserva);
    }

    // Atualizar reserva
    public Reserva atualizarReserva(Long id, Reserva reservaAtualizada) {
        return reservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setCheckIn(reservaAtualizada.getCheckIn());
                    reserva.setCheckOut(reservaAtualizada.getCheckOut());
//                    reserva.setQuarto(reservaAtualizada.getQuarto());
//                    reserva.setHospede(reservaAtualizada.getHospede());
//                    reserva.setPropriedade(reservaAtualizada.getPropriedade());

                    // para recalcular o valor
                    long dias = ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut());
                    reserva.setValorTotal(dias * 200.0);

                    return reservaRepository.save(reserva);
                }).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    // Deletar reserva
    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}
