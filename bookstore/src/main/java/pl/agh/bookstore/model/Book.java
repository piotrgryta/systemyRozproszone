package pl.agh.bookstore.model;

import java.io.File;
import java.util.Date;

public class Book {

	private int book_id;
	private int genre_id;
	private int author_id;
	private String name;
	private double unitprice;
	private int unitsInStock;
	private Date premiereDate;
	private int viewing;
	private File image;
	
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public int getUnitsInStock() {
		return unitsInStock;
	}
	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}
	public Date getPremiereDate() {
		return premiereDate;
	}
	public void setPremiereDate(Date premiereDate) {
		this.premiereDate = premiereDate;
	}
	public int getViewing() {
		return viewing;
	}
	public void setViewing(int viewing) {
		this.viewing = viewing;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	
	
}
