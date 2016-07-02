package com.raspberry.RaspiTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskService {



	private static final Logger LOGGER = Logger.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;
	
	public Task createNewTask(Task task, int dayCount, int i) throws ParseException {
		
		Task newTask = new Task();
		DateFormat df = new SimpleDateFormat("EEE, HH:mm:ss");
		newTask.setTaskName(task.getTaskName());
		LOGGER.error("newTask.getTaskPriority() " + newTask.getTaskPriority());
		newTask.setTaskPriority(task.getTaskPriority());
		newTask.setTaskStatus(task.getTaskStatus());
		LOGGER.error("DATE " + DateUtils.addDays(task.getTaskDate(),dayCount).toString());
		newTask.setTaskDate(DateUtils.addDays(task.getTaskDate(),dayCount));
		newTask.setTaskDay(DateUtils.addDays(task.getTaskDate(),dayCount).getDay());
		newTask.setTaskDescription(task.getTaskName() + " Başla: " + df.format(DateUtils.addDays(task.getTaskDate(), dayCount)));
		LOGGER.error("createNewTask");
		return taskRepository.save(newTask);
	}
}
