package com.annonce.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("facebook","/oauth2/authorization/facebook");
		model.addAttribute("google","/oauth2/authorization/google");
		model.addAttribute("user", new User());
		return "register";
	}
	
	
	@PostMapping("/completeprofile")
    public String registerUser(HttpSession session,@Valid User user, BindingResult bindingResult, Model model,@RequestParam("roles") String role) {
		if(bindingResult.hasErrors()) {
			return "redirect:register";
		}
		
		
		if(userService.isUserPresent(user.getEmail())) {
			model.addAttribute("exist",true);

			return "register";
		}
		
		if(role.equals(null)) {
			model.addAttribute("roleError",true);
			return "register";
			
		}
		else if(role.equals("USER")) {
		userService.createUser(user);
		return "redirect:login";
		}
		else if(role.equals("FORMATEUR")) {
			userService.createFormateur(user);
			session.setAttribute("email", user.getEmail());
			return "completeprofile";
		}
		
		return "page_error";

	}
	@RequestMapping(value = "/singup-process", method = RequestMethod.GET)
	public String singupPage(Model model ,HttpSession session) {
		String email = session.getAttribute("nom").toString();
		model.addAttribute("email",email);
		return "/singup-process";
	}
	
	@PostMapping("/singupProcess")
	public String sinupProcess(HttpSession session,@RequestParam(name = "password") String password,@RequestParam(name = "roles") String role ) {
		String email = session.getAttribute("nom").toString();
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		User user = userService.findByEmail(email);
		user.setPassword(encoder.encode(password));
		user.setRoles(role);
		userRepository.save(user);
		session.invalidate();
			
		return "redirect:/logout";
	}



	
}
