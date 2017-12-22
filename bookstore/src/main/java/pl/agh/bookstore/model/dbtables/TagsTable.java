package pl.agh.bookstore.model.dbtables;

public enum TagsTable {
	TAG_ID("tag_id"),
	NAME("name");
	
	
	private String columnName;
	
	private TagsTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
