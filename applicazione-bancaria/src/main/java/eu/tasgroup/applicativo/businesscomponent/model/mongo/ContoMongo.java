package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.tasgroup.applicativo.businesscomponent.enumerated.TipoConto;
import jakarta.persistence.Id;

@Document
public class ContoMongo implements Serializable {

	private static final long serialVersionUID = -5777712843584436589L;

	@Id
	private ObjectId codConto;

	private TipoConto tipoConto;
	private double saldo;
	private ObjectId codCliente;

	public ObjectId getCodConto() {
		return codConto;
	}

	public void setCodConto(ObjectId codConto) {
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

	public ObjectId getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(ObjectId codCliente) {
		this.codCliente = codCliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codCliente, codConto, saldo, tipoConto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContoMongo other = (ContoMongo) obj;
		return Objects.equals(codCliente, other.codCliente) && Objects.equals(codConto, other.codConto)
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo)
				&& tipoConto == other.tipoConto;
	}

	@Override
	public String toString() {
		return "ContoMongo [codConto=" + codConto + ", tipoConto=" + tipoConto + ", saldo=" + saldo + ", codCliente="
				+ codCliente + "]";
	}

}
