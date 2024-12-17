package eu.tasgroup.applicativo.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMovimento;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoOperazione;
import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.OperazioniBancarieMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mongo.TransazioniMongo;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Conto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.MovimentoConto;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Pagamento;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.TransazioneBancaria;
import eu.tasgroup.applicativo.service.ClientiService;
import eu.tasgroup.applicativo.service.ContiService;
import eu.tasgroup.applicativo.service.MovimentoContoService;
import eu.tasgroup.applicativo.service.OperazioniBancarieMongoService;
import eu.tasgroup.applicativo.service.PagamentoService;
import eu.tasgroup.applicativo.service.TransazioneBancariaService;
import eu.tasgroup.applicativo.service.TransazioneService;
import eu.tasgroup.applicativo.service.TransazioniMongoService;
@Component
public class GestioneTransazioni {
	
	@Autowired
	ClientiService clientiService;
	
	@Autowired
	ContiService contiService;
	
	@Autowired
	TransazioneService transazioneService;
	
	@Autowired
	MovimentoContoService movimentoContoService;
	
	@Autowired
	TransazioniMongoService transazioniMongoService;
	
	@Autowired
	TransazioneBancariaService transazioneBancariaService;
	
	@Autowired
	OperazioniBancarieMongoService operazioniBancarieMongoService;
	
	@Autowired
	PagamentoService pagamentoService;
	
	
	
	public boolean prelievo(Transazione t, Cliente c) {
			if(t.getImporto() < 0) return false;
			if(t.getConto().getSaldo() < t.getImporto()) return false;
			TransazioniMongo tmongo = new TransazioniMongo();
			MovimentoConto mvc = new MovimentoConto();

			
			try {

				t.setDataTransazione(new Date());
				t.setTipoTransazione(TipoTransazione.ADDEBITO);
				t = transazioneService.createOrUpdate(t);
				
				tmongo.setCliente(c.getCodCliente());
				tmongo.setCodTransazione(t.getCodTransazione());
				tmongo.setDataTransazione(t.getDataTransazione());
				tmongo.setImporto(t.getImporto());
				tmongo.setTipoTransazione(t.getTipoTransazione());
				transazioniMongoService.createOrUpdate(tmongo);
				
				Conto conto = t.getConto();
				contiService.createOrUpdate(conto);
				
				conto = contiService.findById(conto.getCodConto()).get();
				
				mvc.setConto(conto);
				mvc.setDataMovimento(new Date());
				mvc.setImporto(t.getImporto());
				mvc.setTipoMovimento(TipoMovimento.ADDEBITO);
				
				mvc = movimentoContoService.createOrUpdate(mvc);
				
				conto.setSaldo(conto.getSaldo()-t.getImporto());
				c = clientiService.findById(c.getCodCliente()).get();
				c.setSaldoConto(c.getSaldoConto()-t.getImporto());
			
				conto = contiService.createOrUpdate(conto);
				c =clientiService.createOrUpdate(c);

				System.err.println(contiService.findById(conto.getCodConto()).get());
				System.err.println(clientiService.findById(c.getCodCliente()).get());
				
				return true;
				
			} catch (Exception e) {
				transazioneService.deleteTransazioneById(t.getCodTransazione());
				transazioniMongoService.deleteTransazioneMongo(tmongo);
				movimentoContoService.deleteMovimentoContoById(mvc.getCodMovimento());
				return false;
			}
			
		
	}
	
	
	public boolean deposito(Transazione t, Cliente c) {
			if(t.getImporto() < 0) return false;
			MovimentoConto mvc = new MovimentoConto();
			TransazioniMongo tmongo = new TransazioniMongo();
			try {

				t.setDataTransazione(new Date());
				t.setTipoTransazione(TipoTransazione.ACCREDITO);
				t = transazioneService.createOrUpdate(t);
				
				
				tmongo.setCliente(c.getCodCliente());
				tmongo.setCodTransazione(t.getCodTransazione());
				tmongo.setDataTransazione(t.getDataTransazione());
				tmongo.setImporto(t.getImporto());
				tmongo.setTipoTransazione(t.getTipoTransazione());
				transazioniMongoService.createOrUpdate(tmongo);
				
				Conto conto = t.getConto();
				contiService.createOrUpdate(conto);
				
				conto = contiService.findById(conto.getCodConto()).get();
				
				mvc.setConto(conto);
				mvc.setDataMovimento(new Date());
				mvc.setImporto(t.getImporto());
				mvc.setTipoMovimento(TipoMovimento.ACCREDITO);
				
				mvc = movimentoContoService.createOrUpdate(mvc);
				
				conto.setSaldo(conto.getSaldo()+t.getImporto());
				c = clientiService.findById(c.getCodCliente()).get();
				c.setSaldoConto(c.getSaldoConto()+t.getImporto());
				
				
				conto = contiService.createOrUpdate(conto);
				c = clientiService.createOrUpdate(c);

				System.err.println(contiService.findById(conto.getCodConto()).get());
				System.err.println(clientiService.findById(c.getCodCliente()).get());
				
				return true;
				
			} catch (Exception e) {
				transazioneService.deleteTransazioneById(t.getCodTransazione());
				transazioniMongoService.deleteTransazioneMongo(tmongo);
				movimentoContoService.deleteMovimentoContoById(mvc.getCodMovimento());
				return false;
			}
			
		
	}
	
