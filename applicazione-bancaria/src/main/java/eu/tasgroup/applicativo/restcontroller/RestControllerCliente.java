package eu.tasgroup.applicativo.restcontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.tasgroup.applicativo.businesscomponent.enumerated.Mese;
import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.service.ClientiMongoService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.PrestitoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import eu.tasgroup.applicativo.utility.DettTrans;
import eu.tasgroup.applicativo.utility.Statistiche;
import eu.tasgroup.applicativo.utility.StatisticheExtra;

@RestController
@RequestMapping("/api")
@CrossOrigin("")
public class RestControllerCliente {

	@Autowired
	ClientiService clientiService;

	@Autowired
	ClientiMongoService clientiMongoService;

	@Autowired
	TransazioniMongoService transazioniMongoService;

	@Autowired
	ContiService contiService;

	@Autowired
	PrestitoService prestitoService;

	@Autowired
	PagamentoService pagamentoService;
	
	@Autowired
	RichiestePrestitoService richiestePrestitoService;

	@GetMapping("/clienti")
	public List<Cliente> getClienti() {
		return clientiService.getClientiList();
	}

	// Creazione cliente
	@PostMapping("/clienti")
	public  ResponseEntity<Cliente> postCliente(@RequestBody Cliente clienteMySQL) {

		System.out.println("Cliente ricevuto: " + clienteMySQL);
	    try {
	        Cliente savedCliente = clientiService.createOrUpdate(clienteMySQL);
	        ClienteMongo clienteMongo = new ClienteMongo();
	        
	        clienteMongo.setCodCliente((int) clienteMySQL.getCodCliente());
	        clienteMongo.setNomeCliente(clienteMySQL.getNomeCliente());
	        clienteMongo.setCognomeCliente(clienteMySQL.getCognomeCliente());
	        clienteMongo.setEmailCliente(clienteMySQL.getEmailCliente());
	        clienteMongo.setPasswordCliente(clienteMySQL.getPasswordCliente());
	        clienteMongo.setTentativiErrati(clienteMySQL.getTentativiErrati());
	        clienteMongo.setAccountBloccato(clienteMySQL.isAccountBloccato());
	        clienteMongo.setSaldoConto(clienteMySQL.getSaldoConto());
	        
	        clientiMongoService.createOrUpdate(clienteMongo);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }

	}

	@GetMapping("/clienti/{id}")
	public Cliente getCliente(@PathVariable long id) {
		return clientiService.findById(id).get();
	}

	@DeleteMapping("/conti/{id}")
	public void eliminaContatto(@PathVariable long id) {
		contiService.deleteContoById(id);
	}

	@GetMapping("/statistiche")
	public Statistiche getStatistiche() {
		Statistiche statistiche = new Statistiche();

		// Numero totale di clienti
		statistiche.setNumeroTotaleCliente(clientiService.totaleClienti());

		// Cliente con il saldo più alto
		statistiche.setClienteSaldoMaggiore(clientiService.clientiSaldoMax());

		// Data dell'ultima transazione
		transazioniMongoService.ultimaTransazione()
				.ifPresent(transazione -> statistiche.setDataUltimaTransazione(transazione.getDataTransazione()));

		// Totale transazioni (numero e somma importi)
		List<TransazioniMongo> listaT = transazioniMongoService.findAll();
		double somma = 0;
		for (TransazioniMongo t : listaT) {
			somma += t.getImporto();
		}
		statistiche.setNumeroTotaleTransazioni(listaT.size());
		statistiche.setSommaTotaleTransazioni(somma);

		// Saldo medio dei conti
		statistiche.setSaldoMedioConti(contiService.saldoMedio());

		// Numero di conti per cliente
		Map<Long, Integer> mappaContiPerCliente = new HashMap<Long, Integer>();
		for (Cliente c : clientiService.getClientiList()) {
			mappaContiPerCliente.put(c.getCodCliente(), clientiService.numeroConti(c.getCodCliente()));
		}
		statistiche.setContiPerCliente(mappaContiPerCliente);

		// Numero di carte di credito per cliente
		Map<Long, Integer> mappaCartePerCliente = new HashMap<Long, Integer>();
		for (Cliente c : clientiService.getClientiList()) {
			mappaCartePerCliente.put(c.getCodCliente(), clientiService.numeroCarte(c.getCodCliente()));
		}
		statistiche.setCartePerCliente(mappaCartePerCliente);

		// Importo totale dei prestiti per cliente
		Map<Long, Double> mappaImportoTotPrestitiPerCliente = clientiService.getClientiList().stream()
				.collect(Collectors.toMap(Cliente::getCodCliente,
						cliente -> prestitoService.sumPrestitiByCliente(cliente.getCodCliente())));
		statistiche.setImportoTotPrestitiPerCliente(mappaImportoTotPrestitiPerCliente);

		// Importo totale dei pagamenti per cliente
		Map<Long, Double> mappaImportoTotPagamentiPerCliente = clientiService.getClientiList().stream()
				.collect(Collectors.toMap(Cliente::getCodCliente,
						cliente -> pagamentoService.sumPagamentiByCliente(cliente.getCodCliente())));
		statistiche.setImportoTotPagamentiPerCliente(mappaImportoTotPagamentiPerCliente);

		// Numero totale di transazioni per tipo
		Map<TipoTransazione, Integer> mappaNumeroTransazioniPerTipo = Arrays.stream(TipoTransazione.values()).collect(
				Collectors.toMap(tipo -> tipo, tipo -> transazioniMongoService.numeroTransazioniPerTipo(tipo)));
		statistiche.setNumeroTransazioniPerTipo(mappaNumeroTransazioniPerTipo);

		// Numero medio di transazioni per cliente
		statistiche.setMediaTransazioniPerCliente(transazioniMongoService.numeroMedioTransazioniPerCliente());

		// Totale importo transazioni per mese
		Map<String, Double> totaleImportoTranszioniPerMese = Arrays.stream(Mese.values())
				.collect(Collectors.toMap(mese -> {
					System.err.println("Mese: " + mese.name() + ", Ordinal: " + mese.ordinal());
					return mese.name();
				}, mese -> transazioniMongoService.totaleImportoPerMese(mese.ordinal())));
		statistiche.setTotaleImportoTranszioniPerMese(totaleImportoTranszioniPerMese);

		return statistiche;
	}
	
