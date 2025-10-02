package com.capgemini.hotelapi.service;


import org.springframework.data.domain.Page;

import com.capgemini.hotelapi.dtos.ReservaResponseDTO;
import com.capgemini.hotelapi.dtos.UserRequestDTO;
import com.capgemini.hotelapi.dtos.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    Page<UserResponseDTO> getAllUsers(int page, int size, String sortBy, String sortDirection);

    List<UserResponseDTO> getAllUsersOrderedByName();

    List<UserResponseDTO> getUsersByName(String nome);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO getUserByCpf(String cpf);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    List<ReservaResponseDTO> getReservasByUserEmail(String email);
}