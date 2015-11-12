package hr.fer.rznu.jdbc;

import java.util.List;

import javax.sql.DataSource;

import hr.fer.rznu.jdbc.templates.postclasses.Post;
import hr.fer.rznu.jdbc.templates.userclasses.User;

public interface PostsDAO {
	/** 
	 * This is the method to be used to initialize
	 * database resources ie. connection.
	 */
	public void setDataSource(DataSource ds);
	/** 
	 * This is the method to be used to create
	 * a record in the posts table.
	 */
	public void create(int userId, String post);
	
	/** 
	 * This is the method to be used to list down
	 * a record from the posts table corresponding
	 * to a passed post id.
	 */
	public Post getpost(Integer id);

	/** 
	 * This is the method to be used to list down
	 * all the records from the posts table.
	 */
	public List<Post> listposts();
	
	/** 
	 * This is the method to be used to list down
	 * all the records from the posts table that are owned by given user.
	 */
	public List<Post> listposts(String username);
	
	/** 
	 * This is the method to be used to delete
	 * a record from the posts table corresponding
	 * to a passed student id.
	 */
	public void delete(Integer id);

	/** 
	 * This is the method to be used to update
	 * a record into the posts table.
	 */
	public void update(Integer id, Integer userId, String post);
	
	/** 
	 * This is the method to be used to update
	 * a record into the posts table.
	 */
	public void update(Integer id, String posttext);
}
