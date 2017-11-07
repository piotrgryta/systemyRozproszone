package pl.agh.bookstore.connection;

public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = 3974752667083108803L;

	public ConnectionPoolException() {
		super("ConnectionPool not initialized");
	}
}