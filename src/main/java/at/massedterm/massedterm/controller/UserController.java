package at.massedterm.massedterm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/user")
	public String user(){
		return "<h1>Welcome back user</h1>";
	}
	
	@GetMapping("/admin")
	public String admin(){
		return "<h1>Welcome back admin</h1>";
	}
}
