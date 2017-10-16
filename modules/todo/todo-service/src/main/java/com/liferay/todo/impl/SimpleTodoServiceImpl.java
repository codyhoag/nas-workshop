package com.liferay.todo.impl;

import com.liferay.todo.api.SimpleTodoService;
import com.liferay.todo.model.Todo;
import com.liferay.todo.service.TodoLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class SimpleTodoServiceImpl implements SimpleTodoService {

	@Override
	public Todo markCompleted(Todo todo) {
		todo.setCompleted(true);
		
		Todo updatedTodo = todoLocalService.updateTodo(todo);
		
		return updatedTodo;
	}
	
	@Reference
	private TodoLocalService todoLocalService;

}