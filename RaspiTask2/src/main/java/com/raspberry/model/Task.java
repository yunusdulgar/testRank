package com.raspberry.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.raspberry.controller.HomeController;

@Entity
@Table(name = "task_list")
public class Task {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private int id;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_description")
	private String taskDescription;

	@Column(name = "task_priority")
	private String taskPriority;

	@Column(name = "task_status")
	private String taskStatus;

	@Column(name = "task_archived")
	private int taskArchived = 0;

	@Column(name = "start_task_date")
	private String startTaskDate;

	@Column(name = "finish_task_date")
	private String finishTaskDate;

	@Column(name = "finish_task_date_string")
	private String finishTaskDateAsString;

	@Column(name = "taskDate")
	private Date taskDate;

	@Column(name = "taskDay")
	private int taskDay;

	@Column(name = "dateType")
	private String dateType;

	@Column(name = "taskItemName")
	private String itemName;

	public int getTaskDay() {
		return taskDay;
	}

	public void setTaskDay(int taskDay) {
		this.taskDay = taskDay;
	}

	public String getFinishTaskDateAsString() {
		return finishTaskDateAsString;
	}

	public void setFinishTaskDateAsString(String finishTaskDateAsString) {
		this.finishTaskDateAsString = finishTaskDateAsString;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {

		this.dateType = dateType;
	}

	public int getTaskId() {
		return id;
	}

	public void setTaskId(int taskId) {
		this.id = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		LOGGER.error("TASK SET TASK NAME " + taskName);
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int isTaskArchived() {
		return taskArchived;
	}

	public void setTaskArchived(int taskArchived) {
		this.taskArchived = taskArchived;
	}

	public String getStartTaskDate() {
		return startTaskDate;
	}

	@SuppressWarnings("deprecation")
	public void setStartTaskDate(String startTaskDate) {
		LOGGER.error("input setStartTaskDate : " + startTaskDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(startTaskDate);
		} catch (ParseException e) {
			LOGGER.error("ParseException");
		}

		DateFormat df = new SimpleDateFormat("EEE, HH:mm:ss");
		if (date == null) {
			date = new Date();
			LOGGER.error("date==null");
		}
		setTaskDate(date);
		setTaskDay(date.getDay());
		setTaskDescription(getTaskName() + " Başla: " + df.format(date));
		LOGGER.error("output setStartTaskDate : " + df.format(date));
		this.startTaskDate = df.format(date);
	}

	public String getFinishTaskDate() {
		return finishTaskDate;
	}

	public void setFinishTaskDate(String finishTaskDate) {
		LOGGER.error("input setfinishTaskDate : " + finishTaskDate);
		setFinishTaskDateAsString(finishTaskDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(finishTaskDate);
		} catch (ParseException e) {
			LOGGER.error("ParseException");
		}
		DateFormat df = new SimpleDateFormat("EEE, HH:mm:ss");
		if (date == null) {
			date = new Date();
			LOGGER.error("date==null");
		}
		LOGGER.error("output setfinishTaskDate : " + df.format(date));
		this.finishTaskDate = df.format(date);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", taskDescription=" + taskDescription + ",taskStatus="
				+ taskStatus + "]";
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
