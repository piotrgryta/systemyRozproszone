package pl.agh.bookstore.security.model;

public enum LoginResults {
	INVALID_PASSWORD, NO_SUCH_USER, USER_BLOCKED, USER_NOT_VERYFIED, SUCCESS, INNER_SERVER_ERROR;
}
