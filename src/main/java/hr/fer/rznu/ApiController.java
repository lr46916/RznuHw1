package hr.fer.rznu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiController {
	
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public String showApi(ModelMap model) {
		model.addAttribute("apiList", ApiDesc.getApiList());
		return "api";
	}

}
