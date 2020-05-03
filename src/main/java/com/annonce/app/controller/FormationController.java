package com.annonce.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.annonce.app.model.Formation;
import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.FormationService;
import com.annonce.app.service.UserService;

@Controller
@RequestMapping("/formateur")
public class FormationController {

	
	
	@Autowired
	private UserService userService;
	@Autowired
	private FormationService formationService;
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping("/dashboard")
	public String showDashboard(Model model, HttpSession session) {
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		model.addAttribute("user",user);
		return "/formateur/dashboard";
		
	}

	@GetMapping("/addformation")
	public String showaddformation(Model model,HttpSession session) {
		
		String email = session.getAttribute("nom").toString();
		model.addAttribute("formation",new Formation());
		
		return "/formateur/addformation";
	}
	
	@PostMapping("/addformation")
	public String addFormation(@RequestParam(name="title", defaultValue=" ",required=false) String title,
			@RequestParam(name="subtitle", defaultValue=" ",required=false) String subtitle,
			@RequestParam(name="price", defaultValue=" ",required=false) String price,
			@RequestParam(name="language", defaultValue=" ",required=false) String language,
			@RequestParam(name="date_depart", defaultValue=" ",required=false) String date_depart,
			@RequestParam(name="limit_student", defaultValue=" ",required=false) String limit_student,
			@RequestParam(name="categorie", defaultValue=" ",required=false) String categorie,
			@RequestParam(name="duration", defaultValue=" ",required=false) String duration,
			@RequestParam(name="place", defaultValue=" ",required=false) String place,
			@RequestParam(name="description", defaultValue=" ",required=false) String description,
			Model model,HttpSession httpSession,  @RequestParam(name="img",required = false) MultipartFile imgUrl) throws IOException {

		
		System.out.println("badr2");
		
		model.addAttribute("success",true);
		
		Formation formation = new Formation();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		formation.setCreation_date(dateFormat.format(date));
		formation.setCurrent_student("0");
		formation.setActive(1);
		formation.setCategorie(categorie);
		formation.setDate_depart(date_depart);
		formation.setDescription(description);
		formation.setDuration(duration);
		formation.setLanguage(language);
		formation.setPlace(place);
		formation.setLimit_student(limit_student);
		formation.setTitle(title);
		formation.setSubtitle(subtitle);
		formation.setPrice(price);
		
		
		
			String folder = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\formationimg\\";
			System.out.print(folder);
			byte[] bytes = imgUrl.getBytes();
			Path path = Paths.get(folder+imgUrl.getOriginalFilename());
			Files.write(path, bytes);
			formation.setImg("/photos/"+imgUrl.getOriginalFilename());

		
		formationService.addFormation(formation, userService.findOne(httpSession.getAttribute("nom").toString()));
				
		return "/formateur/addformation";
		
		
	}
	@GetMapping("/listformation")
	public String showListFormation(String formation_number, Model model, HttpSession session) {
		
		
		String email = session.getAttribute("nom").toString();
		model.addAttribute("formations",formationService.findUserFormation(userService.findOne(email)));
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		model.addAttribute("user",user);

		return "/formateur/listformation";
	}
	

	@PostMapping("/listformation/{id}")
	public String deleteFormtion(@PathVariable String id) {
		
		formationService.deleteFormation(Long.parseLong(id));
		
		return "redirect:/formateur/listformation";
		
	}
	
	@GetMapping("/updateformation")
	public String showUpdatePage(String formation_id,Model model, Principal principal,HttpSession httpSession) {
		
		String email = httpSession.getAttribute("nom").toString();
		httpSession.setAttribute("formation_id", formation_id);
		model.addAttribute("newformation", new Formation());
		model.addAttribute("formation",formationService.findFormation(Long.parseLong(formation_id)));
		return "/formateur/updateformation";
	}
	
	@PostMapping("/updateformation")
	public String updateFormation(@RequestParam(name="title", defaultValue=" ",required=false) String title,
			@RequestParam(name="subtitle", defaultValue=" ",required=false) String subtitle,
			@RequestParam(name="price", defaultValue=" ",required=false) String price,
			@RequestParam(name="language", defaultValue=" ",required=false) String language,
			@RequestParam(name="date_depart", defaultValue=" ",required=false) String date_depart,
			@RequestParam(name="limit_student", defaultValue=" ",required=false) String limit_student,
			@RequestParam(name="categorie", defaultValue=" ",required=false) String categorie,
			@RequestParam(name="duration", defaultValue=" ",required=false) String duration,
			@RequestParam(name="place", defaultValue=" ",required=false) String place,
			@RequestParam(name="description", defaultValue=" ",required=false) String description,
			Model model,HttpSession httpSession,  @RequestParam(name="img",required = false) MultipartFile imgUrl) throws IOException {
		
		Formation oldFormation = formationService.findFormation(Long.parseLong((String)httpSession.getAttribute("formation_id")));
		if(!imgUrl.isEmpty()) {
			String folder = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\formationimg\\";
			System.out.print(folder);
			byte[] bytes = imgUrl.getBytes();
			Path path = Paths.get(folder+imgUrl.getOriginalFilename());
			Files.write(path, bytes);
			oldFormation.setImg("/photos/"+imgUrl.getOriginalFilename());

		}
		oldFormation.setTitle(title);
		oldFormation.setSubtitle(subtitle);
		oldFormation.setPrice(price);
		oldFormation.setLanguage(language);
		oldFormation.setDate_depart(date_depart);
		oldFormation.setLimit_student(limit_student);
		oldFormation.setCategorie(categorie);
		oldFormation.setDuration(duration);
		oldFormation.setPlace(place);
		oldFormation.setDescription(description);
		
		formationService.addFormation(oldFormation, userService.findOne(httpSession.getAttribute("nom").toString()));
		return "redirect:/formateur/updateformation"+"?formation_id="+(String)httpSession.getAttribute("formation_id");
	}
	
	
	@GetMapping("/prerequis")
	public String pre(String formation_id,HttpSession session,Model model) 
	{
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		model.addAttribute("user",user);
		session.setAttribute("pre", formation_id);
		
		return "/formateur/prerequis";
	}
	@PostMapping("/prerequis")
	public String prePost(@RequestParam(value = "title") String[] pre,HttpSession session,Model model) {
		Formation formation = formationService.findFormation(Long.parseLong(session.getAttribute("pre").toString()));
		User user = userService.findByEmail(session.getAttribute("nom").toString());
		model.addAttribute("user",user);
		formation.setPrerequis(pre);
		formationService.updateFormation(formation);
		return "/formateur/prerequis";
		
	}
	
	@GetMapping("/consulter")
	public String consulterView(Model model, String formation_id) {
		System.out.println("id  = " + formation_id);
		Formation formation = formationService.findFormation(Long.parseLong(formation_id));
		
		if(formation.getActive() == 0) {
			model.addAttribute("active", false);
		} else {
			model.addAttribute("active", true);
		}
		
		model.addAttribute("formation", formation);
		
		return "/formateur/consulter";
	}
	
	@GetMapping("/startFormation")
	public String startFormation(String formation_id, Model model) {
		
		Formation formation = formationService.findFormation(Long.parseLong(formation_id));
		formation.setActive(0);
		formationService.updateFormation(formation);

		return "redirect:/formateur/listformation";
	}

}
