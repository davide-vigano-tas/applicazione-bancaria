package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoOperazione;
import jakarta.persistence.Id;

@Document(collection = "OperazioniBancarie")
public class OperazioniBancarieMongo implements Serializable {

	private static final long serialVersionUID = -7826371922445277625L;
	
	@Id
    private String _id;

    @Indexed(unique = true)
    private int codOperazione;
    
	private double importo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataOperazione;

	private TipoOperazione tipoOperazione;

	private long codContoOrigine;

	private long codContoDestinazione;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getCodOperazione() {
		return codOperazione;
	}

	public void setCodOperazione(int codOperazione) {
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

	public long getCodContoOrigine() {
		return codContoOrigine;
	}

	public void setCodContoOrigine(long codContoOrigine) {
		this.codContoOrigine = codContoOrigine;
	}

	public long getCodContoDestinazione() {
		return codContoDestinazione;
	}

	public void setCodContoDestinazione(long codContoDestinazione) {
		this.codContoDestinazione = codContoDestinazione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, codContoDestinazione, codContoOrigine, codOperazione, dataOperazione, importo,
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
		return Objects.equals(_id, other._id) && codContoDestinazione == other.codContoDestinazione
				&& codContoOrigine == other.codContoOrigine && codOperazione == other.codOperazione
				&& Objects.equals(dataOperazione, other.dataOperazione)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoOperazione == other.tipoOperazione;
	}

	@Override
	public String toString() {
		return "OperazioniBancarieMongo [_id=" + _id + ", codOperazione=" + codOperazione + ", importo=" + importo
				+ ", dataOperazione=" + dataOperazione + ", tipoOperazione=" + tipoOperazione + ", codContoOrigine="
				+ codContoOrigine + ", codContoDestinazione=" + codContoDestinazione + "]";
	}


}
