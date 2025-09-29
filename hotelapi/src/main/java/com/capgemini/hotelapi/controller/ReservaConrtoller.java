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

    @GetMapping("/listarreservas")
    public List<Reserva> listarTodas() {
        return reservaService.listarTodas();
    }

    // seria interessante gerar um codigo da reserva para buscar por ele ?
    @GetMapping("/buscarporreserva/{id}")
    public Reserva buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @PostMapping("/fazerreserva")
    public Reserva fazerReserva(@RequestBody Reserva reserva) {
        return reservaService.criarReserva(reserva);
    }

    @PutMapping("/refazerreserva/{id}")
    public Reserva atualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.atualizarReserva(id, reserva);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        reservaService.deletar(id);
    }

}
