package com.AtomicTask.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AtomicTask.Model.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

}
