package hr.fer.rznu.jdbc.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;


import hr.fer.rznu.jdbc.templates.postclasses.Post;
import hr.fer.rznu.jdbc.templates.userclasses.User;

public class PostsJDBCTemplateMock extends PostsJDBCTemplate {

	private Map<Integer, PostData> posts;
	private UsersJDBCTemplate users;
	private AtomicInteger idGen;

	public PostsJDBCTemplateMock(UsersJDBCTemplate users) {
		posts = new HashMap<>();
		idGen = new AtomicInteger();
		this.users = users;
	}
	
	public void setUsers(UsersJDBCTemplate users){
		this.users = users;
	}
	
	public void setData(Map<Integer, PostData> posts, UsersJDBCTemplateMock users) {
		this.posts = posts;
		this.users = users;
		idGen = new AtomicInteger(Collections.max(posts.keySet()) + 1);
	}
	
	

	@Override
	public void setDataSource(DataSource ds) {
		// nothing in mock
	}

	@Override
	public int create(int userId, String post) {
		User user = users.getUser(userId);
		int key = idGen.getAndIncrement();
		posts.put(key, new PostData(user.getUsername(), post));
		return key;
	}

	private Post createPost(int id) {
		PostData pd = posts.get(id);
		Post post = new Post();
		post.setPostId(id);
		post.setUsername(pd.username);
		post.setPostText(pd.text);
		return post;
	}

	@Override
	public Post getpost(Integer id) {
		if (posts.containsKey(id)) {
			return createPost(id);
		}
		return null;
	}

	@Override
	public List<Post> listposts() {
		List<Post> posts = new ArrayList<>();

		for (int key : this.posts.keySet()) {
			posts.add(createPost(key));
		}

		return posts;
	}

	@Override
	public List<Post> listposts(String username) {
		List<Post> posts = new ArrayList<>();

		for (int key : this.posts.keySet()) {
			if (this.posts.get(key).username.equals(username))
				posts.add(createPost(key));
		}

		return posts;
	}

	@Override
	public void delete(Integer id) {
		posts.remove(id);
	}

	@Override
	public void update(Integer id, Integer userId, String post) {
		User user = users.getUser(userId);
		posts.put(id, new PostData(user.getUsername(), post));
	}

	@Override
	public void update(Integer id, String posttext) {
		PostData pd = posts.get(id);
		pd.text = posttext;
	}

	public static class PostData {
		public String username;
		public String text;

		public PostData(String username, String text) {
			super();
			this.username = username;
			this.text = text;
		}

	}
}
