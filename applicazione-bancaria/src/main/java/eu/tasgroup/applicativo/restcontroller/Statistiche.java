package eu.tasgroup.applicativo.restcontroller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Cliente;

public class Statistiche {
	private int numeroTotaleCliente;
	private List<Cliente> clienteSaldoMaggiore;
	private Date dataUltimaTransazione; //Mongo
	
	//Totale transazioni (numero e somma importi)
	private int numeroTotaleTransazioni; //mongo
	private double sommaTotaleTransazioni; //mongo
	
	private Double saldoMedioConti;
	
	private Map<Long, Integer> contiPerCliente; 
	private Map<Long, Integer> cartePerCliente; 
	
	private Map<Long, Double> importoTotPrestitiPerCliente;
	private Map<Long, Double> importoTotPagamentiPerCliente;
	
	private Map<TipoTransazione, Integer> numeroTransazioniPerTipo; //mongo 
	
	private double mediaTransazioniPerCliente; //mongo
	
	private Map<String, Double> totaleImportoTranszioniPerMese; //mongo
	
	
	public int getNumeroTotaleCliente() {
		return numeroTotaleCliente;
	}
	public void setNumeroTotaleCliente(int numeroTotaleCliente) {
		this.numeroTotaleCliente = numeroTotaleCliente;
	}
	public List<Cliente> getClienteSaldoMaggiore() {
		return clienteSaldoMaggiore;
	}
	public void setClienteSaldoMaggiore(List<Cliente> clienteSaldoMaggiore) {
		this.clienteSaldoMaggiore = clienteSaldoMaggiore;
	}
	public Date getDataUltimaTransazione() {
		return dataUltimaTransazione;
	}
	public void setDataUltimaTransazione(Date ultimaTransazione) {
		this.dataUltimaTransazione = ultimaTransazione;
	}
	public double getSommaTotaleTransazioni() {
		return sommaTotaleTransazioni;
	}
	public void setSommaTotaleTransazioni(double totaleTransazioni) {
		this.sommaTotaleTransazioni = totaleTransazioni;
	}
	public Double getSaldoMedioConti() {
		return saldoMedioConti;
	}
	public void setSaldoMedioConti(Double saldoMedio) {
		this.saldoMedioConti = saldoMedio;
	}
	public int getNumeroTotaleTransazioni() {
		return numeroTotaleTransazioni;
	}
	public void setNumeroTotaleTransazioni(int numeroTotaleTransazioni) {
		this.numeroTotaleTransazioni = numeroTotaleTransazioni;
	}
	public Map<Long, Integer> getContiPerCliente() {
		return contiPerCliente;
	}
	public void setContiPerCliente(Map<Long, Integer> contiPerCliente) {
		this.contiPerCliente = contiPerCliente;
	}
	public Map<Long, Integer> getCartePerCliente() {
		return cartePerCliente;
	}
	public void setCartePerCliente(Map<Long, Integer> cartePerCliente) {
		this.cartePerCliente = cartePerCliente;
	}
	public Map<Long, Double> getImportoTotPrestitiPerCliente() {
		return importoTotPrestitiPerCliente;
	}
	public void setImportoTotPrestitiPerCliente(Map<Long, Double> importoTotPrestitiPerCliente) {
		this.importoTotPrestitiPerCliente = importoTotPrestitiPerCliente;
	}
	public Map<Long, Double> getImportoTotPagamentiPerCliente() {
		return importoTotPagamentiPerCliente;
	}
	public void setImportoTotPagamentiPerCliente(Map<Long, Double> importoTotPagamentiPerCliente) {
		this.importoTotPagamentiPerCliente = importoTotPagamentiPerCliente;
	}
	public Map<TipoTransazione, Integer> getNumeroTransazioniPerTipo() {
		return numeroTransazioniPerTipo;
	}
	public void setNumeroTransazioniPerTipo(Map<TipoTransazione, Integer> numeroTransazioniPerTipo) {
		this.numeroTransazioniPerTipo = numeroTransazioniPerTipo;
	}
	public double getMediaTransazioniPerCliente() {
		return mediaTransazioniPerCliente;
	}
	public void setMediaTransazioniPerCliente(double mediaTransazioniPerCliente) {
		this.mediaTransazioniPerCliente = mediaTransazioniPerCliente;
	}
	public Map<String, Double> getTotaleImportoTranszioniPerMese() {
		return totaleImportoTranszioniPerMese;
	}
	public void setTotaleImportoTranszioniPerMese(Map<String, Double> totaleImportoTranszioniPerMese) {
		this.totaleImportoTranszioniPerMese = totaleImportoTranszioniPerMese;
	}
	
	
	
}
