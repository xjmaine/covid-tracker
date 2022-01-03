package io.weblibrary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.weblibrary.models.LocationLib;
import io.weblibrary.services.CoronaUpdateDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaUpdateDataService covid19Service;
	
	@GetMapping("/")
	public String home(Model model) {
		//get total count of active cases
		List<LocationLib> activeStats = covid19Service.getAllRequests();
		
//		Integer totalActiveCases = activeStats.stream().mapToInt(stat ->stat.getTotalActive()).sum();
		model.addAttribute("locationlibs", covid19Service.getAllRequests());
//		model.addAttribute("totalActiveCases", totalActiveCases);
		return "home";
	}

}
