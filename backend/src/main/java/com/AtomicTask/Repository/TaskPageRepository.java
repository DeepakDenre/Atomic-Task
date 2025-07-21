package com.AtomicTask.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AtomicTask.Model.TaskPageModel;

@Repository
public interface TaskPageRepository extends JpaRepository<TaskPageModel, UUID> {

}
