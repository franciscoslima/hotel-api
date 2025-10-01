package com.capgemini.hotelapi.service;


import java.util.List;

import com.capgemini.hotelapi.model.Quarto;

public interface QuartoService {

    List<Quarto> getAll();

    Quarto findById(Long id);

    Quarto create(Quarto quarto);

    Quarto update(Quarto quarto, Long id);

    void delete(Long id);

    Quarto reservarQuarto(Long id);

    Quarto checkin(Long id);

    Quarto checkout(Long id);

    Quarto manutencao(Long id);

    Quarto cancelarResereva(Long id);
}