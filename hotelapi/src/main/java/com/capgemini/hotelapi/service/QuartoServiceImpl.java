package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;
import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.exceptions.InvalidRoomStatusException;
import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.model.QuartoStatus;
import com.capgemini.hotelapi.repository.QuartoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuartoServiceImpl implements QuartoService {

    private final QuartoRepository quartoRepository;
    private final PropriedadeService propriedadeService;

    @Transactional(readOnly = true)
    public List<QuartoResponseDTO> getAll() {
        log.info("Listando todos os quartos");
        return quartoRepository.findAll().stream().map(this::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public QuartoResponseDTO findById(Long id) {
        log.info("Buscando quarto por ID: {}", id);
        return quartoRepository.findById(id).map(this::fromEntity)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com ID: " + id));
    }

    public QuartoResponseDTO create(QuartoRequestDTO quarto) {
        PropriedadeResponseDTO propriedade = propriedadeService.getPropriedadeById(quarto.propriedadeId());
        log.info("Criando novo quarto");

        Quarto quartoToSave = toEntity(quarto);
        quartoToSave.setPropriedade(propriedadeService.fromResponseDTO(propriedade));

        Quarto savedQuarto = quartoRepository.save(quartoToSave);
        log.info("Quarto criado com sucesso. ID: {}", savedQuarto.getId());
        return fromEntity(savedQuarto);
    }

    public QuartoResponseDTO update(QuartoRequestDTO quarto, Long id) {
        log.info("Atualizando quarto ID: {}", id);
        Optional<Quarto> existingQuarto = quartoRepository.findById(id);

        if (existingQuarto.isEmpty()) {
            throw new RuntimeException("Quarto não encontrado com ID: " + id);
        }
        existingQuarto.get().setNumeracao(quarto.numeracao());
        existingQuarto.get().setDescricao(quarto.descricao());
        existingQuarto.get().setStatus(quarto.status());
        existingQuarto.get().setValorDiaria(quarto.valorDiaria());

        Quarto updatedQuarto = quartoRepository.save(existingQuarto.get());
        log.info("Quarto atualizado com sucesso. ID: {}", updatedQuarto.getId());
        return fromEntity(updatedQuarto);
    }

    public void delete(Long id) {
        log.info("Deletando quarto ID: {}", id);

        this.findById(id);

        quartoRepository.deleteById(id);
        log.info("Quarto deletado com sucesso. ID: {}", id);
    }

    public QuartoResponseDTO fromEntity(Quarto quarto) {
        return new QuartoResponseDTO(
                quarto.getId(),
                quarto.getNumeracao(),
                quarto.getDescricao(),
                quarto.getValorDiaria(),
                quarto.getStatus(),
                quarto.getPropriedade().getNome()
        );
    }

    public Quarto toEntity(QuartoRequestDTO dto) {
        return new Quarto(
                null,
                dto.numeracao(),
                dto.descricao(),
                dto.status(),
                null,
                dto.valorDiaria()
        );
    }
}
