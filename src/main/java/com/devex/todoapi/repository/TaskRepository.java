package com.devex.todoapi.repository;

import com.devex.todoapi.model.Task;
import com.devex.todoapi.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = "user")
    Page<Task> findByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT obj FROM Task obj
            WHERE (:titulo IS NULL OR obj.titulo ILIKE %:titulo%)
            AND (:status IS NULL OR obj.status = :status)
            AND (obj.user.id = :user_id)
            """)
    @EntityGraph(attributePaths = "user")
    Page<Task> findByUserIdAndFilters(
            @Param("titulo") String titulo,
            @Param("status") TaskStatus status,
            @Param("user_id") Long user_id,
            Pageable pageable
        );

    @Query("""
            SELECT obj FROM Task obj
            WHERE (:titulo IS NULL OR obj.titulo ILIKE %:titulo%)
            AND (:status IS NULL OR obj.status = :status)
            """)
    @EntityGraph(attributePaths = "user")
    Page<Task> findAllFiltered(
            @Param("titulo") String titulo,
            @Param("status") TaskStatus status,
            Pageable pageable
    );
}
