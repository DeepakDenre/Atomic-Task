package com.AtomicTask.Model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Task_Page")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskPageModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;
	
	private String description;
	
	@OneToMany(mappedBy = "taskPage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskModel> tasksList;

}
