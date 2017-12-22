package pl.agh.bookstore.model.dbtables;

public enum OrdersTable {
	ORDER_ID("order_id"),
	USER_ID("user_id"),
	ORDER_DATE("orderdate"),
	SHIPPED_DATE("shippeddate");
	
	
	private String columnName;
	
	private OrdersTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
