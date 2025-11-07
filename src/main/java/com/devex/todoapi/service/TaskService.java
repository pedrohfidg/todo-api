package com.devex.todoapi.service;

import com.devex.todoapi.dto.TaskRequestDTO;
import com.devex.todoapi.dto.TaskResponseDTO;
import com.devex.todoapi.exception.BusinessException;
import com.devex.todoapi.exception.ResourceNotFoundException;
import com.devex.todoapi.model.Task;
import com.devex.todoapi.model.TaskStatus;
import com.devex.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAll() {
        List<Task> result = taskRepository.findAll();
        return result.stream().map(x -> new TaskResponseDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task de id " + id + " não encontrado")
        );
        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO requestDTO) {
        Task task = new Task();
        copyDtoToEntity(requestDTO, task);
        task.setStatus(TaskStatus.PENDENTE);
        task.setDataCriacao(LocalDateTime.now());
        task = taskRepository.save(task);

        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO update(Long id, TaskRequestDTO requestDTO) {
        try {
            Task task = taskRepository.getReferenceById(id);
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
        if (!taskRepository.existsById(id)) {
           throw new ResourceNotFoundException("Task de id " + id + " não encontrado");
        }
        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskResponseDTO markAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task de id " + id + " não encontrado"));

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
