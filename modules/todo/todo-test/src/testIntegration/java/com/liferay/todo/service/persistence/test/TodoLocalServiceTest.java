/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.todo.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.todo.model.Todo;
import com.liferay.todo.service.TodoLocalServiceUtil;
import com.liferay.todo.service.persistence.TodoPersistence;
import com.liferay.todo.service.persistence.TodoUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TodoLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.todo.service"));

	@Before
	public void setUp() {
		_persistence = TodoUtil.getPersistence();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Todo> iterator = _todos.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testAddBasicTodo() throws Exception {
		Todo todo = TodoLocalServiceUtil.addBasicTodo("new todo");
		
		Assert.assertNotNull(todo);
		
		_todos.add(todo);
		
		Assert.assertEquals("new todo", todo.getTitle());
		Assert.assertFalse(todo.isCompleted());
	}
	
	private List<Todo> _todos = new ArrayList<Todo>();
	private TodoPersistence _persistence;
}