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

import hr.fer.rznu.jdbc.templates.PostsJDBCTemplate;
import hr.fer.rznu.jdbc.templates.PostsJDBCTemplateMock;
import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.userclasses.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:HelloWeb-servlet.xml" })
@WebAppConfiguration
public class PostsControllerTest {

	private MockMvc mvcMock;

	@Autowired
	private WebApplicationContext webAppContext;

	private PostsJDBCTemplate postsDB;

	private UsersJDBCTemplate usersDB;

	private Map<String, String> users;

	@Before
	public void init() {
		DefaultMockMvcBuilder build = MockMvcBuilders.webAppContextSetup(webAppContext);

		mvcMock = build.build();

		postsDB = (PostsJDBCTemplate) webAppContext.getBean("postsJDBCTemplate");

		usersDB = (UsersJDBCTemplate) webAppContext.getBean("usersJDBCTemplate");

		String prefix = "user";
		String password = "testPass";

		String userTestMsg = "This is some test msg.";

		users = new HashMap<>();

		for (int i = 0; i < 5; i++) {
			String user = prefix + i;
			usersDB.create(user, password);
		}
		
		for (User user : usersDB.listUsers()) {
			users.put(user.getUsername(), password);
			postsDB.create(user.getId(), userTestMsg);
		}
		
	}

	@Test
	public void testGetPostList() throws Exception {
		mvcMock.perform(get("/posts")).andExpect(model().attribute("posts", postsDB.listposts()));
	}
	
	@Test
	public void testGetPostListByUser() throws Exception {
		String username = users.keySet().iterator().next();
		mvcMock.perform(get("/users/" + username + "/posts")).andExpect(model().attribute("posts", postsDB.listposts(username)));
	}

}
