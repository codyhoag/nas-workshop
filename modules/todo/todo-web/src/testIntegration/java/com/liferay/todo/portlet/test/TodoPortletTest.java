package com.liferay.todo.portlet.test;

import com.liferay.arquillian.portal.annotation.PortalURL;
import com.liferay.todo.portlet.constants.TodoPortletKeys;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunAsClient
@RunWith(Arquillian.class)
public class TodoPortletTest {

	@Deployment
	public static JavaArchive create() throws Exception {
		final File jarFile = new File(System.getProperty("jarFile"));

		return ShrinkWrap.createFromZipFile(JavaArchive.class, jarFile);
	}

	@Test
	public void testAddPortlet() throws Exception {
		_webDriver.get(_portletURL.toExternalForm());

		Assert.assertTrue(_tasksPortlet.isDisplayed());

		Assert.assertTrue(
			_message.getText(),
			_message.getText().contentEquals(
				"Hello from todo-web JSP!"));
	}

	@FindBy(xpath = "//div[@class='portlet-body']/p[1]")
	private WebElement _message;

	@FindBy(xpath = "//div[contains(@id,'" + TodoPortletKeys.Todo + "')]")
	private WebElement _tasksPortlet;

	@Drone
	private WebDriver _webDriver;

	@PortalURL(TodoPortletKeys.Todo)
	private URL _portletURL;
}
