package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.service.ReservaRequestDTO;
import com.capgemini.hotelapi.service.ReservaResponseDTO;
import com.capgemini.hotelapi.service.ReservaService;
import com.capgemini.hotelapi.service.ReservaUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO reserva = reservaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponseDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.confirmar(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ReservaResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.finalizar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReservaUpdateDTO dto) {
        return ResponseEntity.ok(reservaService.update(id, dto));
    }
}
