package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.QuartoRequestDTO;
import com.capgemini.hotelapi.dtos.QuartoResponseDTO;
import com.capgemini.hotelapi.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/quartos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Quartos", description = "API para gerenciamento de quartos")
public class QuartoController {

    private final QuartoService quartoService;

    @Operation(summary = "Criar novo quarto (vinculando a uma Propriedade ID)")
    @PostMapping
    public ResponseEntity<QuartoResponseDTO> createQuarto(@Valid @RequestBody QuartoRequestDTO quarto) {
        QuartoResponseDTO createdQuarto = quartoService.create(quarto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuarto);
    }

    @Operation(summary = "Listar todos os quartos")
    @GetMapping
    public ResponseEntity<List<QuartoResponseDTO>> getAllQuartos() {
        List<QuartoResponseDTO> quartos = quartoService.getAll();
        return ResponseEntity.ok(quartos);
    }

    @Operation(summary = "Buscar quarto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> getQuartoById(@PathVariable Long id) {
        QuartoResponseDTO quarto = quartoService.findById(id);
        return ResponseEntity.ok(quarto);
    }

    @Operation(summary = "Atualizar quarto")
    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> updateQuarto(
            @PathVariable Long id,
            @Valid @RequestBody QuartoRequestDTO quarto) {

        QuartoResponseDTO updatedQuarto = quartoService.update(id, quarto);
        return ResponseEntity.ok(updatedQuarto);
    }

    @Operation(summary = "Deletar quarto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuarto(@PathVariable Long id) {
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar quartos disponíveis em um período")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<QuartoResponseDTO>> getQuartosDisponiveis(
            @RequestParam("checkIn") String checkIn,
            @RequestParam("checkOut") String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        List<QuartoResponseDTO> disponiveis = quartoService.findDisponiveis(checkInDate, checkOutDate);
        return ResponseEntity.ok(disponiveis);
    }

    @Operation(summary = "Verificar se o quarto está disponível para reserva no período informado")
    @GetMapping("/{id}/disponivel")
    public ResponseEntity<Boolean> isQuartoDisponivel(
            @PathVariable Long id,
            @RequestParam("checkIn") String checkIn,
            @RequestParam("checkOut") String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        boolean disponivel = quartoService.isQuartoDisponivel(id, checkInDate, checkOutDate);
        return ResponseEntity.ok(disponivel);
    }

}
