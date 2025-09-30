package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaConrtoller {

    private final ReservaService reservaService;

    public ReservaConrtoller(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/buscarreservas")
    public List<Reserva> findAll() {
        return reservaService.findAll();
    }

    // seria interessante gerar um codigo da reserva para buscar por ele ?
    @GetMapping("/{id}")
    public Reserva findById(@PathVariable Long id) {
        return reservaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @PostMapping("/criarreservar")
    public Reserva create(@RequestBody Reserva reserva) {
        return reservaService.create(reserva);
    }

    @PutMapping("/atualizarreserva/{id}")
    public Reserva update(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.update(id, reserva);
    }

    @DeleteMapping("/deletarreserva/{id}")
    public void delete(@PathVariable Long id) {
        reservaService.delete(id);
    }

}
