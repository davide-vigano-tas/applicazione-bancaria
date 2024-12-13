package eu.tasgroup.applicativo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.conf.BCryptEncoder;
import eu.tasgroup.applicativo.service.AmministratoriService;
import eu.tasgroup.applicativo.service.ClientiService;

@Controller
public class BaseController {
	
	@Autowired
	private ClientiService cs;
	@Autowired
	private AmministratoriService as;
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@GetMapping("/user/user-login")
	public ModelAndView loginUser() {
		return new ModelAndView("user-login");
	}
	
	@GetMapping(value = "/user/user-registrazione")
	public ModelAndView registrazioneUtente() {
		
		//TODO: controllo : può accedere solo se non loggato
		
		ModelAndView mv = new  ModelAndView();
		mv.setViewName("user-form-reg");
		mv.addObject("cliente", new Cliente());
		return mv;
	}

	@PostMapping(value = "/user/user-form-registrazione")
	public ModelAndView registrazioneUtenteForm(Cliente cliente) {

		ModelAndView mv = new ModelAndView();

		if (cs.findByEmailCliente(cliente.getEmailCliente()).isPresent()) {
			
			// TODO: Gestire errore email già registrata
			
			mv.setViewName("user-form-reg");
			return mv;
		}else {
			cliente.setPasswordCliente(BCryptEncoder.encode(cliente.getPasswordCliente()));
			cs.createOrUpdate(cliente);
			return new ModelAndView("redirect:/user-login");
		}
	}
	
	@GetMapping("/admin-login")
	public ModelAndView loginAdmin() {
		return new ModelAndView("admin-login");
	}
	
	@GetMapping(value = "/admin-registrazione")
	public ModelAndView registrazioneAdmin() {
		
		//TODO: controllo : può accedere solo se non loggato
		
		ModelAndView mv = new  ModelAndView();
		mv.setViewName("admin-form-registrazione");
		mv.addObject("admin", new Amministratore());
		return mv;
	}

	@PostMapping(value = "/admin-form-registrazione")
	public ModelAndView registrazioneAdminForm(Amministratore admin) {

		ModelAndView mv = new ModelAndView();

		if (as.findByEmailAdmin(admin.getEmailAdmin()).isPresent()) {
			
			// TODO: Gestire errore email già registrata
			
			mv.setViewName("admin-form-reg");
			return mv;
		}else {
			admin.setPasswordAdmin(BCryptEncoder.encode(admin.getPasswordAdmin()));
			as.createOrUpdate(admin);
			return new ModelAndView("redirect:/admin-login");
		}
	}
}
