package pl.agh.bookstore.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.bookstore.connection.DButil;
import pl.agh.bookstore.model.Book;
import pl.agh.bookstore.model.dbtables.TagsmapTable;
import pl.agh.bookstore.model.dbtables.TagsmapUserTable;

public class TagServiceImpl implements TagService{

	@Autowired
	LibraryService libraryService;
	
	@Override
	public LinkedList<Book> getRecommendedBooks(int userId) {
		LinkedList<Book> books = new LinkedList<>();
		StringBuilder query = new StringBuilder("Select t.");
		query.append(TagsmapTable.BOOK_ID.getColumnName()+ ", ");
		query.append("sum(" + TagsmapUserTable.VALUE.getColumnName() + ") as val ");
		query.append("from tags.tagsmap_user u ");
		query.append("join tags.tagsmap as t on ");
		query.append("t." + TagsmapTable.TAG_ID.getColumnName() + " = ");
		query.append("u." + TagsmapUserTable.TAG_ID.getColumnName());
		query.append(" where u." + TagsmapUserTable.USER_ID.getColumnName() + " = ?");
		query.append("group by t." + TagsmapTable.BOOK_ID.getColumnName());
		query.append(" order by val desc limit 5");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);			
			rs = ps.executeQuery();
			while (rs.next()) {
				books.add(libraryService.getBookById(rs.getInt(1), con));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;	rs = null; ps = null;
		}
		return books;
	}

	@Override
	public LinkedList<Book> getMostPopularBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Book> getMostRecentBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void feedUserRecommendations(int bookId, int userId) {
		// TODO Auto-generated method stub
		
	}

	
}
