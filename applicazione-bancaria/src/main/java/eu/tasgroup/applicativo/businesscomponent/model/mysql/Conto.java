package eu.tasgroup.applicativo.businesscomponent.model.mysql;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
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
    
    @Column(name = "tipo_conto", nullable = false)
    private String tipoConto;
    
    @Column(nullable = false)
    private double saldo = 0.0;
    
    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private Cliente cliete;

	public long getCodConto() {
		return codConto;
	}

	public void setCodConto(long codConto) {
		this.codConto = codConto;
	}

	public String getTipoConto() {
		return tipoConto;
	}

	public void setTipoConto(String tipoConto) {
		this.tipoConto = tipoConto;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliete() {
		return cliete;
	}

	public void setCliete(Cliente cliete) {
		this.cliete = cliete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliete, codConto, saldo, tipoConto);
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
		return Objects.equals(cliete, other.cliete) && codConto == other.codConto
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo)
				&& Objects.equals(tipoConto, other.tipoConto);
	}

	@Override
	public String toString() {
		return "Conto [codConto=" + codConto + ", tipoConto=" + tipoConto + ", saldo=" + saldo + ", cliete=" + cliete
				+ "]";
	}
    
    

}
