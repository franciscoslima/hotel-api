package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;

import com.capgemini.hotelapi.exceptions.ResourceNotFoundException;
import com.capgemini.hotelapi.mapper.PropriedadeMapper;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PropriedadeServiceImpl implements PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;
    private final PropriedadeMapper mapper; // Injetado

    @Override
    @Transactional(readOnly = true)
    public List<PropriedadeResponseDTO> getAllPropriedades() {
        log.info("Listando todas as propriedades");
        return propriedadeRepository.findAllByOrderByNomeAsc().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PropriedadeResponseDTO getPropriedadeById(Long id) {
        log.info("Buscando propriedade por ID: {}", id);
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade", id));
        return mapper.toResponseDTO(propriedade);
    }

    @Override
    public PropriedadeResponseDTO createPropriedade(PropriedadeRequestDTO dto) {
        log.info("Criando nova propriedade: {}", dto.nome());
        Propriedade novaPropriedade = mapper.toEntity(dto);
        Propriedade savedPropriedade = propriedadeRepository.save(novaPropriedade);
        log.info("Propriedade criada com sucesso. ID: {}", savedPropriedade.getId());
        return mapper.toResponseDTO(savedPropriedade);
    }

    @Override
    public PropriedadeResponseDTO updatePropriedade(Long id, PropriedadeRequestDTO dto) {
        log.info("Atualizando propriedade ID: {}", id);
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade", id));

        propriedade.setNome(dto.nome());
        propriedade.setDescricao(dto.descricao());
        propriedade.setTipo(dto.tipo());

        if (dto.endereco() != null) {
            propriedade.getEndereco().setRua(dto.endereco().rua());
            propriedade.getEndereco().setBairro(dto.endereco().bairro());
            propriedade.getEndereco().setCidade(dto.endereco().cidade());
            propriedade.getEndereco().setEstado(dto.endereco().estado());
        }

        log.info("Propriedade atualizada com sucesso. ID: {}", id);
        return mapper.toResponseDTO(propriedade);
    }

    @Override
    public void deletePropriedade(Long id) {
        log.info("Deletando propriedade ID: {}", id);
        if (!propriedadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Propriedade", id);
        }
        propriedadeRepository.deleteById(id);
        log.info("Propriedade deletada com sucesso. ID: {}", id);
    }
}