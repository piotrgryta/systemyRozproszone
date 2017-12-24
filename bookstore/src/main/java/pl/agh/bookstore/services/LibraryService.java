package pl.agh.bookstore.services;

import java.sql.Connection;
import java.util.List;

import pl.agh.bookstore.model.Book;

public interface LibraryService {
	
	public List<Book> getAllBooks();
	public Book getBookById(int bookId);
	public Book getBookById(int bookId, Connection con);
	public void addBook(Book book);
	public void updateBook(int bookId, Book book);
	
}
