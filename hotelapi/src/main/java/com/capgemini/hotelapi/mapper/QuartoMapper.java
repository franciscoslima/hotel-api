package com.capgemini.hotelapi.mapper;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.model.Quarto;
import org.springframework.stereotype.Component;

@Component
public class QuartoMapper {

    public Quarto toEntity(QuartoRequestDTO dto) {
        return Quarto.builder()
                .numeracao(dto.numeracao())
                .descricao(dto.descricao())
                .valorDiaria(dto.valorDiaria())
                .status(dto.status())
                .build();
    }

    public QuartoResponseDTO toResponseDTO(Quarto entity) {
        String nomePropriedade = entity.getPropriedade() != null ? entity.getPropriedade().getNome() : "N/A";

        return new QuartoResponseDTO(
                entity.getId(),
                entity.getNumeracao(),
                entity.getDescricao(),
                entity.getValorDiaria(),
                entity.getStatus(),
                nomePropriedade
        );
    }
}