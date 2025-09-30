package com.capgemini.hotelapi.controller;


import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.service.QuartoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quarto")
public class QuartoController {
    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @GetMapping
    public List<Quarto> listarTodos(){
        return quartoService.getAll();
    }

    @GetMapping("/{id}")
    public Quarto obterPorId(Long id){
        return quartoService.findById(id);
    }

    @PostMapping
    public Quarto cadastrar(@RequestBody @Valid Quarto quarto) {
        return quartoService.create(quarto);
    }

    @PostMapping("/{id}")
    public Quarto atualizar(@PathVariable Long id, @RequestBody @Valid Quarto quarto) {
        return quartoService.update(quarto, id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        quartoService.delete(id);
    }

    @PutMapping("/{id}/reservar")
    public Quarto reservar(@PathVariable Long id){
        return quartoService.reservarQuarto(id);
    }

    @PutMapping("/{id}/checkin")
    public Quarto checkin(@PathVariable Long id){
        return quartoService.checkin(id);
    }

    @PutMapping("/{id}/realizarManutencao")
    public Quarto manutencao(@PathVariable Long id){
        return quartoService.manutencao(id);
    }

    @PutMapping("/{id}/cancelarResereva")
    public Quarto cancelarResereva(@PathVariable Long id){
        return quartoService.cancelarResereva(id);
    }
}
