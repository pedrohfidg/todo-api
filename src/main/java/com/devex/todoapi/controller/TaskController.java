package com.devex.todoapi.controller;

import com.devex.todoapi.dto.TaskRequestDTO;
import com.devex.todoapi.dto.TaskResponseDTO;
import com.devex.todoapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> findAll(Pageable pageable) {
        Page<TaskResponseDTO> responseDTOS = taskService.findAll(pageable);
        return ResponseEntity.ok().body(responseDTOS);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO>  findById(@PathVariable Long id) {
        TaskResponseDTO responseDTO = taskService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> create (@Valid @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO responseDTO = taskService.create(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO responseDTO = taskService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/{id}/complete")
    public ResponseEntity<TaskResponseDTO> complete(@PathVariable Long id) {
        TaskResponseDTO responseDTO = taskService.markAsCompleted(id);
        return ResponseEntity.ok().body(responseDTO);
    }
}
