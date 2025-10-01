package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.model.QuartoStatusEnum;
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
    public List<Quarto> getAll() {
        log.info("Listando todos os quartos");
        return quartoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Quarto findById(Long id) {
        log.info("Buscando quarto por ID: {}", id);
        return quartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + id));
    }

    public Quarto create(Quarto quarto) {
        log.info("Criando novo quarto");
        Quarto savedQuarto = quartoRepository.save(quarto);
        log.info("Quarto criado com sucesso. ID: {}", savedQuarto.getId());
        return savedQuarto;
    }

    public Quarto update(Quarto quarto, Long id) {
        log.info("Atualizando quarto ID: {}", id);
        Quarto existingQuarto = this.findById(id);
        quarto.setId(id);
        Quarto updatedQuarto = quartoRepository.save(quarto);
        log.info("Quarto atualizado com sucesso. ID: {}", updatedQuarto.getId());
        return updatedQuarto;
    }

    public void delete(Long id) {
        log.info("Deletando quarto ID: {}", id);
        if (!quartoRepository.existsById(id)) {
            throw new RuntimeException("Quarto não encontrado com ID: " + id);
        }
        quartoRepository.deleteById(id);
        log.info("Quarto deletado com sucesso. ID: {}", id);
    }

    public Quarto reservarQuarto(Long id) {
        log.info("Reservando quarto ID: {}", id);
        Quarto quarto = this.findById(id);

        if (quarto.getStatus() != QuartoStatusEnum.DISPONIVEL) {
            throw new RuntimeException("Quarto não está disponível para reserva.");
        }

        quarto.setStatus(QuartoStatusEnum.RESERVADO);
        Quarto quartoReservado = quartoRepository.save(quarto);
        log.info("Quarto reservado com sucesso. ID: {}", quartoReservado.getId());
        return quartoReservado;
    }

    public Quarto checkin(Long id) {
        log.info("Realizando check-in do quarto ID: {}", id);
        Quarto quarto = this.findById(id);

        if (quarto.getStatus() != QuartoStatusEnum.RESERVADO) {
            throw new RuntimeException("Este quarto não possui reservas.");
        }

        quarto.setStatus(QuartoStatusEnum.OCUPADO);
        Quarto quartoOcupado = quartoRepository.save(quarto);
        log.info("Check-in realizado com sucesso. ID: {}", quartoOcupado.getId());
        return quartoOcupado;
    }

    public Quarto checkout(Long id) {
        log.info("Realizando check-out do quarto ID: {}", id);
        Quarto quarto = this.findById(id);

        if (quarto.getStatus() != QuartoStatusEnum.OCUPADO) {
            throw new RuntimeException("Operação inválida.");
        }

        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        Quarto quartoManutencao = quartoRepository.save(quarto);
        log.info("Check-out realizado com sucesso. ID: {}", quartoManutencao.getId());
        return quartoManutencao;
    }

    public Quarto manutencao(Long id) {
        log.info("Marcando quarto para manutenção ID: {}", id);
        Quarto quarto = this.findById(id);

        if (quarto.getStatus() == QuartoStatusEnum.OCUPADO || quarto.getStatus() == QuartoStatusEnum.RESERVADO) {
            throw new RuntimeException("Operação inválida. Quarto está ocupado ou reservado.");
        }

        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        Quarto quartoManutencao = quartoRepository.save(quarto);
        log.info("Quarto marcado para manutenção com sucesso. ID: {}", quartoManutencao.getId());
        return quartoManutencao;
    }

    public Quarto cancelarResereva(Long id) {
        log.info("Cancelando reserva do quarto ID: {}", id);
        Quarto quarto = this.findById(id);

        if (quarto.getStatus() != QuartoStatusEnum.RESERVADO) {
            throw new RuntimeException("Quarto não está reservado.");
        }

        quarto.setStatus(QuartoStatusEnum.DISPONIVEL);
        Quarto quartoDisponivel = quartoRepository.save(quarto);
        log.info("Reserva cancelada com sucesso. ID: {}", quartoDisponivel.getId());
        return quartoDisponivel;
    }
}
