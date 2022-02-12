package spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.UserDao;
import spring.model.User;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public void save(User user) {
		userDao.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> list() {
		return userDao.list();
	}

	@Transactional
	public void delete(String email) {
		userDao.delete(email);
	}
	
	@Transactional
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public User getUser(String email) {
		List<User> data = userDao.getUser(email);
		return (data  != null && !data.isEmpty()) ? data.get(0) : null;
	}
}
