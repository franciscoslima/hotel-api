package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.model.Reserva;
import com.capgemini.hotelapi.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaConrtoller {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaService.listarTodas();
    }

    // seia interessante gerar um codigo da reserva para buscar por ele ?
    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @PostMapping
    public Reserva criar(@RequestBody Reserva reserva) {
        return reservaService.criarReserva(reserva);
    }

    @PutMapping("/{id}")
    public Reserva atualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.atualizarReserva(id, reserva);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        reservaService.deletar(id);
    }

}
