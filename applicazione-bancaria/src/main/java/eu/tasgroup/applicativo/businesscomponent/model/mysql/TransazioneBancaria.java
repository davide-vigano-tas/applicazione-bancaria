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
@Table(name = "transazione_bancaria")
public class TransazioneBancaria implements Serializable {

	private static final long serialVersionUID = -2264301147966168476L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_transazione_bancaria")
	private long codTransazioneBancaria; 
	
	@Column(name="importo", nullable = false)
	private double importo;
	
	@Column(name = "data_transazione", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dataTransazione;
	
	@Column(name="tipo_transazione",  nullable = false)
	private TipoTransazione tipoTransazione;
	
	@ManyToOne
	@JoinColumn(name = "cod_conto_origine", nullable = false)
	private Conto contoOrigine;
	
	@ManyToOne
	@JoinColumn(name = "cod_conto_destinazione", nullable = false)
	private Conto contoDestinazione;

	public long getCodTransazioneBancaria() {
		return codTransazioneBancaria;
	}

	public void setCodTransazioneBancaria(long codTransazioneBancaria) {
		this.codTransazioneBancaria = codTransazioneBancaria;
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

	public Conto getContoOrigine() {
		return contoOrigine;
	}

	public void setContoOrigine(Conto contoOrigine) {
		this.contoOrigine = contoOrigine;
	}

	public Conto getContoDestinazione() {
		return contoDestinazione;
	}

	public void setContoDestinazione(Conto contoDestinazione) {
		this.contoDestinazione = contoDestinazione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codTransazioneBancaria, contoDestinazione, contoOrigine, dataTransazione, importo,
				tipoTransazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransazioneBancaria other = (TransazioneBancaria) obj;
		return codTransazioneBancaria == other.codTransazioneBancaria
				&& Objects.equals(contoDestinazione, other.contoDestinazione)
				&& Objects.equals(contoOrigine, other.contoOrigine)
				&& Objects.equals(dataTransazione, other.dataTransazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoTransazione == other.tipoTransazione;
	}

	@Override
	public String toString() {
		return "TransazioneBancaria [codTransazioneBancaria=" + codTransazioneBancaria + ", importo=" + importo
				+ ", dataTransazione=" + dataTransazione + ", tipoTransazione=" + tipoTransazione + ", contoOrigine="
				+ contoOrigine + ", contoDestinazione=" + contoDestinazione + "]";
	}
}
