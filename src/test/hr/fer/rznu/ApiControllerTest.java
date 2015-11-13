package hr.fer.rznu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:HelloWeb-servlet.xml", "classpath:Beans.xml" })
@WebAppConfiguration
public class ApiControllerTest {

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
	public void testApi() throws Exception {

		MockHttpServletRequestBuilder getReq = get("/api");
		getReq.header("User-Agent", "firefox").header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

		ResultActions result = mvcMock.perform(getReq);

		result.andExpect(status().isOk()).andExpect(view().name("api")).andExpect(forwardedUrl("/WEB-INF/jsp/api.jsp"));
	}

}
