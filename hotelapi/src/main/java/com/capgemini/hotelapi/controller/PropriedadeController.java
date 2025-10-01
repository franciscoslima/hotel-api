package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
import com.capgemini.hotelapi.dtos.PropriedadeResponseDTO;
import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.service.PropriedadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propriedades")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Propriedades", description = "API para gerenciamento de propriedades")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    @Operation(summary = "Criar nova propriedade")
    @PostMapping
    public ResponseEntity<PropriedadeResponseDTO> createPropriedade(@Valid @RequestBody PropriedadeRequestDTO request) {
        PropriedadeResponseDTO novaPropriedade = propriedadeService.createPropriedade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPropriedade);
    }

    @Operation(summary = "Listar todas as propriedades")
    @GetMapping
    public ResponseEntity<List<PropriedadeResponseDTO>> getAllPropriedades() {
        List<PropriedadeResponseDTO> propriedades = propriedadeService.getAllPropriedades();
        return ResponseEntity.ok(propriedades);
    }

    @Operation(summary = "Buscar propriedade por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeResponseDTO> getPropriedadeById(@PathVariable Long id) {
        PropriedadeResponseDTO propriedade = propriedadeService.getPropriedadeById(id);
        return ResponseEntity.ok(propriedade);
    }

    @Operation(summary = "Atualizar propriedade")
    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeResponseDTO> updatePropriedade(
            @PathVariable Long id,
            @Valid @RequestBody PropriedadeRequestDTO request) {
        PropriedadeResponseDTO propriedadeAtualizada = propriedadeService.updatePropriedade(id, request);
        return ResponseEntity.ok(propriedadeAtualizada);
    }

    @Operation(summary = "Deletar propriedade")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        propriedadeService.deletePropriedade(id);
        return ResponseEntity.noContent().build();
    }
}