package pl.agh.bookstore.model.dbtables;

public enum UsersTable {
	USER_ID("objectid"),
	USERNAME("username"),
	EMAIL("email"),
	PASSWORD("password"),
	ROLE("role"),
	ACTIVE("active"),
	CREATION_DATE("creation_date");
	
	
	private String columnName;
	
	private UsersTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
