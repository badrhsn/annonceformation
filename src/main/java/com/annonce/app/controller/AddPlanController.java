package com.annonce.app.controller;

import java.security.Principal;

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

import com.annonce.app.model.Formation;
import com.annonce.app.model.PlanFormation;
import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.FormationService;
import com.annonce.app.service.PlanService;
import com.annonce.app.service.UserService;

@Controller
@RequestMapping("/formateur")
public class AddPlanController {

	
	@Autowired
	private UserService userService;
	@Autowired
	private FormationService formationService;
	@Autowired
	private PlanService planService;

	@GetMapping("/addplan")
	public String showAddplan(String formation_id,Model model,HttpSession session) {		
		
		session.setAttribute("formation_id", formation_id);
		model.addAttribute("plan",new PlanFormation());
		
		return "/formateur/addplan";
	}
	
	@PostMapping("/addplan")
	public String addplan(@Valid PlanFormation plan, BindingResult bindingResult,HttpSession session,Model model) {
		
		if(bindingResult.hasErrors()) {
			
			System.out.println("baaadr");
			return "/formateur/addplan";
		}
		
		Long id = Long.parseLong((String) session.getAttribute("formation_id"));
		Formation formation  = formationService.findFormation((Long)id);
		plan.setFormation(formation);
		
		planService.addPlanFormation(plan, plan.getFormation());
		model.addAttribute("success",true);

		return "redirect:/formateur/listformation";
	}
	@GetMapping("/updatePlan")
	public String updateplan(String plan_id,Model model,HttpSession session) {		
		
		session.setAttribute("plan_id", plan_id);
		
		
		System.out.println("id = " + plan_id);
		model.addAttribute("newplan",new PlanFormation());
		model.addAttribute("plan",planService.findPlanById(Long.parseLong(plan_id)).get());
		
		return "/formateur/updateplan";
	}
	@PostMapping("/updatePlan")
	public String update(@RequestParam (name = "title", defaultValue=" ",required=false) String title, 
						@RequestParam (name = "description", defaultValue=" ",required=false) String description,
						@RequestParam (name = "duration", defaultValue=" ",required=false) String duration,
						@RequestParam (name = "price", defaultValue=" ",required=false) String price,
						HttpSession session,Model model ) {
		
		Long id = Long.parseLong((String) session.getAttribute("plan_id"));
		PlanFormation plan = planService.findPlanById(id).get();
		plan.setDescription(description);
		plan.setDuration(duration);
		plan.setP_title(title);
		plan.setPrice(price);
		planService.updatePlan(plan);
		model.addAttribute("success",true);

		return "redirect:/formateur/listformation";
	}
	@PostMapping("/plan/{id}")
	public String deletePlan(@PathVariable String id,HttpSession session,Model model ) {
		
		planService.deletePlan(Long.parseLong(id));
		return "redirect:/formateur/listformation";
		
	}
	
}
