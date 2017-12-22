package pl.agh.bookstore.model.dbtables;

public enum GenresTable {
	GENRE_ID("genre_id"),
	NAME("name");
	
	
	private String columnName;
	
	private GenresTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
