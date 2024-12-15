package eu.tasgroup.applicativo.utility;

import java.util.Map;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;

public class StatisticheExtra {
	
	private Map<StatoRichiestaPrestito, Integer> prestitiRichiestiPerStato;
	private Map<Long,Double> sommaPrestitiApprovatiPerCliente;
	private double percentualeTransazioniAccredito;
	private Map<Long, DettTrans> dettagliTransazioniPerCliente;
	
	public Map<StatoRichiestaPrestito, Integer> getPrestitiRichiestiPerStato() {
		return prestitiRichiestiPerStato;
	}
	public void setPrestitiRichiestiPerStato(Map<StatoRichiestaPrestito, Integer> prestitiRichiestiPerStato) {
		this.prestitiRichiestiPerStato = prestitiRichiestiPerStato;
	}
	public Map<Long, Double> getSommaPrestitiApprovatiPerCliente() {
		return sommaPrestitiApprovatiPerCliente;
	}
	public void setSommaPrestitiApprovatiPerCliente(Map<Long, Double> sommaPrestitiApprovatiPerCliente) {
		this.sommaPrestitiApprovatiPerCliente = sommaPrestitiApprovatiPerCliente;
	}
	public double getPercentualeTransazioniAccredito() {
		return percentualeTransazioniAccredito;
	}
	public void setPercentualeTransazioniAccredito(double percentualeTransazioniAccredito) {
		this.percentualeTransazioniAccredito = percentualeTransazioniAccredito;
	}
	public Map<Long, DettTrans> getDettagliTransazioniPerCliente() {
		return dettagliTransazioniPerCliente;
	}
	public void setDettagliTransazioniPerCliente(Map<Long, DettTrans> dettagliTransazioniPerCliente) {
		this.dettagliTransazioniPerCliente = dettagliTransazioniPerCliente;
	}
	
}
