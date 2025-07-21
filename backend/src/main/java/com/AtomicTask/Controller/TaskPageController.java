package com.AtomicTask.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.AtomicTask.Service.TaskPageService;

@RestController
public class TaskPageController {
	
	@Autowired
	TaskPageService taskPage;
	
	@GetMapping("/getPages/{userId}")
	public String getPages(@PathVariable String userId) {
		try {
			return taskPage.getTaskPagesbyUserID();
		}catch (Exception e) {
			return "Error: "+e.getMessage();
		}
	}
}