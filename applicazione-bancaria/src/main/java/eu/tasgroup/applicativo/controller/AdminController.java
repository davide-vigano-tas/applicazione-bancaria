package eu.tasgroup.applicativo.controller;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.AdminResetToken;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.conf.BCryptEncoder;
import eu.tasgroup.applicativo.security.AdminOnly;
import eu.tasgroup.applicativo.service.AdminResetTokenService;
import eu.tasgroup.applicativo.service.AmministratoriService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.EmailService;
import eu.tasgroup.applicativo.service.PrestitoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AmministratoriService amministratoriService;
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	TransazioniMongoService transazioniMongoService;
	
	@Autowired
	TransazioneService transazioneService;
	
	@Autowired
	ContiService contiService;
	
	@Autowired
	PrestitoService prestitoService;
	
	@Autowired
	RichiestePrestitoService richiestePrestitoService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	AdminResetTokenService adminResetTokenService;
	
	
	
	
	@GetMapping({"", "/"})
	public ModelAndView adminPage(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-page");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			List<Cliente> clienti = clientiService.getClientiList();
			mv.addObject("clienti", clienti);
			mv.addObject("nuovocliente", new Cliente());
			return mv;
			
		} 
		return new ModelAndView("redirect:/admin/admin-login");
		
	}
	
	@GetMapping("/account_info")
	public ModelAndView accountInfo(@AuthenticationPrincipal UserDetails userDetails) {
		String email = userDetails.getUsername();
		ModelAndView mv = new ModelAndView("admin-account-info");
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);

		if(admin.isPresent()) {
			
			mv.addObject("admin", admin.get());
			return mv;
		}
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@PostMapping("/accountmodifica")
	public ModelAndView accountModifica(Amministratore amministratore) {
		amministratoriService.createOrUpdate(amministratore);
		return new ModelAndView("redirect:/admin/");
	}
	
	@GetMapping("/resetpage") 
	public ModelAndView resetPage(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-reset");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			
			mv.addObject("admin", admin.get());
			return mv;
		}
		return new ModelAndView("redirect:/admin/admin-login");
		
	}
	
	@PostMapping("/sendresetlink")
	public ModelAndView sendResetLink(@RequestParam String email, HttpServletRequest request,
			@AuthenticationPrincipal UserDetails userDetails) {
		String emailAdmin = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent() && emailAdmin.equals(email)) {
			ModelAndView mv = new ModelAndView("admin-reset-sent");
			mv.addObject("admin", admin.get());
			Optional<AdminResetToken> optoken = adminResetTokenService.findByAdminEmail(emailAdmin);
			if(optoken.isPresent()) {
				adminResetTokenService.delete(optoken.get());
			}
			String token = UUID.randomUUID().toString();
			AdminResetToken adt = new AdminResetToken();
			adt.setAdmin(admin.get());
			adt.setExpiryDate(new Date(System.currentTimeMillis()+AdminResetToken.getExpiration()));
			adt.setToken(token);
			adminResetTokenService.create(adt);
			String url = request.getRequestURL()
					.toString().replace("sendresetlink", "changepassword")+"?token="+token;
			emailService.sendResetLinkAdmin(url, email);
			return mv;
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@GetMapping("/changepassword")
	public ModelAndView changePassword(@RequestParam String token) {
		Optional<AdminResetToken> optoken = adminResetTokenService.findByToken(token);
		if(optoken.isPresent()) {
			if(optoken.get().getExpiryDate().compareTo(new Date()) > 0) {
				ModelAndView mv = new ModelAndView("admin-reset-form");
				mv.addObject("token", optoken.get().getToken());
				return mv;
			} else {
				return new ModelAndView("redirect:/admin/resetpage");
			}
		} return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@PostMapping("/resetpassword")
	public ModelAndView saveNewPassword(@RequestParam String newPass, HttpSession session,
			@RequestParam String confirm, @RequestParam String token) {
		Optional<AdminResetToken> optoken = adminResetTokenService.findByToken(token);
		if(newPass.equals(confirm)) {
			if(optoken.get().getExpiryDate().compareTo(new Date()) > 0) {
				Amministratore admin = adminResetTokenService.getAdminByToken(token).get();
				admin.setPasswordAdmin(BCryptEncoder.encode(newPass));
				amministratoriService.createOrUpdate(admin);
				session.invalidate();
				
				
				emailService.sendPasswordConfirmationAdmin(admin.getEmailAdmin());
				return new ModelAndView("redirect:/admin/admin/login");
				
			} else {
				return new ModelAndView("redirect:/admin/resetpage");
			}
		}  else {
			return new ModelAndView("redirect:/admin/changepassword?token="+token);
		}
	}
	
	@PostMapping("/nuovoCliente")
	public ModelAndView nuovoClienteForm(Cliente cliente) {
	

			clientiService.createOrUpdate(cliente);
			
			return new ModelAndView("redirect:/admin/");
		
	}
	
	@GetMapping("/statistiche")
	public ModelAndView statistiche(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-statistiche");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			long numClienti = clientiService.totaleClienti();
			mv.addObject("numero_clienti", numClienti);
			List<Cliente> saldoMassimo = clientiService.clientiSaldoMax();
			mv.addObject("clienti_saldo_max", saldoMassimo);
			Date dataUltimaTransazione = transazioniMongoService.ultimaTransazione().isPresent() ?
					transazioniMongoService.ultimaTransazione().get().getDataTransazione() 
					: null;
			if(dataUltimaTransazione!=null) mv.addObject("data_ultima", dataUltimaTransazione);
			long totaleTransazioni = transazioneService.getAll().size();
			double sommaImporti = transazioneService.getAll().stream()
					.mapToDouble( t -> t.getImporto()).sum();
			mv.addObject("tot_transazioni", totaleTransazioni);
			mv.addObject("somma_importi", sommaImporti);
			
			List<Cliente> clienti = clientiService.getClientiList();
			mv.addObject("clienti", clienti);
			
			double saldoMedio = contiService.saldoMedio();
			mv.addObject("saldo_medio", saldoMedio);
			
			/*
			 * String statistiche[] 
			 * 1. conti per cliente
			 * 2. carte per cliente
			 * 3. importo prestiti per cliente
			 * 4. importo medio transazione
			 */
			
			Map<Cliente, String[]> statisticheClienti = new HashMap<Cliente, String[]>();
			
			for(Cliente c: clienti) {
				
				String[] stat = {
					String.valueOf(clientiService.numeroConti(c.getCodCliente())),
					String.valueOf(clientiService.numeroCarte(c.getCodCliente())),
					String.valueOf(prestitoService.sumPrestitiByCliente(c.getCodCliente())),
					String.valueOf(transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente()))
				};
				
				statisticheClienti.put(c, stat);
			}
			
			mv.addObject("statistiche_clienti", statisticheClienti);
			
			
			long numTransazioniAddebito= transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ADDEBITO);
			mv.addObject("num_addebiti", numTransazioniAddebito);
			
			long numTransazioniAccredito= transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			mv.addObject("num_accrediti", numTransazioniAccredito);
			
			double numeroMedioTransazioniPerCliente = transazioniMongoService.numeroMedioTransazioniPerCliente();
			mv.addObject("numero_medio_t", numeroMedioTransazioniPerCliente);
			
			
			Map<String, Double> importoPerMese = new LinkedHashMap<>();

			for (int i = 1; i <= 12; i++) {
				String nomeMese = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ITALIAN);
			    nomeMese = nomeMese.substring(0, 1).toUpperCase() + nomeMese.substring(1).toLowerCase();
			    importoPerMese.put(nomeMese, transazioniMongoService.totaleImportoPerMese(i));
			}

			mv.addObject("importo_mese", importoPerMese);
			
			// Statistiche extra
			
			// Richieste Prestiti
			long inAttesa = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.IN_ATTESA).size();
			long approvate = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.APPROVATO).size();
			long rifiutate = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.RIFIUTATO).size();
			
			mv.addObject("num_attesa", inAttesa);
			mv.addObject("num_approvate", approvate);
			mv.addObject("num_rifiutate", rifiutate);
			
			/*
			 * String statistiche[] 
			 * 1. numero prestiti
			 * 2. somma prestiti
			 * 3. numero transazioni
			 * 4. transazioni media
			 */
			
			clienti = clientiService.getClientiList();
			List<Prestito> approvati = prestitoService.getAll();

			Map<Cliente, String[]> statisticheClientiExtra = new HashMap<Cliente, String[]>();
			
			for(Cliente c : clienti) {
				double somma = approvati.stream()
						.filter((p) -> p.getCliente().getCodCliente() == c.getCodCliente())
						.mapToDouble((p) -> p.getImporto()).sum();
				
				String[] statExtra = {
						String.valueOf(c.getPrestiti().size()),
						String.valueOf(somma),
						String.valueOf(transazioniMongoService.findByCliente(c.getCodCliente()).size()),
						String.valueOf(transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente())),
				};
				statisticheClientiExtra.put(c, statExtra);
			}
			
			mv.addObject("statistiche_clienti_extra", statisticheClientiExtra);
			
			return mv;
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	
	
	@GetMapping("/contizero")
	public ModelAndView contiZeroSaldo(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-contizero");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			List<Conto> contiZero = contiService.getContiSaldoZero();
			mv.addObject("admin_contizero", contiZero);
			return mv;
			
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@GetMapping("/eliminaConto/{id}")
	public ModelAndView eliminaConto(@PathVariable long id) {
		if(contiService.findById(id).isPresent()) {
			contiService.deleteContoById(id);
			return new ModelAndView("redirect:/admin/contizero");
		}
		return new ModelAndView("redirect:/admin/contizero");
	}
	
	@GetMapping("/riepilogoCliente/{id}") 
	public ModelAndView riepilogo(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-cliente-riepilogo");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			if(clientiService.findById(id).isPresent()) {
				Cliente cliente = clientiService.findById(id).get();
				mv.addObject("admin_cliente", cliente);
				List<Conto> contiCliente = new ArrayList<Conto>(
						cliente.getConti());
				List<Carta> carteCliente = new ArrayList<Carta>(
						cliente.getCarte());
				List<Prestito> prestitiCliente = clientiService.listaPrestitiClienti(cliente.getCodCliente());
				List<TransazioniMongo> transazioniCliente = transazioniMongoService.findByCliente(cliente.getCodCliente());
				
				mv.addObject("admin_conti_cliente", contiCliente);
				mv.addObject("admin_carte_cliente", carteCliente);
				mv.addObject("admin_prestiti_cliente", prestitiCliente);
				mv.addObject("admin_transazioni_cliente", transazioniCliente);

				return mv;
			}
			return new ModelAndView("redirect:/admin/");
			
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@GetMapping("/sospendi/{id}") 
	public ModelAndView sospendi(@PathVariable long id) {
		Optional<Cliente> cliente = clientiService.findById(id);
		if(cliente.isPresent())
			clientiService.changeStatusCliente(cliente.get().getCodCliente());
		return new ModelAndView("redirect:/admin/");
	}
	
	@GetMapping("/abilita/{id}") 
	public ModelAndView abilita(@PathVariable long id) {
		Optional<Cliente> cliente = clientiService.findById(id);
		if(cliente.isPresent())
			clientiService.changeStatusCliente(cliente.get().getCodCliente());
		return new ModelAndView("redirect:/admin/");
	}
	
	@GetMapping("/prestiti")
	public ModelAndView prestiti(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-prestiti");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			List<RichiestaPrestito> lista = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.IN_ATTESA);
			if(!lista.isEmpty()) {		
				mv.addObject("richieste_prestiti", richiestePrestitoService.findByStatus(StatoRichiestaPrestito.IN_ATTESA));
				return mv;
			}
			return new ModelAndView("redirect:/admin");
		}
		return new ModelAndView("redirect:/admin/admin-login");
	}

	@AdminOnly
	@GetMapping("/rifiuta/{id}")
	public ModelAndView rifiuta(@PathVariable long id) {
		Optional<RichiestaPrestito> richiesta = richiestePrestitoService.findById(id);
		if (richiesta.isPresent()) {
			RichiestaPrestito r = richiesta.get();
			r.setStato(StatoRichiestaPrestito.RIFIUTATO);
			richiestePrestitoService.createOrUpdate(r);
		}
		return new ModelAndView("redirect:/admin/prestiti");
	}

	
	@GetMapping("/accetta/{id}")
	public ModelAndView accetta(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-prestiti-form");
		Optional<RichiestaPrestito> richiesta = richiestePrestitoService.findById(id);
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			if (richiesta.isPresent()) {
				RichiestaPrestito r = richiesta.get();
				mv.addObject("richiesta", r);
				Prestito prestito = new Prestito();
				prestito.setImporto(r.getImporto());
				mv.addObject("prestito", prestito);
				return mv;
			}
			return new ModelAndView("redirect:/admin");
		}
		return new ModelAndView("redirect:/user/user-login");
	}
	@AdminOnly
	@PostMapping("/accetta/{id}")
	public ModelAndView accetta(@PathVariable long id,Prestito prestito) {
		Optional<RichiestaPrestito> richiesta = richiestePrestitoService.findById(id);
		if (richiesta.isPresent()) {
			RichiestaPrestito r = richiesta.get();
			r.setStato(StatoRichiestaPrestito.APPROVATO);
			richiestePrestitoService.createOrUpdate(r);
			prestito.setCliente(r.getCliente());
			prestito.setImporto(r.getImporto());
			prestitoService.createOrUpdate(prestito);
		}
		return new ModelAndView("redirect:/admin/prestiti");
	}
}
