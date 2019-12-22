package at.massedterm.massedterm.controller;

import at.massedterm.massedterm.dao.RoundDaoImpl;
import at.massedterm.massedterm.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RoundRestController {
	@Autowired
		private RoundDaoImpl roundDao;
		
		@RequestMapping("/webapi/rounds/getActRounds")
		public Round round(Principal principal) {
			String username = principal != null ? principal.getName() : "";
			return roundDao.getActRound(1, username);
		}
}
