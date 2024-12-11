package eu.tasgroup.applicativo.businesscomponent.model.mongo;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document
public class ClienteMongo implements Serializable {

	private static final long serialVersionUID = 2680924733583898825L;

	@Id
	private String codCliente;

	private String nomeCliente;

	private String cognomeCliente;

	private String emailCliente;

	private String passwordCliente;

	private int tentativiErrati = 0;

	private boolean accountBloccato = false;

	private double saldoConto = 0.0;

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCognomeCliente() {
		return cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getPasswordCliente() {
		return passwordCliente;
	}

	public void setPasswordCliente(String passwordCliente) {
		this.passwordCliente = passwordCliente;
	}

	public int getTentativiErrati() {
		return tentativiErrati;
	}

	public void setTentativiErrati(int tentativiErrati) {
		this.tentativiErrati = tentativiErrati;
	}

	public boolean isAccountBloccato() {
		return accountBloccato;
	}

	public void setAccountBloccato(boolean accountBloccato) {
		this.accountBloccato = accountBloccato;
	}

	public double getSaldoConto() {
		return saldoConto;
	}

	public void setSaldoConto(double saldoConto) {
		this.saldoConto = saldoConto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBloccato, codCliente, cognomeCliente, emailCliente, nomeCliente, passwordCliente,
				saldoConto, tentativiErrati);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteMongo other = (ClienteMongo) obj;
		return accountBloccato == other.accountBloccato && Objects.equals(codCliente, other.codCliente)
				&& Objects.equals(cognomeCliente, other.cognomeCliente)
				&& Objects.equals(emailCliente, other.emailCliente) && Objects.equals(nomeCliente, other.nomeCliente)
				&& Objects.equals(passwordCliente, other.passwordCliente)
				&& Double.doubleToLongBits(saldoConto) == Double.doubleToLongBits(other.saldoConto)
				&& tentativiErrati == other.tentativiErrati;
	}

	@Override
	public String toString() {
		return "ClienteMongo [codCliente=" + codCliente + ", nomeCliente=" + nomeCliente + ", cognomeCliente="
				+ cognomeCliente + ", emailCliente=" + emailCliente + ", passwordCliente=" + passwordCliente
				+ ", tentativiErrati=" + tentativiErrati + ", accountBloccato=" + accountBloccato + ", saldoConto="
				+ saldoConto + "]";
	}
}
