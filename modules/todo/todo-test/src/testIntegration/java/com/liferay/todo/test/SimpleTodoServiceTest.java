package com.liferay.todo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.todo.api.SimpleTodoService;
import com.liferay.todo.model.Todo;
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
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

@RunWith(Arquillian.class)
public class SimpleTodoServiceTest {

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
	public void testSimpleTodoService() throws Exception {
		ServiceTracker<SimpleTodoService, SimpleTodoService> st = ServiceTrackerFactory.create(FrameworkUtil.getBundle(SimpleTodoServiceTest.class), SimpleTodoService.class);
		st.open();
		SimpleTodoService simpleTodoService = st.getService();
		
		Assert.assertNotNull(simpleTodoService);
		
		Todo todo = addTodo();
		
		simpleTodoService.markCompleted(todo);
		
		Assert.assertTrue(todo.isCompleted());
	}
	
	protected Todo addTodo() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Todo todo = _persistence.create(pk);

		todo.setUuid(RandomTestUtil.randomString());

		todo.setGroupId(RandomTestUtil.nextLong());

		todo.setCompanyId(RandomTestUtil.nextLong());

		todo.setUserId(RandomTestUtil.nextLong());

		todo.setUserName(RandomTestUtil.randomString());

		todo.setCreateDate(RandomTestUtil.nextDate());

		todo.setModifiedDate(RandomTestUtil.nextDate());

		todo.setTitle(RandomTestUtil.randomString());

		todo.setCompleted(false);

		_todos.add(_persistence.update(todo));

		return todo;
	}

	private List<Todo> _todos = new ArrayList<Todo>();
	private TodoPersistence _persistence;
}
