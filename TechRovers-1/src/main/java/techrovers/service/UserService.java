package techrovers.service;

import java.util.List;

import techrovers.entity.User;


public interface UserService {
	
	List<User> getAllUsers();
	
	User getUserById(String id);
	
	void saveUser(User userMst, String id) throws Exception;
	
	void updateUser(User userMst, String id) throws Exception;
}
