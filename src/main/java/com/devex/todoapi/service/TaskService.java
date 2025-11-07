package com.devex.todoapi.service;

import com.devex.todoapi.dto.TaskRequestDTO;
import com.devex.todoapi.dto.TaskResponseDTO;
import com.devex.todoapi.exception.BusinessException;
import com.devex.todoapi.exception.ResourceNotFoundException;
import com.devex.todoapi.model.Task;
import com.devex.todoapi.model.TaskStatus;
import com.devex.todoapi.model.User;
import com.devex.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> findAll(Pageable pageable) {
        User userauthenticated = authService.authenticated();
        Page<Task> result = taskRepository.findByUserId(userauthenticated.getId(), pageable);

        return result.map(x -> new TaskResponseDTO(x));
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task de id " + id + " não encontrado")
        );
        authService.validateSelfOrAdmin(task.getUser().getId());
        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO requestDTO) {
        User user = authService.authenticated();

        Task task = new Task();
        copyDtoToEntity(requestDTO, task);
        task.setStatus(TaskStatus.PENDENTE);
        task.setDataCriacao(LocalDateTime.now());
        task.setUser(user);

        task = taskRepository.save(task);

        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO update(Long id, TaskRequestDTO requestDTO) {
        try {
            Task task = taskRepository.getReferenceById(id);
            authService.validateSelfOrAdmin(task.getUser().getId());
            copyDtoToEntity(requestDTO, task);
            task = taskRepository.save(task);
            return new TaskResponseDTO(task);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Task de id " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task de id " + id + " não encontrado"));
        authService.validateSelfOrAdmin(task.getUser().getId());

        try {
            taskRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro de integridade referencial");
        }
    }

    @Transactional
    public TaskResponseDTO markAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task de id " + id + " não encontrado"));

        authService.validateSelfOrAdmin(task.getUser().getId());

        if (task.getStatus() == TaskStatus.CONCLUIDA) {
                throw new BusinessException("Task de id " + id + " já foi concluida");
        }

        task.setStatus(TaskStatus.CONCLUIDA);
        task.setDataConclusao(LocalDateTime.now());

        task = taskRepository.save(task);

        return new TaskResponseDTO(task);
    }

    private void copyDtoToEntity(TaskRequestDTO requestDTO, Task task) {
        if (requestDTO.getTitulo() != null) {
                task.setTitulo(requestDTO.getTitulo());
        }

        if (requestDTO.getDescricao() != null) {
            task.setDescricao(requestDTO.getDescricao());
        }
    }
}
