package at.massedterm.controller;

import at.massedterm.dao.RoundDaoImpl;
import at.massedterm.model.Response;
import at.massedterm.model.Round;
import at.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class RoundRestController {
	@Autowired
		private RoundDaoImpl roundDao;
		
		@RequestMapping("/webapi/stacks/{stackid}/getActRound")
		public Round round(Principal principal, @PathVariable("stackid") int stackid) {
			String username = principal != null ? principal.getName() : "";
			return roundDao.getActRound(stackid, username);
		}
		@RequestMapping("/webapi/stacks/{stackid}/getStackFull")
		public Stack stackFull(Principal principal, @PathVariable("stackid") int stackid) {
			String username = principal != null ? principal.getName() : "";
			return roundDao.getStackFull(stackid, username);
		}
		@PostMapping("/webapi/stacks/{stackid}/addRound")
		public Number addRound(Principal principal, @PathVariable("stackid") int stackid) {
			String username = principal != null ? principal.getName() : "";
			return roundDao.addRound(username, stackid);
		}
		@PostMapping("/webapi/stacks/{stackid}/addResponse")
		public void addResponse(@RequestBody Response newResponse, Principal principal) {
			String username = principal != null ? principal.getName() : "";
			roundDao.addResponse(username, newResponse);
		}
		
}
