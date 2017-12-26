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
	
	@RequestMapping(value = "/myprofile", method = RequestMethod.GET)
	public ModelAndView myProfile(){
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>Profil page</h3></div><br><br>";
		return new ModelAndView("main", "message", message);
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView orderDetails(){
		
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String orderDetailsSubmit(){
		
		return "redirect:/";
	}
}
