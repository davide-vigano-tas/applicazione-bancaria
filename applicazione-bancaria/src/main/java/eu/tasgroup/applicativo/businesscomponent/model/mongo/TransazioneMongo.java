package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import jakarta.persistence.Id;

@Document
public class TransazioneMongo implements Serializable {

	private static final long serialVersionUID = 7732242633800279132L;

	@Id
	private String codTransazione;

	private double importo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataTransazione;

	private TipoTransazione tipoTransazione;

	private String conto;

	public String getCodTransazione() {
		return codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
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

	public String getConto() {
		return conto;
	}

	public void setConto(String conto) {
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
		TransazioneMongo other = (TransazioneMongo) obj;
		return Objects.equals(codTransazione, other.codTransazione) && Objects.equals(conto, other.conto)
				&& Objects.equals(dataTransazione, other.dataTransazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoTransazione == other.tipoTransazione;
	}

	@Override
	public String toString() {
		return "TransazioneMongo [codTransazione=" + codTransazione + ", importo=" + importo + ", dataTransazione="
				+ dataTransazione + ", tipoTransazione=" + tipoTransazione + ", conto=" + conto + "]";
	}

}
