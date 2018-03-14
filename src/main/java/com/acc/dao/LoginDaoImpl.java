package com.acc.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.acc.dto.User;
@Repository
public class LoginDaoImpl extends AbstractDao implements LoginDao {

	public User checkUser(String username) {
		
		Session session = getSession();
		Query query = session.createQuery("select e from User e where e.userName=:username ");
		query.setParameter("username", username);
		return (User)query.uniqueResult();
	}
}
