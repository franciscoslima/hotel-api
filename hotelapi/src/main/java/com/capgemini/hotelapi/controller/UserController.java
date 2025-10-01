package com.capgemini.hotelapi.controller;

import com.capgemini.hotelapi.dtos.UserRequestDTO;
import com.capgemini.hotelapi.dtos.UserResponseDTO;
import com.capgemini.hotelapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuários", description = "API para gerenciamento de usuários/hóspedes")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário/hóspede no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Recebida requisição para criar usuário com email: {}", userRequestDTO.getEmail());
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        log.info("Recebida requisição para buscar usuário com ID: {}", id);
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Listar todos os usuários", description = "Lista todos os usuários com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sortBy,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String sortDirection) {
        
        log.info("Recebida requisição para listar usuários - página: {}, tamanho: {}", page, size);
        Page<UserResponseDTO> users = userService.getAllUsers(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Listar usuários ordenados por nome")
    @GetMapping("/ordered")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersOrderedByName() {
        log.info("Recebida requisição para listar usuários ordenados por nome");
        List<UserResponseDTO> users = userService.getAllUsersOrderedByName();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuários por nome")
    @GetMapping("/search/name")
    public ResponseEntity<List<UserResponseDTO>> getUsersByName(
            @Parameter(description = "Nome para busca") @RequestParam String nome) {
        log.info("Recebida requisição para buscar usuários por nome: {}", nome);
        List<UserResponseDTO> users = userService.getUsersByName(nome);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuário por email")
    @GetMapping("/search/email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @Parameter(description = "Email do usuário") @RequestParam String email) {
        log.info("Recebida requisição para buscar usuário por email: {}", email);
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Buscar usuário por CPF")
    @GetMapping("/search/cpf")
    public ResponseEntity<UserResponseDTO> getUserByCpf(
            @Parameter(description = "CPF do usuário") @RequestParam String cpf) {
        log.info("Recebida requisição para buscar usuário por CPF: {}", cpf);
        UserResponseDTO user = userService.getUserByCpf(cpf);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Recebida requisição para atualizar usuário com ID: {}", id);
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        log.info("Recebida requisição para deletar usuário com ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
