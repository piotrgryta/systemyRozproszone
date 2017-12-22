package pl.agh.bookstore.model.dbtables;

public enum OrderDetailsTable {
	ORDER_ID("order_id"),
	BOOK_ID("book_id"),
	UNITPRICE("unitprice"),
	QUANTITY("quantity"),
	DISCOUNT("discount");
	
	
	private String columnName;
	
	private OrderDetailsTable(String columnName){
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
