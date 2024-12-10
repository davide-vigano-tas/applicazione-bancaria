package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.StatoRichiestaPrestito;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "richieste_prestito")
public class RichiestaPrestito implements Serializable {

	private static final long serialVersionUID = -1006436887827958259L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_richiesta")
	private long codRichiesta; 
	
	@Column(name="importo", nullable = false)
	private double importo;
	
	@Column(name = "data_richiesta", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataRichiesta;
	
	@Column(name = "stato")
	private StatoRichiestaPrestito stato;
	
	@ManyToOne
	@JoinColumn(name = "cod_cliente", nullable = false)
	private Cliente cliente;
	
	

	public long getCodRichiesta() {
		return codRichiesta;
	}

	public void setCodRichiesta(long codRichiesta) {
		this.codRichiesta = codRichiesta;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public StatoRichiestaPrestito getStato() {
		return stato;
	}

	public void setStato(StatoRichiestaPrestito stato) {
		this.stato = stato;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, codRichiesta, dataRichiesta, importo, stato);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RichiestaPrestito other = (RichiestaPrestito) obj;
		return Objects.equals(cliente, other.cliente) && codRichiesta == other.codRichiesta
				&& Objects.equals(dataRichiesta, other.dataRichiesta)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo) && stato == other.stato;
	}

	@Override
	public String toString() {
		return "RichiestaPrestito [codRichiesta=" + codRichiesta + ", importo=" + importo + ", dataRichiesta="
				+ dataRichiesta + ", stato=" + stato + ", cliente=" + cliente + "]";
	}
	
	

}
