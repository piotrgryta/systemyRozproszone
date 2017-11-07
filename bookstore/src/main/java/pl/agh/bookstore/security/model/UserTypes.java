package pl.agh.bookstore.security.model;

public enum UserTypes {
	USER, ADMIN, USER_NO_PASSWORD, ADMIN_NO_PASSWORD, DEFAULT;
	
	public UserTypes getUserType(String type){
		for (UserTypes t : UserTypes.values()){
			if (type.equals("ROLE_" + t.name())){
				return t;
			}
		}
		return UserTypes.DEFAULT;
	}
}
