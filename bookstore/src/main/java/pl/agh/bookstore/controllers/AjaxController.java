package pl.agh.bookstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.agh.bookstore.model.Book;
import pl.agh.bookstore.security.model.User;
import pl.agh.bookstore.services.TagService;
import pl.agh.bookstore.services.UserService;



@Controller
public class AjaxController {

	@Autowired
	UserService userService;
	
	@Autowired
	TagService tagService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/ajax/recommended")
	public List<Book> getRecommendedUserBooks(){
		User user = userService.getUser();
		List<Book> books = tagService.getRecommendedBooks(user.getObjectid());
		if (user == null || nullOrEmpty(books)){
			books = tagService.getMostPopularBooks();
		}
		if (user == null || nullOrEmpty(books)){
			books = tagService.getMostRecentBooks();
		}
		return books;
	}
	
	
	public <T> boolean nullOrEmpty(List<T> list){
		if (list == null || list.isEmpty()){
			return true;
		}
		return false;
	}
}
