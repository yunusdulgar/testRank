package com.raspberry.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.raspberry.model.Item;
import com.raspberry.model.Task;

@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item,Integer> {
	List<Task> findByItemName(@Param("itemName") String itemName);
}
