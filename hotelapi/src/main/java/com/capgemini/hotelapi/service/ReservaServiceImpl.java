package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.model.ReservaStatus;
import com.capgemini.hotelapi.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UserRepository userRepository;
    private final QuartoRepository quartoRepository;

    @Override
    public ReservaResponseDTO create(ReservaRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Quarto quarto = quartoRepository.findById(dto.quartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

        if (quarto.getStatus() != QuartoStatusEnum.DISPONIVEL) {
            throw new RuntimeException("Quarto não está disponível para reserva.");
        }

        long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
        if (dias <= 0) {
            throw new RuntimeException("Datas inválidas para reserva.");
        }

        double valorTotal = dias * quarto.getValorDiaria();

        Reserva reserva = Reserva.builder()
                .user(user)
                .quarto(quarto)
                .checkIn(dto.checkIn())
                .checkOut(dto.checkOut())
                .status(ReservaStatus.PENDENTE)
                .valorTotal(valorTotal)
                .build();

        quarto.setStatus(QuartoStatusEnum.RESERVADO);
        quartoRepository.save(quarto);

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO update(Long id, ReservaUpdateDTO dto) {
        Reserva reserva = findReserva(id);

        long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
        if (dias <= 0) {
            throw new RuntimeException("Datas inválidas para reserva.");
        }

        // pra recalcular precisa juliana add o valor da diaria
        double novoValorTotal = dias * reserva.getQuarto().getValorDiaria();

        reserva.setCheckIn(dto.checkIn());
        reserva.setCheckOut(dto.checkOut());
        reserva.setValorTotal(novoValorTotal);

        return toResponse(reservaRepository.save(reserva));
    }

    //Discutir se poderia ter essas confirmações de reserva - nao foi incluido no trade off
    @Override
    public ReservaResponseDTO confirmar(Long id) {
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.CONFIRMADA);
        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO cancelar(Long id) {
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.CANCELADA);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(QuartoStatusEnum.DISPONIVEL);
        quartoRepository.save(quarto);

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO finalizar(Long id) {
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.FINALIZADA);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        quartoRepository.save(quarto);

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO buscarPorId(Long id) {
        return toResponse(findReserva(id));
    }

    @Override
    public List<ReservaResponseDTO> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Reserva findReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
    }

    private ReservaResponseDTO toResponse(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getUser().getId(),
                reserva.getQuarto().getId(),
                reserva.getCheckIn(),
                reserva.getCheckOut(),
                reserva.getStatus(),
                reserva.getValorTotal(),
                reserva.getDataCriacao(),
                reserva.getDataAtualizacao()
        );
    }
}
