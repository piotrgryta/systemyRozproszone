package pl.agh.bookstore.services;

import java.util.LinkedList;

import pl.agh.bookstore.model.Book;

public interface TagService {
	
	public LinkedList<Book> getRecommendedBooks(int userId);
	public LinkedList<Book> getMostPopularBooks();
	public LinkedList<Book> getMostRecentBooks();
	
	public void feedUserRecommendations(int bookId, int userId);

}
