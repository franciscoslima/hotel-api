package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.exceptions.InvalidRoomStatusException;
import com.capgemini.hotelapi.exceptions.ResourceNotFoundException;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.model.QuartoStatus;
import com.capgemini.hotelapi.repository.QuartoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuartoServiceImpl implements QuartoService {

    private final QuartoRepository quartoRepository;

    @Transactional(readOnly = true)
    public List<QuartoResponseDTO> getAll() {
        log.info("Listando todos os quartos");
        return quartoRepository.findAll().stream()
                .map(this::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuartoResponseDTO findById(Long id) {
        log.info("Buscando quarto por ID: {}", id);
        Quarto quarto = this.getEntityById(id);
        return fromEntity(quarto);
    }

    public QuartoResponseDTO create(QuartoRequestDTO dto) {
        log.info("Criando novo quarto");
        Quarto savedQuarto = quartoRepository.save(toEntity(dto));
        log.info("Quarto criado com sucesso. ID: {}", savedQuarto.getId());
        return fromEntity(savedQuarto);
    }

    public QuartoResponseDTO update(QuartoRequestDTO dto, Long id) {
        log.info("Atualizando quarto ID: {}", id);

        //verifica se quarto existe
        this.getEntityById(id);

        Quarto quarto = toEntity(dto);
        quarto.setId(id);
        quarto = quartoRepository.save(quarto);

        log.info("Quarto atualizado com sucesso. ID: {}", quarto.getId());
        return fromEntity(quarto);
    }

    public void delete(Long id) {
        log.info("Deletando quarto ID: {}", id);

        //verifica se quarto existe
        this.getEntityById(id);

        quartoRepository.deleteById(id);
        log.info("Quarto deletado com sucesso. ID: {}", id);
    }

    public QuartoResponseDTO reservarQuarto(Long id) {
        log.info("Reservando quarto ID: {}", id);

        //verifica se quarto existe
        Quarto quarto = this.getEntityById(id);

        if (quarto.getStatus() != QuartoStatus.DISPONIVEL) {
            throw new InvalidRoomStatusException("Quarto não está disponível para reserva.");
        }

        quarto.setStatus(QuartoStatus.RESERVADO);
        Quarto quartoReservado = quartoRepository.save(quarto);
        log.info("Quarto reservado com sucesso. ID: {}", quartoReservado.getId());
        return fromEntity(quartoReservado);
    }

    public QuartoResponseDTO checkin(Long id) {
        log.info("Realizando check-in do quarto ID: {}", id);
        Quarto quarto = this.getEntityById(id);

        if (quarto.getStatus() != QuartoStatus.RESERVADO) {
            throw new InvalidRoomStatusException("Este quarto não possui reservas.");
        }

        quarto.setStatus(QuartoStatus.OCUPADO);
        Quarto quartoOcupado = quartoRepository.save(quarto);
        log.info("Check-in realizado com sucesso. ID: {}", quartoOcupado.getId());
        return fromEntity(quartoOcupado);
    }

    public QuartoResponseDTO checkout(Long id) {
        log.info("Realizando check-out do quarto ID: {}", id);
        Quarto quarto = this.getEntityById(id);

        if (quarto.getStatus() != QuartoStatus.OCUPADO) {
            throw new InvalidRoomStatusException("Operação inválida.");
        }

        quarto.setStatus(QuartoStatus.MANUTENCAO);
        quarto = quartoRepository.save(quarto);
        log.info("Check-out realizado com sucesso. ID: {}", quarto.getId());
        return fromEntity(quarto);
    }

    public QuartoResponseDTO manutencao(Long id) {
        log.info("Marcando quarto para manutenção ID: {}", id);
        Quarto quarto = this.getEntityById(id);

        if (quarto.getStatus() == QuartoStatus.OCUPADO || quarto.getStatus() == QuartoStatus.RESERVADO) {
            throw new InvalidRoomStatusException("Operação inválida. Quarto está ocupado ou reservado.");
        }

        quarto.setStatus(QuartoStatus.MANUTENCAO);
        quarto = quartoRepository.save(quarto);
        log.info("Quarto marcado para manutenção com sucesso. ID: {}", quarto.getId());
        return fromEntity(quarto);
    }

    public QuartoResponseDTO cancelarResereva(Long id) {
        log.info("Cancelando reserva do quarto ID: {}", id);
        Quarto quarto = this.getEntityById(id);

        if (quarto.getStatus() != QuartoStatus.RESERVADO) {
            throw new InvalidRoomStatusException("Quarto não está reservado.");
        }

        quarto.setStatus(QuartoStatus.DISPONIVEL);
        quarto = quartoRepository.save(quarto);
        log.info("Reserva cancelada com sucesso. ID: {}", quarto.getId());
        return fromEntity(quarto);
    }

    private Quarto getEntityById(Long id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com ID: " + id));
    }

    private Quarto toEntity(QuartoRequestDTO dto) {
        Quarto quarto =  Quarto.builder()
                .numeracao(dto.numeracao())
                .descricao(dto.descricao())
                .status(dto.status())
                .valorDiaria(dto.valorDiaria())
                .build();

        if(dto.propriedadeId() != null) {
            Propriedade propriedade = new Propriedade();
            propriedade.setId(dto.propriedadeId());

            quarto.setPropriedade(propriedade);
        }

        return quarto;
    }

    private QuartoResponseDTO fromEntity(Quarto quarto) {
        if (quarto == null) {
            return null;
        }

        return new QuartoResponseDTO(
                quarto.getId(),
                quarto.getNumeracao(),
                quarto.getDescricao(),
                quarto.getStatus().name(),
                quarto.getPropriedade() != null ? quarto.getPropriedade().getId() : null,
                quarto.getReservas() != null ? quarto.getReservas().stream().map(reserva -> reserva.getId()).toList() : null,
                quarto.getValorDiaria().doubleValue()
        );
    }
}
