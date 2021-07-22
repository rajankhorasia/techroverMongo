package techrovers.serviceImpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import techrovers.entity.User;
import techrovers.model.CustomException;
import techrovers.repository.UserRepository;
import techrovers.service.UserService;
import techrovers.util.Constant;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).orElseGet(null);
	}

	@Override
	public void saveUser(User userMst, String id) throws Exception {
		// TODO Auto-generated method stub
		User user = null;
		if(Objects.nonNull(id)) {
			user = userRepo.findById(id).orElse(null);
			if(Objects.isNull(user) || (user.getRole().getType() != Constant.ADMIN_ROLE)) {
				throw new CustomException(Constant.CUSTOM_ERROR_CODE, "Only Admin can Add users.");
			}
		}
		user = userRepo.findByEmail(userMst.getEmail());
		if(Objects.nonNull(user)) {
			throw new CustomException(Constant.CUSTOM_ERROR_CODE, "User with email "+userMst.getEmail()+" already exists");
		}
		userRepo.save(userMst);
	}

	@Override
	public void updateUser(User userMst, String id) throws Exception {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(userMst.getEmail());
		if(Objects.nonNull(user) && !Objects.equals(user.get_id(), userMst.get_id())) {
			throw new CustomException(Constant.CUSTOM_ERROR_CODE, "User with email "+userMst.getEmail()+" already exists");
		}
		userRepo.save(userMst);
		
	}

}
