package pl.agh.bookstore.model.dbtables;

public enum UserRolesTable {
	USER_ID("objectid"),
	CODE("code"),
	DESCRIPTION("description");
	
	
	private String columnName;
	
	private UserRolesTable(String columnName){
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
