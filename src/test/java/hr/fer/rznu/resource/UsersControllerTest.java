package hr.fer.rznu.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/HelloWeb-servlet.xml" })
@WebAppConfiguration

public class UsersControllerTest {

	private MockMvc mvcMock;

	@Autowired
	private WebApplicationContext webAppContext;

	private UsersJDBCTemplate usersDB;

	@Before
	public void init() {
		DefaultMockMvcBuilder build = MockMvcBuilders.webAppContextSetup(webAppContext);

		mvcMock = build.build();

		usersDB = (UsersJDBCTemplate) webAppContext.getBean("usersJDBCTemplate");

		String prefix = "user";
		String password = "testPass";

		for (int i = 0; i < 5; i++) {
			String user = prefix + i;
			usersDB.create(user, password);
		}
	}

	@Test
	public void testGetUserList() throws Exception {

		mvcMock.perform(get("/users")).andExpect(status().isOk())
				.andExpect(model().attribute("users", usersDB.listUsers()));

	}

}
