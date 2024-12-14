package eu.tasgroup.applicativo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Amministratore;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Prestito;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.service.AmministratoriService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.PrestitoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;

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
			
			Map<Cliente, Integer> numeroContiPerCliente = new HashMap<Cliente, Integer>();
			for(Cliente c : clienti) {
				numeroContiPerCliente.put(c, clientiService.numeroConti(c.getCodCliente()));
			}
			
			mv.addObject("numero_conti", numeroContiPerCliente);
			
			
			Map<Cliente, Integer> numeroCartePerCliente = new HashMap<Cliente, Integer>();
			for(Cliente c : clienti) {
				numeroCartePerCliente.put(c, clientiService.numeroCarte(c.getCodCliente()));
			}
			
			mv.addObject("numero_carte", numeroCartePerCliente);
			
			Map<Cliente, Double> importoTotalePrestitiCliente = new HashMap<Cliente, Double>();
			for(Cliente c : clienti) {
				importoTotalePrestitiCliente.put(c, prestitoService.sumPrestitiByCliente(c.getCodCliente()));
			}
			mv.addObject("clienti_importo_prestiti", importoTotalePrestitiCliente);
			
			long numTransazioniAddebito= transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ADDEBITO);
			mv.addObject("num_addebiti", numTransazioniAddebito);
			
			long numTransazioniAccredito= transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			mv.addObject("num_accrediti", numTransazioniAccredito);
			
			
			double numeroMedioTransazioniPerCliente = transazioniMongoService.numeroMedioTransazioniPerCliente();
			mv.addObject("numero_medio_t", numeroMedioTransazioniPerCliente);
			
			Map<Cliente, Double> importoMedioTransazioniPerCliente = new HashMap<Cliente, Double>();
			for(Cliente c : clienti) {
				importoMedioTransazioniPerCliente.put(c, 
						transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente()));
			}
			
			mv.addObject("importo_medio_t", importoMedioTransazioniPerCliente);
			
			Map<Integer, Double> importoPerMese= new HashMap<Integer, Double>();
			for(int i = 1; i<13; i++) {
				importoPerMese.put(i, 
						transazioniMongoService.totaleImportoPerMese(i));
			}
			mv.addObject("importo_mese", importoPerMese);
			
			
			return mv;
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	
	@GetMapping("/statistiche_extra")
	public ModelAndView statisticheExtra(@AuthenticationPrincipal UserDetails userDetails) {
		ModelAndView mv = new ModelAndView("admin-statistiche-extra");
		String email = userDetails.getUsername();
		Optional<Amministratore> admin = amministratoriService.findByEmailAdmin(email);
		if(admin.isPresent()) {
			Amministratore ad = admin.get();
			mv.addObject("admin", ad);
			
			long inAttesa = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.IN_ATTESA).size();
			long approvate = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.APPROVATO).size();
			long rifiutate = richiestePrestitoService.findByStatus(StatoRichiestaPrestito.RIFIUTATO).size();
			
			mv.addObject("admin_num_attesa", inAttesa);
			mv.addObject("admin_num_approvate", approvate);
			mv.addObject("admin_num_rifiutate", rifiutate);
			
			List<Prestito> approvati = prestitoService.getAll();
			List<Cliente> clienti = clientiService.getClientiList();
			Map<Cliente, Double> sommaImportoPresititi = new HashMap<Cliente, Double>();
			
			for(Cliente c : clienti) {
				double somma = approvati.stream()
						.filter((p) -> p.getCliente().getCodCliente() == c.getCodCliente())
						.mapToDouble((p) -> p.getImporto()).sum();
				sommaImportoPresititi.put(c, somma);
			}
			mv.addObject("admin_somma_prestiti_clienti", sommaImportoPresititi);
			
			int accrediti = transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			int tot = transazioniMongoService.findAll().size();
			
			mv.addObject("admin_perc_accrediti", accrediti/tot);
			Map<Cliente, List<TransazioniMongo>> tPerC = new HashMap<Cliente, List<TransazioniMongo>>();
			Map<Cliente, Double> impMedioPerCliente = new HashMap<Cliente, Double>();
			for(Cliente c : clienti) {
				tPerC.put(c, transazioniMongoService.findByCliente(c.getCodCliente()));
				impMedioPerCliente.put(c, 
						transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente()));
			}
			
			mv.addObject("admin_t_per_cliente", tPerC);
			mv.addObject("admin_importo_medio_cliente", impMedioPerCliente);
			
			return mv;
		} 
		return new ModelAndView("redirect:/admin/admin-login");
	}
	

}
