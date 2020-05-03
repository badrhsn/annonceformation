package com.annonce.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.UserService;

@Controller
public class CompleteProfileController {

	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/completeprofile")
	public String completeProfile(Model model) {
				
		
		return "completeprofile";
	}
	
	
	@PostMapping("/index")
    public String updateUser(HttpSession session,Model model,@RequestParam(name = "address",required=false,defaultValue = " ") String address,
    		@RequestParam(name = "country",required=false,defaultValue = " ") String country,@RequestParam(name = "city",required=false,defaultValue = " ") String city,
    		@RequestParam(name = "tele",required=false,defaultValue = " ") String tele,
    		@RequestParam(name= "description", required=false, defaultValue=" ") String description,
    		@RequestParam(name="imgUrl",required = false) MultipartFile imgUrl,  
    		@RequestParam(name = "cv",required = true) MultipartFile cv)throws IOException
    		 {
		
		
		String email =  (String) session.getAttribute("email");
		User user = userService.findOne(email);
		uploadCv(cv);
		uploadImage(imgUrl);
		user.setImgUrl("/photos/"+imgUrl.getOriginalFilename());
		user.setAddress(address);
		user.setCountry(country);
		user.setCity(city);
		user.setTele(tele);
		user.setDescription(description);
		
		userRepository.save(user);
		
		return "redirect:index";
	}
	public void uploadImage(MultipartFile img) throws IOException {
		String folder = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\photos\\";
		byte[] bytes = img.getBytes();
		Path path = Paths.get(folder+img.getOriginalFilename());
		Files.write(path, bytes);
	}
	public void uploadCv(MultipartFile cv) throws IOException {
		String folder = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\cvs\\";
		byte[] bytes = cv.getBytes();
		Path path = Paths.get(folder+cv.getOriginalFilename());
		Files.write(path, bytes);
	}
	
	
}
