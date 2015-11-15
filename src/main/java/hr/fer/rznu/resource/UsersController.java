package hr.fer.rznu.resource;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.userclasses.User;

@Controller
public class UsersController {
	
	@Autowired
	private ApplicationContext appContext;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsers(ModelMap model){
		
		UsersJDBCTemplate users = (UsersJDBCTemplate) appContext.getBean("usersJDBCTemplate");
		
		List<User> userList = users.listUsers();
		
		model.addAttribute("users", userList);
		
		return "users";
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String getUser(@PathVariable final int userId, ModelMap model, HttpServletResponse response){
		
		UsersJDBCTemplate users = (UsersJDBCTemplate) appContext.getBean("usersJDBCTemplate");
		
		User user = users.getUser(userId);
		
		if(user == null) {
			response.setStatus(404);
		} else {
			model.addAttribute("users", Collections.singletonList(user));
		}
		return "users";
	}
	
}
