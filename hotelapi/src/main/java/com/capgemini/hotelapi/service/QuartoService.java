package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.model.QuartoStatusEnum;
import com.capgemini.hotelapi.repository.QuartoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class QuartoService {
    private final QuartoRepository quartoRepository;

    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    public List<Quarto> getAll() {
        return quartoRepository.findAll();
    }
    public Quarto findById(Long id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto com id " + id + " não encontrado!"));
    }
    public Quarto create(Quarto quarto) {
        return quartoRepository.save(quarto);
    }
    public Quarto update(Quarto quarto, Long id) {
        this.findById(id);
        quarto.setId(id);
        return quartoRepository.save(quarto);
    }
    public void delete(Long id) {
        quartoRepository.deleteById(id);
    }

    public Quarto reservarQuarto(Long id){
        Quarto quarto = this.findById(id);

        //O quarto só pode ser reservado se estiver disponível
        if (quarto.getStatus() != QuartoStatusEnum.DISPONIVEL) {
            throw new RuntimeException("Quarto não está disponível para reserva.");
        }

        quarto.setStatus(QuartoStatusEnum.RESERVADO);
        return quartoRepository.save(quarto);
    }





}
