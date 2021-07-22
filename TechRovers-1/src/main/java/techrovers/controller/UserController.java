package techrovers.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techrovers.entity.User;
import techrovers.model.CustomError;
import techrovers.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers(HttpServletRequest req){
		return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(HttpServletRequest req, @PathVariable(name = "id") String id){
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveUser/{id}", method = RequestMethod.POST)
	public ResponseEntity<CustomError> saveUser(HttpServletRequest req, @PathVariable(name = "id") String id, @RequestBody User userMst) throws Exception{
		userService.saveUser(userMst, id);
		CustomError ce = new CustomError(00, "User created successfully");
		return new ResponseEntity<CustomError>(ce, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/saveAdminUser", method = RequestMethod.POST)
	public ResponseEntity<CustomError> saveAdminUser(HttpServletRequest req, @RequestBody User userMst) throws Exception{
		userService.saveUser(userMst, null);
		CustomError ce = new CustomError(00, "User created successfully");
		return new ResponseEntity<CustomError>(ce, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.POST)
	public ResponseEntity<CustomError> updateUser(HttpServletRequest req, @PathVariable(name = "id") String id, @RequestBody User userMst) throws Exception{
		userService.updateUser(userMst, id);
		CustomError ce = new CustomError(00, "User Updated successfully");
		return new ResponseEntity<CustomError>(ce, HttpStatus.OK);
	}
	

}
