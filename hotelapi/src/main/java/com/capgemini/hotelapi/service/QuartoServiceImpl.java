package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.exceptions.ResourceNotFoundException;
import com.capgemini.hotelapi.mapper.QuartoMapper;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.repository.PropriedadeRepository;
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
    private final PropriedadeRepository propriedadeRepository; // Injetado diretamente
    private final QuartoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<QuartoResponseDTO> getAll() {
        log.info("Listando todos os quartos");
        return quartoRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QuartoResponseDTO findById(Long id) {
        log.info("Buscando quarto por ID: {}", id);
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto", id));
        return mapper.toResponseDTO(quarto);
    }

    @Override
    public QuartoResponseDTO create(QuartoRequestDTO dto) {
        log.info("Criando novo quarto para Propriedade ID: {}", dto.propriedadeId());

        Propriedade propriedade = propriedadeRepository.findById(dto.propriedadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade", dto.propriedadeId()));

        Quarto quartoToSave = mapper.toEntity(dto);

        propriedade.adicionarQuarto(quartoToSave);

        Quarto savedQuarto = quartoRepository.save(quartoToSave);

        log.info("Quarto criado com sucesso. ID: {}", savedQuarto.getId());
        return mapper.toResponseDTO(savedQuarto);
    }

    @Override
    public QuartoResponseDTO update(Long id, QuartoRequestDTO dto) {
        log.info("Atualizando quarto ID: {}", id);
        Quarto existingQuarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto", id));

        existingQuarto.setNumeracao(dto.numeracao());
        existingQuarto.setDescricao(dto.descricao());
        existingQuarto.setStatus(dto.status());
        existingQuarto.setValorDiaria(dto.valorDiaria());

        Quarto updatedQuarto = quartoRepository.save(existingQuarto);
        log.info("Quarto atualizado com sucesso. ID: {}", updatedQuarto.getId());
        return mapper.toResponseDTO(updatedQuarto);
    }

    @Override
    public void delete(Long id) {
        log.info("Deletando quarto ID: {}", id);
        if (!quartoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quarto", id);
        }
        quartoRepository.deleteById(id);
        log.info("Quarto deletado com sucesso. ID: {}", id);
    }
}
