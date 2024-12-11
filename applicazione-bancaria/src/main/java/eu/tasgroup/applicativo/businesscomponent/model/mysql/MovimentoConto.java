package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoMovimento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "movimento_conto")
public class MovimentoConto implements Serializable {

	private static final long serialVersionUID = -3199518034543648598L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_movimento")
	private long codMovimento;

	@Column(name = "importo", nullable = false)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private double importo;

    @Column(name = "data_movimento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date dataMovimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimento", nullable = false)
	private TipoMovimento tipoMovimento;

	@ManyToOne
	@JoinColumn(name = "cod_contoe", nullable = false)
	private Conto conto;

	public long getCodMovimento() {
		return codMovimento;
	}

	public void setCodMovimento(long codMovimento) {
		this.codMovimento = codMovimento;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Conto getConto() {
		return conto;
	}

	public void setConto(Conto conto) {
		this.conto = conto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codMovimento, conto, dataMovimento, importo, tipoMovimento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentoConto other = (MovimentoConto) obj;
		return codMovimento == other.codMovimento && Objects.equals(conto, other.conto)
				&& Objects.equals(dataMovimento, other.dataMovimento)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipoMovimento == other.tipoMovimento;
	}

	@Override
	public String toString() {
		return "MovimentoConto [codMovimento=" + codMovimento + ", importo=" + importo + ", dataMovimento="
				+ dataMovimento + ", tipoMovimento=" + tipoMovimento + ", conto=" + conto + "]";
	}
}
