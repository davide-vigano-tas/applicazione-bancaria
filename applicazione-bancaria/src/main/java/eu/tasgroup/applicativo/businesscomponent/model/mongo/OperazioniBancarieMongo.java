package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoOperazione;
import jakarta.persistence.Id;

@Document
public class OperazioniBancarieMongo implements Serializable {

	private static final long serialVersionUID = -7826371922445277625L;

	@Id
	private ObjectId codOperazione;

	private double importo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataOperazione;

	private TipoOperazione tipoOperazione;

	private ObjectId codContoOrigine;

	private ObjectId codContoDestinazione;

	public ObjectId getCodOperazione() {
		return codOperazione;
	}

	public void setCodOperazione(ObjectId codOperazione) {
		this.codOperazione = codOperazione;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Date getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}

	public TipoOperazione getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(TipoOperazione tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public ObjectId getCodContoOrigine() {
		return codContoOrigine;
	}

	public void setCodContoOrigine(ObjectId codContoOrigine) {
		this.codContoOrigine = codContoOrigine;
	}

	public ObjectId getCodContoDestinazione() {
		return codContoDestinazione;
	}

	public void setCodContoDestinazione(ObjectId codContoDestinazione) {
		this.codContoDestinazione = codContoDestinazione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codContoDestinazione, codContoOrigine, codOperazione, dataOperazione, importo,
				tipoOperazione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperazioniBancarieMongo other = (OperazioniBancarieMongo) obj;
		return Objects.equals(codContoDestinazione, other.codContoDestinazione)
				&& Objects.equals(codContoOrigine, other.codContoOrigine)
				&& Objects.equals(codOperazione, other.codOperazione)
				&& Objects.equals(dataOperazione, other.dataOperazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoOperazione == other.tipoOperazione;
	}

	@Override
	public String toString() {
		return "OperazioniBancarieMongo [codOperazione=" + codOperazione + ", importo=" + importo + ", dataOperazione="
				+ dataOperazione + ", tipoOperazione=" + tipoOperazione + ", codContoOrigine=" + codContoOrigine
				+ ", codContoDestinazione=" + codContoDestinazione + "]";
	}

}
