package eu.tasgroup.applicativo.utility;

import java.util.Map;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;

public class DettTrans {
	private double importoMedio;
	private int numeroTotale;
	private Map<TipoTransazione, Integer> tipi;

	public DettTrans(double importoMedio, int numeroTotale, Map<TipoTransazione, Integer> tipi) {
		this.importoMedio = importoMedio;
		this.numeroTotale = numeroTotale;
		this.tipi = tipi;
	}

	public double getImportoMedio() {
		return importoMedio;
	}

	public void setImportoMedio(double importoMedio) {
		this.importoMedio = importoMedio;
	}

	public int getNumeroTotale() {
		return numeroTotale;
	}

	public void setNumeroTotale(int numeroTotale) {
		this.numeroTotale = numeroTotale;
	}

	public Map<TipoTransazione, Integer> getTipi() {
		return tipi;
	}

	public void setTipi(Map<TipoTransazione, Integer> tipi) {
		this.tipi = tipi;
	}

	@Override
	public String toString() {
		return "DettTrans [importoMedio=" + importoMedio + ", numeroTotale=" + numeroTotale + ", tipi=" + tipi + "]";
	}

}
