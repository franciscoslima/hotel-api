package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.exceptions.ResourceNotFoundException;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.service.PropriedadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    public PropriedadeController(PropriedadeService propriedadeService) {
        this.propriedadeService = propriedadeService;
    }

    @PostMapping
    public ResponseEntity<Propriedade> criarPropriedade(@RequestBody PropriedadeRequestDTO request) {
        Propriedade novaPropriedade = propriedadeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPropriedade);
    }


    @GetMapping
    public ResponseEntity<List<Propriedade>> listarTodasPropriedades() {
        List<Propriedade> propriedades = propriedadeService.listarTodas();
        return ResponseEntity.ok(propriedades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> buscarPropriedadePorId(@PathVariable Long id) {
        try {
            Propriedade propriedade = propriedadeService.buscarPorId(id);
            return ResponseEntity.ok(propriedade);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> atualizarPropriedade(@PathVariable Long id, @RequestBody PropriedadeRequestDTO request) {
        try {
            Propriedade propriedadeAtualizada = propriedadeService.atualizar(id, request);
            return ResponseEntity.ok(propriedadeAtualizada);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPropriedade(@PathVariable Long id) {
        try {
            propriedadeService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}