package com.annonce.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.annonce.app.model.Formation;
import com.annonce.app.model.Subscription;
import com.annonce.app.model.User;
import com.annonce.app.service.FormationService;
import com.annonce.app.service.SubscriptionService;
import com.annonce.app.service.UserService;

@Controller
public class FormationDetailsController {
	
	@Autowired
	private FormationService formationService;
	@Autowired
	private UserService userService;
	@Autowired
	private SubscriptionService subscriptionService;
	
	
	@GetMapping("/formationdetails")
	public String showDetails(String formation_id, Model model, HttpSession session) {
		
		String email = session.getAttribute("nom").toString();
		
		User user = userService.findByEmail(email);
		model.addAttribute("user",user);
		
		Formation currentformation = formationService.findFormation(Long.parseLong(formation_id));
		
		User emailUserOfFormation = currentformation.getUser();
		System.out.println(emailUserOfFormation.getEmail());
		User userOfFormation = userService.findOne(emailUserOfFormation.getEmail());
		
		int limit_students = Integer.parseInt(currentformation.getLimit_student());
		int curr_students = Integer.parseInt(currentformation.getCurrent_student());
		
		if(limit_students == curr_students || currentformation.getActive() == 0) {
			model.addAttribute("disable", true);
		} else {
			model.addAttribute("disable", false);
		}
		
		if (subscriptionService.isSubscriptionExist(currentformation, user)) {
			model.addAttribute("subscribed", true);
		} else {
			model.addAttribute("subscribed", false);
		}
		
		model.addAttribute("currentformation", currentformation);
		model.addAttribute("userOfFormation", userOfFormation);
		
		return returnViews(model);
		
	}
	
	@GetMapping("/inscrire/{id}")
	public String inscrire(@PathVariable String id, HttpSession session) {
		String email = session.getAttribute("nom").toString();
		
		Formation formation = formationService.findFormation(Long.parseLong(id));
		User user = userService.findByEmail(email);
		
		if(subscriptionService.isSubscriptionExist(formation, user)) {
			System.out.println("Subscription Exist !!!");
			return "redirect:/home";
		} else {
			Subscription subscription = new Subscription(formation, user);
			subscriptionService.addSubscription(subscription);
			
			int curr_students = Integer.parseInt(formation.getCurrent_student());
			curr_students++;
			System.out.println("curr users " + curr_students);
			formation.setCurrent_student(String.valueOf(curr_students));
			
			formationService.updateFormation(formation);
			
			return "redirect:/formationdetails/?formation_id="+ id;
		}
	}
	
	@GetMapping("/cancel/{id}")
	public String cancel(@PathVariable String id, HttpSession session) {
		String email = session.getAttribute("nom").toString();
		
		Formation formation = formationService.findFormation(Long.parseLong(id));
		User user = userService.findByEmail(email);	
		
		int curr_students = Integer.parseInt(formation.getCurrent_student());
		curr_students--;
		System.out.println("curr users " + curr_students);
		formation.setCurrent_student(String.valueOf(curr_students));
				
		Subscription sub= subscriptionService.findByFormationIdAndUSer(formation, user);
		subscriptionService.deleteSubscriptionById(sub.getId());
		
		return "redirect:/formationdetails/?formation_id="+ id;
		
	}
	 
	
	private String returnViews(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String user_role =  authentication.getAuthorities().toString();
		System.out.println(user_role);
		if(user_role.equals("[ROLE_FORMATEUR]")) {
			
			model.addAttribute("isFormateur",true);
			model.addAttribute("istLogged",false);
			model.addAttribute("logout",true);

			
			return "formation_detail";
		}
		if(user_role.equals("[ROLE_USER]")) {
			
			model.addAttribute("isUser",true);
			model.addAttribute("istLogged",false);
			model.addAttribute("logout",true);
			
			return "formation_detail";
			
		}
		
		model.addAttribute("isLogged",true);
		model.addAttribute("logout",false);

		
		return "formation_detail";
	}
}
