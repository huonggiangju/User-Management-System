package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repo;
	
	//show list user
	@GetMapping("/users")
	public String showUserList(Model model) {
		List<User> listUsers = service.listAll();
		
		model.addAttribute("listUsers", listUsers);
		return "index"; //return page
	}
	
	//add new user
	@GetMapping("/users/new")
	public String showNewForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("pageTitle", "Add New User");
		return "addUser";
	}
	
	//save new user
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes ra) {
		service.save(user);
		ra.addFlashAttribute("message", "The user has been saved successfully");
		return "redirect:/users"; //return path
	}
	
	
	//get id
	@GetMapping("/users/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		
		try {
			User user = service.getId(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Update User ID " + id);
			return "addUser";
			
		}catch(UserNotFoundException e) {
			 ra.addFlashAttribute("message",e.getMessage());
			 return "redirect:/users"; //return path
		}
	}
	
	//delete user
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message","The user ID "+ id +" has been deleted");
			
		}catch(UserNotFoundException e) {
			 ra.addFlashAttribute("message",e.getMessage());
			
		}
		 return "redirect:/users"; //return path
	}
	
	//show home page
	@GetMapping("")
	public String viewHomePage() {
		return "register";
	}
	
	
	//show signup form
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	//show login page
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	//show login page
		@GetMapping("/logout")
		public String logoutPage() {
			
			return "redirect:/register";
		}
	
	//register processing
	@PostMapping("/process_register")
	public String processRegister(User user, RedirectAttributes ra) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		
		service.save(user);
		ra.addFlashAttribute("message", "You Have Signed up succesfully");
		return "login";
	}
	
}
