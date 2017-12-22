package pl.agh.bookstore.model.dbtables;

public enum AuthorsTable {
	AUTHOR_ID("genre_id"),
	NAME("name"),
	SURNAME("surname");
	
	
	private String columnName;
	
	private AuthorsTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
