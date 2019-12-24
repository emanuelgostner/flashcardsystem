package at.massedterm.massedterm.controller;

import at.massedterm.massedterm.dao.RoundDaoImpl;
import at.massedterm.massedterm.model.Round;
import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
