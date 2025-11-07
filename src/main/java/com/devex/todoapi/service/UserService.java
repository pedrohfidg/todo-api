package com.devex.todoapi.service;

import com.devex.todoapi.dto.UserRequestDTO;
import com.devex.todoapi.dto.UserResponseDTO;
import com.devex.todoapi.exception.BusinessException;
import com.devex.todoapi.exception.ResourceNotFoundException;
import com.devex.todoapi.model.Role;
import com.devex.todoapi.model.User;
import com.devex.todoapi.projection.UserDetailsProjection;
import com.devex.todoapi.repository.RoleRepository;
import com.devex.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String authority = "ROLE_USER";
        Role userRole = roleRepository.findByAuthority(authority).orElseThrow(
                () -> new ResourceNotFoundException("Role '" + authority + "' não encontrada."));

        User user = new User();
        copyDtoToEntity(requestDTO, user);
        user.addRole(userRole);
        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    private void copyDtoToEntity(UserRequestDTO requestDTO, User user) {
        user.setNome(requestDTO.getNome());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Email " + username + " não encontrado.");
        }

        User user = new User();
        user.setEmail(result.getFirst().getUsername());
        user.setPassword(result.getFirst().getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }
}
