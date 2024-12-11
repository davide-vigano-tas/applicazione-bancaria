package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transazioni")
public class Transazione implements Serializable {

	
	private static final long serialVersionUID = 8102685195857549955L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_transazione")
	private long codTransazione; 
	
	@Column(name = "importo",  nullable = false)
	private double importo;
	
	@Column(name = "data_transazione",  nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataTransazione;
	
	@Column(name="tipo_transazione",  nullable = false)
	private TipoTransazione tipoTransazione;
	
	@ManyToOne
	@JoinColumn(name = "cod_conto", nullable = false)
	private Conto conto;

	public long getCodTransazione() {
		return codTransazione;
	}

	public void setCodTransazione(long codTransazione) {
		this.codTransazione = codTransazione;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Date getDataTransazione() {
		return dataTransazione;
	}

	public void setDataTransazione(Date dataTransazione) {
		this.dataTransazione = dataTransazione;
	}

	public TipoTransazione getTipoTransazione() {
		return tipoTransazione;
	}

	public void setTipoTransazione(TipoTransazione tipoTransazione) {
		this.tipoTransazione = tipoTransazione;
	}

	public Conto getConto() {
		return conto;
	}

	public void setConto(Conto conto) {
		this.conto = conto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codTransazione, conto, dataTransazione, importo, tipoTransazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transazione other = (Transazione) obj;
		return codTransazione == other.codTransazione && Objects.equals(conto, other.conto)
				&& Objects.equals(dataTransazione, other.dataTransazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoTransazione == other.tipoTransazione;
	}

	@Override
	public String toString() {
		return "Transazione [codTransazione=" + codTransazione + ", importo=" + importo + ", dataTransazione="
				+ dataTransazione + ", tipoTransazione=" + tipoTransazione + ", conto=" + conto + "]";
	}
	
	

}
