package com.annonce.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.annonce.app.model.Categories;
import com.annonce.app.model.Formation;
import com.annonce.app.model.User;
import com.annonce.app.service.CategoriesService;
import com.annonce.app.service.FormationService;
import com.annonce.app.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	private FormationService fomationService;
	
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private UserService userService;
	
	@GetMapping({"/", "/index"})
	public String showIndexPage(Model model,HttpSession session) {
		
		
		return returnView(model, "index", session);
		   
	}
	
	@GetMapping("home")
	public String showHome(String formation_number, Model model, HttpSession session) {
		
		model.addAttribute("formations",fomationService.findAllFormation());
		
		return returnView(model, "home", session);
	}
	
	@GetMapping("/login") 
	public String showLoginForm(Model model,Authentication authentication) {
		if(authentication != null) {
			model.addAttribute("isUser",true);
			model.addAttribute("istLogged",false);
			model.addAttribute("logout",true);
			return "index";
		}
		model.addAttribute("facebook","/oauth2/authorization/facebook");
		model.addAttribute("google","/oauth2/authorization/google");
		return "login";  
	}

	@GetMapping("/categorie")
	public String categories(Model model,String type,HttpSession session) {
		List<Formation> formations = fomationService.findByCategorie(type.toLowerCase());
		model.addAttribute("formation",formations);
		model.addAttribute("type",type);
		return returnView(model,"categorie",session);
	}

	@GetMapping("/search")
	public String search(@RequestParam("title") String search,Model model,HttpSession session) {
		List<Formation> formation = fomationService.findByTiltleLike(search);
		
		if (formation.isEmpty()) {
			
			model.addAttribute("formation",formation);
			model.addAttribute("notexist",true);
			model.addAttribute("title",search);
		}
		model.addAttribute("formation",formation);
		model.addAttribute("exist",true);
		
		
		return returnView(model, "search",session);
		
	}
	
	public String returnView(Model model, String s,HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Categories> categories = categoriesService.findAllCategories();
		model.addAttribute("categories", categories);
		String user_role =  authentication.getAuthorities().toString();
		System.out.println(user_role);
		if(user_role.equals("[ROLE_FORMATEUR]")) {
			User user = userService.findByEmail(session.getAttribute("nom").toString());
			model.addAttribute("user",user);
			model.addAttribute("isFormateur",true);
			model.addAttribute("istLogged",false);
			model.addAttribute("logout",true);

			
			return s;
		}
		if(user_role.equals("[ROLE_USER]")) {
			User user = userService.findByEmail(session.getAttribute("nom").toString());
			model.addAttribute("user",user);
			model.addAttribute("isUser",true);
			model.addAttribute("istLogged",false);
			model.addAttribute("logout",true);
			
			return s;
			
		}
		
		model.addAttribute("isLogged",true);
		model.addAttribute("logout",false);

		return s;
	}

}
