package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired 
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
//	@Test
//	public void testAddNew() {
//		User user = new User();
//		user.setEmail("le@gmail.com");
//		user.setFirstname("Giang");
//		user.setLastname("Nguyen");
//		user.setPassword("12345");
//		
//		User savedUser = repo.save(user);
//		
//		Assertions.assertThat(savedUser).isNotNull();
//		Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
//		
//	}
	
	
	@Test
	public void testListAll() {
		Iterable<User> users = repo.findAll();
		Assertions.assertThat(users).hasSizeGreaterThan(0);
		
		for(User user: users){
			System.out.println(user);
		}
	}
	
	@Test
	public void testUpdate() {
		
		Integer id = 1;
		Optional<User> optionalUser = repo.findById(id);
		User user = optionalUser.get();
		user.setPassword("0987665");
		repo.save(user);
		
		User updatedUser = repo.findById(id).get();
		Assertions.assertThat(updatedUser.getPassword()).isEqualTo("0987665");
		
	}
	
	@Test 
	public void testget() {
		Integer id = 1;
		Optional<User> optionalUser = repo.findById(id);
		User user = optionalUser.get();
		
		Assertions.assertThat(optionalUser).isPresent();
		System.out.println(optionalUser.get());
	}
	
	@Test
	public void testDeleteById() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<User> optionalUser = repo.findById(id);
		Assertions.assertThat(optionalUser).isNotPresent();
	}
	
	@Test 
	public void testCreateUser() {
		User user = new User();
		user.setEmail("le@gmail.com");
		user.setFirstname("Giang");
		user.setLastname("Nguyen");
		user.setPassword("12345");
		
		User savedUser = repo.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testFindUserByEmail() {
		String email = "giang@gmail.com";
		User user =repo.findByEmail(email);
		assertThat(user).isNotNull();
	}
}
