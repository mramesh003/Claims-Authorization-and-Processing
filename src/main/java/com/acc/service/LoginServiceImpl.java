package com.acc.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.dao.LoginDao;
import com.acc.dto.User;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginDao loginDao;
	@Transactional
	public User checkUser(String username) {
		User user=loginDao.checkUser(username);
		return user;
	}

}
