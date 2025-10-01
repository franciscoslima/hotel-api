package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.ReservaRequestDTO;
import com.capgemini.hotelapi.dtos.ReservaResponseDTO;
import com.capgemini.hotelapi.dtos.ReservaUpdateDTO;
import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.model.QuartoStatusEnum;
import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.model.ReservaStatus;
import com.capgemini.hotelapi.model.User;
import com.capgemini.hotelapi.repository.QuartoRepository;
import com.capgemini.hotelapi.repository.ReservaRepository;
import com.capgemini.hotelapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UserRepository userRepository;
    private final QuartoRepository quartoRepository;

    @Override
    public ReservaResponseDTO create(ReservaRequestDTO dto) {
        log.info("Criando nova reserva para usuário ID: {} e quarto ID: {}", dto.userId(), dto.quartoId());
        
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.userId()));

        Quarto quarto = quartoRepository.findById(dto.quartoId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + dto.quartoId()));

        if (quarto.getStatus() != QuartoStatusEnum.DISPONIVEL) {
            throw new RuntimeException("Quarto não está disponível para reserva.");
        }

        long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
        if (dias <= 0) {
            throw new RuntimeException("Datas inválidas para reserva.");
        }

        double valorTotal = dias * 100.0; // TODO: Implementar campo valorDiaria na entidade Quarto

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
        log.info("Atualizando reserva ID: {}", id);
        Reserva reserva = findReserva(id);

        long dias = ChronoUnit.DAYS.between(dto.checkIn(), dto.checkOut());
        if (dias <= 0) {
            throw new RuntimeException("Datas inválidas para reserva.");
        }

        // pra recalcular precisa juliana add o valor da diaria
        double novoValorTotal = dias * 100.0; // TODO: Implementar campo valorDiaria na entidade Quarto

        reserva.setCheckIn(dto.checkIn());
        reserva.setCheckOut(dto.checkOut());
        reserva.setValorTotal(novoValorTotal);

        return toResponse(reservaRepository.save(reserva));
    }

    //Discutir se poderia ter essas confirmações de reserva - nao foi incluido no trade off
    @Override
    public ReservaResponseDTO confirmar(Long id) {
        log.info("Confirmando reserva ID: {}", id);
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.CONFIRMADA);
        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO cancelar(Long id) {
        log.info("Cancelando reserva ID: {}", id);
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.CANCELADA);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(QuartoStatusEnum.DISPONIVEL);
        quartoRepository.save(quarto);

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponseDTO finalizar(Long id) {
        log.info("Finalizando reserva ID: {}", id);
        Reserva reserva = findReserva(id);
        reserva.setStatus(ReservaStatus.FINALIZADA);

        Quarto quarto = reserva.getQuarto();
        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        quartoRepository.save(quarto);

        return toResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarPorId(Long id) {
        log.info("Buscando reserva por ID: {}", id);
        return toResponse(findReserva(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarTodas() {
        log.info("Listando todas as reservas");
        return reservaRepository.findAll().stream()
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
