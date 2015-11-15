package hr.fer.rznu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/HelloWeb-servlet.xml"})
@WebAppConfiguration
public class ApiControllerTest {

	private MockMvc mvcMock;

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
		getReq.header("User-Agent", "firefox");

		ResultActions result = mvcMock.perform(getReq);

		result.andExpect(status().isOk()).andExpect(view().name("api")).andExpect(forwardedUrl("/WEB-INF/jsp/api.jsp"));
	}

}
