package eu.tasgroup.applicativo.utility;

import java.util.Objects;

import eu.tasgroup.applicativo.businesscomponent.model.mysql.Carta;
import eu.tasgroup.applicativo.businesscomponent.model.mysql.Transazione;

public class TransazioneCarta {
	private Transazione transazione; 
	private Carta carta;
	
	public Transazione getTransazione() {
		return transazione;
	}
	public void setTransazione(Transazione transazione) {
		this.transazione = transazione;
	}
	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	@Override
	public int hashCode() {
		return Objects.hash(carta, transazione);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransazioneCarta other = (TransazioneCarta) obj;
		return Objects.equals(carta, other.carta) && Objects.equals(transazione, other.transazione);
	}
	@Override
	public String toString() {
		return "TransazioneCarta [transazione=" + transazione + ", carta=" + carta + "]";
	}
}
