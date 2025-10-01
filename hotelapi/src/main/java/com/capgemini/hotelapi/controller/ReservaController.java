package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.ReservaRequestDTO;
import com.capgemini.hotelapi.dtos.ReservaResponseDTO;
import com.capgemini.hotelapi.dtos.ReservaUpdateDTO;
import com.capgemini.hotelapi.service.ReservaService;
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
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reservas", description = "API para gerenciamento de reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Criar nova reserva", description = "Cria uma nova reserva no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou quarto não encontrado")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> createReserva(@Valid @RequestBody ReservaRequestDTO dto) {
        log.info("Recebida requisição para criar nova reserva");
        ReservaResponseDTO reserva = reservaService.createReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @Operation(summary = "Listar todas as reservas", description = "Lista todas as reservas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> getAllReservas() {
        log.info("Recebida requisição para listar todas as reservas");
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @Operation(summary = "Buscar reserva por ID", description = "Busca uma reserva específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReservaById(
            @Parameter(description = "ID da reserva") @PathVariable Long id) {
        log.info("Recebida requisição para buscar reserva com ID: {}", id);
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar reserva", description = "Atualiza os dados de uma reserva existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> updateReserva(
            @Parameter(description = "ID da reserva") @PathVariable Long id,
            @Valid @RequestBody ReservaUpdateDTO dto) {
        log.info("Recebida requisição para atualizar reserva com ID: {}", id);
        return ResponseEntity.ok(reservaService.updateReserva(id, dto));
    }

//    @Operation(summary = "Confirmar reserva", description = "Confirma uma reserva pendente")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Reserva confirmada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
//    })
//    @PutMapping("/{id}/confirmar")
//    public ResponseEntity<ReservaResponseDTO> confirmarReserva(
//            @Parameter(description = "ID da reserva") @PathVariable Long id) {
//        log.info("Recebida requisição para confirmar reserva com ID: {}", id);
//        return ResponseEntity.ok(reservaService.confirmar(id));
//    }

//    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
//    })
//    @PutMapping("/{id}/cancelar")
//    public ResponseEntity<ReservaResponseDTO> cancelarReserva(
//            @Parameter(description = "ID da reserva") @PathVariable Long id) {
//        log.info("Recebida requisição para cancelar reserva com ID: {}", id);
//        return ResponseEntity.ok(reservaService.cancelar(id));
//    }
//
//    @Operation(summary = "Finalizar reserva", description = "Finaliza uma reserva confirmada")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Reserva finalizada com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
//    })
//    @PutMapping("/{id}/finalizar")
//    public ResponseEntity<ReservaResponseDTO> finalizarReserva(
//            @Parameter(description = "ID da reserva") @PathVariable Long id) {
//        log.info("Recebida requisição para finalizar reserva com ID: {}", id);
//        return ResponseEntity.ok(reservaService.finalizar(id));
//    }
}
