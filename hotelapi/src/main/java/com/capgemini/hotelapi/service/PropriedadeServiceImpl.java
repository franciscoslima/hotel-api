package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.EnderecoResponseDTO;
import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;

import com.capgemini.hotelapi.model.Endereco;
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
    private final QuartoService quartoService;

    @Transactional(readOnly = true)
    public List<PropriedadeResponseDTO> getAllPropriedades() {
        log.info("Listando todas as propriedades");
        return propriedadeRepository.findAllByOrderByNomeAsc().stream().map(this::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public PropriedadeResponseDTO getPropriedadeById(Long id) {
        log.info("Buscando propriedade por ID: {}", id);
        return propriedadeRepository.findById(id).map(this::fromEntity)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada com ID: " + id));
    }

    public PropriedadeResponseDTO createPropriedade(PropriedadeRequestDTO dto) {
        log.info("Criando nova propriedade: {}", dto.nome());
        Propriedade novaPropriedade = toEntity(dto);
        Propriedade savedPropriedade = propriedadeRepository.save(novaPropriedade);
        log.info("Propriedade criada com sucesso. ID: {}", savedPropriedade.getId());
        return fromEntity(savedPropriedade);
    }

    public PropriedadeResponseDTO updatePropriedade(Long id, PropriedadeRequestDTO dto) {
        log.info("Atualizando propriedade ID: {}", id);
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada com ID: " + id));

        propriedade.setNome(dto.nome());
        propriedade.setDescricao(dto.descricao());
        propriedade.setTipo(dto.tipo());

        if (dto.endereco() != null) {
            propriedade.getEndereco().setRua(dto.endereco().rua());
            propriedade.getEndereco().setBairro(dto.endereco().bairro());
            propriedade.getEndereco().setCidade(dto.endereco().cidade());
            propriedade.getEndereco().setEstado(dto.endereco().estado());
        }

        PropriedadeResponseDTO propriedadeResponseDTO = fromEntity(propriedade);

        log.info("Propriedade atualizada com sucesso. ID: {}", propriedadeResponseDTO.id());
        return propriedadeResponseDTO;
    }

    public void deletePropriedade(Long id) {
        log.info("Deletando propriedade ID: {}", id);
        if (!propriedadeRepository.existsById(id)) {
            throw new RuntimeException("Propriedade não encontrada com ID: " + id);
        }
        propriedadeRepository.deleteById(id);
        log.info("Propriedade deletada com sucesso. ID: {}", id);
    }

    public Propriedade toEntity(PropriedadeRequestDTO dto) {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome(dto.nome());
        propriedade.setDescricao(dto.descricao());
        propriedade.setTipo(dto.tipo());

        if (dto.endereco() != null) {
            Endereco endereco = new Endereco(
                    dto.endereco().rua(),
                    dto.endereco().bairro(),
                    dto.endereco().cidade(),
                    dto.endereco().estado()
            );
            propriedade.setEndereco(endereco);
        }

        return propriedade;
    }

    @Override
    public Propriedade fromResponseDTO(PropriedadeResponseDTO propriedade) {
        if (propriedade == null) {
            return null;
        }
        return new PropriedadeResponseDTO(
                propriedade.id(),
                propriedade.nome(),
                propriedade.descricao(),
                propriedade.tipo(),
                propriedade.endereco() != null ? new EnderecoResponseDTO(
                        propriedade.endereco().rua(),
                        propriedade.endereco().bairro(),
                        propriedade.endereco().cidade(),
                        propriedade.endereco().estado()
                ) : null,
                propriedade.quartos() != null ?
                        propriedade.quartos().stream().map(quartoService::fromEntity).toList() : null
        );
    }
    }

    public PropriedadeResponseDTO fromEntity(Propriedade propriedade) {
        if (propriedade == null) {
            return null;
        }
        return new PropriedadeResponseDTO(
                propriedade.getId(),
                propriedade.getNome(),
                propriedade.getDescricao(),
                propriedade.getTipo(),
                propriedade.getEndereco() != null ? new EnderecoResponseDTO(
                        propriedade.getEndereco().getRua(),
                        propriedade.getEndereco().getBairro(),
                        propriedade.getEndereco().getCidade(),
                        propriedade.getEndereco().getEstado()
                ) : null,
                propriedade.getQuartos() != null ?
                        propriedade.getQuartos().stream().map(quarto -> quartoService.fromEntity(quarto)).toList() : null
        );
    }
}