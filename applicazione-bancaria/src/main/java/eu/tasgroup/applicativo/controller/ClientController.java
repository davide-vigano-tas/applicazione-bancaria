package eu.tasgroup.applicativo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMovimento;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;
import eu.tasgroup.applicativo.service.CartaService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.MovimentoContoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import eu.tasgroup.applicativo.utility.GestioneTransazioni;
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

	GestioneTransazioni gc = new GestioneTransazioni();

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
		return new ModelAndView("redirect:/userlogin");

	}

	// Eleco conti user
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
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
	}

	// Pagina del form per creare una nuova carta
	@GetMapping("/nuovoCarta")
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
			return new ModelAndView("redirect:/userlogin");
	}

	// Inserimento nuovo conto
	@PostMapping("/nuovaCarta")
	public ModelAndView cartaForm(Carta carta) {
		cartaService.createOrUpdate(carta);

		return new ModelAndView("redirect:/user/conti");
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
		return new ModelAndView("redirect:/userlogin");

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
		return new ModelAndView("redirect:/userlogin");

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
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
	}

	// Pagina per inserire la quantità che si desidera prelevare
	@GetMapping("/preleva/{id}")
	public ModelAndView prelevaPage(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
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
				return mv;
			}
			return new ModelAndView("redirect:/user/conti");
		} else
			return new ModelAndView("redirect:/userlogin");
	}

	// Prelievo
	@PostMapping("/preleva")
	public ModelAndView preleva(Transazione transazione, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();

			if (transazione.getImporto() < 0) {
				return new ModelAndView("redirect:/user/preleva/" + transazione.getConto().getCodConto());
			} else if (transazione.getImporto() > transazione.getConto().getSaldo()) {
				return new ModelAndView("redirect:/user/preleva/" + transazione.getConto().getCodConto());
			} else if (!gc.prelievo(transazione, c)) {
				return new ModelAndView("redirect:/user/preleva/" + transazione.getConto().getCodConto());
			}
			mv.addObject("user",c);
			return mv;

		} else
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
	}

	// Prelievo
	@PostMapping("/deposita")
	public ModelAndView deposita(Transazione transazione, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("user-conti");
		String email = userDetails.getUsername();
		Optional<Cliente> cliente = clientiService.findByEmailCliente(email);
		if (cliente.isPresent()) {
			Cliente c = cliente.get();

			if (transazione.getImporto() < 0) {
				return new ModelAndView("redirect:/user/deposita/" + transazione.getConto().getCodConto());
			} else if (!gc.deposito(transazione, c)) {
				return new ModelAndView("redirect:/user/deposita/" + transazione.getConto().getCodConto());
			}
			mv.addObject("user",c);
			return mv;

		} else
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");
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
			mv.addObject("user_contitarget", conti);
			return mv;
		} else
			return new ModelAndView("redirect:/userlogin");
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
			return new ModelAndView("redirect:/userlogin");

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
