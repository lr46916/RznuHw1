package hr.fer.rznu.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hr.fer.rznu.jdbc.templates.UsersJDBCTemplate;
import hr.fer.rznu.jdbc.templates.userclasses.User;

@Controller
// @RequestMapping({ "/logIn", "/logOut" })
public class AuthenticationController {

	@Autowired
	private ApplicationContext appContext;
	
	public final static String successfulyRegMsg = "Successfully registered. You can now log in with chosen username and password.";
	public final static String occupiedUsernameMsg = "Username already taken. Please choose another one.";
	public final static String logInFailMsg = "Login failed. Invalid username or password.";
	public final static String succesfulLogInMsgFormat = "Welcome %s! Enjoy jour stay.";
	public final static String logOutFailMsg = "You must first log in in order to be able to log out.";
	public final static String logOutSuccesMsgFormat = "Log out successful. Goodbye  %s.";
	
	@RequestMapping(value = "/register")
	public String register(HttpSession session, ModelMap model) {
		return "register";
	}

	@RequestMapping(value = "/registerRequest")
	public String processRegistration(HttpSession session,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = false) String password, ModelMap model) {
		
		UsersJDBCTemplate users = (UsersJDBCTemplate) appContext.getBean("usersJDBCTemplate");

		User user = users.getUser(username);

		List<String> messages = new ArrayList<>();

		if (user != null) {
			model.addAttribute("message", occupiedUsernameMsg);
			return "register";
		} else {
			users.create(username, password);
			messages.add(successfulyRegMsg);
			model.addAttribute("messages", messages);
			return "infMsgAndRedirect";
		}
	}

	@RequestMapping(value = "/logIn")
	public String logInView(HttpSession session, ModelMap model) {
		String sessionUsername = (String) session.getAttribute("username");
		if (sessionUsername != null) {
			return handleUserAlreadyLoggedIn(sessionUsername, model);
		}
		return "logInPage";
	}

	@RequestMapping(value = "/logInRequest", method = RequestMethod.POST)
	public String processLogInt(HttpSession session, @RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password, ModelMap model) {

		model.addAttribute("title", "Log in response");

		List<String> messages = new ArrayList<>();
		String sessionUsername = (String) session.getAttribute("username");

		if (sessionUsername != null) {
			return handleUserAlreadyLoggedIn(sessionUsername, model);
		}

		UsersJDBCTemplate users = (UsersJDBCTemplate) appContext.getBean("usersJDBCTemplate");

		User user = users.getUser(username);
		
		if (user != null && password.equals(user.getPasswordhash())) {
			session.setAttribute("username", username);
			messages.add(String.format(succesfulLogInMsgFormat, username));
			model.addAttribute("messages", messages);
		} else {
			model.addAttribute("message", logInFailMsg);
			return "logInPage";
		}
		return "infMsgAndRedirect";
	}

	@RequestMapping("/logOut")
	public String processLogOut(HttpSession session, ModelMap model) {

		model.addAttribute("title", "Log out response");

		List<String> messages = new ArrayList<>();

		if (session.getAttribute("username") == null) {
			messages.add(logOutFailMsg);
		} else {
			messages.add(String.format(logOutSuccesMsgFormat, session.getAttribute("username")));
			session.invalidate();
		}
		model.addAttribute("messages", messages);
		return "infMsgAndRedirect";
	}

	private String handleUserAlreadyLoggedIn(String username, ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Already logged in as \"" + username + "\"");
		messages.add("Please log out if you wish to log in with another username.");
		model.addAttribute("messages", messages);
		model.addAttribute("delay", "6000");
		return "infMsgAndRedirect";
	}

}
