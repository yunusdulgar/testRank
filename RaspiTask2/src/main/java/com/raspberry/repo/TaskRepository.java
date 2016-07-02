package com.raspberry.RaspiTask;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TaskRepository extends CrudRepository<Task, Integer> {

	List<Task> findByTaskArchived(@Param("archivedfalse") int taskArchivedFalse);

	List<Task> findByTaskStatus(@Param("status") String taskStatus);
	
	List<Task> findByTaskName(@Param("taskName") String taskName);

	List<Task> findByTaskDayAndTaskArchived(@Param("taskDay") int taskDay,@Param("archivedfalse") int taskArchivedFalse);


}
