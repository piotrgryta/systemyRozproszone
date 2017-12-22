package pl.agh.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "")
public class MainController {

	@RequestMapping(value = "/")
	public ModelAndView init(){
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>Main page</h3></div><br><br>";
		return new ModelAndView("main", "message", message);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ModelAndView admin(){
		ModelAndView model = new ModelAndView();
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>******ADMIN VIEW TEST****</div><br><br>";

		model.addObject("message", message);
		model.setViewName("admin/admin");

		return model;
	
	}

}
