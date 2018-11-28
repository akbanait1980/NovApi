package com.learn.restapi.nov.NovApi.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Component;
import java.util.Iterator;

import com.learn.restapi.nov.NovApi.bean.User;

@Component
public class UserService {
	
	private static List<User> users = new ArrayList<User>();
	
	static{
		users.add(new User(101, "101", new Date()));
		users.add(new User(202, "202", new Date()));
		users.add(new User(303, "303", new Date()));
		users.add(new User(404, "404", new Date()));
		users.add(new User(505, "505", new Date()));
	}
	
	public List<User> listAll(){
		return users;
	}
	
	public User findUser(int id) {
		for(User user : users) {
			if(id == user.getId()) {
				return user;
			}
		}
		
		return null;
	}
	
	public User deleteUser(int id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(id == user.getId()) {
				users.remove(user);
				return user;
			}
		}
		
		return null;
	}

	public User saveUser(User user) {
		if(user.getId() == null) {
			user.setId(users.size()+1);
		}
		users.add(user);
		return user;
	}

}
