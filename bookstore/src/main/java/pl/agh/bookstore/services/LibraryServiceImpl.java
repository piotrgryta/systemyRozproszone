package pl.agh.bookstore.services;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pl.agh.bookstore.connection.DButil;
import pl.agh.bookstore.model.Book;
import pl.agh.bookstore.model.dbtables.BooksTable;

public class LibraryServiceImpl implements LibraryService {

	@Override
	public List<Book> getAllBooks() {
		String query = "Select * from books.books";
		List<Book> books = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setAuthor_id(rs.getInt(BooksTable.AUTHOR_ID.getColumnName()));
				book.setBook_id(rs.getInt(BooksTable.BOOK_ID.getColumnName()));
				book.setGenre_id(rs.getInt(BooksTable.GENRE_ID.getColumnName()));
				book.setName(rs.getString(BooksTable.NAME.getColumnName()));
				book.setPremiereDate(rs.getDate(BooksTable.PREMIERE_DATE.getColumnName()));
				book.setUnitprice(rs.getDouble(BooksTable.UNITPRICE.getColumnName()));
				book.setUnitsInStock(rs.getInt(BooksTable.UNITS_IN_STOCK.getColumnName()));
				book.setViewing(rs.getInt(BooksTable.VIEWING.getColumnName()));
				File image = new File("");
				FileUtils.writeByteArrayToFile(image, rs.getBytes(BooksTable.IMAGE.getColumnName()));
				book.setImage(image);
				books.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;	rs = null; ps = null;
		}
		return books;
	}

	@Override
	public Book getBookById(int bookId) {
		Book book = new Book();
		try {
			Connection con = DButil.getConnection();
			book = getBookById(bookId, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	@Override
	public Book getBookById(int bookId, Connection con) {
		StringBuffer query = new StringBuffer("Select * from books.books where ");
		query.append(BooksTable.BOOK_ID.getColumnName() + " = ?");
		Book book = new Book();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, bookId);
			rs = ps.executeQuery();
			while (rs.next()) {
				book.setAuthor_id(rs.getInt(BooksTable.AUTHOR_ID.getColumnName()));
				book.setBook_id(rs.getInt(BooksTable.BOOK_ID.getColumnName()));
				book.setGenre_id(rs.getInt(BooksTable.GENRE_ID.getColumnName()));
				book.setName(rs.getString(BooksTable.NAME.getColumnName()));
				book.setPremiereDate(rs.getDate(BooksTable.PREMIERE_DATE.getColumnName()));
				book.setUnitprice(rs.getDouble(BooksTable.UNITPRICE.getColumnName()));
				book.setUnitsInStock(rs.getInt(BooksTable.UNITS_IN_STOCK.getColumnName()));
				book.setViewing(rs.getInt(BooksTable.VIEWING.getColumnName()));
				File image = new File("");
				FileUtils.writeByteArrayToFile(image, rs.getBytes(BooksTable.IMAGE.getColumnName()));
				book.setImage(image);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;	rs = null; ps = null;
		}		
		return book;
	}

	@Override
	public void addBook(Book book) {
		StringBuilder query = new StringBuilder("Insert into books.books(");
		query.append(BooksTable.GENRE_ID.getColumnName() + ",");
		query.append(BooksTable.AUTHOR_ID.getColumnName() + ",");
		query.append(BooksTable.NAME.getColumnName() + ",");
		query.append(BooksTable.UNITPRICE.getColumnName() + ",");
		query.append(BooksTable.UNITS_IN_STOCK.getColumnName() + ",");
		query.append(BooksTable.PREMIERE_DATE.getColumnName() + ",");
		query.append(BooksTable.VIEWING.getColumnName() + ",");
		query.append(BooksTable.IMAGE.getColumnName() + ") ");
		query.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, book.getGenre_id());
			ps.setInt(2, book.getAuthor_id());
			ps.setString(3, book.getName());
			ps.setDouble(4, book.getUnitprice());
			ps.setInt(5, book.getUnitsInStock());
			ps.setDate(6, new java.sql.Date(book.getPremiereDate().getTime()));
			ps.setInt(7, book.getViewing());
			FileInputStream fis = new FileInputStream(book.getImage());
			ps.setBinaryStream(2, fis, book.getImage().length());
			ps.executeUpdate();
			con.commit();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;	ps = null; 
		}
	}

	@Override
	public void updateBook(int bookId, Book book) {
		StringBuilder query = new StringBuilder("Update books.books SET ");
		query.append(BooksTable.GENRE_ID.getColumnName() + " = ?, ");
		query.append(BooksTable.AUTHOR_ID.getColumnName() + " = ?, ");
		query.append(BooksTable.NAME.getColumnName() + " = ?, ");
		query.append(BooksTable.UNITPRICE.getColumnName() + " = ?, ");
		query.append(BooksTable.UNITS_IN_STOCK.getColumnName() + " = ?, ");
		query.append(BooksTable.PREMIERE_DATE.getColumnName() + " = ?, ");
		query.append(BooksTable.VIEWING.getColumnName() + " = ?, ");
		query.append(BooksTable.IMAGE.getColumnName() + " = ?");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, book.getGenre_id());
			ps.setInt(2, book.getAuthor_id());
			ps.setString(3, book.getName());
			ps.setDouble(4, book.getUnitprice());
			ps.setInt(5, book.getUnitsInStock());
			ps.setDate(6, new java.sql.Date(book.getPremiereDate().getTime()));
			ps.setInt(7, book.getViewing());
			FileInputStream fis = new FileInputStream(book.getImage());
			ps.setBinaryStream(2, fis, book.getImage().length());
			ps.executeUpdate();
			con.commit();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;	ps = null; 
		}
	}

}
