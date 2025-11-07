package com.devex.todoapi.service;

import com.devex.todoapi.dto.UserRequestDTO;
import com.devex.todoapi.dto.UserResponseDTO;
import com.devex.todoapi.exception.BusinessException;
import com.devex.todoapi.exception.ResourceNotFoundException;
import com.devex.todoapi.model.User;
import com.devex.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new  ResourceNotFoundException("Usuário de id " + id + " não encontrado")
        );
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new BusinessException("Email já esta em uso");
        }
        User user = new User();
        copyDtoToEntity(requestDTO, user);
        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    private void copyDtoToEntity(UserRequestDTO requestDTO, User user) {
        user.setNome(requestDTO.getNome());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
    }
}