	//statistiche extra
		@GetMapping("/statistiche-extra")
		public StatisticheExtra statisticheExtra() {
			StatisticheExtra stat = new StatisticheExtra();
			
			//Stato delle Richieste di Prestito: Numero di richieste in attesa, approvate e rifiutate.
			Map<StatoRichiestaPrestito, Integer> prestitiRichiestiPerStato = Arrays.stream(StatoRichiestaPrestito.values())
					.collect(Collectors.toMap(
							tipo -> tipo, 
							tipo -> richiestePrestitoService.findByStatus(tipo).size()
							));	
			stat.setPrestitiRichiestiPerStato(prestitiRichiestiPerStato);
			
			
			//Importo Totale dei Prestiti Approvati: Somma degli importi dei prestiti approvati per ogni cliente.
			Map<Long,Double> sommaPrestitiApprovatiPerCliente = new HashMap<Long,Double>();
			for(Cliente c : clientiService.getClientiList()) {
				Double somma = 0.00;
				if(!c.getRichiestePrestiti().isEmpty()) {
					for(RichiestaPrestito p : c.getRichiestePrestiti()) {
						if(p.getStato().equals(StatoRichiestaPrestito.APPROVATO)) {
							somma += p.getImporto();
						}
					}
					//LA LISTA PRESTITI è FATTA DA QUELLI APPROVATI?
//					for(Prestito p : c.getPrestiti()) {
//							somma += p.getImporto();
//					}
					sommaPrestitiApprovatiPerCliente.put(c.getCodCliente(), somma);
				}
			}
			stat.setSommaPrestitiApprovatiPerCliente(sommaPrestitiApprovatiPerCliente);
			
			
			//Rapporto Transazioni ACCREDITO/ADDEBITO: Percentuale di transazioni di tipo ACCREDITO rispetto al totale.
			//conta anche sul totale (include i trasferimenti)
			double percentualeTransazioniAccredito;
			if(transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO) != null) {
				percentualeTransazioniAccredito= (100.00/transazioniMongoService.findAll().size())
						*transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			} else {
				percentualeTransazioniAccredito = 0.00;
			}
			stat.setPercentualeTransazioniAccredito(percentualeTransazioniAccredito);
			
			//Questo considera la percentuale di accrediti sulla somma di acrediti e addebit (senzabtrasferimenti)
			double rapportoAccreditoAddebito;
			if(transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO) != null
					&& transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ADDEBITO) != null) {
				rapportoAccreditoAddebito= (100.00/(
						transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO)
						+transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ADDEBITO)))
						*transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			} else if(transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO) != null) {
				rapportoAccreditoAddebito = 100.00;
			} else {
				rapportoAccreditoAddebito = 0.00;			
			}
			stat.setPercentualeTransazioniAccredito(rapportoAccreditoAddebito);
			
			
			
			//Dettagli Transazioni per Cliente: Visualizzazione dettagliata delle transazioni per ogni
			//cliente, includendo importo medio, numero totale e tipologia.
			Map<Long,DettTrans> dettagliTransazioniPerCliente = new HashMap<Long, DettTrans>();
			for(Cliente c : clientiService.getClientiList()) {
				if(!transazioniMongoService.findByCliente(c.getCodCliente()).isEmpty()) {
					dettagliTransazioniPerCliente.put(c.getCodCliente(),
							new DettTrans(
									transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente()), 
									transazioniMongoService.findByCliente(c.getCodCliente()).size(), 
									Arrays
									.stream(TipoTransazione.values())
									.collect(Collectors.toMap(
											tipo -> tipo, 
											tipo -> transazioniMongoService.numeroTransazioniPerTipo(tipo)
											))
									)
							);
				}
			}
			stat.setDettagliTransazioniPerCliente(dettagliTransazioniPerCliente);
			
			return stat;
		}
		

		

}
