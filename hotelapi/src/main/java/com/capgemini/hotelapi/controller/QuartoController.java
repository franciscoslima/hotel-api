package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.model.Quarto;
import com.capgemini.hotelapi.service.QuartoService;
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
@RequestMapping("/api/quartos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Quartos", description = "API para gerenciamento de quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @Operation(summary = "Listar todos os quartos", description = "Lista todos os quartos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de quartos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Quarto>> getAllQuartos() {
        log.info("Recebida requisição para listar todos os quartos");
        List<Quarto> quartos = quartoService.getAll();
        return ResponseEntity.ok(quartos);
    }

    @Operation(summary = "Buscar quarto por ID", description = "Busca um quarto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto encontrado"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Quarto> getQuartoById(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para buscar quarto com ID: {}", id);
        Quarto quarto = quartoService.findById(id);
        return ResponseEntity.ok(quarto);
    }

    @Operation(summary = "Criar novo quarto", description = "Cria um novo quarto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quarto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Quarto> createQuarto(@Valid @RequestBody Quarto quarto) {
        log.info("Recebida requisição para criar novo quarto");
        Quarto createdQuarto = quartoService.create(quarto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuarto);
    }

    @Operation(summary = "Atualizar quarto", description = "Atualiza os dados de um quarto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> updateQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id,
            @Valid @RequestBody Quarto quarto) {
        log.info("Recebida requisição para atualizar quarto com ID: {}", id);
        Quarto updatedQuarto = quartoService.update(quarto, id);
        return ResponseEntity.ok(updatedQuarto);
    }

    @Operation(summary = "Deletar quarto", description = "Remove um quarto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quarto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para deletar quarto com ID: {}", id);
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reservar quarto", description = "Marca um quarto como reservado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto reservado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quarto não disponível para reserva"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping("/{id}/reservar")
    public ResponseEntity<Quarto> reservarQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para reservar quarto com ID: {}", id);
        Quarto quartoReservado = quartoService.reservarQuarto(id);
        return ResponseEntity.ok(quartoReservado);
    }

    @Operation(summary = "Fazer check-in", description = "Realiza check-in em um quarto reservado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quarto não possui reserva"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping("/{id}/checkin")
    public ResponseEntity<Quarto> checkinQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para check-in do quarto com ID: {}", id);
        Quarto quartoOcupado = quartoService.checkin(id);
        return ResponseEntity.ok(quartoOcupado);
    }

    @Operation(summary = "Realizar manutenção", description = "Marca um quarto para manutenção")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto marcado para manutenção"),
            @ApiResponse(responseCode = "400", description = "Operação inválida"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<Quarto> manutencaoQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para manutenção do quarto com ID: {}", id);
        Quarto quartoManutencao = quartoService.manutencao(id);
        return ResponseEntity.ok(quartoManutencao);
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela a reserva de um quarto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Quarto não está reservado"),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado")
    })
    @PutMapping("/{id}/cancelar-reserva")
    public ResponseEntity<Quarto> cancelarReservaQuarto(
            @Parameter(description = "ID do quarto") @PathVariable Long id) {
        log.info("Recebida requisição para cancelar reserva do quarto com ID: {}", id);
        Quarto quartoDisponivel = quartoService.cancelarResereva(id);
        return ResponseEntity.ok(quartoDisponivel);
    }
}
