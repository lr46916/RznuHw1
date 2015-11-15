package hr.fer.rznu.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.UsersJDBCTemplateMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/HelloWeb-servlet.xml" })
@WebAppConfiguration
public class AuthenticationControllerTest {

	private MockMvc mvcMock;

	@Autowired
	private WebApplicationContext webAppContext;

	private String existingUser;
	private String existingUserPassword;
	private String invalidUser;
	private String newUser;
	private String newUserPass;

	public void createUsers() {

		Map<String, String> users = new HashMap<>();
		Map<Integer, String> usersIds = new HashMap<>();

		users.put(existingUser, existingUserPassword);
		usersIds.put(0, existingUser);

		((UsersJDBCTemplateMock) webAppContext.getBean("usersJDBCTemplate")).setUsers(users, usersIds);
	}

	@Before
	public void init() {
		DefaultMockMvcBuilder build = MockMvcBuilders.webAppContextSetup(webAppContext);

		mvcMock = build.build();

		existingUser = "leon";
		existingUserPassword = "1234";
		invalidUser = "xyz";
		newUser = "newUser";
		newUserPass = "blabla";

		createUsers();
	}

	@Test
	public void testLogInRequest() throws Exception {
		MockHttpServletRequestBuilder reqBuild = MockMvcRequestBuilders.post("/logInRequest")
				.header("User-Agent", "firefox").param("username", existingUser)
				.param("password", existingUserPassword);

		List<String> messages = new ArrayList<>();
		messages.add(String.format(AuthenticationController.succesfulLogInMsgFormat, existingUser));

		// if login was successful user is redirected to infMsgAndRedirect view
		mvcMock.perform(reqBuild).andExpect(view().name("infMsgAndRedirect"))
				.andExpect(model().attribute("messages", messages));

		reqBuild = MockMvcRequestBuilders.post("/logInRequest").header("User-Agent", "firefox")
				.param("username", invalidUser).param("password", "");

		// if login failed user is redirected back to logIn view with
		// appropriate message.
		mvcMock.perform(reqBuild).andExpect(view().name("logInPage"))
				.andExpect(model().attribute("message", AuthenticationController.logInFailMsg));
	}

	@Test
	public void testUserRegistration() throws Exception {
		RequestBuilder reqBuild = MockMvcRequestBuilders.post("/registerRequest").header("User-Agent", "firefox")
				.param("username", newUser).param("password", newUserPass);

		List<String> messages = new ArrayList<>();
		messages.add(AuthenticationController.successfulyRegMsg);

		mvcMock.perform(reqBuild).andExpect(view().name("infMsgAndRedirect"))
				.andExpect(model().attribute("messages", messages));

		reqBuild = MockMvcRequestBuilders.post("/logInRequest").header("User-Agent", "firefox")
				.param("username", newUser).param("password", newUserPass);
		
		messages = new ArrayList<>();
		messages.add(String.format(AuthenticationController.succesfulLogInMsgFormat, newUser));

		// if login was successful user is redirected to infMsgAndRedirect view
		mvcMock.perform(reqBuild).andExpect(view().name("infMsgAndRedirect"))
				.andExpect(model().attribute("messages", messages));
		
		// keep test state
		UsersJDBCTemplate users = (UsersJDBCTemplate) webAppContext.getBean("usersJDBCTemplate");
		users.delete(newUser);
	}

	@Test
	public void testUserLogOut() throws Exception {
		RequestBuilder reqBuild = MockMvcRequestBuilders.post("/logOut").header("User-Agent", "firefox")
				.param("username", newUser).sessionAttr("username", existingUser);

		mvcMock.perform(reqBuild).andExpect(view().name("infMsgAndRedirect"))
				.andExpect(model().attribute("messages", Collections
						.singletonList(String.format(AuthenticationController.logOutSuccesMsgFormat, existingUser))));
	}

}