	public boolean transazioneBancaria(TransazioneBancaria tb, Pagamento pagamento) {
		if(tb.getImporto() < 0) return false;
		if(tb.getImporto() > tb.getContoOrigine().getSaldo()) return false;
		Transazione fromOrigin = new Transazione();
		Transazione toDest = new Transazione();
		OperazioniBancarieMongo opmongo = new OperazioniBancarieMongo();
	
		Cliente or = tb.getContoOrigine().getCliente();
		Cliente dest = tb.getContoDestinazione().getCliente();
		
		try {
			
			
			tb.setDataTransazione(new Date());
			tb.setTipoTransazione(TipoTransazione.TRASFERIMENTO);
			
			fromOrigin.setConto(tb.getContoOrigine());
			fromOrigin.setImporto(tb.getImporto());
			
			toDest.setConto(tb.getContoDestinazione());
			toDest.setImporto(tb.getImporto());
			
			if(!prelievo(fromOrigin, or)) return false;
			if(!deposito(toDest, dest)) return false;
			
			
		
			
			tb = transazioneBancariaService.createOrUpdate(tb);
			opmongo.setCodContoDestinazione(tb.getContoDestinazione().getCodConto());
			opmongo.setCodContoOrigine(tb.getContoOrigine().getCodConto());
			opmongo.setDataOperazione(tb.getDataTransazione());
			opmongo.setImporto(tb.getImporto());
			opmongo.setTipoOperazione(TipoOperazione.TRASFERIMENTO);
			opmongo.setCodOperazione(tb.getCodTransazioneBancaria());
			System.err.println(opmongo);
			opmongo = operazioniBancarieMongoService.createOrUpdate(opmongo);
			
			or = clientiService.findById(or.getCodCliente()).get();
			pagamento.setCliente(or);
			pagamento.setDataPagamento(new Date());
			pagamento.setImporto(tb.getImporto());
			
			pagamento = pagamentoService.createOrUpdate(pagamento);

			System.err.println(or);
			
			return true;
			
		} catch (Exception e) {
			transazioneBancariaService.deleteTransazioneBancariaById(tb.getCodTransazioneBancaria());
			operazioniBancarieMongoService.delete(opmongo);
			transazioneService.deleteTransazioneById(fromOrigin.getCodTransazione());
			transazioneService.deleteTransazioneById(toDest.getCodTransazione());
			pagamentoService.deletePagamentoById(pagamento.getCodPagamento());
			return false;
		}
		
	
	}
}
