package pl.agh.bookstore.model.dbtables;

public enum TagsmapTable {
	TAGSMAP_ID("tagsmap_id"),
	BOOK_ID("book_id"),
	TAG_ID("tag_id");
	
	
	private String columnName;
	
	private TagsmapTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
