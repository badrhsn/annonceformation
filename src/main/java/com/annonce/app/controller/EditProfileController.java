package com.annonce.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.UserService;

@Controller
@RequestMapping("/formateur")
public class EditProfileController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("editprofile")
	public String showEditprofile(Model model,HttpSession session) {
		
		String email = session.getAttribute("nom").toString();
		User user = userService.findOne(email);
		
		model.addAttribute("user",user);
		model.addAttribute("name", user.getName());
		model.addAttribute("first_name", user.getFirst_name());
		model.addAttribute("last_name", user.getLast_name());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("country", user.getCountry());
		model.addAttribute("city", user.getCity());
		model.addAttribute("address", user.getAddress());
		model.addAttribute("tele", user.getTele());
		model.addAttribute("imgUrl", user.getImgUrl());

		return "/formateur/editprofile";

	}
	
	public void addinfosToModel(HttpSession session,Model model) {
		
		User userlogged = userService.findOne(session.getAttribute("nom").toString());

		model.addAttribute("user",userlogged);
		model.addAttribute("name", userlogged.getName());
		model.addAttribute("first_name", userlogged.getFirst_name());
		model.addAttribute("last_name", userlogged.getLast_name());
		model.addAttribute("email", userlogged.getEmail());
		model.addAttribute("country", userlogged.getCountry());
		model.addAttribute("city", userlogged.getCity());
		model.addAttribute("address", userlogged.getAddress());
		model.addAttribute("tele", userlogged.getTele());
	}
	
	
	@PostMapping("/editprofile")
	public String saveChanges(@RequestParam(name="name",required=false,defaultValue="") String name,@RequestParam(name="first_name",required=false,defaultValue="") String first_name,
			HttpSession session, Model model,@RequestParam(name="password",required=false,defaultValue=" ") String password,
			@RequestParam(name="last_name",required=false,defaultValue=" ") String last_name,
			@RequestParam(name="country",required=false,defaultValue=" ") String country,
			@RequestParam(name="city",required=false,defaultValue=" ") String city,
			@RequestParam(name="address",required=false,defaultValue=" ") String address,
			@RequestParam(name="tele",required=false,defaultValue=" ") String tele,
			@RequestParam(name="description",required=false,defaultValue=" ") String description,
			@RequestParam(name= "imgUrl",required = false) MultipartFile imgUrl ) throws IOException {
	
		
		if(password.equals(" ")) {
			model.addAttribute("passordRequired",true);
			this.addinfosToModel(session, model);
			return "/formateur/editprofile";
		}else {
		
			
			User userlogged = userService.findByEmail(session.getAttribute("nom").toString());
		    BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();	
		    
			if(encoder.matches(password, userlogged.getPassword())) {
				model.addAttribute("saveCorrect",true);
				if(!imgUrl.isEmpty()) {
				String folder = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\photos\\";
				System.out.print(folder);
				byte[] bytes = imgUrl.getBytes();
				Path path = Paths.get(folder+imgUrl.getOriginalFilename());
				Files.write(path, bytes);
				userlogged.setImgUrl("/photos/"+imgUrl.getOriginalFilename());
				}
				userlogged.setName(name);
				userlogged.setFirst_name(first_name);
				userlogged.setLast_name(last_name);
				userlogged.setCountry(country);
				userlogged.setCity(city);
				userlogged.setAddress(address);
				userlogged.setTele(tele);
				userlogged.setDescription(description);
				userRepository.save(userlogged);
				

				this.addinfosToModel(session, model);

				
				return "/formateur/editprofile";
			}else {
				model.addAttribute("passwordWrong",true);
				this.addinfosToModel(session, model);

				return "/formateur/editprofile";
			}
		}
		
	}
}
