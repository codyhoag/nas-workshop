package com.liferay.todo.test;

import com.liferay.todo.impl.SimpleTodoServiceImpl;
import com.liferay.todo.model.Todo;
import com.liferay.todo.service.TodoLocalService;

import java.util.concurrent.atomic.AtomicBoolean;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class SimpleTodoServiceMockTest {

	@Test
	public void testSimpleTodoServiceMock() throws Exception {
		Todo todo = mockTodo();

		SimpleTodoServiceImpl impl = mockSimpleTodoServieImpl();

		Assert.assertEquals(false, todo.isCompleted());

		impl.markCompleted(todo);

		Assert.assertTrue(todo.isCompleted());
	}

	private Todo mockTodo() {
		Todo todo = EasyMock.mock(Todo.class);

		AtomicBoolean completed = new AtomicBoolean(false);

		todo.setCompleted(EasyMock.anyBoolean());

		EasyMock.expectLastCall().andAnswer(() -> {
			boolean parm = (boolean) EasyMock.getCurrentArguments()[0];
			completed.set(parm);

			return null;
		});

		EasyMock.expect(todo.isCompleted()).andAnswer(() -> {
			return completed.get();
		}).anyTimes();

		EasyMock.replay(todo);

		return todo;
	}

	private SimpleTodoServiceImpl mockSimpleTodoServieImpl() {
		TodoLocalService todoLocalService = EasyMock.mock(TodoLocalService.class);

		SimpleTodoServiceImpl impl = EasyMock.partialMockBuilder(SimpleTodoServiceImpl.class).createMock();

		Whitebox.setInternalState(impl, "todoLocalService", todoLocalService);

		EasyMock.replay(impl);

		return impl;
	}
}
