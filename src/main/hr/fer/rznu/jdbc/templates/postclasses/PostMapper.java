package hr.fer.rznu.jdbc.templates.postclasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostMapper implements RowMapper<Post> {

	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post = new Post();
		post.setPostId(rs.getInt("postId"));
		post.setUsername("username");
		post.setPostText("postText");
		return post;
	}
}
