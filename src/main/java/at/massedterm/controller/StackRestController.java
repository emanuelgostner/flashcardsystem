package at.massedterm.controller;

import at.massedterm.dao.StackDaoImpl;
import at.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	
	@PostMapping("/webapi/stacks/addStack")
	public Number newStack(@RequestBody Stack newStack, Principal principal) {
		String username = principal != null ? principal.getName() : "";
		return stackDao.addStack(username, newStack);
	}
	
	@PostMapping("/webapi/stacks/deleteStack")
	public void deleteStack(@RequestBody Stack stack, Principal principal) {
		String username = principal != null ? principal.getName() : "";
		stackDao.deleteStack(username, stack);
	}
	
	@PostMapping("/webapi/stacks/updateStack")
	public void updateStack(@RequestBody Stack stack, Principal principal) {
		String username = principal != null ? principal.getName() : "";
		stackDao.updateStack(username, stack);
	}
}
