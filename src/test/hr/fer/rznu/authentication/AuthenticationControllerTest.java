package hr.fer.rznu.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import hr.fer.rznu.logging.RznuLogger;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:HelloWeb-servlet.xml", "classpath:Beans.xml" })
@WebAppConfiguration
public class AuthenticationControllerTest {

	private MockMvc mvcMock;

	// @Autowired
	// private ApiController apiController;

	@Autowired
	private WebApplicationContext webAppContext;

	@Before
	public void init() {
		DefaultMockMvcBuilder build = MockMvcBuilders.webAppContextSetup(webAppContext);

		mvcMock = build.build();
	}

	@Test
	public void testUserLogIn() throws Exception {

		MockHttpServletRequestBuilder reqBuild = MockMvcRequestBuilders.post("/logInRequest")
					.header("User-Agent", "firefox")
					.param("username", "leon")
					.param("password", "xxx");

		mvcMock.perform(reqBuild).andExpect(view().name("infMsgAndRedirect"));
	}

}
