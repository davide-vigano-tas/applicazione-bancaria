package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoTransazione;
import jakarta.persistence.Id;

@Document(collection = "Transazioni")
public class TransazioniMongo implements Serializable {

	private static final long serialVersionUID = 7732242633800279132L;

	@Id
	private String _id;

	@Indexed(unique = true)
	private long codTransazione;

	private double importo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataTransazione;

	private TipoTransazione tipoTransazione;

	private long cliente;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


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


	public long getCliente() {
		return cliente;
	}

	public void setCliente(long cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, cliente, codTransazione, dataTransazione, importo, tipoTransazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransazioniMongo other = (TransazioniMongo) obj;
		return Objects.equals(_id, other._id) && cliente == other.cliente && codTransazione == other.codTransazione
				&& Objects.equals(dataTransazione, other.dataTransazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoTransazione == other.tipoTransazione;
	}

	@Override
	public String toString() {
		return "TransazioniMongo [_id=" + _id + ", codTransazione=" + codTransazione + ", importo=" + importo
				+ ", dataTransazione=" + dataTransazione + ", tipoTransazione=" + tipoTransazione + ", cliente="
				+ cliente + "]";
	}

	
}
