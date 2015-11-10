package hr.fer.rznu.jdbc.templates.userclasses;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User student = new User();
		student.setId(rs.getInt("id"));
		student.setUsername(rs.getString("username"));
		student.setPasswordhash(rs.getString("passwordhash"));
		return student;
	}

}
