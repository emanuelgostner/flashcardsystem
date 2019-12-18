package at.massedterm.massedterm.controller;

import at.massedterm.massedterm.dao.StackDaoImpl;
import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
	@Autowired
	private StackDaoImpl stackDao;
	
	@GetMapping("/")
	public String home(){
		return "redirect:stacks";
	}
	
	
	@GetMapping("/stacks")
	public String stacks(Model model, Principal principal){
		String username = principal != null ? principal.getName() : "";
		List<Stack> stacks = stackDao.getAllStacks(username);
		model.addAttribute("stacks",stacks);
		
		return "stacks";
	}
	@GetMapping("/*")
	public String redLogin(){
		return "redirect:stacks";
	}
}
