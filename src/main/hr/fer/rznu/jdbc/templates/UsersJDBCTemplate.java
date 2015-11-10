package hr.fer.rznu.jdbc.templates;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import hr.fer.rznu.jdbc.templates.userclasses.User;
import hr.fer.rznu.jdbc.templates.userclasses.UserMapper;
import hr.fer.rznu.jdbc.templates.userclasses.UsersDAO;

public class UsersJDBCTemplate implements UsersDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String username, String passwordHash) {
		String SQL = "insert into UserTable (username, passwordhash) values (?, ?)";
		jdbcTemplateObject.update(SQL, username, passwordHash);
	}

	@Override
	public User getUser(Integer id) {
		String SQL = "select * from UserTable where id = ?";
		User student = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserMapper());
		return student;
	}

	@Override
	public User getUser(String username) {
		String SQL = "select * from UserTable where username = ?";
		User student = null;
		try {
			student = jdbcTemplateObject.queryForObject(SQL, new Object[] { username }, new UserMapper());
		} catch (DataAccessException e) {
			student = null;
		}
		return student;
	}

	@Override
	public List<User> listUsers() {
		String SQL = "select * from UserTable";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from UserTable where id = ?";
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public void update(Integer id, String username, String passwordhash) {
		String SQL = "update UserTable set username = ?, passwordhash = ? where id = ?";
		jdbcTemplateObject.update(SQL, username, passwordhash, id);
	}

}
