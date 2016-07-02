package com.raspberry.RaspiTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableScheduling
public class HomeController {
	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private LedController ledController;

	@RequestMapping("/home")
	public String home() {
		LOGGER.error("HOMEEE");
		return "index";
	}

	@RequestMapping("/init")
	public void init() {

		for (int i = 0; i < 6; i++) {
			Task task = new Task();
			task.setTaskName("taskName" + i);
			task.setTaskDescription("taskDescription" + i);
			task.setTaskStatus("ACTIVE");
			task.setTaskPriority("GREEN");
			if (i > 2) {
				task.setTaskPriority("RED");
			}
			if (i > 4) {
				task.setTaskPriority("YELLOW");
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
			Date date = new Date();
			task.setStartTaskDate(date.toString());
			task.setFinishTaskDate(date.toString());
			task.setTaskDay(date.getDay());

			taskRepository.save(task);
		}

	}

	@Scheduled(fixedRate = 5000)
	public void passive() {
		LOGGER.error("passive");
		List<Task> taskList = taskRepository.findByTaskStatus("ACTIVE");
		if (taskList.isEmpty()) {
			return;
		}

		for (Task task : taskList) {
			if (DateUtils.isSameInstant(DateUtils.truncate(new Date(), Calendar.MINUTE),
					DateUtils.truncate(task.getTaskDate(), Calendar.MINUTE))) {
				LOGGER.error("TASK NAME {} " + task.getTaskName());
				LOGGER.error("TASK NAME {} " + task.getTaskPriority());
				task.setTaskStatus("COMPLETED");
				if (StringUtils.equals(task.getTaskPriority(), "GREEN")) {
					LOGGER.error("GREEN");
					ledController.greeting("green");
				} else if (StringUtils.equals(task.getTaskPriority(), "RED")) {
					LOGGER.error("RED");
					ledController.greeting("red");
				} else {
					LOGGER.error("OFF");
					ledController.greeting("off");
				}
				taskRepository.save(task);
			}

		}
	}

	@RequestMapping("/getPassive")
	public ResponseEntity<List<Task>> getPassive() {
		LOGGER.error("getPassive");

		return new ResponseEntity<List<Task>>(taskRepository.findByTaskArchived(1), HttpStatus.OK);
	}

	@RequestMapping("/getAll")
	public ResponseEntity<List<Task>> getAll() {
		LOGGER.error("getAll");

		return new ResponseEntity<List<Task>>(taskRepository.findByTaskArchived(0), HttpStatus.OK);
	}
}
