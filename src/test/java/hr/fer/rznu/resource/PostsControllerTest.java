package hr.fer.rznu.resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hr.fer.rznu.jdbc.templates.PostsJDBCTemplate;
import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.postclasses.Post;
import hr.fer.rznu.jdbc.templates.userclasses.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/HelloWeb-servlet.xml" })
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
		mvcMock.perform(get("/users/" + username + "/posts"))
				.andExpect(model().attribute("posts", postsDB.listposts(username)));
	}

	@Test
	public void testPutPostByUser() throws Exception {
		String username = users.keySet().iterator().next();

		String msgContent = "New message!";

		// if user is not logged in
		mvcMock.perform(put("/posts")).andExpect(status().isForbidden());

		MvcResult resCreate = mvcMock.perform(put("/posts").sessionAttr("username", username).content(msgContent))
				.andExpect(status().isCreated()).andReturn();

		JSONObject data = (JSONObject) JSONValue.parse(resCreate.getResponse().getContentAsString());
		
		Post createdPost = new ObjectMapper().readValue(data.get("post").toString(), Post.class);

		mvcMock.perform(get("/posts/" + createdPost.getPostId()))
				.andExpect(model().attribute("posts", Collections.singletonList(createdPost)));

	}

	@Test
	public void testPostDeletion() throws Exception {
		String username = users.keySet().iterator().next();

		Post post = postsDB.listposts(username).get(0);

		mvcMock.perform(delete("/posts/" + post.getPostId())).andExpect(status().isForbidden());

		mvcMock.perform(delete("/posts/" + post.getPostId()).sessionAttr("username", username))
				.andExpect(status().isNoContent());

		@SuppressWarnings("unchecked")
		List<Post> posts = (List<Post>) mvcMock.perform(get("/posts")).andReturn().getModelAndView().getModel()
				.get("posts");

		Assert.assertFalse("Message deleted", posts.contains(post));
	}

	@Test
	public void testPostUpdate() throws Exception {
		String username = users.keySet().iterator().next();

		Post post = postsDB.listposts(username).get(0);

		String newContent = "New msg content!!";

		mvcMock.perform(put("/posts/" + post.getPostId()).content(newContent)).andExpect(status().isForbidden());

		mvcMock.perform(put("/posts/" + post.getPostId()).content(newContent).sessionAttr("username", username))
				.andExpect(status().isResetContent());

		@SuppressWarnings("unchecked")
		List<Post> posts = (List<Post>) mvcMock.perform(get("/posts/" + post.getPostId())).andReturn().getModelAndView()
				.getModel().get("posts");

		Assert.assertTrue("Message update", posts.contains(post));
	}

}
