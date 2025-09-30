package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.EnderecoResponseDTO;
import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;
import com.capgemini.hotelapi.exceptions.ResourceNotFoundException;
import com.capgemini.hotelapi.model.Endereco;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.repository.PropriedadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropriedadeServiceImpl implements PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeServiceImpl(PropriedadeRepository propriedadeRepository) {
        this.propriedadeRepository = propriedadeRepository;
    }

    private static final String NOT_FOUND_MSG = "Propriedade não encontrada com o ID: ";

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


    public List<Propriedade> listarTodas() {
        return propriedadeRepository.findAllByOrderByNomeAsc();
    }

    public Propriedade buscarPorId(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MSG + id));
    }

    public Propriedade criar(PropriedadeRequestDTO dto) {
        Propriedade novaPropriedade = toEntity(dto);
        return propriedadeRepository.save(novaPropriedade);
    }

    @Transactional
    public Propriedade atualizar(Long id, PropriedadeRequestDTO dto) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MSG + id));

        propriedade.setNome(dto.nome());
        propriedade.setDescricao(dto.descricao());
        propriedade.setTipo(dto.tipo());

        if (dto.endereco() != null) {
            propriedade.getEndereco().setRua(dto.endereco().rua());
            propriedade.getEndereco().setBairro(dto.endereco().bairro());
            propriedade.getEndereco().setCidade(dto.endereco().cidade());
            propriedade.getEndereco().setEstado(dto.endereco().estado());
        }

        return propriedade;
    }

    @Transactional
    public void deletar(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new ResourceNotFoundException(NOT_FOUND_MSG + id);
        }
        propriedadeRepository.deleteById(id);
    }

}