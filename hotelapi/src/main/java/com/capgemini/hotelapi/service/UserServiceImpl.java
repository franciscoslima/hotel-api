package com.capgemini.hotelapi.service;

import com.capgemini.hotelapi.dtos.ReservaResponseDTO;
import com.capgemini.hotelapi.dtos.UserRequestDTO;
import com.capgemini.hotelapi.dtos.UserResponseDTO;
import com.capgemini.hotelapi.model.User;
import com.capgemini.hotelapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservaService reservaService;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("Criando novo usuário com email: {}", userRequestDTO.getEmail());
        
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com este email: " + userRequestDTO.getEmail());
        }
        
        if (userRepository.findByCpf(userRequestDTO.getCpf()).isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com este CPF: " + userRequestDTO.getCpf());
        }

        try {
            User user = convertToEntity(userRequestDTO);
            User savedUser = userRepository.save(user);
            log.info("Usuário criado com sucesso. ID: {}", savedUser.getId());
            return convertToResponseDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao criar usuário: {}", e.getMessage());
            throw new RuntimeException("Dados duplicados. Verifique email e CPF.");
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        log.info("Buscando usuário por ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return convertToResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        log.info("Listando usuários - página: {}, tamanho: {}", page, size);
        
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsersOrderedByName() {
        log.info("Listando todos os usuários ordenados por nome");
        List<User> users = userRepository.findAllOrderByNome();
        return users.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByName(String nome) {
        log.info("Buscando usuários por nome: {}", nome);
        List<User> users = userRepository.findByNomeContainingIgnoreCase(nome);
        return users.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        log.info("Buscando usuário por email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
        return convertToResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByCpf(String cpf) {
        log.info("Buscando usuário por CPF: {}", cpf);
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com CPF: " + cpf));
        return convertToResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        log.info("Atualizando usuário ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        if (userRepository.existsByEmailAndIdNot(userRequestDTO.getEmail(), id)) {
            throw new RuntimeException("Já existe outro usuário cadastrado com este email: " + userRequestDTO.getEmail());
        }

        if (userRepository.existsByCpfAndIdNot(userRequestDTO.getCpf(), id)) {
            throw new RuntimeException("Já existe outro usuário cadastrado com este CPF: " + userRequestDTO.getCpf());
        }

        try {
            updateUserData(existingUser, userRequestDTO);
            User updatedUser = userRepository.save(existingUser);
            log.info("Usuário atualizado com sucesso. ID: {}", updatedUser.getId());
            return convertToResponseDTO(updatedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro de integridade ao atualizar usuário: {}", e.getMessage());
            throw new RuntimeException("Dados duplicados. Verifique email e CPF.");
        }
    }

    public void deleteUser(Long id) {
        log.info("Deletando usuário ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("Usuário deletado com sucesso. ID: {}", id);
    }

    private User convertToEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setNome(userRequestDTO.getNome());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        user.setTelefone(userRequestDTO.getTelefone());
        user.setDataNascimento(userRequestDTO.getDataNascimento());
        user.setEndereco(userRequestDTO.getEndereco());
        return user;
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getCpf(),
                user.getTelefone(),
                user.getDataNascimento(),
                user.getEndereco()
        );
    }

    private void updateUserData(User existingUser, UserRequestDTO userRequestDTO) {
        existingUser.setNome(userRequestDTO.getNome());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setCpf(userRequestDTO.getCpf());
        existingUser.setTelefone(userRequestDTO.getTelefone());
        existingUser.setDataNascimento(userRequestDTO.getDataNascimento());
        existingUser.setEndereco(userRequestDTO.getEndereco());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> getReservasByUserEmail(String email) {
        log.info("Buscando reservas para usuário com email: {}", email);
        
        UserResponseDTO user = getUserByEmail(email);
        return reservaService.getReservasByUserId(user.getId());
    }
}
