package com.AtomicTask.Model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "task_master")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String title;
	
	@Column(length = 200)
	private String description;
	private Date dueDate;
	private Date createdDate;
	private Date updatedDate;
	private boolean completed;
	
	@ManyToOne
	@JoinColumn(name = "task_page_id")
	private TaskPageModel taskPage;
}