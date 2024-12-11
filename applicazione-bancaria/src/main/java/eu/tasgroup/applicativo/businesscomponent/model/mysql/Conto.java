package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Objects;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Conto implements Serializable {

	private static final long serialVersionUID = -5781053561417393743L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_conto")
    private long codConto;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conto", nullable = false)
    private TipoConto tipoConto;
    
    @Column(nullable = false)
    private double saldo = 0.0;
    
    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private Cliente cliente;

	public long getCodConto() {
		return codConto;
	}

	public void setCodConto(long codConto) {
		this.codConto = codConto;
	}

	public TipoConto getTipoConto() {
		return tipoConto;
	}

	public void setTipoConto(TipoConto tipoConto) {
		this.tipoConto = tipoConto;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getClinete() {
		return cliente;
	}

	public void setClinete(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, codConto, saldo, tipoConto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conto other = (Conto) obj;
		return Objects.equals(cliente, other.cliente) && codConto == other.codConto
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo)
				&& tipoConto == other.tipoConto;
	}

	@Override
	public String toString() {
		return "Conto [codConto=" + codConto + ", tipoConto=" + tipoConto + ", saldo=" + saldo + ", cliete=" + cliente
				+ "]";
	}

}
