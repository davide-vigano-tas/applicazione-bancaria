package eu.tasgroup.applicativo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMetodo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.AdminResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.ClientResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;
import eu.tasgroup.applicativo.conf.BCryptEncoder;
import eu.tasgroup.applicativo.service.CartaService;
import eu.tasgroup.applicativo.service.ClientResetTokenService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.EmailService;
import eu.tasgroup.applicativo.service.MovimentoContoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import eu.tasgroup.applicativo.utility.GestioneTransazioni;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class ClientController {

	@Autowired
	ClientiService clientiService;

	@Autowired
	ContiService contiService;
	
	@Autowired
	CartaService cartaService;

	@Autowired
	TransazioneService transazioneService;

	@Autowired
	MovimentoContoService movimentoContoService;

	@Autowired
	TransazioniMongoService transazioniMongoService;

	@Autowired
	RichiestePrestitoService richiestePrestitoService;
	
	@Autowired
	GestioneTransazioni gc;
	
	@Autowired
	ClientResetTokenService clientResetTokenService;
	
	@Autowired
	
	EmailService emailService;

	// Homepage user
	@GetMapping({ "", "/" })
	public ModelAndView userPage(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-page");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			return mv;
		}
		return new ModelAndView("redirect:/user/user-login");

	}
	
	@GetMapping("/account_info")
	public ModelAndView accountInfo(@AuthenticationPrincipal UserDetails userDetails) {
		String email = userDetails.getUsername();
		ModelAndView mv = new ModelAndView("user-account-info");
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);

		if(cliente.isPresent()) {
			
			mv.addObject("user", cliente.get());
			return mv;
		}
		return new ModelAndView("redirect:/user/user-login");
	}
	
	@PostMapping("/accountmodifica")
	public ModelAndView accountModifica(Cliente cliente) {
		clientiService.createOrUpdate(cliente);
		return new ModelAndView("redirect:/user/");
	}
	
	@GetMapping("/resetpage") 
	public ModelAndView resetPage(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("cliente-reset");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent()) {
			
			mv.addObject("user", cliente.get());
			return mv;
		}
		return new ModelAndView("redirect:/user/user-login");
		
	}
	
	@PostMapping("/sendresetlink")
	public ModelAndView sendResetLink(@RequestParam String email, HttpServletRequest request,
			@AuthenticationPrincipal UserDetails userDetails) {
		String emailCliente = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if(cliente.isPresent() && emailCliente.equals(email)) {
			ModelAndView mv = new ModelAndView("cliente-reset-sent");
			mv.addObject("user", cliente.get());
			Optional<ClientResetToken> optoken = clientResetTokenService.findByClientEmail(emailCliente);
			if(optoken.isPresent()) {
				clientResetTokenService.delete(optoken.get());
			}
			String token = UUID.randomUUID().toString();
			ClientResetToken cdt = new ClientResetToken();
			cdt.setCliente(cliente.get());
			cdt.setExpiryDate(new Date(System.currentTimeMillis()+AdminResetToken.getExpiration()));
			cdt.setToken(token);
			clientResetTokenService.create(cdt);
			String url = request.getRequestURL()
					.toString().replace("sendresetlink", "changepassword")+"?token="+token;
			emailService.sendResetLinkClient(url, email);
			return mv;
		} 
		return new ModelAndView("redirect:/user/user-login");
	}
	
	@GetMapping("/changepassword")
	public ModelAndView changePassword(@RequestParam String token) {
		Optional<ClientResetToken> optoken = clientResetTokenService.findByToken(token);
		if(optoken.isPresent()) {
			if(optoken.get().getExpiryDate().compareTo(new Date()) > 0) {
				ModelAndView mv = new ModelAndView("cliente-reset-form");
				mv.addObject("token", optoken.get().getToken());
				return mv;
			} else {
				return new ModelAndView("redirect:/user/resetpage");
			}
		} return new ModelAndView("redirect:/user/user-login");
	}
	
	@PostMapping("/resetpassword")
	public ModelAndView saveNewPassword(@RequestParam String newPass, HttpSession session,
			@RequestParam String confirm, @RequestParam String token) {
		Optional<ClientResetToken> optoken = clientResetTokenService.findByToken(token);
		if(newPass.equals(confirm)) {
			if(optoken.get().getExpiryDate().compareTo(new Date()) > 0) {
				Cliente cliente = clientResetTokenService.getClientByToken(token).get();
				cliente.setPasswordCliente(BCryptEncoder.encode(newPass));
				clientiService.createOrUpdate(cliente);
				session.invalidate();
				
				
				emailService.sendPasswordConfirmationCliente(cliente.getEmailCliente());
				return new ModelAndView("redirect:/user/user-login");
				
			} else {
				return new ModelAndView("redirect:/user/resetpage");
			}
		}  else {
			return new ModelAndView("redirect:/user/changepassword?token="+token);
		}
	}

	// Elenco conti user
	@GetMapping("/conti")
	public ModelAndView userConti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user", c);
			List<Conto> conti = new ArrayList<Conto>(c.getConti());
			mv.addObject("user_conti", conti);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Pagina del form per aprire un nuovo conto
	@GetMapping("/nuovoConto")
	public ModelAndView contoForm(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-contoform");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			mv.addObject("tipo_conto", TipoConto.values());
			mv.addObject("user", cliente.get());
			Conto conto = new Conto();
			conto.setCliente(cliente.get());
			mv.addObject("user_conto", conto);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Inserimento nuovo conto
	@PostMapping("/nuovoConto")
	public ModelAndView contoForm(Conto conto) {
		contiService.createOrUpdate(conto);

		return new ModelAndView("redirect:/user/conti");
	}

	// Elenco carte user
	@GetMapping("/carte")
	public ModelAndView userCarte(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-carte");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user", c);
			List<Carta> carte = new ArrayList<Carta>(c.getCarte());
			mv.addObject("user_carte", carte);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Pagina del form per creare una nuova carta
	@GetMapping("/nuovaCarta")
	public ModelAndView cartaForm(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-cartaform");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			Carta carta = new Carta();
			carta.setCliente(cliente.get());
			mv.addObject("user_carta", carta);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Inserimento nuovo conto
	@PostMapping("/nuovaCarta")
	public ModelAndView cartaForm(Carta carta) {
		cartaService.createOrUpdate(carta);

		return new ModelAndView("redirect:/user/carte");
	}
	
	// 
	@GetMapping("/eliminaCarta/{id}")
	public ModelAndView cartaForm(@PathVariable long id) {
		cartaService.deleteCartaById(id);
		
		return new ModelAndView("redirect:/user/carte");
	}

	// Transazioni legate a un conto
	@GetMapping("/transazioniConto/{id}")
	public ModelAndView transazioniConto(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-transazioniconto");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			if (contiService.findById(id).isPresent()) {
				Conto conto = contiService.findById(id).get();
				List<Transazione> transazioni = transazioneService.getAll().stream()
						.filter((t) -> t.getConto().equals(conto)).toList();
				mv.addObject("user_transazioni", transazioni);
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");

		}
		return new ModelAndView("redirect:/user/user-login");

	}

	// Movimenti legate a un conto
	@GetMapping("/movimentiConto/{id}")
	public ModelAndView movimentiConto(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-movimenticonto");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			mv.addObject("user", cliente.get());
			if (contiService.findById(id).isPresent()) {
				Conto conto = contiService.findById(id).get();
				List<MovimentoConto> movimenti = movimentoContoService.findMovimentiContoByConto(conto);
				mv.addObject("user_movimenti", movimenti);
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");

		}
		return new ModelAndView("redirect:/user/user-login");

	}

	// Visualizza richieste prestiti
	@GetMapping("/richiestePrestiti")
	public ModelAndView userRichiestePrestiti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-richiesteprestiti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			List<RichiestaPrestito> rp = new ArrayList<RichiestaPrestito>(c.getRichiestePrestiti());
			mv.addObject("user_richieste", rp);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Prestiti andati a buonfine
	@GetMapping("/prestiti")
	public ModelAndView userPrestiti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-prestiti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			List<Prestito> prestiti = clientiService.listaPrestitiClienti(c.getCodCliente());
			mv.addObject("user_prestiti", prestiti);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Elenco pagamenti cliente
	@GetMapping("/pagamenti")
	public ModelAndView userPagamenti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-pagamenti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			List<Pagamento> pagamenti = clientiService.listaPagamentiClienti(c.getCodCliente());
			mv.addObject("user_pagamenti", pagamenti);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Pagina per inserire la quantità che si desidera prelevare
	@GetMapping("/preleva/{id}")
	public ModelAndView prelevaPage(HttpServletRequest request,@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails,
				@RequestParam(required = false) String message) {
	   
	    //Rimuovo messaggi 
		
		ModelAndView mv = new ModelAndView("user-addebito");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			if (contiService.findById(id).isPresent()) {
				Conto conto = contiService.findById(id).get();
				mv.addObject("user_conto", conto);
				Transazione tr = new Transazione();
				tr.setConto(conto);
				mv.addObject("transazione", tr);
				mv.addObject("message",message);
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Prelievo
	@PostMapping("/preleva")
	public ModelAndView preleva(HttpServletRequest request, Transazione transazione, @AuthenticationPrincipal UserDetails userDetails) {
		
		request.getSession().removeAttribute("message");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();

			if (transazione.getImporto() <= 0) {
				String message ="importo_negativo";
				ModelAndView mv = new ModelAndView("redirect:/user/preleva/"+ transazione.getConto().getCodConto());
				mv.addObject("message",message);
				return mv;
			} else if (transazione.getImporto() > transazione.getConto().getSaldo()) {
				String message ="importo_troppo_alto";
				ModelAndView mv = new ModelAndView("redirect:/user/preleva/"+ transazione.getConto().getCodConto());
				mv.addObject("message",message);
				return mv;
			} else if (!gc.prelievo(transazione, c)) {
				return new ModelAndView("redirect:/user/preleva/" + transazione.getConto().getCodConto());
			}
			ModelAndView mv = new ModelAndView("redirect:/user/conti");
			mv.addObject("user",c);
			return mv;

		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Pagina per inserire la quantità che si desidera prelevare
	@GetMapping("/deposita/{id}")
	public ModelAndView depositaPage(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-deposito");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			if (contiService.findById(id).isPresent()) {
				Conto conto = contiService.findById(id).get();
				mv.addObject("user_conto", conto);
				Transazione tr = new Transazione();
				tr.setConto(conto);
				mv.addObject("transazione", tr);
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Prelievo
	@PostMapping("/deposita")
	public ModelAndView deposita(Transazione transazione, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("redirect:/user/conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();

			if (transazione.getImporto() <= 0) {
				return new ModelAndView("redirect:/user/deposita/" + transazione.getConto().getCodConto());
			} else if (!gc.deposito(transazione, c)) {
				return new ModelAndView("redirect:/user/deposita/" + transazione.getConto().getCodConto());
			}
			mv.addObject("user",c);
			return mv;

		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Form richiesta prestito
	@GetMapping("/richiediPrestito")
	public ModelAndView richiestaForm(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-richiestaform");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user", c);
			RichiestaPrestito rp = new RichiestaPrestito();
			rp.setCliente(c);
			mv.addObject("user_richiesta", rp);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Richiesta prrestito
	@PostMapping("/richiediPrestito")
	public ModelAndView richiesta(RichiestaPrestito richiestaPrestito) {
		if (richiestaPrestito.getImporto() < 0)
			return new ModelAndView("redirect:/user/richiediPrestito");
		richiestaPrestito.setDataRichiesta(new Date());
		richiestaPrestito.setStato(StatoRichiestaPrestito.IN_ATTESA);
		richiestePrestitoService.createOrUpdate(richiestaPrestito);
		return new ModelAndView("redirect:/user/richiestePrestiti");
	}

	// Lista di conti,tra cui scegliere a quale si vuole trasferire denaro dal conto identificato da id
	@GetMapping("/contitarget/{id}")
	public ModelAndView conti(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("user-contitarget");
		if (!contiService.findById(id).isPresent())
			return new ModelAndView("redirect:/user/conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			Conto conto = contiService.findById(id).get();
			session.setAttribute("user_conto", conto);
			List<Conto> conti = contiService.getAll();
			conti.remove(conto);
			mv.addObject("user_contitarget", conti);
			mv.addObject("conto_partenza", conto);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");
	}

	// Impostazioni transazione bancaria verso un altro conto
	@GetMapping("/formtransazionebancaria/{id}")
	public ModelAndView formTBancaria(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("user-tbancaria-form");

		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			mv.addObject("user",c);
			Conto origine = (Conto) session.getAttribute("user_conto");
			if (!contiService.findById(id).isPresent())
				return new ModelAndView("redirect:/user/contitarget/" + origine.getCodConto());
			Conto destinazione = contiService.findById(id).get();
			TransazioneBancaria tb = new TransazioneBancaria();
			tb.setContoOrigine(origine);
			tb.setContoDestinazione(destinazione);

			mv.addObject("user_transazionebancaria", tb);
			return mv;
		} else
			return new ModelAndView("redirect:/user/user-login");

	}

	@PostMapping("/transazionebancaria")
	public ModelAndView transazioneBancaria(@RequestParam String metodoPagamento, TransazioneBancaria tb,
			HttpSession session) {
		if (tb.getImporto() < 0)
			return new ModelAndView(
					"redirect:/user/formtransazionebancaria/" + tb.getContoDestinazione().getCodConto());
		if (tb.getImporto() > tb.getContoOrigine().getSaldo())
			return new ModelAndView(
					"redirect:/user/formtransazionebancaria/" + tb.getContoDestinazione().getCodConto());

		Pagamento p = new Pagamento();
		try {
			p.setMetodoPagamento(TipoMetodo.valueOf(metodoPagamento.toUpperCase()));
		} catch (IllegalArgumentException e) {
			return new ModelAndView(
					"redirect:/user/formtransazionebancaria/" + tb.getContoDestinazione().getCodConto());
		}

		if (!gc.transazioneBancaria(tb, p))
			return new ModelAndView(
					"redirect:/user/formtransazionebancaria/" + tb.getContoDestinazione().getCodConto());
		session.removeAttribute("user_conto");
		return new ModelAndView("redirect:/user/conti");
	}

}
