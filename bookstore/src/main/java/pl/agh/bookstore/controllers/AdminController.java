package pl.agh.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ModelAndView admin(){
		ModelAndView model = new ModelAndView();
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>******ADMIN VIEW TEST****</div><br><br>";

		model.addObject("message", message);
		model.setViewName("admin/admin");

		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin")
	public String adminPost(){
		
		return "redirect:/admin";
	}
}
