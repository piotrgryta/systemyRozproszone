package pl.agh.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.agh.bookstore.security.model.User;
import pl.agh.bookstore.services.UserService;
import pl.agh.bookstore.validators.UserValidator;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "user/registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userForm.setUsername(userForm.getEmail());
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "user/registration";
		}
		try {
			userService.addUser(userForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView logddin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}

		model.addObject("userForm", new User());
		model.setViewName("user/login");

		return model;

	}
	
    @RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    public String welcome(Model model) {
      SecurityContextHolder.getContext().setAuthentication(null) ;

      return "redirect:/";
    }

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(User user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", "You do not have permission to access this page!");
		}

		model.setViewName("templates/403");
		return model;

	}
}
