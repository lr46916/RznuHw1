package hr.fer.rznu.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import hr.fer.rznu.jdbc.templates.PostsJDBCTemplate;
import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.postclasses.Post;
import hr.fer.rznu.jdbc.templates.userclasses.User;
import hr.fer.rznu.json.JSONViewFromater;

@Controller
public class PostsController {

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String getPosts(ModelMap model) {

		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		List<Post> postList = posts.listposts();

		model.addAttribute("posts", postList);
		
		return "posts";
	}
	
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
	public String getPost(@PathVariable final int postId, ModelMap model, HttpServletResponse response) {
		
		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		Post post = posts.getpost(postId);
		
		if(post != null){
			List<Post> postList = Collections.singletonList(post);
			model.addAttribute("posts", postList);
		} else {
			response.setStatus(404);
		}

		return "posts";
	}

	@RequestMapping(value = "/users/{username}/posts", method = RequestMethod.GET)
	public String getUserPosts(@PathVariable final String username, ModelMap model) {

		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		List<Post> postList = posts.listposts(username);

		model.addAttribute("posts", postList);
		
		model.addAttribute("disablePosting", true);
		
		return "posts";
	}

	@RequestMapping(value = "/posts/update", method = RequestMethod.GET)
	public String updatePostView(@RequestParam(value = "postId", required = true) int postId, HttpSession session,
			HttpServletResponse response, ModelMap model) {

		if (checkInvalidAccess(postId, session, response)) {
			return null;
		} else {

			PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

			Post post = posts.getpost(postId);

			model.addAttribute("post", post);

			return "updatePost";
		}
	}

	@RequestMapping(value = "/**/posts", method = RequestMethod.PUT)
	public void createPost(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {
		String sessionUsername = (String) session.getAttribute("username");

		if (sessionUsername == null) {
			sendForbbidenAccess(response);
			return;
		}
		
		BufferedReader is = request.getReader();

		char[] data = new char[256];

		int res = is.read(data);

		String posttext = new String(Arrays.copyOf(data, res));
		
		UsersJDBCTemplate users = (UsersJDBCTemplate) appContext.getBean("usersJDBCTemplate");
		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");
		
		User user = users.getUser(sessionUsername);
		
		int key = posts.create(user.getId(), posttext);
		
		model.addAttribute("post", posts.getpost(key));
		
		response.setContentType("application/json");
		response.setStatus(201);
		JSONViewFromater.format(model, response);
	}

	@RequestMapping(value = "/**/posts/{postId}", method = RequestMethod.PUT)
	public void updatePost(@PathVariable final int postId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (checkInvalidAccess(postId, session, response)) {
			return;
		}

		BufferedReader is = request.getReader();

		char[] data = new char[256];

		int res = is.read(data);

		String posttext = new String(Arrays.copyOf(data, res));

		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		Post post = posts.getpost(postId);

		if (post != null) {
			posts.update(postId, posttext);
			response.setStatus(205);
		} else {
			response.setStatus(304);
		}
	}
	
	@RequestMapping(value = "/**/posts/{postId}", method = RequestMethod.DELETE)
	public void deletePost(@PathVariable final int postId, HttpSession session, HttpServletResponse response) {

		if (checkInvalidAccess(postId, session, response)) {
			return;
		}

		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		posts.delete(postId);
		
		response.setStatus(204);
	}

	private boolean checkInvalidAccess(int postId, HttpSession session, HttpServletResponse response) {
		String sessionUsername = (String) session.getAttribute("username");

		if (sessionUsername == null) {
			sendForbbidenAccess(response);
			return true;
		}

		PostsJDBCTemplate posts = (PostsJDBCTemplate) appContext.getBean("postsJDBCTemplate");

		if (!isUsersPost(postId, sessionUsername, posts)) {
			sendForbbidenAccess(response);
			return true;
		}
		return false;
	}

	private boolean isUsersPost(int postId, String username, PostsJDBCTemplate posts) {
		Post post = posts.getpost(postId);

		return post != null && post.getUsername().equals(username);
	}

	private void sendForbbidenAccess(HttpServletResponse response) {
		try {
			response.sendError(403, "You have no premission to perform this action.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
