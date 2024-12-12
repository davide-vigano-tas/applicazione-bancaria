package eu.tasgroup.applicativo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.MovimentoContoService;
import eu.tasgroup.applicativo.service.TransazioneService;

@Controller
@RequestMapping("/user")
public class ClientController {
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	ContiService contiService;
	
	@Autowired
	TransazioneService transazioneService;
	
	@Autowired
	MovimentoContoService movimentoContoService;
	
	//Homepage user
	@GetMapping({"", "/"})
	public ModelAndView userPage(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user_homepage");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			return mv;
		}
		return new ModelAndView("redirect:/userlogin");
		
	}
	
	
	//Eleco conti user
	@GetMapping("/conti")
	public ModelAndView userConti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user_conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject(c);
			List<Conto> conti = new ArrayList<Conto>(c.getConti()) ;
			mv.addObject("user_conti", conti);
			return mv;
		} else return new ModelAndView("redirect:/userlogin");
	}
	
	//Pagina del form per aprire un nuovo conto
	@GetMapping("/nuovoConto")
	public ModelAndView contoForm(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user_contoform");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			Conto conto = new Conto();
			conto.setCliente(cliente.get());
			mv.addObject("user_conto", conto);
			return mv;
		}
		else return new ModelAndView("redirect:/userlogin");
	}
	
	
	//Inserimento nuovo conto
	@PostMapping("/nuovoConto")
	public ModelAndView contoForm(Conto conto) {
		contiService.createOrUpdate(conto);
		
		return new ModelAndView("redirect:/user/conti");
	}
	
	//Transazioni legate a un conto
	@GetMapping("/transazioniConto/{id}")
	public ModelAndView transazioniConto(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user_transazioni_conto");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			if(contiService.findById(id).isPresent()) {
				Conto conto = contiService.findById(id).get();
				List<Transazione> transazioni = transazioneService.getAll()
						.stream().filter((t) -> t.getConto().equals(conto)).toList();
				mv.addObject("user_transazioni", transazioni);
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");
			
		}
		return new ModelAndView("redirect:/userlogin");
		
	}
	
	
//	//Movimenti legate a un conto
//	@GetMapping("/movimentiConto/{id}")
//	public ModelAndView movimentiConto(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
//		ModelAndView mv = new ModelAndView("user_transazioni_conto");
//		String email = userDetails.getUsername();
//		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
//		if(cliente.isPresent()) {
//			mv.addObject("user", cliente.get());
//			if(contiService.findById(id).isPresent()) {
//				Conto conto = contiService.findById(id).get();
//				List<MovimentoConto> movimenti = transazioneService.getAll()
//						.stream().filter((t) -> t.getConto().equals(conto)).toList();
//				mv.addObject("user_transazioni", transazioni);
//				return mv;
//			}
//			return new ModelAndView("redirect:/user/conti");
//			
//		}
//		return new ModelAndView("redirect:/userlogin");
//		
//	}
	
	
	

}
