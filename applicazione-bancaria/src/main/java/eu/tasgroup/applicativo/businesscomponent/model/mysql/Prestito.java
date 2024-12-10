package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestiti")
public class Prestito implements Serializable {


	private static final long serialVersionUID = 8837782913642560317L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_prestito")
	private long codPrestito; 

	@Column(name="importo", nullable = false)
	private double importo;
	
	@Column(name = "tasso_interesse", nullable = false)
	private double tassoInteresse;
	
	@Column(name = "durata_mesi", nullable = false)
	private int durataMesi;
	
	@ManyToOne
	@JoinColumn(name="cod_cliente", nullable = false)
	private Cliente cliente;

	public long getCodPrestito() {
		return codPrestito;
	}

	public void setCodPrestito(long codPrestito) {
		this.codPrestito = codPrestito;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public double getTassoInteresse() {
		return tassoInteresse;
	}

	public void setTassoInteresse(double tassoInteresse) {
		this.tassoInteresse = tassoInteresse;
	}

	public int getDurataMesi() {
		return durataMesi;
	}

	public void setDurataMesi(int durataMesi) {
		this.durataMesi = durataMesi;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, codPrestito, durataMesi, importo, tassoInteresse);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestito other = (Prestito) obj;
		return Objects.equals(cliente, other.cliente) && codPrestito == other.codPrestito
				&& durataMesi == other.durataMesi
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& Double.doubleToLongBits(tassoInteresse) == Double.doubleToLongBits(other.tassoInteresse);
	}

	@Override
	public String toString() {
		return "Prestito [codPrestito=" + codPrestito + ", importo=" + importo + ", tassoInteresse=" + tassoInteresse
				+ ", durataMesi=" + durataMesi + ", cliente=" + cliente + "]";
	}
	
	
}
