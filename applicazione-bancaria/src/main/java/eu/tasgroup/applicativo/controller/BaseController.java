package eu.tasgroup.applicativo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.conf.BCryptEncoder;
import eu.tasgroup.applicativo.service.ClientiMongoService;
import eu.tasgroup.applicativo.service.ClientiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BaseController {
	
	@Autowired
	private ClientiService cs;
	@Autowired
	private ClientiMongoService cms;
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("home");
	}

	@GetMapping("/user/user-login")
	public ModelAndView userLogin(HttpServletRequest request, Model model) {
	    String message = (String)request.getSession().getAttribute("message");
	    String tentativiRimasti = (String)request.getSession().getAttribute("tentativi");
	    Boolean error = (Boolean)request.getSession().getAttribute("error");
	   
	    //Rimuovo messaggi 
	    request.getSession().removeAttribute("error");
	    request.getSession().removeAttribute("message");
	    request.getSession().removeAttribute("tentativi");
	    
	    ModelAndView mv = new  ModelAndView("user-login");
	    mv.addObject("error", error);
	    mv.addObject("message", message);
	    mv.addObject("tentativi", tentativiRimasti);

	    return mv;
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
	public ModelAndView registrazioneUtenteForm(@Valid Cliente cliente, BindingResult result,HttpServletRequest request) {
		
		request.getSession().removeAttribute("message");
		ModelAndView mv = new ModelAndView();
		
		if(cliente.getEmailCliente().endsWith("@tasgroup.eu")) {
			String message = "email_non_valida";
			mv.addObject("message", message);
			mv.setViewName("user-form-reg");
			return mv;
		}
		
		if (cs.findByEmailCliente(cliente.getEmailCliente()).isPresent()) {
			String message = "email_gia_utilizzata";
			mv.addObject("message", message);
			mv.setViewName("user-form-reg");
			return mv;
		}else {
			if(result.hasErrors()) {
				mv.addObject("cliente", cliente);
				mv.setViewName("user-form-reg");
				return mv;
			} else {
				
			
				cliente.setPasswordCliente(BCryptEncoder.encode(cliente.getPasswordCliente()));
				cliente = cs.createOrUpdate(cliente);
				
				// Aggiungo il cliente anche su MongoDB
				ClienteMongo clienteMongo = new ClienteMongo();
				clienteMongo.setAccountBloccato(cliente.isAccountBloccato());
				clienteMongo.setCodCliente(cliente.getCodCliente());
				clienteMongo.setCognomeCliente(cliente.getCognomeCliente());
				clienteMongo.setEmailCliente(cliente.getEmailCliente());
				clienteMongo.setNomeCliente(cliente.getNomeCliente());
				clienteMongo.setPasswordCliente(cliente.getPasswordCliente());
				clienteMongo.setSaldoConto(cliente.getSaldoConto());
				clienteMongo.setTentativiErrati(cliente.getTentativiErrati());
				
				cms.createOrUpdate(clienteMongo);
				
				return new ModelAndView("redirect:/user/user-login");
			}
		}
	}

	
	@GetMapping("/admin/admin-login")
	public ModelAndView loginAdmin(HttpServletRequest request, Model model) {
	    String message = (String)request.getSession().getAttribute("message");
	    String tentativiRimasti = (String)request.getSession().getAttribute("tentativi");
	    Boolean error = (Boolean)request.getSession().getAttribute("error");
	   
	    //Rimuovo messaggi 
	    request.getSession().removeAttribute("error");
	    request.getSession().removeAttribute("message");
	    request.getSession().removeAttribute("tentativi");
	    
	    ModelAndView mv = new  ModelAndView("admin-login");
	    mv.addObject("error", error);
	    mv.addObject("message", message);
	    mv.addObject("tentativi", tentativiRimasti);

	    return mv;
	}

	
}
