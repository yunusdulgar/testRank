package com.raspberry.RaspiTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

	private static final Logger LOGGER = Logger.getLogger(TaskController.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/taskController")
	public void create(@RequestParam(value = "taskName") String taskName) throws ParseException {
		Task task = (Task) taskRepository.findByTaskName(taskName).get(0);
		createFinishTask(task, null);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		LOGGER.error("DateType : " + task.getDateType());
		
		
		if (task.getDateType().equals("G")) {
			LOGGER.error("NOT " + task.getTaskDay());
			int dayCount = 0;
			for (int i = task.getTaskDay(); i < 7; i++) {
				dayCount++;
				Task newTask = taskService.createNewTask(task, dayCount, i);

				Date date = formatter.parse(task.getFinishTaskDateAsString());
				createFinishTask(newTask, DateUtils.addDays(date,dayCount));
			}

			LOGGER.error("HEYO");
		}
	}

	@SuppressWarnings("deprecation")
	private void createFinishTask(Task task, Date date) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Task finishTask = new Task();
		finishTask.setTaskName(task.getTaskName() + "-Finish");
		finishTask.setTaskDescription(task.getTaskDescription());
		finishTask.setTaskPriority("OFF");
		finishTask.setTaskStatus(task.getTaskStatus());
		DateFormat df = new SimpleDateFormat("EEE, HH:mm:ss");
		if (date == null) {
			finishTask.setTaskDate(formatter.parse(task.getFinishTaskDateAsString()));
			finishTask.setTaskDay(formatter.parse(task.getFinishTaskDateAsString()).getDay());
			finishTask.setTaskDescription(
					task.getTaskName() + " Bitiş: " + df.format(formatter.parse(task.getFinishTaskDateAsString())));
		} else {
			finishTask.setTaskDate(date);
			finishTask.setTaskDay(date.getDay());
			finishTask.setTaskDescription(task.getTaskName() + " Bitiş: " + df.format(date));
		}

		taskRepository.save(finishTask);
	}

}
