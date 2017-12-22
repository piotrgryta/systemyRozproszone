package pl.agh.bookstore.model.dbtables;

public enum TagsmapUserTable {
	TAGSMAP_ID("tagsmap_id"),
	USER_ID("user_id"),
	TAG_ID("tag_id"),
	VALUE("value");
	
	
	private String columnName;
	
	private TagsmapUserTable(String columnName){
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
