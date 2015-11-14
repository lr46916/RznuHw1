package hr.fer.rznu.jdbc.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import hr.fer.rznu.jdbc.templates.userclasses.User;

public class UsersJDBCTemplateMock extends UsersJDBCTemplate {

	private Map<String, String> usersData;
	private Map<Integer, String> usersDatabaseKey;
	private AtomicInteger idGen;
	
	public UsersJDBCTemplateMock(){
		usersData = new HashMap<>();
		usersDatabaseKey = new HashMap<>();
		idGen = new AtomicInteger();
	}

	public void setUsers(Map<String, String> usersData, Map<Integer, String> userDatabaseKey) {
		this.usersData = usersData;
		this.usersDatabaseKey = userDatabaseKey;
		idGen = new AtomicInteger(Collections.max(userDatabaseKey.keySet())+1);
	}
	
	@Override
	public void setDataSource(DataSource ds) {
		// Nothing this is a mock
	}

	@Override
	public void create(String name, String passowrdHash) {
		if (!usersData.containsKey(name)) {
			usersData.put(name, passowrdHash);
			int id = idGen.getAndIncrement();
			usersDatabaseKey.put(id, name);
		}
	}

	@Override
	public User getUser(Integer id) {
		if(usersDatabaseKey.containsKey(id)){
			User user = new User();
			user.setId(id);
			user.setUsername(usersDatabaseKey.get(id));
			user.setPasswordhash(user.getUsername());
			return user;
		}
		return null;
	}

	@Override
	public User getUser(String username) {
		if (usersData.containsKey(username)) {
			User user = new User();
			user.setId(findId(username));
			user.setUsername(username);
			user.setPasswordhash(usersData.get(username));
			return user;
		} else
			return null;
	}

	private Integer findId(String username) {
		for(int key : usersDatabaseKey.keySet()){
			if(username.equals(usersDatabaseKey.get(key)))
				return key;
		}
		return null;
	}

	@Override
	public List<User> listUsers() {
		List<User> lst = new ArrayList<>();

		for (int id : usersDatabaseKey.keySet()) {
			User user = new User();
			user.setId(id);
			user.setUsername(usersDatabaseKey.get(id));
			user.setPasswordhash(usersData.get(usersDatabaseKey.get(id)));
			lst.add(user);
		}

		return lst;
	}

	@Override
	public void delete(Integer id) {
		usersData.remove(usersDatabaseKey.get(id));
		usersDatabaseKey.remove(id);
	}

	@Override
	public void delete(String username) {
		usersData.remove(username);
		usersDatabaseKey.remove(findId(username));
	}

	@Override
	public void update(Integer id, String username, String passwordHash) {
		// not used
	}

}
