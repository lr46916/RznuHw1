package hr.fer.rznu.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
}
