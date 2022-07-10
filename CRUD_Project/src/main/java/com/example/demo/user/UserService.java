package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	public List<User> listAll(){
		
		return(List<User>) repo.findAll();
	}

	public void save(User user) {
		repo.save(user);
		
	}
	
	public User getId( Integer id) throws UserNotFoundException{
		Optional<User> uid = repo.findById(id);
		if(uid.isPresent()) {
			return uid.get();
		}
		throw new UserNotFoundException("could not find any users with id " + id);
	}
	
	public void delete(Integer id) throws UserNotFoundException{
		Long count = repo.countById(id);
		if(count ==null || count ==0) {
			throw new UserNotFoundException("could not find any users with id " + id);
		}
		repo.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	
}
