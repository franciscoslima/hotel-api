package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.model.Propriedade;

import java.util.List;

public interface PropriedadeService {

    List<Propriedade> listarTodas();

    Propriedade buscarPorId(Long id);

    Propriedade criar(PropriedadeRequestDTO dto);

    Propriedade atualizar(Long id, PropriedadeRequestDTO dto);

    void deletar(Long id);
}
