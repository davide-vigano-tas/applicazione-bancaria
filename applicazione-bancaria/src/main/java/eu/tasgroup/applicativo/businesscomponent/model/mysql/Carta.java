package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carte_di_credito")
public class Carta implements Serializable {


	private static final long serialVersionUID = -7060237652529379132L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_carta")
	private long codCarta;
	
	@Column(name = "numero_carta", nullable = false)
	private String numeroCarta;

	@Column(nullable = false)
    @Schema(description = "saldo della carta", example = "100")
    private double saldo = 0.0;
	
	@Column(name = "data_scadenza", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataScadenza;
	
	@Column(name = "cvv", nullable = false)
	private String cvv;
	
	@ManyToOne
	@JoinColumn(name = "cod_cliente", nullable = false)
	private Cliente cliente;

	public long getCodCarta() {
		return codCarta;
	}

	public void setCodCarta(long codCarta) {
		this.codCarta = codCarta;
	}

	public String getNumeroCarta() {
		return numeroCarta;
	}

	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, codCarta, cvv, dataScadenza, numeroCarta, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carta other = (Carta) obj;
		return Objects.equals(cliente, other.cliente) && codCarta == other.codCarta && Objects.equals(cvv, other.cvv)
				&& Objects.equals(dataScadenza, other.dataScadenza) && Objects.equals(numeroCarta, other.numeroCarta)
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo);
	}

	@Override
	public String toString() {
		return "Carta [codCarta=" + codCarta + ", numeroCarta=" + numeroCarta + ", saldo=" + saldo + ", dataScadenza="
				+ dataScadenza + ", cvv=" + cvv + ", cliente=" + cliente + "]";
	}


	
}
