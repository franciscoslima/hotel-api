package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.PropriedadeRequestDTO;
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

    @Autowired
    private PropriedadeService propriedadeService;

    @Operation(summary = "Criar nova propriedade", description = "Cria uma nova propriedade no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propriedade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Propriedade> createPropriedade(@Valid @RequestBody PropriedadeRequestDTO request) {
        log.info("Recebida requisição para criar nova propriedade: {}", request.nome());
        Propriedade novaPropriedade = propriedadeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPropriedade);
    }

    @Operation(summary = "Listar todas as propriedades", description = "Lista todas as propriedades ordenadas por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de propriedades retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Propriedade>> getAllPropriedades() {
        log.info("Recebida requisição para listar todas as propriedades");
        List<Propriedade> propriedades = propriedadeService.listarTodas();
        return ResponseEntity.ok(propriedades);
    }

    @Operation(summary = "Buscar propriedade por ID", description = "Busca uma propriedade específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade encontrada"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> getPropriedadeById(
            @Parameter(description = "ID da propriedade") @PathVariable Long id) {
        log.info("Recebida requisição para buscar propriedade com ID: {}", id);
        Propriedade propriedade = propriedadeService.buscarPorId(id);
        return ResponseEntity.ok(propriedade);
    }

    @Operation(summary = "Atualizar propriedade", description = "Atualiza os dados de uma propriedade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> updatePropriedade(
            @Parameter(description = "ID da propriedade") @PathVariable Long id,
            @Valid @RequestBody PropriedadeRequestDTO request) {
        log.info("Recebida requisição para atualizar propriedade com ID: {}", id);
        Propriedade propriedadeAtualizada = propriedadeService.atualizar(id, request);
        return ResponseEntity.ok(propriedadeAtualizada);
    }

    @Operation(summary = "Deletar propriedade", description = "Remove uma propriedade do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Propriedade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropriedade(
            @Parameter(description = "ID da propriedade") @PathVariable Long id) {
        log.info("Recebida requisição para deletar propriedade com ID: {}", id);
        propriedadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}