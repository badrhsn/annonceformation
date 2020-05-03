package com.annonce.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.annonce.app.model.Categories;
import com.annonce.app.model.Formation;
import com.annonce.app.model.Subscription;
import com.annonce.app.model.User;
import com.annonce.app.service.CategoriesService;
import com.annonce.app.service.FormationService;
import com.annonce.app.service.SubscriptionService;
import com.annonce.app.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private FormationService formationService;
	@Autowired
	private UserService userService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private CategoriesService categoriesService;
	
	@GetMapping("/userprofile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String showUserProfile(Model model, HttpSession session) {
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		List<Categories> categories = categoriesService.findAllCategories();
		model.addAttribute("categories", categories);
		List<Subscription> subs = subscriptionService.findByUser(user);
		model.addAttribute("user", user);

		List<Formation> formations = new ArrayList<Formation>();
		
		for(Subscription sub : subs) {
			formations.add(sub.getFormation());
		}
		
		model.addAttribute("formations", formations);
		model.addAttribute("isUser", true);
	
		model.addAttribute("logout",true);
	
		return "profile_u";
	}

	@GetMapping("/formateurprofile")
	@PreAuthorize("hasRole('ROLE_FORMATEUR')")
	public String showFormateurProfile(Model model, HttpSession session) {
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		List<Categories> categories = categoriesService.findAllCategories();
		model.addAttribute("categories", categories);
		List<Formation> formations = formationService.findUserFormation(user);
		
		model.addAttribute("formations", formations);
		model.addAttribute("user", user);
		model.addAttribute("isFormateur", true);
	
		model.addAttribute("logout",true);
	
		return "profile_f";
	}
	@GetMapping("/user/editprofile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String editUserProfile(Model model ,HttpSession session) {
		String email = session.getAttribute("nom").toString();
		User user = userService.findOne(email);
		List<Categories> categories = categoriesService.findAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("user", user);
		
		model.addAttribute("isUser", true);
	
		model.addAttribute("logout",true);
	
		return "user/editprofile";
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
	
	
	@PostMapping("/user/editprofile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String saveChanges(@RequestParam(name="name",required=false,defaultValue="") String name,@RequestParam(name="first_name",required=false,defaultValue="") String first_name,
			HttpSession session, Model model,@RequestParam(name="password",required=false,defaultValue=" ") String password,
			@RequestParam(name="last_name",required=false,defaultValue=" ") String last_name,
			@RequestParam(name="country",required=false,defaultValue=" ") String country,
			@RequestParam(name="city",required=false,defaultValue=" ") String city,
			@RequestParam(name="address",required=false,defaultValue=" ") String address,
			@RequestParam(name="tele",required=false,defaultValue=" ") String tele,
			@RequestParam(name="description",required=false,defaultValue=" ") String description,
			@RequestParam("imgUrl") MultipartFile imgUrl ) throws IOException {
	
		
		if(password.equals(" ")) {
			model.addAttribute("passordRequired",true);
			this.addinfosToModel(session, model);
			return "/user/editprofile";
		}else {
		
			
			User userlogged = userService.findByEmail(session.getAttribute("nom").toString());
		    BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder(10);	
		    
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
				userService.updateUser(userlogged);
				

				this.addinfosToModel(session, model);

				
				return "/user/editprofile";
			}else {
				model.addAttribute("passwordWrong",true);
				this.addinfosToModel(session, model);

				return "/user/editprofile";
			}
		}
		
	}
}
