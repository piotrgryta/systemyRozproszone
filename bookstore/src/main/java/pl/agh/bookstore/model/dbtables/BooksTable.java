package pl.agh.bookstore.model.dbtables;

public enum BooksTable {
	BOOK_ID("book_id"),
	GENRE_ID("genre_id"),
	AUTHOR_ID("author_id"),
	NAME("name"),
	UNITPRICE("unitprice"),
	UNITS_IN_STOCK("unitsInStock"), 
	PREMIERE_DATE("premiereDane"),
	VIEWING("viewing");
	
	
	private String columnName;
	
	private BooksTable(String columnName){
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
