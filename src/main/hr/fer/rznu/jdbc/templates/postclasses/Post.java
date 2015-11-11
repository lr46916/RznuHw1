package hr.fer.rznu.jdbc.templates.postclasses;

public class Post {

	private Integer postId;
	private String username;
	private String postText;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	@Override
	public String toString(){
		return String.format("%d, %s %s", postId , username, postText);
	}
	
}
