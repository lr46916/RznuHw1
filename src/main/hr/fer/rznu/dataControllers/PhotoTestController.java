package hr.fer.rznu.dataControllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhotoTestController {

	@RequestMapping(value = "/users/{username}/photos")
	public String testMe(HttpSession session, @PathVariable final String username, ModelMap model) {
		String sessionUsername = (String) session.getAttribute("username");
		if(sessionUsername == null || !username.equals(sessionUsername)){
			model.addAttribute("message", "You have no premission to view this page.");
		} else {
			model.addAttribute("message", "OK");
		}
		return "hello";
	}

}
