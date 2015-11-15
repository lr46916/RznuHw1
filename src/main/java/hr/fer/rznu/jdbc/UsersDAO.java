package hr.fer.rznu.jdbc;

import java.util.List;
import javax.sql.DataSource;

import hr.fer.rznu.jdbc.templates.userclasses.User;

public interface UsersDAO {
/** 
 * This is the method to be used to initialize
 * database resources ie. connection.
 */
public void setDataSource(DataSource ds);
/** 
 * This is the method to be used to create
 * a record in the Student table.
 */
public void create(String name, String passowrdHash);
/** 
 * This is the method to be used to list down
 * a record from the Student table corresponding
 * to a passed student id.
 */
public User getUser(Integer id);

/**
 * Will read user data from database and return it in User object.
 * @param username string representing username.
 * @return User object or null if user with given username does not exist.
 */
public User getUser(String username);

/** 
 * This is the method to be used to list down
 * all the records from the Student table.
 */
public List<User> listUsers();
/** 
 * This is the method to be used to delete
 * a record from the Student table corresponding
 * to a passed student id.
 */
public void delete(Integer id);

public void delete(String username);

/** 
 * This is the method to be used to update
 * a record into the Student table.
 */
public void update(Integer id, String username, String passwordHash);
}
