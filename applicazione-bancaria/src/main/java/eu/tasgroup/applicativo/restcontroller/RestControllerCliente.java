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
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.ClienteMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.service.ClientiMongoService;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.PrestitoService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
import eu.tasgroup.applicativo.utility.Statistiche;

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

}
