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

    private Propriedade toEntity(PropriedadeRequestDTO dto) {
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

//        if (dto.getQuartos() != null) {
//            List<Quarto> quartos = dto.getQuartos().stream()
//                    .map(quartoDto -> new Quarto(null, propriedade, quartoDto.getNumeracao(), quartoDto.getDescricao(), quartoDto.getStatus()))
//                    .collect(Collectors.toList());
//
//            quartos.forEach(propriedade::adicionarQuarto);
//        }

        return propriedade;
    }

    private PropriedadeResponseDTO fromEntity(Propriedade propriedade) {
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
                ) : null
        );
    }


    @Transactional(readOnly = true)
    public List<Propriedade> listarTodas() {
        log.info("Listando todas as propriedades");
        return propriedadeRepository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public Propriedade buscarPorId(Long id) {
        log.info("Buscando propriedade por ID: {}", id);
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada com ID: " + id));
    }

    public Propriedade criar(PropriedadeRequestDTO dto) {
        log.info("Criando nova propriedade: {}", dto.nome());
        Propriedade novaPropriedade = toEntity(dto);
        Propriedade savedPropriedade = propriedadeRepository.save(novaPropriedade);
        log.info("Propriedade criada com sucesso. ID: {}", savedPropriedade.getId());
        return savedPropriedade;
    }

    public Propriedade atualizar(Long id, PropriedadeRequestDTO dto) {
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

        log.info("Propriedade atualizada com sucesso. ID: {}", propriedade.getId());
        return propriedade;
    }

    public void deletar(Long id) {
        log.info("Deletando propriedade ID: {}", id);
        if (!propriedadeRepository.existsById(id)) {
            throw new RuntimeException("Propriedade não encontrada com ID: " + id);
        }
        propriedadeRepository.deleteById(id);
        log.info("Propriedade deletada com sucesso. ID: {}", id);
    }

}