package eu.tasgroup.applicativo.restcontroller;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

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

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.RichiestaPrestito;
import eu.tasgroup.applicativo.dto.LoginRequest;
import eu.tasgroup.applicativo.dto.LoginResponse;
import eu.tasgroup.applicativo.security.AuthService;
import eu.tasgroup.applicativo.service.ClientiMongoService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.OperazioniBancarieMongoService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.PrestitoService;
import eu.tasgroup.applicativo.service.RichiestePrestitoService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import eu.tasgroup.applicativo.utility.DettTrans;
import eu.tasgroup.applicativo.utility.Statistiche;
import eu.tasgroup.applicativo.utility.StatisticheExtra;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestControllerAdmin {

	@Autowired
	private ClientiService clientiService;

	@Autowired
	ClientiMongoService clientiMongoService;

	@Autowired
	TransazioniMongoService transazioniMongoService;

	@Autowired
	OperazioniBancarieMongoService operazioniBancarieMongoService;

	@Autowired
	ContiService contiService;

	@Autowired
	PrestitoService prestitoService;

	@Autowired
	PagamentoService pagamentoService;

	@Autowired
	RichiestePrestitoService richiestePrestitoService;

	@Autowired
	private AuthService authService;

	/* ============== LOGIN ============= */
	@Operation(summary = "Login per token", description = "Permette di loggarsi per ottenere il token JWT per autenticarsi")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Autenticazione riuscita", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"token\": \"valore del token\"}"))),
			@ApiResponse(responseCode = "400", description = "Richiesta non valida"),
			@ApiResponse(responseCode = "401", description = "Credenziali non valide"), })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			LoginResponse response = authService.login(request);
			System.out.println("Risposta inviata: " + response);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body("Credenziali non valide");
		}
	}

	/* ============= CLIENTI ============= */
	@Operation(summary = "Lista di tutti i clienti", description = "Ritorna una lista di clienti")
	@GetMapping("/clienti")
	public ResponseEntity<List<Cliente>> getClienti() {
		return ResponseEntity.ok(clientiService.getClientiList());
	}

	@Operation(summary = "Cliente by Id", description = "Ritorna il cliente specifico con determinato id")
	@GetMapping("/clienti/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable long id) {

		Optional<Cliente> cliente = clientiService.findById(id);
		if (cliente.isEmpty())
			return ResponseEntity.status(404).body("Cliente non trovato");

		return ResponseEntity.ok(cliente.get());
	}

	@Operation(summary = "Create del cliente", description = "crea un cliente")
	@PostMapping("/clienti")
	public ResponseEntity<?> createClientes(@RequestBody Cliente clienteMySQL) {

		System.out.println("Cliente ricevuto: " + clienteMySQL);
		try {
			Cliente savedCliente = clientiService.createOrUpdate(clienteMySQL);
			ClienteMongo clienteMongo = new ClienteMongo();

			clienteMongo.setCodCliente(savedCliente.getCodCliente());
			clienteMongo.setNomeCliente(savedCliente.getNomeCliente());
			clienteMongo.setCognomeCliente(savedCliente.getCognomeCliente());
			clienteMongo.setEmailCliente(savedCliente.getEmailCliente());
			clienteMongo.setPasswordCliente(savedCliente.getPasswordCliente());
			clienteMongo.setTentativiErrati(savedCliente.getTentativiErrati());
			clienteMongo.setAccountBloccato(savedCliente.isAccountBloccato());
			clienteMongo.setSaldoConto(savedCliente.getSaldoConto());

			clientiMongoService.createOrUpdate(clienteMongo);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore durante il processo di creazione del cliente");
		}
	}

	/* ============== CONTI ============= */
	@Operation(summary = "Lista di tutti i conti", description = "Ritorna una lista contente tutti i conti")
	@GetMapping("/conti")
	public ResponseEntity<List<Conto>> getConti() {
		return ResponseEntity.ok(contiService.getAll());
	}

	@Operation(summary = "Conto by id", description = "Ritorna il conto specifico con determinato id")
	@GetMapping("/conti/{id}")
	public ResponseEntity<?> getContiById(@PathVariable long id) {

		Optional<Conto> contoOpt = contiService.findById(id);
		if (contoOpt.isEmpty())
			return ResponseEntity.status(404).body("Conto non trovato");

		return ResponseEntity.ok(contoOpt.get());
	}

	@Operation(summary = "Elimina Conto by id", description = "Elimina il conto specifico con determinato id")
	@DeleteMapping("/conti/{id}")
	public ResponseEntity<String> eliminaConto(@PathVariable long id) {
		try {
			contiService.deleteContoById(id);
			return ResponseEntity.ok("Conto eliminato correttamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore durante il processo di eliminazione del conto");
		}
	}

	/* =========== STATISTICHE =========== */

	@Operation(summary = "Statistiche", description = "Ottieni varie statistiche")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Autenticazione riuscita", content = @Content(mediaType = "application/json", schema = @Schema(example = "{"
					+ "\"numeroTotaleCliente\": 1," + "\"clienteSaldoMaggiore\": [" + "    {"
					+ "      \"codCliente\": 3," + "      \"nomeCliente\": \"Marco\","
					+ "      \"cognomeCliente\": \"Polo\"," + "      \"emailCliente\": \"marcoPolo@gmail.com\","
					+ "      \"passwordCliente\": \"$2a$10$b6LuAFzrdif76yUDY.PC7ux0zpUXQ.7BrYeVMgg6TzZnOzW/QhV9i\","
					+ "      \"tentativiErrati\": 0," + "      \"accountBloccato\": false,"
					+ "      \"saldoConto\": 3259.05" + "    }" + "  ],"
					+ "\"dataUltimaTransazione\": \"2024-12-18T11:14:43.360+00:00\","
					+ "\"numeroTotaleTransazioni\": 28," + "\"sommaTotaleTransazioni\": 12541.05,"
					+ "\"saldoMedioConti\": 651.81," + "\"contiPerCliente\": {" + "    \"0\": 3" + "  },"
					+ "\"cartePerCliente\": {" + "    \"0\": 1" + "  }," + "\"importoTotPrestitiPerCliente\": {"
					+ "    \"0\": 5000" + "  }," + "\"importoTotPagamentiPerCliente\": {" + "    \"1752\": 4000"
					+ "  }," + "\"numeroTransazioniPerTipo\": {" + "    \"ADDEBITO\": 16," + "    \"TRASFERIMENTO\": 0,"
					+ "    \"ACCREDITO\": 12" + "  }," + "\"mediaTransazioniPerCliente\": 28,"
					+ "\"totaleImportoTranszioniPerMese\": {" + "    \"Gennaio\": 0," + "    \"Febbraio\": 0,"
					+ "    \"Marzo\": 0," + "    \"Aprile\": 0," + "    \"Maggio\": 0," + "    \"Giugno\": 0,"
					+ "    \"Luglio\": 0," + "    \"Agosto\": 0," + "    \"Settembre\": 0," + "    \"Ottobre\": 0,"
					+ "    \"Novembre\": 0," + "    \"Dicembre\": 12541.05" + "  }" + "}"))) })

	@GetMapping("/statistiche")
	public ResponseEntity<?> getStatistiche() {
		Statistiche statistiche = new Statistiche();

		try {

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
			Map<TipoTransazione, Integer> mappaNumeroTransazioniPerTipo = Arrays.stream(TipoTransazione.values())
					.collect(Collectors.toMap(tipo -> tipo,
							tipo -> transazioniMongoService.numeroTransazioniPerTipo(tipo)));
			statistiche.setNumeroTransazioniPerTipo(mappaNumeroTransazioniPerTipo);

			// Numero medio di transazioni per cliente
			statistiche.setMediaTransazioniPerCliente(transazioniMongoService.numeroMedioTransazioniPerCliente());

			// Totale importo transazioni per mese
			Map<String, Double> totaleImportoTranszioniPerMese = new LinkedHashMap<>();
			for (int i = 1; i <= 12; i++) {
				String nomeMese = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ITALIAN);
				nomeMese = nomeMese.substring(0, 1).toUpperCase() + nomeMese.substring(1).toLowerCase();
				totaleImportoTranszioniPerMese.put(nomeMese, transazioniMongoService.totaleImportoPerMese(i));
			}
			statistiche.setTotaleImportoTranszioniPerMese(totaleImportoTranszioniPerMese);

		} catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore durante il processo di creazione delle statistiche");
		}

		return ResponseEntity.ok(statistiche);
	}

	@Operation(summary = "Statistiche extra", description = "Ottieni varie statistiche aggiuntive")
	@GetMapping("/statistiche-extra")
	public ResponseEntity<StatisticheExtra> statisticheExtra() {
		StatisticheExtra stat = new StatisticheExtra();
		try {
			// Stato delle Richieste di Prestito: Numero di richieste in attesa, approvate e
			// rifiutate.
			Map<StatoRichiestaPrestito, Integer> prestitiRichiestiPerStato = Arrays
					.stream(StatoRichiestaPrestito.values()).collect(
							Collectors.toMap(tipo -> tipo, tipo -> richiestePrestitoService.findByStatus(tipo).size()));
			stat.setPrestitiRichiestiPerStato(prestitiRichiestiPerStato);

			// Importo Totale dei Prestiti Approvati: Somma degli importi dei prestiti
			// approvati per ogni cliente.
			Map<Long, Double> sommaPrestitiApprovatiPerCliente = new HashMap<Long, Double>();
			for (Cliente c : clientiService.getClientiList()) {
				Double somma = 0.00;
				if (!c.getRichiestePrestiti().isEmpty()) {
					for (RichiestaPrestito p : c.getRichiestePrestiti()) {
						if (p.getStato().equals(StatoRichiestaPrestito.APPROVATO)) {
							somma += p.getImporto();
						}
					}
					sommaPrestitiApprovatiPerCliente.put(c.getCodCliente(), somma);
				}
			}
			stat.setSommaPrestitiApprovatiPerCliente(sommaPrestitiApprovatiPerCliente);

			// Rapporto Transazioni ACCREDITO/ADDEBITO: Percentuale di transazioni di tipo
			// ACCREDITO rispetto al totale.
			// conta anche sul totale (include i trasferimenti)
			double percentualeTransazioniAccredito;
			if (transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO) != null) {
				percentualeTransazioniAccredito = (100.00 / transazioniMongoService.findAll().size())
						* transazioniMongoService.numeroTransazioniPerTipo(TipoTransazione.ACCREDITO);
			} else {
				percentualeTransazioniAccredito = 0.00;
			}
			stat.setPercentualeTransazioniAccredito(percentualeTransazioniAccredito);
			Map<Long, DettTrans> dettagliTransazioniPerCliente = new HashMap<Long, DettTrans>();
			for (Cliente c : clientiService.getClientiList()) {
				if (!transazioniMongoService.findByCliente(c.getCodCliente()).isEmpty()) {
					dettagliTransazioniPerCliente.put(c.getCodCliente(),
							new DettTrans(transazioniMongoService.calcoloMediaTransazioniPerCliente(c.getCodCliente()),
									transazioniMongoService.findByCliente(c.getCodCliente()).size(),
									Arrays.stream(TipoTransazione.values()).collect(Collectors.toMap(tipo -> tipo,
											tipo -> tipo.equals(TipoTransazione.TRASFERIMENTO)
													? operazioniBancarieMongoService.findAll().size()
													: transazioniMongoService.numeroTransazioniPerTipo(tipo)))));
				}
			}
			stat.setDettagliTransazioniPerCliente(dettagliTransazioniPerCliente);

		} catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore durante il processo di creazione delle statistiche extra");
		}

		return ResponseEntity.ok(stat);
	}
}
