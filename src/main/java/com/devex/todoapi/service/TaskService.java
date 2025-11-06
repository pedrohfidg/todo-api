package com.devex.todoapi.service;

import com.devex.todoapi.dto.TaskRequestDTO;
import com.devex.todoapi.dto.TaskResponseDTO;
import com.devex.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAll() {
        return null;
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        return null;
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO requestDTO) {
        return null;
    }

    @Transactional
    public TaskResponseDTO update(Long id, TaskRequestDTO requestDTO) {
        return null;
    }

    @Transactional
    public void delete(Long id) {

    }
}
