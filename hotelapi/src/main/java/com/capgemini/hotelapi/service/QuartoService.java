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

    public Quarto checkin(Long id){
        Quarto quarto = this.findById(id);

        //Só pode fazer check-in em quartos com status RESERVADO
        if (quarto.getStatus() != QuartoStatusEnum.RESERVADO) {
            throw new RuntimeException("Este quarto não possui reservas.");
        }

        quarto.setStatus(QuartoStatusEnum.OCUPADO);
        return quartoRepository.save(quarto);
    }

    public Quarto checkout(Long id){
        Quarto quarto = this.findById(id);

        //Só pode fazer check-out de quartos com status OCUPADO
        if (quarto.getStatus() != QuartoStatusEnum.OCUPADO) {
            throw new RuntimeException("Operação inválida.");
        }

        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        return quartoRepository.save(quarto);
    }

    public Quarto manutencao(Long id){
        Quarto quarto = this.findById(id);

        //Só pode fazer check-out de quartos com status OCUPADO
        if (quarto.getStatus() != QuartoStatusEnum.MANUTENCAO) {
            throw new RuntimeException("Operação inválida.");
        }

        quarto.setStatus(QuartoStatusEnum.MANUTENCAO);
        return quartoRepository.save(quarto);
    }

    public Quarto cancelarResereva(Long id){
        Quarto quarto = this.findById(id);

        //Só pode fazer check-out de quartos com status OCUPADO
        if (quarto.getStatus() != QuartoStatusEnum.RESERVADO) {
            throw new RuntimeException("Quarto não está ocupado.");
        }

        quarto.setStatus(QuartoStatusEnum.DISPONIVEL);
        return quartoRepository.save(quarto);
    }
}
