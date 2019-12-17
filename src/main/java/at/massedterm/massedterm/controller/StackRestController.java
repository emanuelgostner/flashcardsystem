package at.massedterm.massedterm.controller;

import at.massedterm.massedterm.dao.StackDaoImpl;
import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class StackRestController {
	@Autowired
	private StackDaoImpl stackDao;
	
	@RequestMapping("/webapi/stacks/getAllStacks")
	public List stack(Principal principal) {
		String username = principal != null ? principal.getName() : "";
		return stackDao.getAllStacks(username);
	}
	@RequestMapping("/webapi/stacks/addStack")
	public List addStack(@RequestParam(value="name", defaultValue="World") String name, Principal principal) {
		String username = principal != null ? principal.getName() : "";
		return stackDao.getAllStacks(username);
	}
}
