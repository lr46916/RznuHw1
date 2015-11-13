package hr.fer.rznu.jdbc.templates;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import hr.fer.rznu.jdbc.PostsDAO;
import hr.fer.rznu.jdbc.templates.postclasses.Post;
import hr.fer.rznu.jdbc.templates.postclasses.PostMapper;

public class PostsJDBCTemplate implements PostsDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void create(int userId, String post) {
		String SQL = "insert into PostTable (userId, postText) values (?, ?)";
		jdbcTemplateObject.update(SQL,userId, post);
	}

	@Override
	public Post getpost(Integer id) {
		String SQL = "select * from UserTable JOIN PostTable ON id = userId where postid = ?";
		Post post = null;
		try{
			post = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new PostMapper());
		} catch (DataAccessException e){
			//igonre
		}
		return post;
	}

	@Override
	public List<Post> listposts() {
		String SQL = "select * from UserTable JOIN PostTable ON id = userId";
		List<Post> posts = jdbcTemplateObject.query(SQL, new PostMapper());
		return posts;
	}

	@Override
	public List<Post> listposts(String username) {
		String SQL = "select * from UserTable JOIN PostTable ON id = userId where username = ?";
		List<Post> posts = jdbcTemplateObject.query(SQL, new PostMapper(), username);
		return posts;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from PostTable where postid = ?";
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public void update(Integer id, Integer userId, String post) {
		String SQL = "update PostTable set userid = ?, postText = ? where postid = ?";
		jdbcTemplateObject.update(SQL, userId, post, id);
	}
	
	@Override
	public void update(Integer id, String posttext) {
		String SQL = "update PostTable set postText = ? where postid = ?";
		jdbcTemplateObject.update(SQL, posttext, id);
	}

}
